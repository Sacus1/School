public class Angle {
  private double radian;

  Angle(double rad) {
    this.radian = rad % Math.PI;
  }

  Angle(int deg) {
    this(deg * Math.PI / 180);
  }

  Angle() {
    this(Math.PI / 2);
  }

  /**
   * @return un string des radian
   */
  public String toString() {
    return (this.radian + " radian");
  }

  /**
   * @return la valeur en radian de l'angle
   */
  public double radians() {
    return (this.radian);
  }

  /**
   * @return la valeur en degres de l'angle
   */
  public int degres() {
    return ((int) Math.round(this.radian * 180 / Math.PI));
  }

  /**
   * @return Un angle de la somme des 2 angle
   * @param l'angle a ajouter
   */
  public Angle somme(Angle ang) {
    return new Angle(this.radian + ang.radians());
  }

  /**
   * @return un angle correspondant a l'opposé de l'angle
   */
  public Angle oppose() {
    return (new Angle(-this.somme(new Angle()).radians()));
  }

  /**
   * @param l'angle a ajouter
   */
  public void ajouter(Angle ang) {
    this.radian += ang.radians();
  }

  /** Inverse la valeur de l'angle */
  public void inverser() {
    this.radian *= -1;
  }
}
