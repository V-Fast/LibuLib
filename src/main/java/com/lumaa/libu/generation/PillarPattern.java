package com.lumaa.libu.generation;

import com.lumaa.libu.util.Geometry;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class PillarPattern extends PatternGeneration implements IGenerator {
    private final int maxPillars = 35;
    public Block pillar;

    public PillarPattern(MazeCore generationCore, Block pillarBlock) {
        super(generationCore);
        this.pillar = pillarBlock;
    }

    @Override
    public void generate(World world, BlockPos origin) {
        int pillars = new Random().nextInt(this.maxPillars);

        for (int i = 0; i < pillars; i++) {
            fillPillar(origin);
        }
    }

    public void fillPillar(BlockPos origin) {
        MazeCore generationCore = (MazeCore) this.getGenCore();
        Geometry.Coordinate mazeSize = generationCore.getSize();

        BlockPos rotPoint = origin.add(-mazeSize.getX(), 0, -mazeSize.getZ());

        int x = new Random().nextInt(mazeSize.getX() * 2);
        int z = new Random().nextInt(mazeSize.getZ() * 2);

        fill(this.pillar.getDefaultState(), rotPoint.add(x, 0, z), rotPoint.add(x, mazeSize.getY(), z));
    }
}
