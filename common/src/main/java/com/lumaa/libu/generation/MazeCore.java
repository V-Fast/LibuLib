package com.lumaa.libu.generation;

import com.lumaa.libu.generation.type.ShapeType;
import com.lumaa.libu.generation.type.StructureType;
import com.lumaa.libu.util.MinecraftGeometry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Random;

public class MazeCore extends GenerationCore implements IGenerator {
    private ShapeType shape;
    private StructureType type;
    private int iterations;

    private Orientation next = null;

    public MazeCore(Block floor, Block walls, Block roof, int iterations) {
        super(floor, walls, roof);
        this.iterations = iterations;
    }

    @Override
    public void generate(Level world, BlockPos origin) {
        super.generate(world, origin);
        this.shape = getShape();
        this.type = getType();

        for (int i = 0; i < iterations; i++) {
            // walls
            if (type == StructureType.BLOCKY && shape == ShapeType.BOX) {
                if (hasVariants == VariantType.WALLS || hasVariants == VariantType.ALL) {
                    fillVariants(WALLS, WALLS_VARIANTS, origin.offset(-sizeX / 2 - 1, 0, -sizeZ / 2 - 1), origin.offset(sizeX / 2 + 1, sizeY + 1, sizeZ / 2 + 1), VariantType.WALLS);
                } else {
                    fill(WALLS.defaultBlockState(), origin.offset(-sizeX / 2 - 1, 0, -sizeZ / 2 - 1), origin.offset(sizeX / 2 + 1, sizeY + 1, sizeZ / 2 + 1));
                }
                fill(Blocks.AIR.defaultBlockState(), origin.offset(-sizeX / 2, 1, -sizeZ / 2), origin.offset(sizeX / 2, sizeY + 1, sizeZ / 2));
            }

            // floor
            if (hasVariants == VariantType.FLOOR || hasVariants == VariantType.ALL) {
                fillVariants(FLOOR, FLOOR_VARIANTS, origin.offset(-sizeX / 2, 0, -sizeZ / 2), origin.offset(sizeX / 2, 0, sizeZ / 2), VariantType.FLOOR);
            } else {
                fill(FLOOR.defaultBlockState(), origin.offset(-sizeX / 2, 0, -sizeZ / 2), origin.offset(sizeX / 2, 0, sizeZ / 2));
            }

            // roof
            if (hasVariants == VariantType.FLOOR || hasVariants == VariantType.ALL) {
                fillVariants(ROOF, ROOF_VARIANTS, origin.offset(-sizeX / 2, sizeY + 1, -sizeZ / 2), origin.offset(sizeX / 2, sizeY + 1, sizeZ / 2), VariantType.ROOF);
            } else {
                fill(ROOF.defaultBlockState(), origin.offset(-sizeX / 2 - 1, sizeY + 1, -sizeZ / 2 - 1), origin.offset(sizeX / 2 + 1, sizeY + 1, sizeZ / 2 + 1));
            }

            // pillars
//            PillarPattern pillarPattern = new PillarPattern(this, WALLS);
//            pillarPattern.generate(world, origin);

            // remove random wall and continue
            if (i != iterations) {
                this.next = Orientation.values()[new Random().nextInt(Orientation.values().length)];

                BlockPos a = toFill(next, origin).posA;
                BlockPos b = toFill(next, origin).posB;

                fill(Blocks.AIR.defaultBlockState(), a, b);

                origin = updateOrigin(origin, next);
            }
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
                return origin.east((int) (getSize().getZ()) + 1);
            }
            case WEST -> {
                return origin.west((int) (getSize().getZ()) + 1);
            }
            case NORTH -> {
                return origin.north((int) (getSize().getX()) + 1);
            }
            case SOUTH -> {
                return origin.south((int) (getSize().getX()) + 1);
            }
        }
        return origin.north((int) (getSize().getX()) + 1);
    }

    public MinecraftGeometry.Scale3d toFill(Orientation orientation, BlockPos origin) {
        BlockPos start = null;
        BlockPos end = null;

        switch (orientation) {
            case EAST -> {
                start = origin.offset(sizeX / 2, sizeY, sizeZ / 2);
                end = origin.offset(sizeX / 2, 0, -sizeZ / 2);
            }
            case WEST -> {
                start = origin.offset(-sizeX / 2, sizeY, sizeZ / 2);
                end = origin.offset(-sizeX / 2, 0, -sizeZ / 2);
            }
            case NORTH -> {
                start = origin.offset(sizeX / 2, sizeY, sizeZ / 2);
                end = origin.offset(-sizeX / 2, 0, sizeZ / 2);
            }
            case SOUTH -> {
                start = origin.offset(sizeX / 2, sizeY, -sizeZ / 2);
                end = origin.offset(-sizeX / 2, 0, -sizeZ / 2);
            }
        }

        return new MinecraftGeometry.Scale3d(start, end);
    }
}
