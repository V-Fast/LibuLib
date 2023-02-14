package com.lumaa.libu.generation;

import com.lumaa.libu.LibuLibClient;
import com.lumaa.libu.generation.type.ShapeType;
import com.lumaa.libu.generation.type.StructureType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.joml.Vector3d;

import java.util.List;
import java.util.Random;

public class GenerationCore {
    private static World world;

    // normal blocks
    public static Block FLOOR = null;
    public static Block WALLS = null;
    public static Block ROOF = null;

    // variants
    public static VariantType hasVariants = VariantType.NONE;
    public static List<Block> FLOOR_VARIANTS = null;
    public static List<Block> WALLS_VARIANTS = null;
    public static List<Block> ROOF_VARIANTS = null;

    // variant amounts
    public static VariantAmount FLOOR_VARIANTS_AMOUNT = VariantAmount.MEDIUM;
    public static VariantAmount WALLS_VARIANTS_AMOUNT = VariantAmount.MEDIUM;
    public static VariantAmount ROOF_VARIANTS_AMOUNT = VariantAmount.MEDIUM;

    // sizes
    public static int sizeX = 16;
    public static int sizeY = 3;
    public static int sizeZ = 16;
    public static final int maxSize = 150;

    // types
    private static StructureType type = StructureType.BLOCKY;
    private static ShapeType shape = ShapeType.BOX;

    private static boolean breakNonair = false;
    private static boolean dropOnBreak = false;

    public GenerationCore(Block floor, Block walls, Block roof) {
        FLOOR = floor;
        WALLS = walls;
        ROOF = roof;
    }

    public void setVariants(List<Block> floor, List<Block> walls, List<Block> roof) {
        // force devs to NOT put their normal blocks in their variants list
        if (!floor.contains(FLOOR) && !walls.contains(WALLS) && !roof.contains(ROOF)) {
            FLOOR_VARIANTS = floor;
            WALLS_VARIANTS = walls;
            ROOF_VARIANTS = roof;
            hasVariants = VariantType.ALL;
        } else {
            LibuLibClient.logger.error("Variant lists must not contain their normal blocks");
        }
    }

    public void setFloorVariants(List<Block> floorVariants) {
        if (floorVariants.contains(FLOOR)) LibuLibClient.logger.error("Floor Variant list must not contain normal floor block");
        FLOOR_VARIANTS = floorVariants;
        hasVariants = VariantType.FLOOR;
    }

    public void setWallsVariants(List<Block> wallsVariants) {
        if (wallsVariants.contains(WALLS)) LibuLibClient.logger.error("Walls Variant list must not contain normal walls block");
        WALLS_VARIANTS = wallsVariants;
        hasVariants = VariantType.WALLS;
    }

    public void setRoofVariants(List<Block> roofVariants) {
        if (roofVariants.contains(ROOF)) LibuLibClient.logger.error("Roof Variant list must not contain normal roof block");
        ROOF_VARIANTS = roofVariants;
        hasVariants = VariantType.ROOF;
    }

    public void setSize(int x, int z) {
        GenerationCore.sizeX = MathHelper.clamp(x, 2, maxSize);
        GenerationCore.sizeZ = MathHelper.clamp(z, 2, maxSize);
    }

    public void setHeight(int height) {
        GenerationCore.sizeY = height;
    }

    public void setShape(ShapeType shape) {
        if (sizeX != sizeZ && shape == ShapeType.BOX) {
            sizeZ = sizeX;
            LibuLibClient.logger.warn("Size of structure changed according to shape");
        }
        GenerationCore.shape = shape;
    }

    public ShapeType getShape() {
        return shape;
    }

    public void setType(StructureType type) {
        GenerationCore.type = type;
        if (type == StructureType.MODERN) {
            LibuLibClient.logger.error("MODERN Structure Type is not available yet. Set to BLOCKY");
            GenerationCore.type = StructureType.BLOCKY;
        }
    }

    public static StructureType getType() {
        return type;
    }

    /**
     * Define if the non-air block should break and drop
     * @param breakNonAir Define if the non-air block should break
     * @param drop Set if the non-air block drops
     */
    public static void setBreakNonAir(boolean breakNonAir, boolean drop) {
        GenerationCore.breakNonair = breakNonAir;
        GenerationCore.dropOnBreak = drop;
    }

    private static void fixVars() {
        if (FLOOR_VARIANTS != null && WALLS_VARIANTS != null && ROOF_VARIANTS != null) {
            hasVariants = VariantType.ALL;
        } else if (FLOOR_VARIANTS != null && WALLS_VARIANTS == null && ROOF_VARIANTS == null) {
            hasVariants = VariantType.FLOOR;
        } else if (FLOOR_VARIANTS == null && WALLS_VARIANTS != null && ROOF_VARIANTS == null) {
            hasVariants = VariantType.WALLS;
        } else if (FLOOR_VARIANTS == null && WALLS_VARIANTS == null && ROOF_VARIANTS != null) {
            hasVariants = VariantType.ROOF;
        }
    }

