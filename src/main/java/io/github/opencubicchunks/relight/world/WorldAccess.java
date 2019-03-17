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
package io.github.opencubicchunks.relight.world;

import io.github.opencubicchunks.relight.heightmap.HeightMap;
import io.github.opencubicchunks.relight.util.ChunkPos;
import io.github.opencubicchunks.relight.util.ColumnPos;

import java.util.List;

/**
 * Provides light data readers and writers optimized for specified chunk coordinate ranges
 */
public interface WorldAccess {

    // light access
    LightDataReader getLightChunk(ChunkPos minPos, ChunkPos maxPos);

    LightDataWriter getWriterFor(ChunkPos minPos, ChunkPos maxPos);

    // height map
    HeightMap getHeightMap(ColumnPos pos);

    // chunk access
    boolean isChunkLoaded(int chunkX, int chunkY, int chunkZ);

    default boolean isChunkLoaded(ChunkPos pos) {
        return isChunkLoaded(pos.getX(), pos.getY(), pos.getZ());
    }

    LightChunk getLightChunk(ChunkPos pos);

    /**
     * Returns an ordered list of all chunks in the given chunk height range from a given column
     */
    List<LightChunk> chunksBetween(ColumnPos pos, int start, int end);
}
