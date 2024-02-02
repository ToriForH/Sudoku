package sudoku.logic;

import sudoku.base.SudokuCell;
import sudoku.constants.SudokuState;
import sudoku.base.SudokuGame;

import java.util.*;

import static sudoku.base.SudokuGame.GRID_BOUNDARY;

public class SudokuLogic {
    public static SudokuGame getNewSudoku() {
        SudokuCell [][] newSudokuGrid = SudokuGenerator.getNewSudokuGrid();
        return new SudokuGame(SudokuState.NEW, newSudokuGrid);
    }

    public static SudokuState checkForCompletion(SudokuCell[][] grid) {
        int[][] gridAsArray = SudokuCell.SudokuGridToArray(grid);
        if (sudokuIsInvalid(gridAsArray)) return SudokuState.ACTIVE;
        if (gridHasEmptyCells(gridAsArray)) return SudokuState.ACTIVE;
        return SudokuState.COMPLETE;
    }

    static boolean sudokuIsInvalid(int[][] grid) {
        if (rowsOrColumnsAreInvalid(grid)) return true;
        return squaresAreInvalid(grid);
    }

    private static boolean rowsOrColumnsAreInvalid(int[][] grid) {
        List<List<Integer>> columns = new ArrayList<>();
        for (int x = 0; x < GRID_BOUNDARY; x++) {
            List <Integer> row = new ArrayList<>();
            for (int y = 0; y < GRID_BOUNDARY; y++) {
                columns.add(new ArrayList<>());
                if(grid[x][y] > 0) {
                    row.add(grid[x][y]);
                    columns.get(y).add(grid[x][y]);
                }
            }
            if (collectionHasRepeats(row)) return true;
        }
        for (List<Integer> column : columns) {
            if (collectionHasRepeats(column)) return true;
        }
        return false;
    }

    private static boolean squaresAreInvalid(int[][] grid) {
        List<List<Integer>> squares = new ArrayList<>();
        for(int x = 0; x < GRID_BOUNDARY; x++) {
            for(int y = 0; y < GRID_BOUNDARY; y++) {
                if (squares.size() < GRID_BOUNDARY) {
                    squares.add(new ArrayList<>());
                }
                int squareIndex = (x / 3) + ((y / 3) * 3);
                if (grid[x][y] > 0) {
                    squares.get(squareIndex).add(grid[x][y]);
                }
            }
        }
        for (List<Integer> square: squares) {
            if (collectionHasRepeats(square)) return true;
        }
        return false;
    }

    private static boolean collectionHasRepeats(List<Integer> list) {
        Set<Integer> set = new HashSet<Integer>(list);
        return set.size() < list.size();
    }

    private static boolean gridHasEmptyCells(int[][] grid) {
        for (int x = 0; x < GRID_BOUNDARY; x++) {
            for (int y = 0; y < GRID_BOUNDARY; y++) {
                if (grid[x][y] == 0) return true;
            }
        }
        return false;
    }
}
