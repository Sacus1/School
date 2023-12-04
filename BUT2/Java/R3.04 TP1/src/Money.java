import java.util.Objects;

public class Money implements Comparable<Money> {
  private final int montant;
  private final String devise;

  public Money(int montant, String dev) {
    this.montant = montant;
    this.devise = dev;
  }

  /***
   * @return the amount of money
   */
  public int getMontant() {
    return this.montant;
  }

  /***
   * @return the devise of the money
   */
  public String getDevise() {
    return this.devise;
  }

  /***
   * @param m the money to add to this money
   * @return the sum of the two money
   * @throws DeviseException if the two money have different devises
   */
  public Money add(Money m) throws DeviseException {
    // if not the same devise throw exception
    if (!Objects.equals(this.devise, m.devise)) {
      throw new DeviseException(this.devise, m.devise);
    }
    // return new Money with the sum of the two
    return new Money(this.getMontant() + m.getMontant(), this.getDevise());
  }

  @Override
  public String toString() {
    return this.getMontant() + " " + this.getDevise();
  }

  @Override
  public boolean equals(Object ob) {
    // if the object is compared with itself then return true
    if (this == ob) return true;
    // if ob is null or not an instance of Money return false
    if (!(ob instanceof Money money)) return false;
    // if the two objects have the same devise and the same amount return true
    return montant == money.getMontant() && devise.equals(money.getDevise());
  }

  @Override
  public int hashCode() {
    return 31;
  }

  @Override
  public int compareTo(Money o) {
    return o.getMontant() - this.getMontant();
  }
}
