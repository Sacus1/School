import java.util.Comparator;

public class DeviseComparator implements Comparator<Money> {
    @Override
    public int compare(Money o1, Money o2) {
        return o1.getDevise().compareTo(o2.getDevise());
    }
}
