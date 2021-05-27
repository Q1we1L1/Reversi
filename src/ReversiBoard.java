import java.io.Serializable;

/**
 * The Board class for Reversi game, which has the board with board size.
 * And the board size could be get from the reversi constant.
 *
 * @since java18
 */
public class ReversiBoard implements Serializable {
    static final long serialVersionUID = 1L;

    /**
     * The board variable for the reversi game.
     */
    private int[][] board;

    /**
     * constructor for the reversi board game.
     */
    ReversiBoard() {
        // init the board game.
        this.board = new int[ReversiConstants.BOARD_SIZE][ReversiConstants.BOARD_SIZE];
    }

    /**
     * Getter of the board
     *
     * @return the board
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * setter of the board.
     *
     * @param board the input board
     */
    public void setBoard(int[][] board) {
        this.board = board;
    }

    /**
     * @param row  the input row
     * @param col  the input col
     * @param role the input role
     */
    void updateBoard(int row, int col, int role) {
        board[row][col] = role;
    }
}