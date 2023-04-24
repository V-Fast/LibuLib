package com.lumaa.libu.generation;

import com.lumaa.libu.util.Geometry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.Random;

@Deprecated
public class PillarPattern extends PatternGeneration implements IGenerator {
    private final int maxPillars = 35;
    public Block pillar;

    public PillarPattern(MazeCore generationCore, Block pillarBlock) {
        super(generationCore);
        this.pillar = pillarBlock;
    }

    @Override
    public void generate(Level world, BlockPos origin) {
        int pillars = new Random().nextInt(this.maxPillars);

        for (int i = 0; i < pillars; i++) {
            fillPillar(origin);
        }
    }

    public void fillPillar(BlockPos origin) {
        MazeCore generationCore = (MazeCore) this.getGenCore();
        Geometry.Coordinate mazeSize = generationCore.getSize();

        BlockPos rotPoint = origin.offset(-mazeSize.getX(), 0, -mazeSize.getZ());

        int x = new Random().nextInt(mazeSize.getX() * 2);
        int z = new Random().nextInt(mazeSize.getZ() * 2);

        fill(this.pillar.defaultBlockState(), rotPoint.offset(x, 0, z), rotPoint.offset(x, mazeSize.getY(), z));
    }
}
