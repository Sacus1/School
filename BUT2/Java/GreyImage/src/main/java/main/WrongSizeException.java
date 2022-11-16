package main;

public class WrongSizeException extends Exception {
	public WrongSizeException() {
          super("The size of the array is not correct");
	}
}
