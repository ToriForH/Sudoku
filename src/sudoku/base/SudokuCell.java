package sudoku.base;

import sudoku.userinterface.SudokuTextField;

import java.io.Serializable;

public class SudokuCell implements Serializable {
    private final SudokuTextField textField;
    private int value;
    private final boolean isMutable;

    public SudokuCell(SudokuTextField textField, int value, boolean isMutable) {
        this.textField = textField;
        this.value = value;
        this.isMutable = isMutable;
    }

    public SudokuTextField getTextField() {
        return textField;
    }

    public int getValue() {
        return value;
    }

    public boolean getIsMutable() {
        return isMutable;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SudokuCell[][] arrayToSudokuCellGrid(int[][] array) {
        SudokuCell[][] sudokuGrid = new SudokuCell[SudokuGame.GRID_BOUNDARY][SudokuGame.GRID_BOUNDARY];
        for (int x = 0; x < SudokuGame.GRID_BOUNDARY; x++) {
            for (int y = 0; y < SudokuGame.GRID_BOUNDARY; y++) {
                int value = array[x][y];
                boolean mutability = value == 0;
                sudokuGrid[x][y] = new SudokuCell(new SudokuTextField(x, y), value, mutability);
            }
        }
        return sudokuGrid;
    }

    public static int[][] SudokuGridToArray (SudokuCell[][] grid) {
        int[][] array = new int[SudokuGame.GRID_BOUNDARY][SudokuGame.GRID_BOUNDARY];
        for (int x = 0; x < SudokuGame.GRID_BOUNDARY; x++) {
            for (int y = 0; y < SudokuGame.GRID_BOUNDARY; y++) {
                array[x][y] = grid[x][y].getValue();
            }
        }
        return array;
    }

    public SudokuCell getCopy(){
        SudokuTextField copyTextField = this.textField.getCopy();
        return new SudokuCell(copyTextField, this.value, this.isMutable);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SudokuCell)) return false;
        SudokuCell input = (SudokuCell) o;
        if (this.getValue() != input.getValue()) return false;
        if (this.getIsMutable() != input.getIsMutable()) return false;
        return this.getTextField().equals(input.getTextField());
    }

    @Override
    public int hashCode() {
        int result = this.getTextField().hashCode();
        result = 31 * result + this.getValue();
        result = 31 * result + (this.getIsMutable() ? 1 : 0);
        return result;
    }
}
