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
package io.github.opencubicchunks.relight.util;

import java.util.Arrays;

public class Vec3List {

    private int[] coords;

    private int ptr = 0;
    private int readPtr = -3;

    public Vec3List(int initSize) {
        if (initSize <= 0) {
            throw new IllegalArgumentException("initSize must be positive but got " + initSize);
        }
        this.coords = new int[initSize * 3];
    }

    public void add(int x, int y, int z) {
        if (this.ptr >= this.coords.length) {
            this.coords = Arrays.copyOf(this.coords, this.coords.length * 2);
            assert this.coords.length % 3 == 0;
        }
        assert this.ptr % 3 == 0;
        this.coords[ptr++] = x;
        this.coords[ptr++] = y;
        this.coords[ptr++] = z;
    }

    public boolean next() {
        readPtr += 3;
        return readPtr < ptr;
    }

    public int getX() {
        return this.coords[readPtr];
    }

    public int getY() {
        return this.coords[readPtr + 1];
    }

    public int getZ() {
        return this.coords[readPtr + 2];
    }

}
