public abstract class Chambre implements java.io.Serializable {
	private int no;
  public int nbLits;
	public Chambre(int nbLits) {
		this.nbLits = nbLits;
		no = 0;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	abstract public String typeChambre();

	@Override
	public String toString() {
		boolean hasDouche = this instanceof Douche;
		return "Chambre " + no + " " + typeChambre() + " avec " + nbLits + " lits" + (hasDouche ? " et douche" : "");
	}
}
