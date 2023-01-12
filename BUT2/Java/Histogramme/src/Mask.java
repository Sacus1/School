public class Mask extends GreyImage{
	public Mask(int dimX, int dimY,short[] data) throws OutOfBoundException, WrongSizeException {
      super(dimX, dimY, data);
  }
	double getSumWeight()  {
		double sum = 0;
		for (int i = 0; i < getSizeData(); i++) {
			try {
				sum += getPixel(i);
			}
			catch (OutOfBoundException e){
				System.out.println("Error: " + e.getMessage());
			}
		}
		return sum;
	}
}
