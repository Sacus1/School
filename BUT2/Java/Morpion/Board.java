import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    //contient les configurations possibles à jouer à partir d’une configuration
//remplie par la fonction minmax
    ArrayList<PointsAndScores> successeursScores;
    Scanner scan = new Scanner(System.in);
    //tableau de jeu
    int[][] board = new int[3][3];

    /** @return le point avec le score le plus élevé de la liste des successeurs */
    public Point returnBestMove(){
        int MAX = Integer.MIN_VALUE;
        int best = -1;
        for (int i = 0; i < successeursScores.size(); ++i) {
            if (MAX < successeursScores.get(i).getScore()) {
                MAX = successeursScores.get(i).getScore();
                best = i;
            }
        }
        return successeursScores.get(best).getPoint();
    }

    /**Permet d’appeler la fonction minmax après avoir initialisé la liste des successeurs.*/
    public void callMinimax(int depth, int turn){
        successeursScores = new ArrayList<>();
        minimax(depth, turn);
        placeAMove(returnBestMove(), 1);
    }
    /** Fonction minmax recurssive à partir du niveau (depth)
     * pour n’importe quel joueur (turn).
     * turn=1 : joueur qui maximise; turn=2 : joueur qui minimise
     * @return le meilleur score */
    public int minimax(int depth, int turn)
    {
        //si X a gagné renvoie +1 //score = 1
        if (hasXWon()) return +1;
        //si O a gagné renvoie -1 //score = -1
        if (hasOWon()) return -1;
        //créer la liste des points possibles (getAvailablePoints())
        ArrayList<Point> pointsAvailable = getAvailablePoints();
        //si cette liste est vide renvoie 0 //score = 0
        if (pointsAvailable.isEmpty()) return 0;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        //-- if (turn == 1 ) // X sélectionne le MAX des scores
        if (turn == 1) {
            //for tout point possible faire :
            for (Point point : pointsAvailable) {
                //on place le mouvement
                placeAMove(point, 1);
                int currentScore = minimax(depth + 1, 2);
                if (max < currentScore) max = currentScore;
                if (depth == 0) // on garde le premier "étage" de l’arbre du jeux
                    successeursScores.add(new PointsAndScores(currentScore, point));
                //on réinitialise le point:
                board[point.x][point.y] = 0;
            }
        }
        //-- if (turn == 2 ) // O sélectionne le MIN des scores
        if (turn == 2){
        //for tout point possible faire :
            for (Point point : pointsAvailable) {
                //on place le mouvement
                placeAMove(point, 2);
                int currentScore = minimax(depth + 1, 1);
                if (min > currentScore) min = currentScore;
                if (depth == 0) // on garde le premier "étage" de l’arbre du jeux
                    successeursScores.add(new PointsAndScores(-currentScore, point));
                //on réinitialise le point:
                board[point.x][point.y] = 0;
            }
        }
        return turn == 1 ? max : min;
    }
    /**affiche le plateau de jeu*/
    public void displayBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] == 1 ? "X" : board[i][j] == 2?"O":"_");
            }
            System.out.println();
        }
    }

    /**
     *  met dans le plateau à la case point, le symbole pour le joueur : player=1 pour X, player=2 pour O
    *@param point le point à placer
     * @param player le joueur qui joue
   */
    public void placeAMove(Point point, int player) {
        // check if the point is valid
        if (point.x < 0 || point.x > 2 || point.y < 0 || point.y > 2 || board[point.x][point.y] != 0)
            throw new IllegalArgumentException("Invalid board position");

        board[point.x][point.y] = player;
    }

    /**Invite l’utilisateur a saisir au clavier les coordonnées d’un point (i,j) et
     place le mouvement sur le plateau pour le joueur passé en paramètre.*/
    void takeHumanInput(int player){
        System.out.println("Enter the coordinates of your move (i,j) : ");
        // read the input
        int i = scan.nextInt();
        int j = scan.nextInt();
        Point point = new Point(i,j);
        try {
            placeAMove(point, player);

        } catch (IllegalArgumentException e) {
            // if the move is invalid, ask again
            System.out.println("Invalid move");
            takeHumanInput(player);
        }
    }

    /** @return  la liste des cases vides du plateau*/
    public ArrayList<Point> getAvailablePoints(){
        ArrayList<Point> availablePoints = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if(board[i][j] == 0)
                    availablePoints.add(new Point(i,j));
        return availablePoints;

    }

    /** vérifie si X a fait l’une des 2 diagonales ou une ligne ou une colonne*/
    public boolean hasXWon(){
        if (board[0][0] == 1 && board[0][1] == 1 && board[0][2] == 1)  // diagonale haut gauche vers bas droite
            return true;

        if (board[0][2] == 1 && board[1][1] == 1 && board[2][0] == 1)  // diagonale haut droite vers bas gauche
            return true;

        for (int i = 0; i < 3; i++) {
            if (board[i][0] == 1 && board[i][1] == 1 && board[i][2] == 1)  // ligne
                return true;

            if (board[0][i] == 1 && board[1][i] == 1 && board[2][i] == 1)  // colonne
                return true;

        }
        return false;

    }

    /** vérifie si O a fait l’une des 2 diagonales ou une ligne ou une colonne*/
    public boolean hasOWon(){
        if (board[0][0] == 2 && board[0][1] == 2 && board[0][2] == 2)  // diagonale haut gauche vers bas droite
            return true;

        if (board[0][2] == 2 && board[1][1] == 2 && board[2][0] == 2)  // diagonale haut droite vers bas gauche
            return true;

        for (int i = 0; i < 3; i++) {
            if (board[i][0] == 2 && board[i][1] == 2 && board[i][2] == 2) // ligne
                return true;

            if (board[0][i] == 2 && board[1][i] == 2 && board[2][i] == 2)  // colonne
                return true;

        }
        return false;
    }

    /** vérifie si le jeu est fini (quelqu’un a gagné OU le tableau est plein (égalité))*/
    public boolean isGameOver(){
        return (hasXWon() || hasOWon() || getAvailablePoints().isEmpty());
    }
}
