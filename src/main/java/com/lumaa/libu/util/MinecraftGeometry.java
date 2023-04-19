package com.lumaa.libu.util;

import net.minecraft.util.math.BlockPos;

public class MinecraftGeometry {
    public static class Scale3d {
        public BlockPos posA;
        public BlockPos posB;

        public Scale3d(BlockPos positionA, BlockPos positionB) {
            this.posA = positionA;
            this.posB = positionB;
        }

        public Geometry.Scale3d toInt() {
            return new Geometry.Scale3d(new Geometry.Coordinate(posA.getX(), posA.getY(), posA.getZ()), new Geometry.Coordinate(posB.getX(), posB.getY(), posB.getZ()));
        }
    }
}
