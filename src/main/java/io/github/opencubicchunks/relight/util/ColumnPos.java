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

import java.util.Objects;

public class ColumnPos {
    private final int x, z;

    public ColumnPos(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public ColumnPos add(ColumnPos other) {
        return new ColumnPos(this.x + other.x, this.z + other.z);
    }

    public ColumnPos add(int x, int z) {
        return new ColumnPos(this.x + x, this.z + z);
    }

    public ColumnPos add(int other) {
        return new ColumnPos(this.x + other, this.z + other);
    }

    public ColumnPos sub(ColumnPos other) {
        return new ColumnPos(this.x - other.x, this.z - other.z);
    }

    public ColumnPos sub(int x, int z) {
        return new ColumnPos(this.x - x, this.z - z);
    }

    public ColumnPos sub(int other) {
        return new ColumnPos(this.x - other, this.z - other);
    }

    public ColumnPos mul(ColumnPos other) {
        return new ColumnPos(this.x * other.x, this.z * other.z);
    }

    public ColumnPos mul(int x, int z) {
        return new ColumnPos(this.x * x, this.z * z);
    }

    public ColumnPos mul(int other) {
        return new ColumnPos(this.x * other, this.z * other);
    }

    public ColumnPos div(ColumnPos other) {
        return new ColumnPos(this.x / other.x, this.z / other.z);
    }

    public ColumnPos div(int x, int z) {
        return new ColumnPos(this.x / x, this.z / z);
    }

    public ColumnPos div(int other) {
        return new ColumnPos(this.x / other, this.z / other);
    }


    public int minBlockX() {
        return this.x << 4;
    }

    public int minBlockZ() {
        return this.z << 4;
    }

    public int blockX(int dx) {
        return minBlockX() + dx;
    }

    public int blockZ(int dz) {
        return minBlockZ() + dz;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ColumnPos columnPos = (ColumnPos) o;
        return x == columnPos.x &&
            z == columnPos.z;
    }

    @Override public int hashCode() {
        return Objects.hash(x, z);
    }

    @Override public String toString() {
        return "ColumnPos{" +
            "x=" + x +
            ", z=" + z +
            '}';
    }
}
