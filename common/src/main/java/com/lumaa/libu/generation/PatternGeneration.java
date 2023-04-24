package com.lumaa.libu.generation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class PatternGeneration implements IGenerator {
    private GenerationCore genCore;
    private Level world;

    private static boolean breakNonair = false;

    public PatternGeneration(Level world) {
        this.world = world;
    }

    public PatternGeneration(GenerationCore generationCore) {
        this.genCore = generationCore;
        setBreakNonair(this.genCore.isBreakNonair());
    }

    @Override
    public void generate(Level world, BlockPos origin) {}

    @Override
    public void fill(BlockState block, BlockPos start, BlockPos end, boolean overwrite) {
        int width = end.getX() - start.getX();
        int depth = end.getZ() - start.getZ();
        int height = end.getY() - start.getY();

        if (width == 0) {
            width = 1;
        } else if (height == 0) {
            height = 1;
        } else if (depth == 0) {
            depth = 1;
        }

        int total = width * depth * height;

        for (int t = 0; t < total; t++) {
            int i = t % width;
            int j = t / (width * depth);
            int k = (t / width) % depth;
            BlockPos blockPos = start.offset(i, j, k);
            if ((world.getBlockState(blockPos).isAir() && world.isEmptyBlock(blockPos) && overwrite == false)) {
                world.setBlockAndUpdate(blockPos, block);
            } else if (overwrite == true && world.isEmptyBlock(blockPos)) {
                if (breakNonair && !world.getBlockState(blockPos).isAir()) world.removeBlock(blockPos, true);
                world.setBlockAndUpdate(blockPos, block);
            }
        }
    }

    public static void setBreakNonair(boolean breakNonair) {
        PatternGeneration.breakNonair = breakNonair;
    }

    public boolean isBreakNonair() {
        return breakNonair;
    }

    public GenerationCore getGenCore() {
        return genCore;
    }

    public Level getWorld() {
        return world;
    }

    public boolean useVariant(int maxCombination) {
        if (maxCombination == 0) return false; // probability isn't valid
        return new Random().nextInt(maxCombination) == 0;
    }
}
