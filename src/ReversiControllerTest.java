import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReversiControllerTest {

    ReversiController reversiController;
    ReversiBoard reversiBoard;
    ReversiModel reversiModel;

    @BeforeEach
    void setUp() {
        reversiBoard = new ReversiBoard();
        reversiModel = new ReversiModel();
        reversiController = new ReversiController();
    }

    @Test
    void setReversiModel() {
        reversiController.setReversiModel(reversiModel);
        Assertions.assertEquals(8, reversiController.getReversiModel().board.length);
    }

    @Test
    void setComputerScore() {
        reversiController.setComputerScore(2);
        Assertions.assertEquals(4, reversiController.getComputerScore());
    }

    @Test
    void setHumanScore() {
        reversiController.setHumanScore(2);
        Assertions.assertEquals(4, reversiController.getHumanScore());
    }

    @Test
    void getGameBoard() {
        Assertions.assertEquals(8, reversiController.getGameBoard().length);
        Assertions.assertEquals(8, reversiController.getReversiModel().getReversiBoard().getBoard().length);
    }

    @Test
    void setGameBoard() {
        String[][] board = new String[3][3];
        reversiController.setGameBoard(board);
        Assertions.assertEquals(3, reversiController.getGameBoard().length);
    }

    @Test
    void getSize() {
        Assertions.assertEquals(8, reversiController.getSize());
    }

    @Test
    void setSize() {
        reversiController.setSize(4);
        Assertions.assertEquals(4, reversiController.getSize());
    }

    @Test
    void getReversiModel() {
        Assertions.assertEquals(8, reversiController.getReversiModel().board.length);
    }

    @Test
    void clear() {
        reversiController.clear();
        Assertions.assertEquals(8, reversiController.getReversiModel().board.length);

    }

    @Test
    void getHumanScore() {
        int score = reversiController.getHumanScore();
        Assertions.assertEquals(4, score);

    }

    @Test
    void getComputerScore() {
        int score = reversiController.getComputerScore();
        Assertions.assertEquals(4, score);
    }

    @Test
    void moveAction() {
        reversiController.moveAction(2, 3, ReversiConstants.HUMAN_FLAG);
        reversiController.moveAction(2, 3, ReversiConstants.COMPUTER_FLAG);
        int score = reversiController.getComputerScore();
        Assertions.assertEquals(5, score);
    }

    @Test
    void humanTurn() {
        reversiController.humanTurn(1, 2);
    }

    @Test
    void computerTurn() {
        reversiController.humanTurn(1, 2);
        reversiController.computerTurn();
    }

    @Test
    void checkWinner() {
        boolean b = reversiController.checkWinner();
        Assertions.assertFalse(b);
    }

    @Test
    void checkMove() {
        boolean b = reversiController.checkMove(1, 2, "W", false);
        Assertions.assertFalse(b);
    }

    @Test
    void checkMove1() {
        boolean b = reversiController.checkMove("W");
        Assertions.assertTrue(b);
    }
}