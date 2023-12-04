public class Morpion {
  public static void main(String[] args) {
    Board board = new Board();
    // while the game is not over
    while (!board.isGameOver()) {

      board.callMinimax(0, 1);
      // check if the player that just played won
      if (board.hasXWon()) {
        System.out.println("X won");
        board.displayBoard();
        return;
      }
      board.displayBoard();
      // take the input
      board.takeHumanInput(2);
      if (board.hasOWon()) {
        System.out.println("O won");
        board.displayBoard();
        return;
      }
      // display the board
      board.displayBoard();
    }
    // if the game is over and no one won, it's a draw
    System.out.println("Draw");
  }
}
