package sudoku.logic;

import sudoku.base.SudokuCell;
import sudoku.base.SudokuGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sudoku.base.SudokuGame.GRID_BOUNDARY;

public class SudokuGenerator {
    private static final Random RANDOM = new Random();
    public static SudokuCell[][] getNewSudokuGrid() {
        int[][] sudokuArray = unsolveSudoku(getSolvedSudoku());
        return SudokuCell.arrayToSudokuCellGrid(sudokuArray);
    }

    private static int[][] getSolvedSudoku() {
        int[][] solvedSudoku = new int[GRID_BOUNDARY][GRID_BOUNDARY];
        List<List<Integer>> availableNumbers = new ArrayList<>();
        for (int i = 0; i < (GRID_BOUNDARY * GRID_BOUNDARY); i++) {
            List<Integer> numbers = new ArrayList<>();
            addSudokuNumbersToList(numbers);
            availableNumbers.add(numbers);
        }
        int count = 0;
        while (count < 81) {
            int x = count % 9;
            int y = count / 9;
            if (availableNumbers.get(count).size() != 0) {
                int valueIndex = RANDOM.nextInt(availableNumbers.get(count).size());
                int value = availableNumbers.get(count).get(valueIndex);
                solvedSudoku[x][y] = value;
                availableNumbers.get(count).remove(valueIndex);
                if (SudokuLogic.sudokuIsInvalid(solvedSudoku)) solvedSudoku[x][y] = 0;
                else count++;
            } else {
                addSudokuNumbersToList(availableNumbers.get(count));
                count--;
            }
        }
        return solvedSudoku;
    }

    private static int[][] unsolveSudoku(int[][] solvedSudoku) {
        int[][] unsolvedSudoku = new int[GRID_BOUNDARY][GRID_BOUNDARY];
        SudokuGame.copySudokuArrayValues(solvedSudoku, unsolvedSudoku);
        int index = 0;
        while (index < 42) {
            int x = RANDOM.nextInt(GRID_BOUNDARY);
            int y = RANDOM.nextInt(GRID_BOUNDARY);
            if (unsolvedSudoku[x][y] != 0) {
                int temp = unsolvedSudoku[x][y];
                unsolvedSudoku[x][y] = 0;
                if (SudokuSolver.sudokuIsSolvable(unsolvedSudoku)) {
                    index++;
                } else {
                    unsolvedSudoku[x][y] = temp;
                }
            }
        }
        return unsolvedSudoku;
    }

    static void addSudokuNumbersToList(List<Integer> list) {
        for (int value = 1; value <= GRID_BOUNDARY; value++) {
            list.add(value);
        }
    }
}
