package sudoku.userinterface;

import sudoku.base.SudokuCell;
import sudoku.logic.SudokuLogic;
import sudoku.constants.SudokuState;
import sudoku.constants.Messages;
import sudoku.base.LocalStorage;
import sudoku.base.SudokuGame;

import java.io.IOException;

public class Controller {
    private LocalStorage storage;
    private UserInterface userInterface;

    public Controller(LocalStorage storage, UserInterface userInterface) {
        this.storage = storage;
        this.userInterface = userInterface;
    }

    void inputToCell(int x, int y, int input) {
        try {
            SudokuGame gameData = storage.getGameData();
            SudokuCell[][] newSudokuGrid = gameData.getCopyOfSudokuGrid();
            newSudokuGrid[x][y].setValue(input);
            gameData = new SudokuGame(SudokuLogic.checkForCompletion(newSudokuGrid), newSudokuGrid);
            storage.updateGameData(gameData);
            userInterface.updateCell(x, y, input);
            if(gameData.getGameState() == SudokuState.COMPLETE) userInterface.showMessage(Messages.GAME_COMPLETE);
        } catch (IOException e) {
            e.printStackTrace();
            userInterface.showMessage(Messages.ERROR);
        }

    }

    void startNewGame() {
        try {
            storage.updateGameData(SudokuLogic.getNewSudoku());
            userInterface.updateBoard(storage.getGameData());
        } catch (IOException e) {
            userInterface.showMessage(Messages.ERROR);
        }
    }
}
