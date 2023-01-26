package com.lumaa.libu.generation;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MazeCore extends GenerationCore {
    private ShapeType shape;
    private StructureType type;

    public MazeCore(Block floor, Block walls, Block roof) {
        super(floor, walls, roof);
    }

    @Override
    public void generate(World world, BlockPos origin) {
        super.generate(world, origin);
        this.shape = getShape();
        this.type = getType();


        // walls
        if (type == StructureType.BLOCKY && shape == ShapeType.BOX) {
            if (hasVarients == VarientType.WALLS || hasVarients == VarientType.ALL) {
                fillVarients(WALLS, WALLS_VARIANTS, origin.add(-sizeX / 2 - 1, 0, -sizeZ / 2 - 1), origin.add(sizeX / 2 + 1, sizeY + 1, sizeZ / 2 + 1));
            } else {
                fill(WALLS.getDefaultState(), origin.add(-sizeX / 2 - 1, 0, -sizeZ / 2 - 1), origin.add(sizeX / 2 + 1, sizeY + 1, sizeZ / 2 + 1));
            }
            fill(Blocks.AIR.getDefaultState(), origin.add(-sizeX / 2, 1, -sizeZ / 2), origin.add(sizeX / 2, sizeY + 1, sizeZ / 2));
        }

        // floor
        if (hasVarients == VarientType.FLOOR || hasVarients == VarientType.ALL) {
            fillVarients(FLOOR, FLOOR_VARIANTS, origin.add(-sizeX / 2, 0, -sizeZ / 2), origin.add(sizeX / 2, 0, sizeZ / 2));
        } else {
            fill(FLOOR.getDefaultState(), origin.add(-sizeX / 2, 0, -sizeZ / 2), origin.add(sizeX / 2, 0, sizeZ / 2));
        }

        // roof
        if (hasVarients == VarientType.FLOOR || hasVarients == VarientType.ALL) {
            fillVarients(ROOF, ROOF_VARIANTS, origin.add(-sizeX / 2, sizeY + 1, -sizeZ / 2), origin.add(sizeX / 2, sizeY + 1, sizeZ / 2));
        } else {
            fill(ROOF.getDefaultState(), origin.add(-sizeX / 2, sizeY + 1, -sizeZ / 2), origin.add(sizeX / 2, sizeY + 1, sizeZ / 2));
        }
    }

    private void setRandomWall(BlockPos origin, Block block) {
        Orientation removeWallOrientation = Orientation.values()[(int) (Math.random() * Orientation.values().length)];
        BlockPos start; // from origin
        BlockPos end; // from origin

        switch (removeWallOrientation) {
            case EAST -> {
                start = origin.add(-sizeX / 2, 0, sizeZ / 2);
                end = origin.add(sizeX / 2, sizeY + 1, sizeZ / 2);
            }
            case WEST -> {
                start = origin.add(-sizeX / 2, 0, -sizeZ / 2);
                end = origin.add(sizeX / 2, sizeY + 1, -sizeZ / 2);
            }
        }
    }
}
