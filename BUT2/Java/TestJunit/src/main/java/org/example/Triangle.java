package org.example;

public class Triangle {
  public int a, b, c;

  public Triangle(int a, int b, int c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }

  public int triangleType() {
    if (a == b && b == c && a > 0) {
      return 1;
    } else if ((a == b || b == c || a == c) && (a > 0 && b > 0)) {
      return 2;
    } else if (a > 0 && b > 0 && c > 0 && a + b > c && a + c > b && b + c > a) {
      return 3;
    } else {
      return 4;
    }
  }
}
