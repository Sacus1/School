import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoneyList {
    private final List<Money> list;

    public MoneyList() {
        list = new ArrayList<>();
    }

    public List<Money> getList() {
        return list;
    }

    public void ajouterSomme(Money m) throws DeviseException {
        // look for a money with the same currency
        for (int i = 0; i < list.size(); i++) {
            // if found, add the amount
            if (list.get(i).getDevise().equals(m.getDevise())) {
                Money money = list.get(i).add(m);
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
