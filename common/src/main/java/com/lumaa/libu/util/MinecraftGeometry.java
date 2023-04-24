package com.lumaa.libu.util;

import net.minecraft.core.BlockPos;
import org.joml.Math;

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

    public static class AngleYaw {
        public float yaw;

        public AngleYaw(float yaw) {
            this.yaw = Math.clamp(this.yaw, -180.0f, 180.0f);
        }

        public float to360() {
            return yaw + 180;
        }

        public float to1(boolean belowZero) {
            if (belowZero) {
                return this.yaw / 180;
            } else {
                return this.to360() / 360;
            }
        }
    }

    public static class AnglePitch {
        public float pitch;

        public AnglePitch(float pitch) {
            this.pitch = Math.clamp(this.pitch, -90.0f, 90.0f);
        }

        public float to180() {
            return pitch + 90;
        }

        public float to1(boolean belowZero) {
            if (belowZero) {
                return this.pitch / 90;
            } else {
                return this.to180() / 180;
            }
        }
    }
}
