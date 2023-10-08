package tetris;

import java.util.Scanner;

public class TetrisGame {
    private final GameBoard board;

    public TetrisGame(int width, int height) {
        this.board = new GameBoard(width, height);
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println(board);
            String command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "left":
                    board.moveLeft();
                    break;
                case "right":
                    board.moveRight();
                    break;
                case "down":
                    board.moveDown();
                    break;
                case "rotate":
                    board.rotatePiece();
                    break;
                case "piece":
                    String newTetrisPiece = scanner.nextLine().trim();
                    board.setCurrentPiece(TetrisPiece.valueOf(newTetrisPiece));
                    break;
                case "break":
                    board.breakLines();
                    break;
                case "exit":
                    isRunning = false;
                    break;
            }
            if (board.isGameOver()) {
                System.out.println(board);
                System.out.println("Game Over!");
                isRunning = false;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] gridDimensions = scanner.nextLine().split(" ");
        int M = Integer.parseInt(gridDimensions[0]);
        int N = Integer.parseInt(gridDimensions[1]);
        TetrisGame game = new TetrisGame(M, N);
        game.startGame();
    }
}
