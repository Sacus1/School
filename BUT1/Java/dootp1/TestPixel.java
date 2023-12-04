class TestPixel {
  public static void main(String[] args) {

    Pixel px = new Pixel(10, 10);
    System.out.println(px);
    Pixel px2 = new Pixel();
    System.out.println(px2);
    Pixel px3 = new Pixel(px);
    System.out.println(px3);
    Pixel px4 = new Pixel(5.5, 3.3);
    System.out.println(px4);
    Pixel px5 = new Pixel(px, px4);
    System.out.println(px5);
    System.out.println(px.distance2());
    System.out.println(px.plusLoinQue(px4));
    System.out.println(px.distance2(new Pixel(5, 5)));
    System.out.println(px.distanceManhattan(px2));
  }
}
