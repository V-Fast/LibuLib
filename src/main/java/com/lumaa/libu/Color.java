package com.lumaa.libu;

public class Color {
  public static final int white = fromRGB(255, 255, 255);
  public static final int black = fromRGB(0, 0, 0);
  public static final int brand = fromRGB(248, 189, 74);

  public static int fromRGB(int r, int g, int b) {
    return ((r & 0x0ff) << 16) | ((g & 0x0ff) << 8) | (b & 0x0ff);
  }

  public class Backrooms {
    public static final int blue = fromRGB(39, 54, 242);
  }
}
