package sudoku.base;

import java.io.*;

public class LocalStorage {
    private static final File GAME_DATA = new File(System.getProperty("user.home"), "sudokudata");

    public void updateGameData(SudokuGame sudokuGame) throws IOException {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(GAME_DATA));
            objectOutputStream.writeObject(sudokuGame);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new IOException("Unable to access Game Data");
        }
    }

    public SudokuGame getGameData() throws IOException {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(GAME_DATA));
            SudokuGame game = (SudokuGame) objectInputStream.readObject();
            objectInputStream.close();
            return game;
        } catch (ClassNotFoundException e) {
            throw new IOException("File Not Found");
        }
    }
}
