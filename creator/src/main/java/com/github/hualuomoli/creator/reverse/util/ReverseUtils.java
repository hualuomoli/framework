package com.github.hualuomoli.creator.reverse.util;

public final class ReverseUtils {

  public static String hump(String name) {
    StringBuilder buffer = new StringBuilder();
    char[] array = name.toCharArray();
    boolean upper = false;
    for (char c : array) {
      if (c == '_') {
        upper = true;
        continue;
      }
      if (upper) {
        buffer.append(String.valueOf(c).toUpperCase());
        upper = false;
      } else {
        buffer.append(String.valueOf(c));
      }
    }
    return buffer.toString();
  }

}
