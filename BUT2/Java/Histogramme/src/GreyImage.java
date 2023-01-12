import java.io.IOException;

public class GreyImage {
	private int dimX = 0;
	private int dimY = 0;
	private int size = 0; // dimensions and size of the image
	private short[] data; // array of pixels

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
	 * Return the pixel value at position (x,y) , if the position is not valid throw an exception
	 * @throws OutOfBoundException
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
	 * Set the pixel value at position (x,y) , if the position is not valid, throw an exception
	 * @throws OutOfBoundException
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

	public void writePGM(String filename) throws IOException {
		PGMFileIO pgm = new PGMFileIO(filename);
		pgm.writePGM(dimX, dimY, data);
	}

	void adjustContrast(int min, int max) {
		int gmin = getMin();
		int gmax = getMax();
		int gdiff = gmax - gmin;
		int diff = max - min;
		for (int i = 0; i < size; i++) {
			data[i] = (short) (min + diff * (data[i] - gmin) / gdiff);
		}
	}

	void equalize(int min, int max) {
		Histogram histogram = new Histogram(this, 256);
		int[] cum = new int[256];
		for (int i = 0; i < 256; i++) {
			cum[i] = histogram.data[i];
			if (i > 0)
				cum[i] += cum[i - 1];
		}
		for (int i = 0; i < size; i++) {
			data[i] = (short) (255 * cum[data[i]] / size);
		}
	}

	void seiller(int seuil) {
		for (int i = 0; i < size; i++) {
			if (data[i] < seuil)
				data[i] = 0;
			else
				data[i] = 255;
		}
	}
	void truncate(short min,short max){
		for (int i = 0; i < size; i++) {
			if (data[i] < min)
				data[i] = min;
			else if(data[i]>max)
				data[i]=max;
		}
	}

	GreyImage convolve(Mask M) {
		GreyImage img = null;
		try {
			img = new GreyImage(dimX, dimY,new short[dimX*dimY]);
		} catch (WrongSizeException | OutOfBoundException e) {
			e.printStackTrace();
		}
		int x, y;
		// size of M = 2p +1 * 2p +1
		int p = M.getSizeX() / 2;
		for (int i = 0; i < size; i++) {
			x = i % dimX;
			y = i / dimX;
			if (!isPosValid(x-p, y-p) || !isPosValid(x+p, y+p)) {
				continue;
			}
			for (int k = 0; k < 2 * p + 1; k++) {
				for (int l = 0; l < 2 * p + 1; l++) {
					try {
						short val = getPixel(x + k - p, y + l - p);
						short valM = M.getPixel(k, l);
						img.data[i] += val * valM;
					} catch (OutOfBoundException e) {
						System.err.println("Error in convolve at position (" + x + "," + y + ")");
						return null;
					}
				}
			}
			if (M.getSumWeight() > 0) {
				img.data[i] = (short) (img.data[i] / M.getSumWeight());
			}
		}
		img.truncate((short)0,(short)255);
		return img;
	}
}
