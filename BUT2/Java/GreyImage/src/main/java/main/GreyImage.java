package main;

public class GreyImage {
	private final int dimX, dimY, size; // dimensions and size of the image
	private final short[] data; // array of pixels
	public GreyImage(int dimX,int dimY,short[] data) {
		this.dimX = Math.max(dimX, 0);
		this.dimY = Math.max(dimY, 0);
		this.size = this.dimX*this.dimY;
		// check if the size of the array is correct
		if (data.length != size)
			this.data = new short[size];
		else
			this.data = data;
	}
	public GreyImage(){
		this(0,0,new short[0]);
	}

	public GreyImage(GreyImage img){
		this(img.dimX,img.dimY,new short[img.dimX*img.dimY]); // copy constructor
		System.arraycopy(img.data, 0, this.data, 0, size);
	}
	/**
	* Return the size X of the image
	 */
	public int getSizeX() {
		return dimX;
	}
	/**
	* Return the size Y of the image
	 */
	public int getSizeY() {
		return dimY;
	}
	/**
	* Return the size of the image
	 */
	public int getSizeData() {
		return size;
	}
	/**
	* Return the content of the image
	 */
	public short[] getData() {
		return data;
	}
	/**
	* Return the pixel value at position (x,y) , if the position is not valid, return -1
	 */
	public short getPixel(int x, int y) {
		if (x < 0 || x >= dimX || y < 0 || y >= dimY)
			return -1;
		else
			return data[x + y * dimX];
	}
	/**
	* {@link GreyImage#getPixel(int,int)}
	 */
	public short getPixel(int i) {
		if (i < 0 || i >= size)
			return -1;
		else
			return data[i];
	}
	/**
	* Set the pixel value at position (x,y) , if the position is not valid, do nothing
	 */
	public void setPixel(int x, int y, short value) {
		if (!(x < 0 || x >= dimX || y < 0 || y >= dimY))
			if (value < 0)
				data[x + y * dimX] = 0;
			else if (value > 255)
				data[x + y * dimX] = 255;
			else
				data[x + y * dimX] = value;
	}

	/**
	 * is the position valid ?
	 * @param x the x position
	 * @param y the y position
	 * @return true if the position is valid
	 */
	public boolean isPosValid(int x, int y) {
		return !(x < 0 || x >= dimX || y < 0 || y >= dimY);
	}
	/**
	 * is the position valid ?
	 * @param i the position
	 * @return true if the position is valid
	 */
	public boolean isPosValid(int i) {
		return !(i < 0 || i >= size);
	}
	/**
	 * get the minimum grey level of the image
	 * @return the minimum grey level
	 */
	public short getMin() {
		short min = data[0];
		for (int i = 1; i < size; i++)
			if (data[i] < min)
				min = data[i];
		return min;
	}
	/**
	 * get the maximum grey level of the image
	 * @return the maximum grey level
	 */
	public short getMax() {
		short max = data[0];
		for (int i = 1; i < size; i++)
			if (data[i] > max)
				max = data[i];
		return max;
	}
	/**
	 * negative of the image
	 * @return the negative of the image
	 */
	public GreyImage negative() {
		GreyImage img = new GreyImage(this);
		short max = getMax();
		for (int i = 0; i < size; i++)
			img.data[i] = (short) (max - data[i]);
		return img;
	}
}
