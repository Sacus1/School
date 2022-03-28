public class App {
    private static Graphe FranceModerne() {
        Graphe regions = new Graphe(13);
        int i = 0;
        for (String Region : new String[] { /* 0 */ "Bretagne",
                /* 1 */ "Normandie",
                /* 2 */ "Pays de La Loire",
                /* 3 */ "Nouvelle-Aquitaine",
                /* 4 */ "Occitanie",
                /* 5 */ "Centre-Val de Loire",
                /* 6 */ "Ile-de-France",
                /* 7 */ "Provence-Alpes-Côtes d'Azure",
                /* 8 */ "Auvergne-Rhône-Alpes",
                /* 9 */ "Grand-Est",
                /* 10 */ "Hauts-de-France",
                /* 11 */ "Corse",
                /* 12 */ "Bourgogne-Franche-Compté" }) {
            regions.add(new Sommet(Region));
        }
        i = 0;
        for (int[] Dests : new int[][] { { 1, 2 }, { 2, 4, 5, 10 }, { 3, 5 }, { 4, 5, 8 }, { 7, 8, 11 }, { 6, 8, 12 },
                { 9, 10, 12 }, { 8, 11 }, { 12 }, { 10 } }) {
            for (int Dest : Dests) {
                double cout = Math.random() * 10;
                regions.Get(i).NewChemin(new Chemin(regions.Get(Dest), cout));
                regions.Get(Dest).NewChemin(new Chemin(regions.Get(i), cout));
            }
            i++;
        }
        return regions;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(FranceModerne());

    }
}