package sudoku.logic;

import sudoku.base.SudokuGame;
import java.util.ArrayList;
import java.util.List;

import static sudoku.base.SudokuGame.GRID_BOUNDARY;

public class SudokuSolver {
    static boolean sudokuIsSolvable(int[][] sudokuGrid) {
        int[][] copyOfGrid = new int[GRID_BOUNDARY][GRID_BOUNDARY];
        SudokuGame.copySudokuArrayValues(sudokuGrid, copyOfGrid);
        List<List<Integer>> availableNumbers = new ArrayList<>();
        for (int i = 0; i < (GRID_BOUNDARY * GRID_BOUNDARY); i++) {
            int x = i % 9;
            int y = i / 9;
            List<Integer> numbers = new ArrayList<>();
            if (copyOfGrid[x][y] == 0) {
                SudokuGenerator.addSudokuNumbersToList(numbers);
            } else {
                numbers.add(copyOfGrid[x][y]);
            }
            availableNumbers.add(numbers);
        }
        List<Integer> sudokuList = fillListWithSudokuGrid(copyOfGrid);
        int attempts = 0;
        while (sudokuList.contains(0)) {
            for (int i = 0; i < sudokuList.size(); i++) {
                int x = i % 9;
                int y = i / 9;
                if(copyOfGrid[x][y] == 0) {
                    if(availableNumbers.get(i).size() == 1) { //every puzzle must have only 1 solution
                        copyOfGrid[x][y] = availableNumbers.get(i).getFirst();
                    } else {
                        for(int j = 0; j < availableNumbers.get(i).size(); j++) {
                            copyOfGrid[x][y] = availableNumbers.get(i).get(j);
                            if(SudokuLogic.sudokuIsInvalid(copyOfGrid)) {
                                availableNumbers.get(i).remove(j);
                                j--;
                            }
                        }
                        copyOfGrid[x][y] = 0;
                    }
                }
            }
            sudokuList.clear();
            sudokuList = fillListWithSudokuGrid(copyOfGrid);
            attempts++;
            if (attempts > 400) {
                return false;
            }
        }
        return true;
    }

    private static List<Integer> fillListWithSudokuGrid(int[][] sudokuGrid) {
        List<Integer> list = new ArrayList<>();
        for (int x = 0; x < GRID_BOUNDARY; x++) {
            for (int y = 0; y < GRID_BOUNDARY; y++) {
                list.add(sudokuGrid[x][y]);
            }
        }
        return list;
    }
}
