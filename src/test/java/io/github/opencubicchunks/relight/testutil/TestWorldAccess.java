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

import io.github.opencubicchunks.relight.util.LightType;
import io.github.opencubicchunks.relight.world.LightDataReader;
import io.github.opencubicchunks.relight.world.LightDataWriter;
import io.github.opencubicchunks.relight.world.WorldAccess;

public class TestWorldAccess implements WorldAccess, LightDataWriter, LightDataReader {

    @Override public LightDataReader getReaderFor(int minChunkX, int minChunkY, int minChunkZ, int maxChunkX, int maxChunkY, int maxChunkZ) {
        return this;
    }

    @Override public LightDataWriter getWriterFor(int minChunkX, int minChunkY, int minChunkZ, int maxChunkX, int maxChunkY, int maxChunkZ) {
        return this;
    }

    @Override public boolean isChunkLoaded(int chunkX, int chunkY, int chunkZ) {
        //todo: implement;
    }

    @Override public int getLightSource(int x, int y, int z, LightType type) {
        return 0;
    }

    @Override public void setLight(int x, int y, int z, int value, LightType type) {

    }
}
