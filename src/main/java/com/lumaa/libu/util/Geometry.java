package com.lumaa.libu.util;

import com.lumaa.libu.LibuLibClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Geometry {
    public static class Coordinate {
        private int x;
        private int y;
        private int z;

        private static boolean is3d = true;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;

            setIs3d(false);
        }

        public Coordinate(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;

            setIs3d(true);
        }

        public Coordinate(BlockPos blockPos) {
            new Coordinate(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }

        public Coordinate(Vec3d vec3d) {
            new Coordinate((int) vec3d.x,(int)  vec3d.y,(int)  vec3d.z);
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getZ() {
            try {
                if (!is3d()) throw new Exception("Cannot get Z coordinates in a 2D space");
                return z;
            } catch (Exception e) {
                LibuLibClient.logger.error(e.getMessage());
            }
            return 0;
        }

        public void setZ(int z) {
            this.z = z;
            setIs3d(true);
        }

        public static void setIs3d(boolean is3d) {
            Coordinate.is3d = is3d;
        }

        public static boolean is3d() {
            return is3d;
        }
    }

    public static class Translation2d {
        private int x1 = 0;
        private int x2;
        private int y1 = 0;
        private int y2;

        public Translation2d() {}

        public Translation2d setOriginCoordinates(int x, int y) {
            this.x1 = x;
            this.y1 = y;
            return this;
        }

        public Translation2d setEndCoordinates(int x, int y) {
            this.x2 = x;
            this.y2 = y;
            return this;
        }

        public Coordinate getOriginCoordinates() {
            return new Coordinate(this.x1, this.y1);
        }

        public Coordinate getEndCoordinates() {
            return new Coordinate(this.x2, this.y2);
        }

        public int getOriginX() {
            return x1;
        }

        public int getEndX() {
            return x2;
        }

        public int getOriginY() {
            return y1;
        }

        public int getEndY() {
            return y2;
        }
    }

    public static class Translation3d {
        private int x1 = 0;
        private int x2;

        private int y1 = 0;
        private int y2;

        private int z1 = 0;
        private int z2;


        public Translation3d() {}

        public Translation3d setOriginCoordinates(int x, int y, int z) {
            this.x1 = x;
            this.y1 = y;
            this.z1 = z;
            return this;
        }

        public Translation3d setEndCoordinates(int x, int y, int z) {
            this.x2 = x;
            this.y2 = y;
            this.z2 = z;
            return this;
        }
    }

    public static class Scale3d {
        public Coordinate posA;
        public Coordinate posB;

        public Scale3d(Coordinate positionA, Coordinate positionB) {
            this.posA = positionA;
            this.posB = positionB;
        }

        public MinecraftGeometry.Scale3d toBlockPos() {
            try {
                return new MinecraftGeometry.Scale3d(new BlockPos(posA.getX(), posA.getY(), posA.getZ()), new BlockPos(posB.getX(), posB.getY(), posB.getZ()));
            } catch (Exception e) {
                LibuLibClient.logger.error(e.getMessage());
                return null;
            }
        }
    }
}

