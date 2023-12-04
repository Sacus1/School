public class Main {
  public static void main(String[] args) {
    Complexe cplx = new Complexe(10, 5);
    System.out.println(cplx);
    System.out.println(cplx.conjugue());
    System.out.println(cplx.partieReelle());
    System.out.println(cplx.partieImaginaire());
    System.out.println(cplx.module());
    System.out.println(cplx.argument());
    System.out.println(cplx.estReelPur());
    System.out.println(new Complexe(10, 0).estReelPur());
    System.out.println(cplx.somme(new Complexe(5, 10)));
    System.out.println(cplx.produit(new Complexe(1, 2)));
  }
}
