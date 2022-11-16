package main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GreyImageTest {

	@Test
	public void EmptyImage() {
		GreyImage img = new GreyImage();
		assertEquals(0, img.getSizeX(),"Size X should be 0");
		assertEquals(0, img.getSizeY(),"Size Y should be 0");
		assertEquals(0, img.getSizeData(),"Size Data should be 0");
	}
	@Test
	public void NormalDimension(){
		GreyImage img = new GreyImage(2,3,new short[6]);
		assertEquals(2, img.getSizeX(),"Size X should be 2");
		assertEquals(3, img.getSizeY(),"Size Y should be 3");
		assertEquals(6, img.getSizeData(),"Size Data should be 6");
	}
	@Test
	public void NegativeValue(){
		GreyImage img = new GreyImage(-2,3,new short[6]);
		assertEquals(0, img.getSizeX(),"Size X should be 0");
		assertEquals(3, img.getSizeY(),"Size Y should be 3");
		assertEquals(0, img.getSizeData(),"Size Data should be 0");
	}
	@Test
	public void UnmatchedSize(){
		GreyImage img = new GreyImage(2,3,new short[5]);
		assertEquals(2, img.getSizeX(),"Size X should be 2");
		assertEquals(3, img.getSizeY(),"Size Y should be 3");
		assertEquals(6, img.getSizeData(),"Size Data should be 6");
	}
	@Test
	public void CopyConstructor(){
		GreyImage img = new GreyImage(2,3,new short[6]);
		GreyImage img2 = new GreyImage(img);
		assertEquals(2, img2.getSizeX(),"Size X should be 2");
		assertEquals(3, img2.getSizeY(),"Size Y should be 3");
		assertEquals(6, img2.getSizeData(),"Size Data should be 6");
	}
	@Test
	public void CopyConstructorModify(){
		GreyImage img = new GreyImage(2,3,new short[6]);
		GreyImage img2 = new GreyImage(img);
		img2.getData()[0] = 1;
		assertEquals(0, img.getData()[0],"Pixel should be 0");
	}
	@Test
	public void getPixel(){
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertEquals(1, img.getPixel(0,0),"Pixel should be 1");
		assertEquals(2, img.getPixel(1,0),"Pixel should be 2");
		assertEquals(3, img.getPixel(0,1),"Pixel should be 3");
		assertEquals(4, img.getPixel(1,1),"Pixel should be 4");
		assertEquals(5, img.getPixel(0,2),"Pixel should be 5");
		assertEquals(6, img.getPixel(1,2),"Pixel should be 6");
	}
	@Test
	public void getPixelOutOfBound(){
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertEquals(-1, img.getPixel(-1,0),"Pixel should be -1");
		assertEquals(-1, img.getPixel(2,0),"Pixel should be -1");
		assertEquals(-1, img.getPixel(0,-1),"Pixel should be -1");
		assertEquals(-1, img.getPixel(0,3),"Pixel should be -1");
	}
	@Test
	public void getPixelOffset(){
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertEquals(1, img.getPixel(0),"Pixel should be 1");
		assertEquals(2, img.getPixel(1),"Pixel should be 2");
		assertEquals(3, img.getPixel(2),"Pixel should be 3");
		assertEquals(4, img.getPixel(3),"Pixel should be 4");
		assertEquals(5, img.getPixel(4),"Pixel should be 5");
		assertEquals(6, img.getPixel(5),"Pixel should be 6");
	}
	@Test
	public void getPixelOffsetOutOfBound(){
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertEquals(-1, img.getPixel(-1),"Pixel should be -1");
		assertEquals(-1, img.getPixel(6),"Pixel should be -1");
	}
	@Test
	public void setPixel(){
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
	public void setPixelOutOfBound(){
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		img.setPixel(-1,0, (short) 7);
		img.setPixel(2,0, (short) 8);
		img.setPixel(0,-1, (short) 9);
		img.setPixel(0,3, (short) 10);
		assertEquals(1, img.getPixel(0,0),"Pixel should be 1");
		assertEquals(2, img.getPixel(1,0),"Pixel should be 2");
		assertEquals(3, img.getPixel(0,1),"Pixel should be 3");
		assertEquals(4, img.getPixel(1,1),"Pixel should be 4");
		assertEquals(5, img.getPixel(0,2),"Pixel should be 5");
		assertEquals(6, img.getPixel(1,2),"Pixel should be 6");
	}
	@Test
	public void setPixelNegativeValue(){
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		img.setPixel(0,0, (short) -1);
		img.setPixel(1,0, (short) -2);
		img.setPixel(0,1, (short) -3);
		img.setPixel(1,1, (short) -4);
		img.setPixel(0,2, (short) -5);
		img.setPixel(1,2, (short) -6);
		assertEquals(0, img.getPixel(0,0),"Pixel should be 0");
		assertEquals(0, img.getPixel(1,0),"Pixel should be 0");
		assertEquals(0, img.getPixel(0,1),"Pixel should be 0");
		assertEquals(0, img.getPixel(1,1),"Pixel should be 0");
		assertEquals(0, img.getPixel(0,2),"Pixel should be 0");
		assertEquals(0, img.getPixel(1,2),"Pixel should be 0");
	}
	@Test
	public void setPixelOverflowValue(){
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		img.setPixel(0,0, (short) 256);
		img.setPixel(1,0, (short) 257);
		img.setPixel(0,1, (short) 258);
		img.setPixel(1,1, (short) 259);
		img.setPixel(0,2, (short) 260);
		img.setPixel(1,2, (short) 261);
		assertEquals(255, img.getPixel(0,0),"Pixel should be 255");
		assertEquals(255, img.getPixel(1,0),"Pixel should be 255");
		assertEquals(255, img.getPixel(0,1),"Pixel should be 255");
		assertEquals(255, img.getPixel(1,1),"Pixel should be 255");
		assertEquals(255, img.getPixel(0,2),"Pixel should be 255");
		assertEquals(255, img.getPixel(1,2),"Pixel should be 255");
	}
	@Test
	public void setPixelTooBigValue(){
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		img.setPixel(0,0, (short) 256);
		img.setPixel(1,0, (short) 257);
		img.setPixel(0,1, (short) 258);
		img.setPixel(1,1, (short) 259);
		img.setPixel(0,2, (short) 260);
		img.setPixel(1,2, (short) 261);
		assertEquals(255, img.getPixel(0,0),"Pixel should be 255");
		assertEquals(255, img.getPixel(1,0),"Pixel should be 255");
		assertEquals(255, img.getPixel(0,1),"Pixel should be 255");
		assertEquals(255, img.getPixel(1,1),"Pixel should be 255");
		assertEquals(255, img.getPixel(0,2),"Pixel should be 255");
		assertEquals(255, img.getPixel(1,2),"Pixel should be 255");
	}
	@Test
	public void isPosValid(){
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertTrue(img.isPosValid(0,0),"Pixel should be valid");
		assertTrue(img.isPosValid(1,0),"Pixel should be valid");
		assertTrue(img.isPosValid(0,1),"Pixel should be valid");
		assertTrue(img.isPosValid(1,1),"Pixel should be valid");
		assertTrue(img.isPosValid(0,2),"Pixel should be valid");
		assertTrue(img.isPosValid(1,2),"Pixel should be valid");
	}
	@Test
	public void isPosValidOutOfBound(){
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertFalse(img.isPosValid(-1,0),"Pixel should be invalid");
		assertFalse(img.isPosValid(2,0),"Pixel should be invalid");
		assertFalse(img.isPosValid(0,-1),"Pixel should be invalid");
		assertFalse(img.isPosValid(0,3),"Pixel should be invalid");
	}
	@Test
	public void isPosValidOffset(){
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertTrue(img.isPosValid(0),"Pixel should be valid");
		assertTrue(img.isPosValid(1),"Pixel should be valid");
		assertTrue(img.isPosValid(2),"Pixel should be valid");
		assertTrue(img.isPosValid(3),"Pixel should be valid");
		assertTrue(img.isPosValid(4),"Pixel should be valid");
		assertTrue(img.isPosValid(5),"Pixel should be valid");
	}
	@Test
	public void isPosValidOffsetOutOfBound(){
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertFalse(img.isPosValid(-1),"Pixel should be invalid");
		assertFalse(img.isPosValid(6),"Pixel should be invalid");
	}
	@Test
	public void getMin(){
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertEquals(1, img.getMin(),"Min should be 1");
		img.setPixel(0,0, (short) 5);
		assertEquals(2, img.getMin(),"Min should be 2");
	}
	@Test
	public void getMax(){
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		assertEquals(6, img.getMax(),"Max should be 6");
		img.setPixel(1,2, (short) 1);
		assertEquals(5, img.getMax(),"Max should be 5");
	}
	@Test
	public void Negative(){
		GreyImage img = new GreyImage(2,3,new short[]{1,2,3,4,5,6});
		GreyImage img2 = img.negative();
		short max = img.getMax();
		// pixel i : max - i
		assertEquals(max-1, img2.getPixel(0,0),"Pixel should be 5");
		assertEquals(max-2, img2.getPixel(1,0),"Pixel should be 4");
	}
}
