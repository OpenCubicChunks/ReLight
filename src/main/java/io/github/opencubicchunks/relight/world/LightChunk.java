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

import io.github.opencubicchunks.relight.util.LightType;

public interface LightChunk {

    int getLight(int x, int y, int z, LightType type);

    int getLightSource(int x, int y, int z, LightType type);

    /**
     * Returns opacity between block locations (for for Minecraft 1.14+).
     */
    int getOpacityBetween(int fromX, int fromY, int fromZ, int toX, int toY, int toZ);

    /**
     * Returns the base opacity of a given block. Should only be used for heightmap.
     * {@link #getOpacityBetween(int, int, int, int, int, int)} should be used otherwise.
     */
    int getOpacity(int blockX, int blockY, int blockZ);

    int getX();

    int getY();

    int getZ();
}
