package com.lumaa.libu.util;

public class Geometry {
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

        public Translation2n getOriginCoordinates() {
            return new Translation2n(x1, y1);
        }

        public Translation2n getEndCoordinates() {
            return new Translation2n(x2, y2);
        }
    }

    public static class Translation2n {
        public int x;
        public int y;

        private Translation2n(int x, int y) {
            this.x = x;
            this.y = y;
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

        public Translation3d setOriginCoordinates(int x, int y) {
            this.x1 = x;
            this.y1 = y;
            return this;
        }

        public Translation3d setEndCoordinates(int x, int y) {
            this.x2 = x;
            this.y2 = y;
            return this;
        }
    }
}
