package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

		@Test
		void Test_Isosceles() {
			Triangle triangle = new Triangle(2, 2, 1);
			assertEquals(2, triangle.triangleType(), "Le retour attendu est 2");
			Triangle triangle2 = new Triangle(2,1,2);
			assertEquals(2, triangle2.triangleType(), "Le retour attendu est 2");
			Triangle triangle3 = new Triangle(1,2,2);
			assertEquals(2, triangle3.triangleType(), "Le retour attendu est 2");
			Triangle triangle4 = new Triangle(Integer.MAX_VALUE,Integer.MAX_VALUE-1,Integer.MAX_VALUE-1);
			assertEquals(2, triangle4.triangleType(), "Le retour attendu est 2");
		}
		@Test
		void Test_Equilateral() {
			Triangle triangle = new Triangle(2, 2, 2);
			assertEquals(1, triangle.triangleType(), "Le retour attendu est 1");
		}

		@Test
		void Test_Quelconque() {
			Triangle triangle = new Triangle(2, 3, 4);
			assertEquals(3, triangle.triangleType(), "Le retour attendu est 3");
		}

		@Test
		void Test_NonTriangle() {
			Triangle triangle = new Triangle(3, 4, -2);
			assertEquals(4, triangle.triangleType());
			Triangle triangle1 = new Triangle(Integer.MIN_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
			assertEquals(4, triangle1.triangleType(), "Le retour attendu est 4");
			Triangle triangle2 = new Triangle(-5,-5,-5);
			assertEquals(4, triangle2.triangleType(), "Le retour attendu est 4");
		}

}
