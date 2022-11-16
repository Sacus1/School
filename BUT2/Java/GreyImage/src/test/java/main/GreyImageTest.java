package main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GreyImageTest {

	@Test
	public void EmptyImage() throws WrongSizeException, OutOfBoundException {
		GreyImage img = new GreyImage();
		assertEquals(0, img.getSizeX(),"Size X should be 0");
		assertEquals(0, img.getSizeY(),"Size Y should be 0");
		assertEquals(0, img.getSizeData(),"Size Data should be 0");
	}
	@Test
	public void NormalDimension() throws WrongSizeException, OutOfBoundException {
		GreyImage img = new GreyImage(2,3,new short[6]);
		assertEquals(2, img.getSizeX(),"Size X should be 2");
		assertEquals(3, img.getSizeY(),"Size Y should be 3");
		assertEquals(6, img.getSizeData(),"Size Data should be 6");
	}
	@Test
	public void NegativeValue(){
		assertThrows(OutOfBoundException.class, () -> new GreyImage(-1,3,new short[6]));
	}
	@Test
	public void UnmatchedSize(){
		assertThrows(WrongSizeException.class, () -> new GreyImage(2,3,new short[5]));
	}
	@Test
	public void CopyConstructor() throws WrongSizeException, OutOfBoundException {
		GreyImage img = new GreyImage(2,3,new short[6]);
		GreyImage img2 = new GreyImage(img);
		assertEquals(2, img2.getSizeX(),"Size X should be 2");
		assertEquals(3, img2.getSizeY(),"Size Y should be 3");
		assertEquals(6, img2.getSizeData(),"Size Data should be 6");
	}
	@Test
	public void CopyConstructorModify() throws WrongSizeException, OutOfBoundException {
		GreyImage img = new GreyImage(2,3,new short[6]);
		GreyImage img2 = new GreyImage(img);
		img2.getData()[0] = 1;
		assertEquals(0, img.getData()[0],"Pixel should be 0");
	}
	@Test
	public void getPixel() throws OutOfBoundException, WrongSizeException {
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertEquals(1, img.getPixel(0,0),"Pixel should be 1");
		assertEquals(2, img.getPixel(1,0),"Pixel should be 2");
		assertEquals(3, img.getPixel(0,1),"Pixel should be 3");
		assertEquals(4, img.getPixel(1,1),"Pixel should be 4");
		assertEquals(5, img.getPixel(0,2),"Pixel should be 5");
		assertEquals(6, img.getPixel(1,2),"Pixel should be 6");
	}
	@Test
	public void getPixelOutOfBound() throws WrongSizeException, OutOfBoundException {
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertThrows(OutOfBoundException.class, () -> img.getPixel(-1,0));
		assertThrows(OutOfBoundException.class, () -> img.getPixel(2,0));
		assertThrows(OutOfBoundException.class, () -> img.getPixel(0,-1));
		assertThrows(OutOfBoundException.class, () -> img.getPixel(0,3));
	}
	@Test
	public void getPixelOffset() throws OutOfBoundException, WrongSizeException {
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertEquals(1, img.getPixel(0),"Pixel should be 1");
		assertEquals(2, img.getPixel(1),"Pixel should be 2");
		assertEquals(3, img.getPixel(2),"Pixel should be 3");
		assertEquals(4, img.getPixel(3),"Pixel should be 4");
		assertEquals(5, img.getPixel(4),"Pixel should be 5");
		assertEquals(6, img.getPixel(5),"Pixel should be 6");
	}
	@Test
	public void getPixelOffsetOutOfBound() throws WrongSizeException, OutOfBoundException {
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertThrows(OutOfBoundException.class, () -> img.getPixel(-1));
		assertThrows(OutOfBoundException.class, () -> img.getPixel(6));
	}
	@Test
	public void setPixel() throws OutOfBoundException, WrongSizeException {
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		img.setPixel(0,0, (short) 7);
		img.setPixel(1,0, (short) 8);
		img.setPixel(0,1, (short) 9);
		img.setPixel(1,1, (short) 10);
		img.setPixel(0,2, (short) 11);
		img.setPixel(1,2, (short) 12);
		assertEquals(7, img.getPixel(0,0),"Pixel should be 7");
		assertEquals(8, img.getPixel(1,0),"Pixel should be 8");
		assertEquals(9, img.getPixel(0,1),"Pixel should be 9");
		assertEquals(10, img.getPixel(1,1),"Pixel should be 10");
		assertEquals(11, img.getPixel(0,2),"Pixel should be 11");
		assertEquals(12, img.getPixel(1,2),"Pixel should be 12");
	}
	@Test
	public void setPixelOutOfBound() throws WrongSizeException, OutOfBoundException {
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertThrows(OutOfBoundException.class, () -> img.setPixel(-1,0, (short) 7));
		assertThrows(OutOfBoundException.class, () -> img.setPixel(2,0, (short) 7));
		assertThrows(OutOfBoundException.class, () -> img.setPixel(0,-1, (short) 7));
		assertThrows(OutOfBoundException.class, () -> img.setPixel(0,3, (short) 7));
	}
	@Test
	public void setPixelNegativeValue() throws WrongSizeException, OutOfBoundException {
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertThrows(OutOfBoundException.class, () -> img.setPixel(0,0, (short) -1));
		assertThrows(OutOfBoundException.class, () -> img.setPixel(1,0, (short) -1));
		assertThrows(OutOfBoundException.class, () -> img.setPixel(0,1, (short) -1));
		assertThrows(OutOfBoundException.class, () -> img.setPixel(1,1, (short) -1));
		assertThrows(OutOfBoundException.class, () -> img.setPixel(0,2, (short) -1));
		assertThrows(OutOfBoundException.class, () -> img.setPixel(1,2, (short) -1));
	}
	@Test
	public void setPixelOverflowValue() throws WrongSizeException, OutOfBoundException {
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertThrows(OutOfBoundException.class, () -> img.setPixel(0,0, (short) 256));
		assertThrows(OutOfBoundException.class, () -> img.setPixel(1,0, (short) 257));
		assertThrows(OutOfBoundException.class, () -> img.setPixel(0,1, (short) 258));
	}
	@Test
	public void setPixelTooBigValue() throws WrongSizeException, OutOfBoundException {
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertThrows(OutOfBoundException.class, () -> img.setPixel(0,0, (short) 1000));
		assertThrows(OutOfBoundException.class, () -> img.setPixel(1,0, (short) 1000));
		assertThrows(OutOfBoundException.class, () -> img.setPixel(0,1, (short) 1000));
		assertThrows(OutOfBoundException.class, () -> img.setPixel(1,1, (short) 1000));
		assertThrows(OutOfBoundException.class, () -> img.setPixel(0,2, (short) 1000));
		assertThrows(OutOfBoundException.class, () -> img.setPixel(1,2, (short) 1000));
	}
	@Test
	public void isPosValid() throws WrongSizeException, OutOfBoundException {
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertTrue(img.isPosValid(0,0),"Pixel should be valid");
		assertTrue(img.isPosValid(1,0),"Pixel should be valid");
		assertTrue(img.isPosValid(0,1),"Pixel should be valid");
		assertTrue(img.isPosValid(1,1),"Pixel should be valid");
		assertTrue(img.isPosValid(0,2),"Pixel should be valid");
		assertTrue(img.isPosValid(1,2),"Pixel should be valid");
	}
	@Test
	public void isPosValidOutOfBound() throws WrongSizeException, OutOfBoundException {
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertFalse(img.isPosValid(-1,0),"Pixel should be invalid");
		assertFalse(img.isPosValid(2,0),"Pixel should be invalid");
		assertFalse(img.isPosValid(0,-1),"Pixel should be invalid");
		assertFalse(img.isPosValid(0,3),"Pixel should be invalid");
	}
	@Test
	public void isPosValidOffset() throws WrongSizeException, OutOfBoundException {
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertTrue(img.isPosValid(0),"Pixel should be valid");
		assertTrue(img.isPosValid(1),"Pixel should be valid");
		assertTrue(img.isPosValid(2),"Pixel should be valid");
		assertTrue(img.isPosValid(3),"Pixel should be valid");
		assertTrue(img.isPosValid(4),"Pixel should be valid");
		assertTrue(img.isPosValid(5),"Pixel should be valid");
	}
	@Test
	public void isPosValidOffsetOutOfBound() throws WrongSizeException, OutOfBoundException {
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertFalse(img.isPosValid(-1),"Pixel should be invalid");
		assertFalse(img.isPosValid(6),"Pixel should be invalid");
	}
	@Test
	public void getMin() throws WrongSizeException, OutOfBoundException {
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertEquals(1, img.getMin(),"Min should be 1");
		img.setPixel(0,0, (short) 5);
		assertEquals(2, img.getMin(),"Min should be 2");
	}
	@Test
	public void getMax() throws OutOfBoundException, WrongSizeException {
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertEquals(6, img.getMax(),"Max should be 6");
		img.setPixel(1,2, (short) 1);
		assertEquals(5, img.getMax(),"Max should be 5");
	}
	@Test
	public void Negative() throws OutOfBoundException, WrongSizeException {
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		GreyImage img2 = img.negative();
		short max = img.getMax();
		// pixel i : max - i
		assertEquals(max-1, img2.getPixel(0,0),"Pixel should be 5");
		assertEquals(max-2, img2.getPixel(1,0),"Pixel should be 4");
	}
}
