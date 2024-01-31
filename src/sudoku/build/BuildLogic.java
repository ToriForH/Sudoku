package sudoku.build;

import sudoku.logic.SudokuLogic;
import sudoku.base.LocalStorage;
import sudoku.base.SudokuGame;
import sudoku.userinterface.UserInterface;
import sudoku.userinterface.Controller;

import java.io.IOException;

public class BuildLogic {
    public static void build(UserInterface userInterface) throws IOException {
        SudokuGame initialState;
        LocalStorage storage = new LocalStorage();
        try {
            initialState = storage.getGameData();
        } catch (IOException e) {
            initialState = SudokuLogic.getNewSudoku();
            storage.updateGameData(initialState);
        }
        Controller controller = new Controller(storage, userInterface);
        userInterface.setController(controller);
        userInterface.updateBoard(initialState);
    }
}
