package tetris;

import java.util.*;

enum TetrisPiece {
    O(new int[][]{{4, 14, 15, 5}}),
    I(new int[][]{{4, 14, 24, 34}, {3, 4, 5, 6}}),
    S(new int[][]{{5, 4, 14, 13}, {4, 14, 15, 25}}),
    Z(new int[][]{{4, 5, 15, 16}, {5, 15, 14, 24}}),
    L(new int[][]{{4, 14, 24, 25}, {5, 15, 14, 13}, {4, 5, 15, 25}, {6, 5, 4, 14}}),
    J(new int[][]{{5, 15, 25, 24}, {15, 5, 4, 3}, {5, 4, 14, 24}, {4, 14, 15, 16}}),
    T(new int[][]{{4, 14, 24, 15}, {4, 13, 14, 15}, {5, 15, 25, 14}, {4, 5, 6, 15}});

    private final List<int[]> rotations;
    int currentRotation;

    TetrisPiece(int[][] rotations) {
        this.rotations = Arrays.asList(rotations);
        this.currentRotation = 0;
    }

    public int[] getCurrentRotation() {
        return rotations.get(currentRotation);
    }

    public void rotate() {
        currentRotation = (currentRotation + 1) % rotations.size();

    }

    public int[] getNextRotation() {
        int nextRotationIndex = (currentRotation + 1) % rotations.size();
        return rotations.get(nextRotationIndex);
    }

}
