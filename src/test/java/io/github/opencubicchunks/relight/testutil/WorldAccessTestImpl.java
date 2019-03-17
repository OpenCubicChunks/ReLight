/*
 *  This file is part of ReLight, licensed under the MIT License (MIT).
 *
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package io.github.opencubicchunks.relight.testutil;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Stream.concat;
import static org.junit.Assert.assertEquals;

import io.github.opencubicchunks.relight.heightmap.ColumnHeights;
import io.github.opencubicchunks.relight.heightmap.HeightMap;
import io.github.opencubicchunks.relight.util.BlockPos;
import io.github.opencubicchunks.relight.util.ChunkPos;
import io.github.opencubicchunks.relight.util.ColumnPos;
import io.github.opencubicchunks.relight.util.LightType;
import io.github.opencubicchunks.relight.world.LightChunk;
import io.github.opencubicchunks.relight.world.LightDataReader;
import io.github.opencubicchunks.relight.world.LightDataWriter;
import io.github.opencubicchunks.relight.world.WorldAccess;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A trivial test implementation of WorldAccess
 */
public class WorldAccessTestImpl implements WorldAccess, LightDataWriter, LightDataReader {

    // this test world access has no real concept of chunks, so simulatye them being loaded
    private final Set<ChunkPos> loadedChunks = new HashSet<>();
    private final Set<ColumnPos> loadedColumns;
    private final Set<ChunkPos> newChunks;


    private final Map<BlockPos, Integer> heightmap;
    private final Map<BlockPos, Integer> blocklightSrc;

    private final Map<BlockPos, Integer> skylight = new HashMap<>();
    private final Map<BlockPos, Integer> blocklight;

    private final Set<BlockPos> opaqueBlocks = new HashSet<>();
    private final Map<ColumnPos, ColumnHeights> heightmaps;

    public WorldAccessTestImpl(Set<ChunkPos> preLoadedChunks, Set<ChunkPos> newChunks,
        Set<BlockPos> oldOpaqueBlocks, Set<BlockPos> newOpaqueBlocks,
        Set<BlockPos> oldBlockLightSources, Set<BlockPos> newBlockLightSources) {

        this.loadedChunks.addAll(preLoadedChunks);
        this.loadedChunks.addAll(newChunks);
        this.newChunks = newChunks;

        this.heightmaps =
            this.loadedChunks.stream().collect(groupingBy(ChunkPos::toColumn, collectingAndThen(counting(), cnt -> new ColumnHeights())));

        this.loadedColumns = this.loadedChunks.stream().map(ChunkPos::toColumn).collect(Collectors.toSet());

        this.heightmap = oldOpaqueBlocks.stream().collect(
            groupingBy(p -> new BlockPos(p.getX(), 0, p.getZ()),
                collectingAndThen(maxBy(comparingInt(BlockPos::getY)), p -> p.map(BlockPos::getY).get())
            )
        );
        this.heightmap.forEach((pos, height) -> heightmaps.get(pos.columnPos()).setHeight(pos.localX(), pos.localZ(), height));

        this.blocklightSrc = concat(oldBlockLightSources.stream(), newBlockLightSources.stream()).collect(toMap(p -> p, p -> 15));

        this.opaqueBlocks.addAll(oldOpaqueBlocks);
        this.opaqueBlocks.addAll(newOpaqueBlocks);

        // initialize correct blocklight
        this.blocklight = oldBlockLightSources.stream().collect(toMap(p -> p, p -> 15));

        // initialize correct skylight
        this.heightmap.forEach((pos, height) -> {
            this.loadedChunks.stream().filter(p -> p.toColumn().equals(pos.columnPos())).forEach(cube -> {
                for (int dy = 0; dy < 16; dy++) {
                    int y = (cube.getY() << 4) + dy;
                    setLight(pos.getX(), y, pos.getZ(), y > height ? 15 : 0, LightType.SKY);
                }
            });
        });
    }

    public void verifyLight() {
        newChunks.forEach(chunk -> chunk.forAllBlocks(this::verifyPosLight));
    }

    private void verifyPosLight(BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        assertEquals("Blocklight at " + pos, getLightSource(x, y, z, LightType.BLOCK), getLight(x, y, z, LightType.BLOCK));
        assertEquals("Skylight at " + pos, getLightSource(x, y, z, LightType.SKY), getLight(x, y, z, LightType.SKY));
    }

