package com.lumaa.libu.generation;

import com.lumaa.libu.generation.type.ShapeType;
import com.lumaa.libu.generation.type.StructureType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class MazeCore extends GenerationCore {
    private ShapeType shape;
    private StructureType type;
    private int iterations;

    private Orientation previousEmpty = null;
    private Orientation empty = null;

    public MazeCore(Block floor, Block walls, Block roof, int iterations) {
        super(floor, walls, roof);
        this.iterations = iterations;
    }

    @Override
    public void generate(World world, BlockPos origin) {
        super.generate(world, origin);
        this.shape = getShape();
        this.type = getType();

        for (int i = 0; i < iterations; i++) {
            // walls
            if (type == StructureType.BLOCKY && shape == ShapeType.BOX) {
                if (hasVarients == VarientType.WALLS || hasVarients == VarientType.ALL) {
                    fillVarients(WALLS, WALLS_VARIANTS, origin.add(-sizeX / 2 - 1, 0, -sizeZ / 2 - 1), origin.add(sizeX / 2 + 1, sizeY + 1, sizeZ / 2 + 1));
                } else {
                    fill(WALLS.getDefaultState(), origin.add(-sizeX / 2 - 1, 0, -sizeZ / 2 - 1), origin.add(sizeX / 2 + 1, sizeY + 1, sizeZ / 2 + 1));
                }
                fill(Blocks.AIR.getDefaultState(), origin.add(-sizeX / 2, 1, -sizeZ / 2), origin.add(sizeX / 2, sizeY + 1, sizeZ / 2));

                if (previousEmpty != null) {
                    fill(Blocks.AIR.getDefaultState(), previousEmpty.toFill(origin).posA, previousEmpty.toFill(origin).posB);
                }
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

            // remove random wall and continue
            this.previousEmpty = this.empty;
            this.empty = fillRandomWall(origin, Blocks.AIR);
            origin = updateOrigin(origin, empty);
        }
    }

    /**
     * Move the origin
     * @param origin
     * @param orientation
     * @return
     */
    private BlockPos updateOrigin(BlockPos origin, Orientation orientation) {
        switch (orientation) {
            case EAST -> {
                return origin.east((int) (getSize().z));
            }
            case WEST -> {
                return origin.west((int) (getSize().z));
            }
            case NORTH -> {
                return origin.north((int) (getSize().x));
            }
            case SOUTH -> {
                return origin.south((int) (getSize().x));
            }
        }
        return origin.north((int) (getSize().x / 2));
    }

    /**
     * Fills one of the 4 walls
     */
    private Orientation fillRandomWall(BlockPos origin, Block block) {
        Orientation removeWallOrientation = Orientation.values()[new Random().nextInt(Orientation.values().length - 1)];
        BlockPos start = removeWallOrientation.toFill(origin).posA; // from origin
        BlockPos end = removeWallOrientation.toFill(origin).posB; // from origin

        fill(block.getDefaultState(), start, end);

        return removeWallOrientation;
    }
}
