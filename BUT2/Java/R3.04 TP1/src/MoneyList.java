import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoneyList {
  private final List<Money> list;

  /** Constructor for MoneyList */
  public MoneyList() {
    list = new ArrayList<>();
  }

  /** Get the list of Money objects */
  public List<Money> getList() {
    return list;
  }

  /**
   * Add a Money object to the list
   *
   * @param m the Money object to add
   * @throws DeviseException if the devise of the Money object is not the same as the other Money
   *     objects in the list
   */
  public void ajouterSomme(Money m) throws DeviseException {
    // look for money with the same currency
    for (int i = 0; i < list.size(); i++) {
      // if found, add the amount
      Money money = list.get(i);
      if (money.getDevise().equals(m.getDevise())) {
        money = money.add(m);
        list.set(i, money);
        return;
      }
    }
    // if not found, add the money to the list
    list.add(m);
  }

  @Override
  public String toString() {
    return list.toString();
  }

  @Override
  public boolean equals(Object obj) {
    // if the object is compared with itself then return true
    if (this == obj) return true;
    // if the object is null or not an instance of MoneyList then return false
    if (!(obj instanceof MoneyList moneyList)) return false;
    int size = list.size();
    // if the sizes are different, return false
    if (size != moneyList.getList().size()) return false;
    // compare each money in the list
    for (int i = 0; i < size; i++) {
      if (!list.get(i).equals(moneyList.getList().get(i))) return false;
    }
    // if the function didn't return false yet then the lists are equal and return true
    return true;
  }

  @Override
  public int hashCode() {
    return 1;
  }

  public void triMontant() {
    Collections.sort(list);
  }

  public void triDevise() {
    list.sort(new DeviseComparator());
  }
}
