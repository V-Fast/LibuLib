package com.lumaa.libu.generation;

import com.lumaa.libu.util.MinecraftGeometry;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IGenerator {
    /**
     * Generate the structure
     * @param world The world to generate the structure
     * @param origin The origin point
     */
    void generate(World world, BlockPos origin);

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
