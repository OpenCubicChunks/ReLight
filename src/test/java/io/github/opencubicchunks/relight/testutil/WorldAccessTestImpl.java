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

import io.github.opencubicchunks.relight.heightmap.HeightMap;
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

/**
 * A trivial test implementation of WorldAccess
 */
public class WorldAccessTestImpl implements WorldAccess, LightDataWriter, LightDataReader {

    // this test world access has no real concept of chunks, so simulatye them being loaded
    private final Set<ChunkPos> loadedChunks = new HashSet<>();

    private final Map<ColumnPos, Integer> heightmap = new HashMap<>();
    private final Map<ChunkPos, Integer> blocklightSrc = new HashMap<>();

    private final Map<ChunkPos, Integer> skylight = new HashMap<>();
    private final Map<ChunkPos, Integer> blocklight = new HashMap<>();

    @Override public LightDataReader getLightChunk(ChunkPos minPos, ChunkPos maxPos) {
        return null;
    }

    @Override public LightDataWriter getWriterFor(ChunkPos minPos, ChunkPos maxPos) {
        return null;
    }

    @Override public HeightMap getHeightMap(ColumnPos pos) {
        return null;
    }

    @Override public boolean isChunkLoaded(int chunkX, int chunkY, int chunkZ) {
        return loadedChunks.contains(new ChunkPos(chunkX, chunkY, chunkZ));
    }

    @Override public LightChunk getLightChunk(ChunkPos pos) {
        return null;
    }

    @Override public List<LightChunk> chunksBetween(int start, int end) {
        return null;
    }

    @Override public int getLight(int x, int y, int z, LightType type) {
        /*if (!isChunkLoaded(layout.blockToChunkX(x), layout.blockToChunkY(y), layout.blockToChunkZ(z))) {
            throw new RuntimeException("Chunk for block " + new ChunkPos(x, y, z) + " not loaded!");
        }*/
        switch(type) {
            case SKY:
                return skylight.getOrDefault(new ChunkPos(x, y, z), type.defaultValue());
            case BLOCK:
                return blocklight.getOrDefault(new ChunkPos(x, y, z), type.defaultValue());
            default:
                throw new Error("Unknown light type: " + type);
        }
    }

    @Override public int getLightSource(int x, int y, int z, LightType type) {
        /*if (!isChunkLoaded(layout.blockToChunkX(x), layout.blockToChunkY(y), layout.blockToChunkZ(z))) {
            throw new RuntimeException("Chunk for block " + new ChunkPos(x, y, z) + " not loaded!");
        }*/
        switch(type) {
            case SKY:
                return y > heightmap.getOrDefault(new ColumnPos(x, z), Integer.MIN_VALUE) ? 0 : 1;
            case BLOCK:
                return blocklightSrc.getOrDefault(new ChunkPos(x, y, z), 0);
            default:
                throw new Error("Unknown light type: " + type);
        }
    }

    @Override public void setLight(int x, int y, int z, int value, LightType type) {
       /* if (!isChunkLoaded(layout.blockToChunkX(x), layout.blockToChunkY(y), layout.blockToChunkZ(z))) {
            throw new RuntimeException("Chunk for block " + new ChunkPos(x, y, z) + " not loaded!");
        }*/
        switch(type) {
            case SKY:
                skylight.put(new ChunkPos(x, y, z), value);
                break;
            case BLOCK:
                blocklight.put(new ChunkPos(x, y, z), value);
                break;
            default:
                throw new Error("Unknown light type: " + type);
        }
    }

    public void setHeight(int x, int z, int y) {
        heightmap.put(new ColumnPos(x, z), y);
    }

    public void setBlocklight(int x, int y, int z, int value) {
        blocklightSrc.put(new ChunkPos(x, y, z), value);
    }

    public void setLoaded(int chunkX, int chunkY, int chunkZ, boolean loaded) {
        if (loaded) {
            loadedChunks.add(new ChunkPos(chunkX, chunkY, chunkZ));
        } else {
            loadedChunks.remove(new ChunkPos(chunkX, chunkY, chunkZ));
        }
    }
}
