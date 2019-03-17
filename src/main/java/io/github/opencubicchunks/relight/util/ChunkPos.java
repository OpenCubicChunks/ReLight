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

public class ChunkPos {

    private final int x, y, z;

    public ChunkPos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public ChunkPos add(ChunkPos other) {
        return new ChunkPos(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public ChunkPos add(int x, int y, int z) {
        return new ChunkPos(this.x + x, this.y + y, this.z + z);
    }

    public ChunkPos add(int other) {
        return new ChunkPos(this.x + other, this.y + other, this.z + other);
    }

    public ChunkPos sub(ChunkPos other) {
        return new ChunkPos(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public ChunkPos sub(int x, int y, int z) {
        return new ChunkPos(this.x - x, this.y - y, this.z - z);
    }

    public ChunkPos sub(int other) {
        return new ChunkPos(this.x - other, this.y - other, this.z - other);
    }

    public ChunkPos mul(ChunkPos other) {
        return new ChunkPos(this.x * other.x, this.y * other.y, this.z * other.z);
    }

    public ChunkPos mul(int x, int y, int z) {
        return new ChunkPos(this.x * x, this.y * y, this.z * z);
    }

    public ChunkPos mul(int other) {
        return new ChunkPos(this.x * other, this.y * other, this.z * other);
    }

    public ChunkPos div(ChunkPos other) {
        return new ChunkPos(this.x / other.x, this.y / other.y, this.z / other.z);
    }

    public ChunkPos div(int x, int y, int z) {
        return new ChunkPos(this.x / x, this.y / y, this.z / z);
    }

    public ChunkPos div(int other) {
        return new ChunkPos(this.x / other, this.y / other, this.z / other);
    }

    public int minBlockX() {
        return this.x << 4;
    }

    public int minBlockY() {
        return this.y << 4;
    }

    public int minBlockZ() {
        return this.z << 4;
    }

    public int blockX(int dx) {
        return minBlockX() + dx;
    }

    public int blockY(int dz) {
        return minBlockY() + y;
    }

    public int blockZ(int dz) {
        return minBlockZ() + dz;
    }

    public ColumnPos toColumn() {
        return new ColumnPos(this.x, this.z);
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChunkPos chunkPos = (ChunkPos) o;
        return x == chunkPos.x &&
            y == chunkPos.y &&
            z == chunkPos.z;
    }

    @Override public String toString() {
        return "ChunkPos{" +
            "x=" + x +
            ", y=" + y +
            ", z=" + z +
            '}';
    }

    @Override public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
