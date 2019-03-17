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
package io.github.opencubicchunks.relight.handler;

import static io.github.opencubicchunks.relight.util.MathUtil.rangeIntersectMax;
import static io.github.opencubicchunks.relight.util.MathUtil.rangeIntersectMin;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import io.github.opencubicchunks.relight.heightmap.ColumnHeights;
import io.github.opencubicchunks.relight.heightmap.HeightMap;
import io.github.opencubicchunks.relight.util.ChunkPos;
import io.github.opencubicchunks.relight.util.ColumnPos;
import io.github.opencubicchunks.relight.util.Vec3List;
import io.github.opencubicchunks.relight.world.LightChunk;
import io.github.opencubicchunks.relight.world.WorldAccess;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirstLightHandler {

    private final WorldAccess dataAccess;

    public FirstLightHandler(WorldAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    /**
     * Calculates list of block positions to update. Does not modify the heightmap.
     */
    public void apply(Collection<ChunkPos> chunks, Vec3List output) {
        chunks.forEach(chunkPos -> {
            addAllThisChunk(chunkPos, output);
        });
        Map<ColumnPos, List<ChunkPos>> byColumn = chunks.stream().collect(groupingBy(ChunkPos::toColumn, toList()));
        byColumn.values().forEach(list -> list.sort((a, b) -> b.getY() - a.getY())); // sort highest to lowest

        Map<ColumnPos, ColumnHeights> columnHeights = new HashMap<>();
        byColumn.forEach((pos, cubes) -> columnHeights.put(pos, getHeights(cubes)));

        addHeightDiff(output, columnHeights);
    }

    private void addHeightDiff(Vec3List output, Map<ColumnPos, ColumnHeights> columnHeights) {
        for (Map.Entry<ColumnPos, ColumnHeights> entry : columnHeights.entrySet()) {
            ColumnPos pos = entry.getKey();
            ColumnHeights heights = entry.getValue();
            HeightMap existingHeightMap = this.dataAccess.getHeightMap(pos);

            for (int dx = 0; dx < 16; dx++) {
                for (int dz = 0; dz < 16; dz++) {
                    if (!heights.exists(dx, dz)) {
                        continue;
                    }
                    int minY = existingHeightMap.getTopY(dx, dz);
                    int maxY = heights.getTopY(dx, dz);
                    if (minY > maxY) {
                        continue;
                    }
                    assert minY != maxY : "Using FirstLightHandler on cube that already has surface tracked!";

                    int blockX = pos.blockX(dx);
                    int blockZ = pos.blockZ(dz);
                    dataAccess.chunksBetween(minY >> 4, maxY >> 4).forEach(chunk -> {
                        int chunkY = chunk.getY();
                        int chunkMinY = chunkY << 4;
                        int chunkMaxY = chunkMinY + 15;
                        int minBlockY = rangeIntersectMin(minY, chunkMinY);
                        int maxBlockY = rangeIntersectMax(maxY, chunkMaxY);
                        for (int y = minBlockY; y <= maxBlockY; y++) {
                            output.add(blockX, y, blockZ);
                        }
                    });

                }
            }
        }
    }

    private ColumnHeights getHeights(List<ChunkPos> cubes) {

        ColumnHeights heights = new ColumnHeights();

        for (ChunkPos pos : cubes) {
            LightChunk reader = this.dataAccess.getLightChunk(pos);
            for (int dx = 0; dx < 16; dx++) {
                for (int dz = 0; dz < 16; dz++) {
                    if (heights.exists(dx, dz)) {
                        continue;
                    }
                    int blockX = pos.blockX(dx);
                    int blockZ = pos.blockZ(dz);
                    for (int dy = 15; dy >= 0; dy--) {
                        int blockY = pos.blockY(dy);
                        if (reader.getOpacity(blockX, blockY, blockZ) > 0) {
                            heights.setHeight(dx, dz, blockY);
                            break;
                        }
                    }
                }
            }
        }
        return heights;
    }

    private void addAllThisChunk(ChunkPos chunkPos, Vec3List output) {
        for (int dx = 0; dx < 16; dx++) {
            for (int dy = 0; dy < 16; dy++) {
                for (int dz = 0; dz < 16; dz++) {
                    // TODO: benchmark: does it make sense to check if it's opaque here, or leave it for propagator?
                    output.add(chunkPos.blockX(dx), chunkPos.blockY(dy), chunkPos.blockZ(dz));
                }
            }
        }
    }
}