    /**
     * Generate the structure
     * @param world The world to generate the structure
     * @param origin The origin point
     */
    public void generate(World world, BlockPos origin) {
        fixVars();
        GenerationCore.world = world;
    }

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
            BlockPos blockPos = start.add(i, j, k);
            if ((world.getBlockState(blockPos) == Blocks.AIR.getDefaultState() || world.getBlockState(blockPos) == Blocks.CAVE_AIR.getDefaultState()) && world.canSetBlock(blockPos) && overwrite == false) {
                world.setBlockState(blockPos, block);
            } else if (overwrite == true && world.canSetBlock(blockPos)) {
                if (breakNonair && !(world.getBlockState(blockPos) == Blocks.AIR.getDefaultState() || world.getBlockState(blockPos) == Blocks.CAVE_AIR.getDefaultState())) world.breakBlock(blockPos, true);
                world.setBlockState(blockPos, block);
            }
        }
    }

    public void fill(BlockState block, BlockPos start, BlockPos end) {
        fill(block, start, end, true);
    }

//    public void fill(BlockState block, BlockPos origin, Orientation orientation) {
//        Scale3d scale = orientation.toFill(origin);
//        fill(block, scale.posA, scale.posB);
//    }

    public void fillVariants(Block normalBlock, List<Block> variants, BlockPos start, BlockPos end, boolean overwrite, VariantType variantType) {
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

            // variable variant chance
            boolean hasVariant = hasVariant(variantType);
            if ((world.getBlockState(blockPos) == Blocks.AIR.getDefaultState() || world.getBlockState(blockPos) == Blocks.CAVE_AIR.getDefaultState()) && overwrite == false) {
                if (hasVariant) {
                    world.setBlockState(blockPos, variants.get(new Random().nextInt(variants.size())).getDefaultState());
                } else {
                    world.setBlockState(blockPos, normalBlock.getDefaultState());
                }
            } else if (overwrite == true) {
                BlockState blockPlace = normalBlock.getDefaultState();
                if (hasVariant) {
                    blockPlace = variants.get((int) (Math.random() * (variants.size() - 1))).getDefaultState();
                }
                world.setBlockState(blockPos, blockPlace);
            }
        }
    }

    public void fillVariants(Block normalBlock, List<Block> variants, BlockPos start, BlockPos end, VariantType variantType) {
        fillVariants(normalBlock, variants, start, end, true, variantType);
    }

    public void fillVariants(Block normalBlock, List<Block> variants, BlockPos start, BlockPos end) {
        fillVariants(normalBlock, variants, start, end, true, VariantType.ALL);
    }

    private boolean hasVariant(VariantType type) {
        if (type == VariantType.ALL || type == VariantType.NONE) {
            return VariantAmount.UNDEFINED.test();
        } else if (type == VariantType.FLOOR) {
            return FLOOR_VARIANTS_AMOUNT.test();
        } else if (type == VariantType.WALLS) {
            return WALLS_VARIANTS_AMOUNT.test();
        } else if (type == VariantType.ROOF) {
            return ROOF_VARIANTS_AMOUNT.test();
        }
        return false;
    }

    public World getWorld() {
        return world;
    }

    public Vector3d getSize() {
        return new Vector3d(sizeX, sizeY, sizeZ);
    }

    public static void setFloorVariantsAmount(VariantAmount floorVariantsAmount) {
        FLOOR_VARIANTS_AMOUNT = floorVariantsAmount;
    }

    public static void setWallsVariantsAmount(VariantAmount wallsVariantsAmount) {
        WALLS_VARIANTS_AMOUNT = wallsVariantsAmount;
    }

    public static void setRoofVariantsAmount(VariantAmount roofVariantsAmount) {
        ROOF_VARIANTS_AMOUNT = roofVariantsAmount;
    }

    // other

    public enum VariantType {
        NONE,
        ALL,
        FLOOR,
        WALLS,
        ROOF
    }

    public enum Orientation {
        NORTH,
        SOUTH,
        EAST,
        WEST;

        public Scale3d toFill(BlockPos origin) {
            BlockPos k = origin;
            BlockPos start = null;
            BlockPos end = null;

            switch (this) {
                case EAST -> {
                    k = k.east(sizeX);
                    k = k.north(sizeZ);
                    start = k.up(sizeY);
                    k = origin;
                    k = k.east(sizeX);
                    end = k.south(sizeZ);
                }
                case WEST -> {
                    k = k.west(sizeX);
                    k = k.north(sizeZ);
                    start = k.up(sizeY);
                    k = origin;
                    k = k.west(sizeX);
                    end = k.south(sizeZ);
                }
                case NORTH -> {
                    k = k.north(sizeZ);
                    k = k.east(sizeX);
                    start = k.up(sizeY);
                    k = origin;
                    k = k.north(sizeZ);
                    end = k.west(sizeX);
                }
                case SOUTH -> {
                    k = k.south(sizeZ);
                    k = k.east(sizeX);
                    start = k.up(sizeY);
                    k = origin;
                    k = k.south(sizeZ);
                    end = k.west(sizeX);
                }
            }

            return new Scale3d(start, end);
        }
    }

    public static class Scale3d {
        public BlockPos posA;
        public BlockPos posB;

        public Scale3d(BlockPos positionA, BlockPos positionB) {
            this.posA = positionA;
            this.posB = positionB;
        }
    }

    public enum VariantAmount {

        UNDEFINED(5),
        LOW(20),
        MEDIUM(10),
        HIGH(3),
        EVERYTIME(0);

        public int rngMax;

        VariantAmount(int rngMax) {
            this.rngMax = rngMax;
        }

        public boolean test() {
            if (this.rngMax == 0) return true;
            return new Random().nextInt(this.rngMax) == 0;
        }
    }
}
