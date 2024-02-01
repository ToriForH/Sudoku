package sudoku.base;

import sudoku.constants.SudokuState;

import java.io.Serializable;
import java.util.Arrays;

public class SudokuGame implements Serializable {
    public static final int GRID_BOUNDARY = 9;
    private final SudokuState sudokuState;
    private SudokuCell[][] sudokuGrid = new SudokuCell[GRID_BOUNDARY][GRID_BOUNDARY];

    public SudokuGame(SudokuState sudokuState, SudokuCell[][] sudokuGrid) {
        this.sudokuState = sudokuState;
        this.sudokuGrid = sudokuGrid;
    }

    public SudokuState getGameState() {
        return sudokuState;
    }

    public SudokuCell[][] getCopyOfSudokuGrid() {
        SudokuCell[][] copyOfArray = new SudokuCell[GRID_BOUNDARY][GRID_BOUNDARY];
        SudokuGame.copySudokuGridValues(this.sudokuGrid, copyOfArray);
        return copyOfArray;
    }

    public static void copySudokuGridValues(SudokuCell[][] oldGrid, SudokuCell[][] newGrid) {
        for (int x = 0; x < GRID_BOUNDARY; x++) {
            for (int y = 0; y < GRID_BOUNDARY; y++) {
                newGrid[x][y] = oldGrid[x][y].getCopy();
            }
        }
    }

    public static void copySudokuArrayValues(int[][] oldArray, int[][] newArray) {
        for (int x = 0; x < GRID_BOUNDARY; x++) {
            newArray[x] = Arrays.copyOf(oldArray[x], GRID_BOUNDARY);
        }
    }
}
