package com.lumaa.libu.generation;

import com.lumaa.libu.LibuLibClient;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class GenerationCore {
    private static World world;

    // normal blocks
    private static Block FLOOR = null;
    private static Block WALLS = null;
    private static Block ROOF = null;

    // variants
    private static VarientType hasVarients = VarientType.NONE;
    private static List<Block> FLOOR_VARIANTS = null;
    private static List<Block> WALLS_VARIANTS = null;
    private static List<Block> ROOF_VARIANTS = null;

    // sizes
    private static int sizeX = 16;
    private static int sizeY = 3;
    private static int sizeZ = 16;
    private static final int maxSize = 150;

    // types
    private static StructureType type = StructureType.BLOCKY;
    private static ShapeType shape = ShapeType.SQUARE;

    public GenerationCore(Block floor, Block walls, Block roof) {
        FLOOR = floor;
        WALLS = walls;
        ROOF = roof;
    }

    public void setVariants(List<Block> floor, List<Block> walls, List<Block> roof) {
        // force devs to put their normal blocks in their variants list
        if (!floor.contains(FLOOR) && !walls.contains(WALLS) && !roof.contains(ROOF)) {
            FLOOR_VARIANTS = floor;
            WALLS_VARIANTS = walls;
            ROOF_VARIANTS = roof;
            hasVarients = VarientType.ALL;
        } else {
            LibuLibClient.logger.error("Variant lists must not contain their normal blocks");
        }
    }

    public void setFloorVariants(List<Block> floorVariants) {
        if (floorVariants.contains(FLOOR)) LibuLibClient.logger.error("Floor Variant list must not contain normal floor block");
        FLOOR_VARIANTS = floorVariants;
        hasVarients = VarientType.FLOOR;
    }

    public void setWallsVariants(List<Block> wallsVariants) {
        if (wallsVariants.contains(WALLS)) LibuLibClient.logger.error("Walls Variant list must not contain normal walls block");
        WALLS_VARIANTS = wallsVariants;
        hasVarients = VarientType.WALLS;
    }

    public void setRoofVariants(List<Block> roofVariants) {
        if (roofVariants.contains(ROOF)) LibuLibClient.logger.error("Roof Variant list must not contain normal roof block");
        ROOF_VARIANTS = roofVariants;
        hasVarients = VarientType.ROOF;
    }

    public void setSize(int x, int z) {
        GenerationCore.sizeX = MathHelper.clamp(x, 2, maxSize);
        GenerationCore.sizeZ = MathHelper.clamp(z, 2, maxSize);
    }

    public void setHeight(int height) {
        GenerationCore.sizeY = height;
    }

    public void setShape(ShapeType shape) {
        if (sizeX != sizeZ && shape == ShapeType.SQUARE) {
            sizeZ = sizeX;
            LibuLibClient.logger.warn("Size of structure changed according to shape");
        }
        GenerationCore.shape = shape;
    }

    public void setType(StructureType type) {
        GenerationCore.type = type;
        if (type == StructureType.MODERN) {
            LibuLibClient.logger.error("MODERN Structure Type is not available yet. Set to BLOCKY");
            GenerationCore.type = StructureType.BLOCKY;
        }
    }

    private static void fixVars() {
        if (FLOOR_VARIANTS != null && WALLS_VARIANTS != null && ROOF_VARIANTS != null) {
            hasVarients = VarientType.ALL;
        } else if (FLOOR_VARIANTS != null && WALLS_VARIANTS == null && ROOF_VARIANTS == null) {
            hasVarients = VarientType.FLOOR;
        } else if (FLOOR_VARIANTS == null && WALLS_VARIANTS != null && ROOF_VARIANTS == null) {
            hasVarients = VarientType.WALLS;
        } else if (FLOOR_VARIANTS == null && WALLS_VARIANTS == null && ROOF_VARIANTS != null) {
            hasVarients = VarientType.ROOF;
        }
    }

    public void generate(World world, BlockPos origin) {
        fixVars();
        GenerationCore.world = world;

        // walls
        if (type == StructureType.BLOCKY && shape == ShapeType.SQUARE) {
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

    private void fill(BlockState block, BlockPos start, BlockPos end, boolean overwrite) {
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
            BlockPos blockPos = start.add(i, j, k);
            if (world.getBlockState(blockPos) == Blocks.AIR.getDefaultState() || world.getBlockState(blockPos) == Blocks.CAVE_AIR.getDefaultState() && overwrite == false) {
                world.setBlockState(blockPos, block);
            } else if (overwrite == true) {
                world.setBlockState(blockPos, block);
            }
        }
    }

    private void fill(BlockState block, BlockPos start, BlockPos end) {
        fill(block, start, end, true);
    }

    private void fillVarients(Block normalBlock, List<Block> variants, BlockPos start, BlockPos end, boolean overwrite) {
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
            BlockPos blockPos = start.add(i, j, k);

            // one of five change of setting a variant block
            double hasVariant = Math.random() * 5;
            if (world.getBlockState(blockPos) == Blocks.AIR.getDefaultState() || world.getBlockState(blockPos) == Blocks.CAVE_AIR.getDefaultState() && overwrite == false) {
                BlockState blockPlace = normalBlock.getDefaultState();
                if (hasVariant == 0) {
                    blockPlace = variants.get((int) (Math.random() * (variants.size() - 1))).getDefaultState();
                }
                world.setBlockState(blockPos, blockPlace);
            } else if (overwrite == true) {
                BlockState blockPlace = normalBlock.getDefaultState();
                if (hasVariant == 0) {
                    blockPlace = variants.get((int) (Math.random() * (variants.size() - 1))).getDefaultState();
                }
                world.setBlockState(blockPos, blockPlace);
            }
        }
    }

    private void fillVarients(Block normalBlock, List<Block> variants, BlockPos start, BlockPos end) {
        fillVarients(normalBlock, variants, start, end, true);
    }

    private enum VarientType {
        NONE,
        ALL,
        FLOOR,
        WALLS,
        ROOF
    }
}
