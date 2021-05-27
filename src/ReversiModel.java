import javafx.scene.paint.Color;

import java.util.Observable;

/**
 * The class of the ReversiModel
 */
public class ReversiModel extends Observable {

    /**
     * ReversiBoard variable
     */
    private ReversiBoard reversiBoard;
    /**
     * The board of the game
     */
    String[][] board;

    /**
     * The constructor of the ReversiModel
     */
    public ReversiModel() {
        reversiBoard = new ReversiBoard();
        board = new String[ReversiConstants.BOARD_SIZE][ReversiConstants.BOARD_SIZE];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = ReversiConstants.EMPTY_FLAG;
                if (i == j && (i == 3 || i == 4)) {
                    board[i][i] = ReversiConstants.HUMAN_FLAG;
                    reversiBoard.updateBoard(i, i, ReversiConstants.W_VALUE);
                }
                if (i * j == 12 && (i == 3 || i == 4)) {
                    board[i][12 / i] = ReversiConstants.COMPUTER_FLAG;
                    reversiBoard.updateBoard(i, 12 / i, ReversiConstants.B_VALUE);

                }
            }
        }
    }

    /**
     * The constructor of the view
     *
     * @param reversiBoard input ReversiBoard
     */
    public ReversiModel(ReversiBoard reversiBoard) {
        this.reversiBoard = reversiBoard;
        board = new String[ReversiConstants.BOARD_SIZE][ReversiConstants.BOARD_SIZE];
        for (int i = 0; i < board.length; i++) {
            int j = 0;
            while (j < board.length) {
                if (this.reversiBoard.getBoard()[i][j] == ReversiConstants.EMPTY_VALUE) {
                    board[i][j] = ReversiConstants.EMPTY_FLAG;
                } else if (this.reversiBoard.getBoard()[i][j] == ReversiConstants.W_VALUE) {
                    board[i][j] = ReversiConstants.HUMAN_FLAG;
                } else if (this.reversiBoard.getBoard()[i][j] == ReversiConstants.B_VALUE) {
                    board[i][j] = ReversiConstants.COMPUTER_FLAG;
                }
                j++;
            }
        }
    }


    /**
     * Getter of the reversiBoard
     *
     * @return reversiBoard
     */
    ReversiBoard getReversiBoard() {
        return reversiBoard;
    }

    /**
     * Getter of the board
     *
     * @return game board
     */
    String[][] getBoard() {
        return board;
    }

    /**
     * Board size
     *
     * @return board size
     */
    int getBoardSize() {
        return ReversiConstants.BOARD_SIZE;
    }

    /**
     * @param row   the input row
     * @param col   the input col
     * @param color the input color
     */
    void setBoard(int row, int col, Color color) {
        reversiBoard.updateBoard(row, col, (color.equals(Color.WHITE) ? ReversiConstants.W_VALUE : ReversiConstants.B_VALUE));
        setChanged();
        notifyObservers(reversiBoard);
    }
}