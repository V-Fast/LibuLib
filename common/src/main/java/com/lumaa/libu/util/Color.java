package com.lumaa.libu.util;

import org.joml.Math;

// using RGB to be more visible at the eye
public class Color {
  public static final int white = fromRGB(255, 255, 255);
  public static final int black = fromRGB(0, 0, 0);
  /**
   * The branded color of Lumaa
   */
  public static final int brand = fromRGB(248, 189, 74);

  public static int fromRGB(int r, int g, int b) {
    return ((Math.clamp(r, 0, 255) & 0x0ff) << 16) | ((Math.clamp(g, 0, 255) & 0x0ff) << 8) | (Math.clamp(b, 0, 255) & 0x0ff);
  }

  /**
   * Colors used in <a href="https://modrinth.com/mod/backrooms">The Backrooms Mod</a>
   */
  public class Backrooms {
    public static final int blue = fromRGB(39, 54, 242);
  }
}
