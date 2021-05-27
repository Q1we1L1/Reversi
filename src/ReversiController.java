import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * The constroller of the reversi game.
 */
public class ReversiController {
    /**
     * The reversiModel of the mvc
     */
    ReversiModel reversiModel;
    /**
     * the score of the computer
     */
    private int computerScore;
    /**
     * the score of the human
     */
    private int humanScore;
    /**
     * The game board of the reversi
     */
    private String[][] gameBoard;
    /**
     * The size for the board.
     */
    private int size;

    /**
     * The constructor for the reversi controller.
     */
    ReversiController() {
        try {
            // open the file, if there is a game board existed in the folder.
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(ReversiConstants.FILE_NAME));
            // Serializable the reversi model from the file.
            this.reversiModel = new ReversiModel((ReversiBoard) objectInputStream.readObject());
            // close the input steam
            objectInputStream.close();
        } catch (Exception e) {
            // if there is no existed game file.
            this.reversiModel = new ReversiModel();
        }
        // the game board to init
        this.gameBoard = reversiModel.getBoard();
        // the size to init
        this.size = reversiModel.getBoardSize();
        // all score init to 2;
        this.setComputerScore(2);
        this.setComputerScore(2);
    }

    /**
     * Setter for the
     *
     * @param reversiModel ReversiModel
     */
    public void setReversiModel(ReversiModel reversiModel) {
        this.reversiModel = reversiModel;
    }

    /**
     * Setter for the computer socre
     *
     * @param computerScore input computerScore
     */
    public void setComputerScore(int computerScore) {
        this.computerScore = computerScore;
    }

    /**
     * Setter of the computer
     *
     * @param humanScore the input human score
     */
    public void setHumanScore(int humanScore) {
        this.humanScore = humanScore;
    }

    /**
     * the getter of the game board/
     *
     * @return the game board
     */
    public String[][] getGameBoard() {
        return gameBoard;
    }

    /**
     * The setter of the game board.
     *
     * @param gameBoard input gameBoard
     */
    public void setGameBoard(String[][] gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * getter of the size of the board size.
     *
     * @return the the size
     */
    public int getSize() {
        return size;
    }

    /**
     * The setter size of the size
     *
     * @param size the input size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * the getter of the ReversiModel
     *
     * @return ReversiModel
     */
    ReversiModel getReversiModel() {
        return reversiModel;
    }

    /**
     * Reset the game board.
     */
    void clear() {
        gameBoard = reversiModel.getBoard();
    }

    /**
     * return the score of the role
     *
     * @param role the role of the current game.
     * @return the score of the role
     */
    private int calculateScore(String role) {
        int score = 0;
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                if (reversiModel.board[i][j].equals(role))
                    score += 1;
            }
        }
        return score;
    }

    /**
     * The getter of human score
     *
     * @return huamen score
     */
    int getHumanScore() {
        this.humanScore = calculateScore(ReversiConstants.HUMAN_FLAG);
        return humanScore;
    }

    /**
     * getter of computer score
     *
     * @return computer score
     */
    int getComputerScore() {
        this.computerScore = calculateScore(ReversiConstants.COMPUTER_FLAG);
        return computerScore;
    }

    /**
     * do the action of the game play process.
     *
     * @param row  the input row of the board
     * @param col  the input col of the board
     * @param role the current player
     */
    void moveAction(int row, int col, String role) {
        if (role.equals(ReversiConstants.HUMAN_FLAG)) {
            reversiModel.setBoard(row, col, Color.WHITE);
        } else if (role.equals(ReversiConstants.COMPUTER_FLAG)) {
            reversiModel.setBoard(row, col, Color.BLACK);
        }
        gameBoard[row][col] = role;
    }

    /**
     * The humanTurn Logic
     *
     * @param row the input row
     * @param col the input col
     */
    public void humanTurn(int row, int col) {

    }

    /**
     * The computer turn
     */
    public void computerTurn() {

    }

    /**
     * Check full for the board
     *
     * @return the check
     */
    private boolean fullCheck() {
        boolean isFull = false;
        {
            int i = 0;
            while (i < ReversiConstants.BOARD_SIZE) {
                int j = 0;
                while (j < ReversiConstants.BOARD_SIZE) {
                    if (gameBoard[i][j].equals(ReversiConstants.EMPTY_FLAG))
                        isFull = true;

                    j++;
                }
                i++;
            }
        }
        return isFull;
    }

    /**
     * Check for the process
     *
     * @return the winner.
     */
    boolean checkWinner() {
        boolean isFull = fullCheck();
        if (isFull) {
            boolean isMoveNext = false;
            for (int i = 0; i < ReversiConstants.BOARD_SIZE; i++) {
                for (int j = 0; j < ReversiConstants.BOARD_SIZE; j++) {
                    if (!checkMove(i, j, ReversiConstants.COMPUTER_FLAG, false) && !checkMove(i, j, ReversiConstants.HUMAN_FLAG, false)) {
                        continue;
                    }
                    isMoveNext = true;
                }
            }
            return !isMoveNext;
        } else {
            return true;
        }
    }

    /**
     * Check next move
     *
     * @param row    the input row
     * @param col    the input col
     * @param role   the input role
     * @param isMove can be move
     * @return the next move is valid.
     */
    boolean checkMove(int row, int col, String role, boolean isMove) {
        boolean moveCheck = false;
        if (!reversiModel.board[row][col].equals("_"))
            return false;
        for (int i = -1; i < 2; i++)
            for (int y = -1; y < 2; y++) {
                if (i == 0 && y == 0) {
                    continue;
                }
                int rows = row + i;
                int cols = col + y;
                if (rows >= 0 && cols >= 0 && rows < size && cols < size) {
                    if (reversiModel.board[rows][cols].equals(role.equals(ReversiConstants.HUMAN_FLAG) ? ReversiConstants.COMPUTER_FLAG : ReversiConstants.HUMAN_FLAG)) {
                        for (int rIndex = 0; rIndex < size; rIndex++) {
                            int r = row + rIndex * i;
                            int c = col + rIndex * y;
                            if (r < 0 || c < 0 || r > size - 1 || c > size - 1) {
                                continue;
                            }
                            if (reversiModel.board[r][c].equals(role)) {
                                if (isMove) {
                                    for (int nextRIndex = 1; nextRIndex < rIndex; nextRIndex++) {
                                        int rtemp = row + nextRIndex * i;
                                        int ctemp = col + nextRIndex * y;
                                        if (role.equals(ReversiConstants.HUMAN_FLAG)) {
                                            reversiModel.setBoard(rtemp, ctemp, Color.WHITE);
                                        }
                                        if (role.equals(ReversiConstants.COMPUTER_FLAG)) {
                                            reversiModel.setBoard(rtemp, ctemp, Color.BLACK);
                                        }
                                        gameBoard[rtemp][ctemp] = role;
                                    }
                                }
                                moveCheck = true;
                                break;
                            }
                        }
                    }
                }
            }
        return moveCheck;
    }

    /**
     * Move check
     *
     * @param role the input role
     * @return the move valid
     */
    boolean checkMove(String role) {
        int i = 0;
        while (i < size) {
            int j = 0;
            while (j < size) {
                if (reversiModel.board[i][j].equals(ReversiConstants.EMPTY_FLAG))
                    if (checkMove(i, j, role, false))
                        return true;
                j++;
            }
            i++;
        }
        return false;
    }
}