    private void verifyChunkLoaded(int x, int y, int z) {
        if (!isChunkLoaded(x >> 4, y >> 4, z >> 4)) {
            throw new IllegalArgumentException("Chunk for block " + x + ", " + y + ", " + z + " is not loaded!");
        }
    }

    private void verifyColumnLoaded(int x, int z) {
        if (!this.loadedColumns.contains(new ColumnPos(x >> 4, z >> 4))) {
            throw new IllegalArgumentException("Column for block " + x + ", " + z + " is not loaded!");
        }
    }

    @Override public int getLight(int x, int y, int z, LightType type) {
        verifyChunkLoaded(x, y, z);
        return (type == LightType.SKY ? this.skylight : this.blocklight).getOrDefault(new BlockPos(x, y, z), 0);
    }

    @Override public int getLightSource(int x, int y, int z, LightType type) {
        if (type == LightType.BLOCK) {
            verifyChunkLoaded(x, y, z);
            return this.blocklightSrc.getOrDefault(new BlockPos(x, y, z), 0);
        } else {
            verifyColumnLoaded(x, z);
            return y > this.heightmap.getOrDefault(new BlockPos(x, 0, z), Integer.MIN_VALUE) ? 15 : 0;
        }
    }

    @Override public void setLight(int x, int y, int z, int value, LightType type) {
        verifyChunkLoaded(x, y, z);
        (type == LightType.SKY ? this.skylight : this.blocklight).put(new BlockPos(x, y, z), value);
    }

    @Override public LightDataReader getLightChunk(ChunkPos minPos, ChunkPos maxPos) {
        return this;
    }

    @Override public LightDataWriter getWriterFor(ChunkPos minPos, ChunkPos maxPos) {
        return this;
    }

    @Override public HeightMap getHeightMap(ColumnPos pos) {
        if (!loadedColumns.contains(pos)) {
            throw new IllegalArgumentException("Column at " + pos + " is not loaded!");
        }
        return heightmaps.get(pos);
    }

    @Override public boolean isChunkLoaded(int chunkX, int chunkY, int chunkZ) {
        return loadedChunks.contains(new ChunkPos(chunkX, chunkY, chunkZ));
    }

    @Override public LightChunk getLightChunk(ChunkPos pos) {
        if (!isChunkLoaded(pos)) {
            throw new IllegalArgumentException("Chunk at " + pos + " is not loaded!");
        }
        return new LightChunkTestImpl(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override public List<LightChunk> chunksBetween(ColumnPos pos, int start, int end) {
        return loadedChunks.stream().filter(p -> p.toColumn().equals(pos)).filter(this::isChunkLoaded).map(this::getLightChunk).collect(toList());
    }

    private class LightChunkTestImpl implements LightChunk {

        private final int xOrigin, yOrigin, zOrigin;

        public LightChunkTestImpl(int x, int y, int z) {
            this.xOrigin = x << 4;
            this.yOrigin = y << 4;
            this.zOrigin = z << 4;
        }

        private int worldX(int x) {
            return (x & 0xF) + xOrigin;
        }

        private int worldY(int y) {
            return (y & 0xF) + yOrigin;
        }

        private int worldZ(int z) {
            return (z & 0xF) + zOrigin;
        }

        @Override public int getLight(int x, int y, int z, LightType type) {
            return WorldAccessTestImpl.this.getLight(worldX(x), worldY(y), worldZ(z), type);
        }

        @Override public int getLightSource(int x, int y, int z, LightType type) {
            return WorldAccessTestImpl.this.getLightSource(worldX(x), worldY(y), worldZ(z), type);
        }

        @Override public int getOpacityBetween(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
            return getOpacity(toX, toY, toZ);
        }

        @Override public int getOpacity(int x, int y, int z) {
            return opaqueBlocks.contains(new BlockPos(worldX(x), worldY(y), worldZ(z))) ? 15 : 0;
        }

        @Override public int getX() {
            return xOrigin >> 4;
        }

        @Override public int getY() {
            return yOrigin >> 4;
        }

        @Override public int getZ() {
            return zOrigin >> 4;
        }
    }
}
