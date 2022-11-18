package main;

import java.io.IOException;

public class GreyImage {
	private int dimX = 0;
	private int dimY = 0;
	private int size = 0; // dimensions and size of the image
	private short[] data = new short[0]; // array of pixels

	public GreyImage(int dimX, int dimY, short[] data) throws WrongSizeException, OutOfBoundException {
		if (dimX < 0 || dimY < 0)
			throw new OutOfBoundException("The dimensions of the image must be positive");
		this.dimX = dimX;
		this.dimY = dimY;
		this.size = this.dimX * this.dimY;
		this.data = data;
		// check if the size of the array is correct
		if (data.length != size)
			throw new WrongSizeException();
	}

	public GreyImage() throws WrongSizeException, OutOfBoundException {
		this(0, 0, new short[0]);
	}

	public GreyImage(GreyImage img) throws WrongSizeException, OutOfBoundException {
		this(img.dimX, img.dimY, new short[img.dimX * img.dimY]); // copy constructor
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
	public short getPixel(int x, int y) throws OutOfBoundException {
		if (x < 0 || x >= dimX || y < 0 || y >= dimY)
			throw new OutOfBoundException("The position is not valid");
		else
			return data[x + y * dimX];
	}

	/**
	 * {@link GreyImage#getPixel(int, int)}
	 */
	public short getPixel(int i) throws OutOfBoundException {
		if (i < 0 || i >= size)
			throw new OutOfBoundException("The position is not valid");
		else
			return data[i];
	}

	/**
	 * Set the pixel value at position (x,y) , if the position is not valid, do nothing
	 */
	public void setPixel(int x, int y, short value) throws OutOfBoundException {
		if (!(x < 0 || x >= dimX || y < 0 || y >= dimY)) {
			if (value < 0 || value > 255)
				throw new OutOfBoundException("The value is not valid");
			else
				data[x + y * dimX] = value;
		} else
			throw new OutOfBoundException("The position is not valid");
	}

	/**
	 * is the position valid ?
	 *
	 * @param x the x position
	 * @param y the y position
	 * @return true if the position is valid
	 */
	public boolean isPosValid(int x, int y) {
		return !(x < 0 || x >= dimX || y < 0 || y >= dimY);
	}

	/**
	 * is the position valid ?
	 *
	 * @param i the position
	 * @return true if the position is valid
	 */
	public boolean isPosValid(int i) {
		return !(i < 0 || i >= size);
	}

	/**
	 * get the minimum grey level of the image
	 *
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
	 *
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
	 *
	 * @return the negative of the image
	 */
	public void negative() throws WrongSizeException, OutOfBoundException {
		short max = getMax();
		for (int i = 0; i < size; i++)
			data[i] = (short) (max - data[i]);
	}
	public static GreyImage loadPGM(String filename) throws IOException {
		PGMFileIO pgm = new PGMFileIO(filename);
		GreyImage img = null;
		pgm.readPGM();
		try {
			img = new GreyImage(pgm.getSizeX(), pgm.getSizeY(), pgm.getData());
		} catch (WrongSizeException | OutOfBoundException e) {
			e.printStackTrace();
		}
		return img;
	}
	public void writePGM(String filename) throws IOException {
		PGMFileIO pgm = new PGMFileIO(filename);
		pgm.writePGM(dimX, dimY, data);
	}
}
