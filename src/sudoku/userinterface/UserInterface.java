package sudoku.userinterface;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sudoku.base.Coordinates;
import sudoku.base.SudokuGame;
import sudoku.constants.Messages;

import java.util.HashMap;

public class UserInterface implements EventHandler<KeyEvent> {
    private final Stage stage;
    private final Group root;
    private HashMap<Coordinates, SudokuTextField> textFieldCoordinates;
    private Controller controller;
    private static final double BOARD_PADDING = 30;
    private static final Color WINDOW_BACKGROUD_COLOR = Color.rgb(191, 48, 120);
    private static final Color BOARD_BACKGROUD_COLOR = Color.rgb(240, 125, 182);
    private static final Color LINE_COLOR = Color.rgb(166, 7, 73);
    private static final double CELL_SIDE_SIZE = 50;
    private static final double BOARD_SIDE_SIZE = CELL_SIDE_SIZE * 9;
    private static final String TEXT_STYLE = "-fx-text-fill: rgb(112, 10, 70)";
    private static final Font TITLE_FONT = new Font("Constantia",30);
    private static final double WINDOW_X = BOARD_PADDING * 2 + BOARD_SIDE_SIZE;
    private static final double WINDOW_Y = WINDOW_X + TITLE_FONT.getSize() + BOARD_PADDING;
    private static final String SUDOKU_TITLE = "Sudoku by Victoria";
    private static final Font NUMBERS_FONT = new Font("Cambria", 28);

    public UserInterface(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        this.textFieldCoordinates = new HashMap<>();
        initializeUserInterface();
    }

    private void initializeUserInterface() {
        setBackground(root);
        setTitle(root);
        setBoard(root);
        setTextFields(root);
        setGridLines(root);
        stage.show();
    }

    private void setBackground(Group root) {
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        scene.setFill(WINDOW_BACKGROUD_COLOR);
        stage.setScene(scene);
        stage.setResizable(false);
    }

    private void setTitle(Group root) {
        Text temp = new Text(SUDOKU_TITLE);
        temp.setFont(TITLE_FONT);
        double y = WINDOW_X + temp.getLayoutBounds().getHeight();
        double x = (WINDOW_X - temp.getLayoutBounds().getWidth()) / 2;
        Text title = new Text(x, y, SUDOKU_TITLE);
        title.setFill(BOARD_BACKGROUD_COLOR);
        title.setFont(TITLE_FONT);
        root.getChildren().add(title);
    }

    private void setBoard(Group root) {
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);
        boardBackground.setWidth(BOARD_SIDE_SIZE);
        boardBackground.setHeight(BOARD_SIDE_SIZE);
        boardBackground.setFill(BOARD_BACKGROUD_COLOR);
        root.getChildren().addAll(boardBackground);
    }

    private void setTextFields(Group root) {
        final double cellSize = CELL_SIDE_SIZE;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                double xLayout = BOARD_PADDING + x * cellSize;
                double yLayout = BOARD_PADDING + y * cellSize;
                SudokuTextField cell = new SudokuTextField(x, y);
                cell.setFont(NUMBERS_FONT);
                cell.setAlignment(Pos.CENTER);
                cell.setLayoutX(xLayout);
                cell.setLayoutY(yLayout);
                cell.setPrefHeight(CELL_SIDE_SIZE);
                cell.setPrefWidth(CELL_SIDE_SIZE);
                cell.setBackground(Background.EMPTY);
                cell.setOnKeyPressed(this);
                textFieldCoordinates.put(new Coordinates(x, y), cell);
                root.getChildren().add(cell);
            }
        }
    }

    private void setGridLines(Group root) {
        double positionXOrY = BOARD_PADDING + CELL_SIDE_SIZE;
        int index = 0;
        while (index < 8) {
            int thickness = 2;
            if(index == 2 || index == 5) {
                thickness = 3;
            }
            Rectangle verticalLine = getLine(
                    positionXOrY + CELL_SIDE_SIZE * index,
                    BOARD_PADDING,
                    BOARD_SIDE_SIZE,
                    thickness
            );
            Rectangle horizontalLine = getLine(

                    BOARD_PADDING,
                    positionXOrY + CELL_SIDE_SIZE * index,
                    thickness,
                    BOARD_SIDE_SIZE
            );
            root.getChildren().addAll(verticalLine, horizontalLine);
            index++;
        }
    }

    private Rectangle getLine(double x, double y, double height, double width) {
        Rectangle line = new Rectangle();
        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);
        line.setFill(LINE_COLOR);
        return line;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void updateCell(int x, int y, int input) {
        SudokuTextField cell = textFieldCoordinates.get(new Coordinates(x, y));
        String value = Integer.toString(input);
        if (value.equals("0")) value = "";
        cell.textProperty().setValue(value);
    }

    public void updateBoard(SudokuGame game) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                TextField cell = textFieldCoordinates.get(new Coordinates(x, y));
                String value = Integer.toString(game.getCopyOfSudokuGrid()[x][y].getValue());
                if (value.equals("0")) value = "";
                cell.setText(value);
                if (game.getCopyOfSudokuGrid()[x][y].getIsMutable()) {
                    cell.setStyle("-fx-opacity: 1;");
                    cell.setDisable(false);
                } else {
                    cell.setStyle("-fx-opacity: 0.7;");
                    cell.setDisable(true);
                }
                cell.setStyle(TEXT_STYLE);
            }
        }
    }

    public void showMessage(Messages message) {
        Alert.AlertType type = Alert.AlertType.CONFIRMATION;
        if (message == Messages.ERROR) type = Alert.AlertType.ERROR;
        Alert dialog = new Alert(type, message.getMessage(), ButtonType.OK);
        dialog.showAndWait();
        if (dialog.getResult() == ButtonType.OK) controller.startNewGame();
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            SudokuTextField eventSource = (SudokuTextField) keyEvent.getSource();
            int value = 0;
            if (keyEvent.getText().matches("[0-9]")) value = Integer.parseInt(keyEvent.getText());
            //else if (eventSource.getText().matches("[0-9]")) value = Integer.parseInt(eventSource.getText());
            eventSource.setText("");
            controller.inputToCell(eventSource.getX(), eventSource.getY(), value);
        }
        keyEvent.consume();
    }
}
