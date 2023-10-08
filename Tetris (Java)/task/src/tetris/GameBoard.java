package tetris;

import java.util.Arrays;

class GameBoard {

    private final int width;
    private final int height;
    private final char[][] board;
    private TetrisPiece currentPiece;
    private int piecePositionX, piecePositionY;
    private boolean isFixed = false;
    private boolean isGameOver = false;

    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        board = new char[height][width];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < height; i++) {
            Arrays.fill(board[i], '-');
        }
        piecePositionX = 0;
        piecePositionY = 0;
    }

    public void setCurrentPiece(TetrisPiece piece) {
        this.currentPiece = piece;
        piece.currentRotation = 0;
        piecePositionX = 0;
        piecePositionY = 0;
        isFixed = false;
        placeCurrentPiece();
    }

    private void handleLineCompletion() {
        for (int i = height - 1; i >= 0; i--) {
            while (isLineComplete(i)) {
                removeLine(i);
            }
        }
    }

    private boolean isLineComplete(int rowIndex) {
        for (int j = 0; j < width; j++) {
            if (board[rowIndex][j] == '-') {
                return false;
            }
        }
        return true;
    }

    private void removeLine(int rowIndex) {
        for (int i = rowIndex; i > 0; i--) {
            board[i] = board[i - 1].clone();
        }
        Arrays.fill(board[0], '-');
    }

    public void breakLines() {
        handleLineCompletion();
    }

    private void clearCurrentPiece() {
        for (int index : currentPiece.getCurrentRotation()) {
            int x = piecePositionX + index % 10;
            int y = piecePositionY + index / 10;
            if (x >= 0 && x < width && y >= 0 && y < height) { // Ensure we are within the board boundaries
                board[y][x] = '-';
            }
        }
    }

    public void placeCurrentPiece() {
        for (int index : currentPiece.getCurrentRotation()) {
            int x = piecePositionX + index % 10;
            int y = piecePositionY + index / 10;
            if (x >= 0 && x < width && y >= 0 && y < height) { // Ensure we are within the board boundaries
                board[y][x] = '0';
            }
        }
    }

    private boolean canMoveLeft() {
        for (int index : currentPiece.getCurrentRotation()) {
            int x = piecePositionX + index % 10 - 1;
            int y = piecePositionY + index / 10;
            if (x < 0 || (y >= 0 && y < height && board[y][x] == '0')) {
                return false;
            }
        }
        return true;
    }

    private boolean canMoveRight() {
        for (int index : currentPiece.getCurrentRotation()) {
            int x = piecePositionX + index % 10 + 1;
            int y = piecePositionY + index / 10;
            if (x >= width || (y >= 0 && y < height && board[y][x] == '0')) {
                return false;
            }
        }
        return true;
    }

    private boolean canMoveDown() {
        for (int index : currentPiece.getCurrentRotation()) {
            int x = piecePositionX + index % 10;
            int y = piecePositionY + index / 10 + 1;
            if (y >= height || (x >= 0 && x < width && board[y][x] == '0')) {
                return false;
            }
        }
        return true;
    }

    private boolean canRotate() {
        int[] nextRotation = currentPiece.getNextRotation();

        for (int index : nextRotation) {
            int x = piecePositionX + index % 10;
            int y = piecePositionY + index / 10;
            if (x < 0 || x >= width || y < 0 || y >= height || board[y][x] == '0') {
                return false;
            }
        }
        return true;
    }

    public void moveLeft() {
        clearCurrentPiece();
        if (!isFixed) {
            if (canMoveLeft()) {
                piecePositionX--;
            }
            if (canMoveDown()) {
                piecePositionY++;
                if (!canMoveDown()) {
                    isFixed = true;
                }
            } else {
                isFixed = true;
            }
        }
        placeCurrentPiece();
    }

    public void moveRight() {
        clearCurrentPiece();
        if (!isFixed) {
            if (canMoveRight()) {
                piecePositionX++;
            }
            if (canMoveDown()) {
                piecePositionY++;
                if (!canMoveDown()) {
                    isFixed = true;
                }
            } else {
                isFixed = true;
            }
        }
        placeCurrentPiece();
    }

    public void rotatePiece() {
        clearCurrentPiece();
        if (!isFixed) {
            if (canRotate()) {
                currentPiece.rotate();
                if (canMoveDown()) {
                    piecePositionY++;
                    if (!canMoveDown()) {
                        isFixed = true;
                    }
                } else {
                    isFixed = true;
                }
            }
        }
        placeCurrentPiece();
    }

    public void moveDown() {
        clearCurrentPiece();
        if (!isFixed) {
            if (canMoveDown()) {
                piecePositionY++;
                if (!canMoveDown()) {
                    isFixed = true;
                    if (isPieceTouchingTop()) {
                        isGameOver = true;
                    }
                }
            } else {
                isFixed = true;
                if (isPieceTouchingTop()) {
                    isGameOver = true;
                }
            }
        }
        placeCurrentPiece();
    }

    private boolean isPieceTouchingTop() {
        for (int index : currentPiece.getCurrentRotation()) {
            int y = piecePositionY + index / 10;
            if (y == 0) {
                return true;
            }
        }
        return false;
    }

    boolean isGameOver() {
        return isGameOver;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] row : board) {
            for (char cell : row) {
                sb.append(cell).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
