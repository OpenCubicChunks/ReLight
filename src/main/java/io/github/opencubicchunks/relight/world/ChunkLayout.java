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

/**
 * Provides information about world chunk layout:
 *  - chunk coordinate bounds (these are NOT normal accessible world bounds, chunks beyond that are assumed to not make sense in the world)
 *  - size of a chunk
 *
 * Also contains methods for coordinate conversion.
 */
public class ChunkLayout {

    private final int chunkSizeX;
    private final int chunkSizeY;
    private final int chunkSizeZ;

    private final int minChunkX;
    private final int minChunkY;
    private final int minChunkZ;

    private final int maxChunkX;
    private final int maxChunkY;
    private final int maxChunkZ;

    private ChunkLayout(Builder builder) {
        this.chunkSizeX = builder.chunkSizeX;
        this.chunkSizeY = builder.chunkSizeY;
        this.chunkSizeZ = builder.chunkSizeZ;
        this.minChunkX = builder.minChunkX;
        this.minChunkY = builder.minChunkY;
        this.minChunkZ = builder.minChunkZ;
        this.maxChunkX = builder.maxChunkX;
        this.maxChunkY = builder.maxChunkY;
        this.maxChunkZ = builder.maxChunkZ;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns size of a chunk alone the X axis. The value must be a power of 2 (to allow more optimized math)
     *
     * @return size of a chunk along X axis
     */
    public int getChunkSizeX() {
        return chunkSizeX;
    }

    /**
     * Returns size of a chunk alone the Y axis. The value must be a power of 2 (to allow more optimized math)
     *
     * @return size of a chunk along Y axis
     */
    public int getChunkSizeY() {
        return chunkSizeY;
    }

    /**
     * Returns size of a chunk alone the Z axis. The value must be a power of 2 (to allow more optimized math)
     *
     * @return size of a chunk along Z axis
     */
    public int getChunkSizeZ() {
        return chunkSizeZ;
    }

    /**
     * Returns the minimum chunk coordinate on the X axis, or Integer.MIN_VALUE if all possible world coordinates are supported.
     *
     * @return the minimum chunk X coordinate
     */
    public int getMinChunkX() {
        return minChunkX;
    }

    /**
     * Returns the minimum chunk coordinate on the Y axis, or Integer.MIN_VALUE if all possible world coordinates are supported.
     *
     * @return the minimum chunk Y coordinate
     */
    public int getMinChunkY() {
        return minChunkY;
    }

    /**
     * Returns the minimum chunk coordinate on the Z axis, or Integer.MIN_VALUE if all possible world coordinates are supported.
     *
     * @return the minimum chunk Z coordinate
     */
    public int getMinChunkZ() {
        return minChunkZ;
    }

    /**
     * Returns the maximum chunk coordinate on the X axis, or Integer.MAX_VALUE if all possible world coordinates are supported.
     *
     * @return the maximum chunk X coordinate
     */
    public int getMaxChunkX() {
        return maxChunkX;
    }

    /**
     * Returns the maximum chunk coordinate on the Y axis, or Integer.MAX_VALUE if all possible world coordinates are supported.
     *
     * @return the maximum chunk Y coordinate
     */
    public int getMaxChunkY() {
        return maxChunkY;
    }

    /**
     * Returns the maximum chunk coordinate on the Z axis, or Integer.MAX_VALUE if all possible world coordinates are supported.
     *
     * @return the maximum chunk Z coordinate
     */
    public int getMaxChunkZ() {
        return maxChunkZ;
    }

    public int chunkToMinBlockX(int chunkX);

    public int chunkToMinBlockY(int chunkY);

    public int chunkToMinBlockZ(int chunkZ);

    public int blockToChunkX(int blockX);

    public int blockToChunkY(int blockY);

    public int blockToChunkY(int blockY);

    public int blockToLocalX(int blockX);

    public int blockToLocalY(int blockY);

    public int blockToLocalZ(int blockZ);

    public static class Builder {

        private int chunkSizeX;
        private int chunkSizeY;
        private int chunkSizeZ;
        private int minChunkX = Integer.MIN_VALUE;
        private int minChunkY = Integer.MIN_VALUE;
        private int minChunkZ = Integer.MIN_VALUE;
        private int maxChunkX = Integer.MAX_VALUE;
        private int maxChunkY = Integer.MAX_VALUE;
        private int maxChunkZ = Integer.MAX_VALUE;

        public Builder setChunkSizeX(int chunkSizeX) {
            this.chunkSizeX = chunkSizeX;
            return this;
        }

        public Builder setChunkSizeY(int chunkSizeY) {
            this.chunkSizeY = chunkSizeY;
            return this;
        }

        public Builder setChunkSizeZ(int chunkSizeZ) {
            this.chunkSizeZ = chunkSizeZ;
            return this;
        }

        public Builder setMinChunkX(int minChunkX) {
            this.minChunkX = minChunkX;
            return this;
        }

        public Builder setMinChunkY(int minChunkY) {
            this.minChunkY = minChunkY;
            return this;
        }

        public Builder setMinChunkZ(int minChunkZ) {
            this.minChunkZ = minChunkZ;
            return this;
        }

        public Builder setMaxChunkX(int maxChunkX) {
            this.maxChunkX = maxChunkX;
            return this;
        }

        public Builder setMaxChunkY(int maxChunkY) {
            this.maxChunkY = maxChunkY;
            return this;
        }

        public Builder setMaxChunkZ(int maxChunkZ) {
            this.maxChunkZ = maxChunkZ;
            return this;
        }

        public Builder setChunkSize(int size) {
            return setChunkSizeX(size).setChunkSizeY(size).setChunkSizeZ(size);
        }

        public ChunkLayout createChunkLayout() {
            return new ChunkLayout(this);
        }
    }
}
