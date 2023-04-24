package com.lumaa.libu.generation;

import com.lumaa.libu.util.MinecraftGeometry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IGenerator {
    /**
     * Generate the structure
     * @param world The world to generate the structure
     * @param origin The origin point
     */
    void generate(Level world, BlockPos origin);

    void fill(BlockState block, BlockPos start, BlockPos end, boolean overwrite);

    default void fill(BlockState block, BlockPos start, BlockPos end) {
        fill(block, start, end, true);
    }

    default void fill(BlockState block, MinecraftGeometry.Scale3d scale) {
        fill(block, scale, true);
    }

    default void fill(BlockState block, MinecraftGeometry.Scale3d scale, boolean overwrite) {
        fill(block, scale.posA, scale.posB, overwrite);
    }
}
