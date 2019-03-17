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

public class BlockPos {
    private final int x, y, z;

    public BlockPos(int x, int y, int z) {
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

    public int localX() {
        return this.x & 0xF;
    }

    public int localY() {
        return this.y & 0xF;
    }

    public int localZ() {
        return this.z & 0xF;
    }

    public BlockPos add(BlockPos other) {
        return new BlockPos(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public BlockPos add(int x, int y, int z) {
        return new BlockPos(this.x + x, this.y + y, this.z + z);
    }

    public BlockPos add(int other) {
        return new BlockPos(this.x + other, this.y + other, this.z + other);
    }

    public BlockPos sub(BlockPos other) {
        return new BlockPos(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public BlockPos sub(int x, int y, int z) {
        return new BlockPos(this.x - x, this.y - y, this.z - z);
    }

    public BlockPos sub(int other) {
        return new BlockPos(this.x - other, this.y - other, this.z - other);
    }

    public BlockPos mul(BlockPos other) {
        return new BlockPos(this.x * other.x, this.y * other.y, this.z * other.z);
    }

    public BlockPos mul(int x, int y, int z) {
        return new BlockPos(this.x * x, this.y * y, this.z * z);
    }

    public BlockPos mul(int other) {
        return new BlockPos(this.x * other, this.y * other, this.z * other);
    }

    public BlockPos div(BlockPos other) {
        return new BlockPos(this.x / other.x, this.y / other.y, this.z / other.z);
    }

    public BlockPos div(int x, int y, int z) {
        return new BlockPos(this.x / x, this.y / y, this.z / z);
    }

    public BlockPos div(int other) {
        return new BlockPos(this.x / other, this.y / other, this.z / other);
    }

    public ChunkPos chunkPos() {
        return new ChunkPos(x >> 4, y >> 4, z >> 4);
    }

    public ColumnPos columnPos() {
        return new ColumnPos(x >> 4, z >> 4);
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BlockPos blockPos = (BlockPos) o;
        return x == blockPos.x &&
            y == blockPos.y &&
            z == blockPos.z;
    }

    @Override public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override public String toString() {
        return "BlockPos{" +
            "x=" + x +
            ", y=" + y +
            ", z=" + z +
            '}';
    }
}
