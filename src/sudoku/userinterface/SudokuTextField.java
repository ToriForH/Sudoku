package sudoku.userinterface;

import javafx.scene.control.TextField;
import java.io.Serializable;
import java.util.Objects;

public class SudokuTextField extends TextField implements Serializable {
    private final int x;
    private final int y;

    public SudokuTextField(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void replaceText(int i, int i1, String s) {
        if(!s.matches("[0-9]")) {
            super.replaceText(i, i1, s);
        }
    }

    public SudokuTextField getCopy(){
        return new SudokuTextField(this.x, this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SudokuTextField)) return false;
        SudokuTextField input = (SudokuTextField) o;
        if (this.getX() != input.getX()) return false;
        return this.getY() == input.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
