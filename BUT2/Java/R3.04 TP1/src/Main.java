public class Main {
    public static void main(String[] args) {
        Money m1 = new Money(10, "EUR");
        Money m2 = new Money(20, "EUR");
        Money m3, m4;
        try {
            // add two Money objects but in try catch block because it throws DeviseException
            m3 = m1.add(m2);
            m4 = m1.add(new Money(20, "USD"));
            System.out.println(m3);
            System.out.println(m4);
        }
        catch (DeviseException e) {
            System.out.println(e.getMessage());
        }
        try{
            // compare two Money objects with different values
            System.out.println(m1.equals(m2));

            // compare two Money objects with same values and same devise
            System.out.println(m1.equals(new Money(10, "EUR")));

            // compare two Money objects with same values but different devise
            System.out.println(m1.equals(new Money(10, "USD")));

            // compare two Money objects with different types
            System.out.println(m1.equals(new Object()));

            // money list tests
            MoneyList moneyList = new MoneyList();

            // add money to money list
            moneyList.ajouterSomme(m1);
            System.out.println(moneyList);

            // add money to money list of same devise
            moneyList.ajouterSomme(m2);
            System.out.println(moneyList);

            // add money to money list of different devise
            moneyList.ajouterSomme(new Money(40, "USD"));
            System.out.println(moneyList);

            // make another money list to compare equality
            MoneyList moneyList2 = new MoneyList();
            moneyList2.ajouterSomme(m1);
            moneyList2.ajouterSomme(m2);
            moneyList2.ajouterSomme(new Money(40, "USD"));
            System.out.println(moneyList.equals(moneyList2));

            // make another money list to compare inequality
            MoneyList moneyList3 = new MoneyList();
            moneyList3.ajouterSomme(m1);
            moneyList3.ajouterSomme(m2);
            System.out.println(moneyList.equals(moneyList3));

            // sort money list by amount
            moneyList.triMontant();
            System.out.println(moneyList);

            // sort money list by devise
            moneyList.triDevise();
            System.out.println(moneyList);

        } catch (DeviseException e) {
            e.printStackTrace();
        }
        // 3.1 : Le HashSet est une classe qui permet d'assurer l'unicité des éléments dans une structure de données. Il n'y a pas besoin d'adapter
        try {
            ListeIp listeIp = new ListeIp(false);
            listeIp.chargerFichier("logs");
            System.out.println(listeIp.ips);
            // sorted list
            ListeIp listeIp2 = new ListeIp(true);
            listeIp2.chargerFichier("logs");
            System.out.println(listeIp2.ips);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
