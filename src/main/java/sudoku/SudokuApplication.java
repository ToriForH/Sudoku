package sudoku;

import javafx.application.Application;
import javafx.stage.Stage;
import sudoku.buildsudoku.BuildSudoku;
import sudoku.userinterface.UserInterface;

import java.io.IOException;

public class SudokuApplication extends Application{
    private UserInterface userInterface;

    @Override
    public void start(Stage primaryStage) throws Exception {
        userInterface = new UserInterface(primaryStage);
        try {
            BuildSudoku.build(userInterface);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
