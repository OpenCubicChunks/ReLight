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

import io.github.opencubicchunks.relight.propagator.NoopLightPropagator;
import io.github.opencubicchunks.relight.testutil.WorldAccessTestImpl;
import io.github.opencubicchunks.relight.util.BlockPos;
import io.github.opencubicchunks.relight.util.ChunkPos;
import io.github.opencubicchunks.relight.util.LightType;
import io.github.opencubicchunks.relight.util.Vec3List;
import org.junit.Test;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TestFirstLightHandler {

    // TODO: tests
    @Test
    public void testEmptyCubicChunksFullyLit() {
        Random rand = new Random(42);
        Set<ChunkPos> preLoadedChunks = new HashSet<>();
        Set<ChunkPos> newChunks = new HashSet<>();
        Set<BlockPos> oldOpaqueBlocks = new HashSet<>();
        Set<BlockPos> newOpaqueBlocks = new HashSet<>();
        Set<BlockPos> oldBlockLightSources = new HashSet<>();
        Set<BlockPos> newBlockLightSources = new HashSet<>();

        for (int i = 0; i < 200; i++) {
            preLoadedChunks.add(new ChunkPos(rand.nextInt(20), rand.nextInt(20), rand.nextInt(20)));
        }

        for (int i = 0; i < 20; i++) {
            newChunks.add(new ChunkPos(rand.nextInt(20), rand.nextInt(20), rand.nextInt(20)));
        }

        for (ChunkPos pos : preLoadedChunks) {
            for (int i = 0; i < 200; i++) {
                BlockPos block = new BlockPos(
                    pos.minBlockX() + rand.nextInt(16),
                    pos.minBlockY() + rand.nextInt(16),
                    pos.minBlockZ() + rand.nextInt(16)
                );
                oldOpaqueBlocks.add(block);
            }
        }
        for (ChunkPos pos : newChunks) {
            for (int i = 0; i < 200; i++) {
                BlockPos block = new BlockPos(
                    pos.minBlockX() + rand.nextInt(16),
                    pos.minBlockY() + rand.nextInt(16),
                    pos.minBlockZ() + rand.nextInt(16)
                );
                newOpaqueBlocks.add(block);
            }
        }
    }

    private void doTest(Set<ChunkPos> preLoadedChunks,
        Set<ChunkPos> newChunks,
        Set<BlockPos> oldOpaqueBlocks,
        Set<BlockPos> newOpaqueBlocks,
        Set<BlockPos> oldBlockLightSources,
        Set<BlockPos> newBlockLightSources) {

        WorldAccessTestImpl worldAccess = new WorldAccessTestImpl(preLoadedChunks, newChunks, oldOpaqueBlocks, newOpaqueBlocks,
            oldBlockLightSources, newBlockLightSources);

        FirstLightHandler handler = new FirstLightHandler(worldAccess);

        Vec3List updatedBlocks = new Vec3List(100000);
        handler.apply(newChunks, updatedBlocks);

        NoopLightPropagator propagator = new NoopLightPropagator(worldAccess, worldAccess);
        propagator.update(updatedBlocks, EnumSet.of(LightType.SKY));

        worldAccess.verifyLight();
    }

}
