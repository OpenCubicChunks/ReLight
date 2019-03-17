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
package io.github.opencubicchunks.relight.heightmap;

import io.github.opencubicchunks.relight.util.BlockPos;

import java.util.Arrays;
import java.util.Map;

public class ColumnHeights implements HeightMap {
    private final int[] heights = new int[16 * 16];

    public ColumnHeights() {
        Arrays.fill(this.heights, Integer.MIN_VALUE);
    }

    public ColumnHeights(Map<BlockPos, Integer> map) {
        this();
        map.forEach((pos, height) -> setHeight(pos.getX() & 0xF, pos.getZ() & 0xF, height));
    }

    public int getTopY(int localX, int localZ) {
        return this.heights[localX | localZ << 4];
    }

    public void setHeight(int localX, int localZ, int newVal) {
        this.heights[localX | localZ << 4] = newVal;
    }

    public boolean exists(int localX, int localZ) {
        return getTopY(localX, localZ) != Integer.MIN_VALUE;
    }
}
