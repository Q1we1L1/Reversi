import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReversiBoardTest {
    ReversiBoard reversiBoard;

    @Test
    void getBoard() {
        reversiBoard = new ReversiBoard();
        Assertions.assertEquals(8, reversiBoard.getBoard().length);
    }

    @Test
    void setBoard() {
        int[][] boards = new int[4][4];
        reversiBoard = new ReversiBoard();
        reversiBoard.setBoard(boards);
        Assertions.assertEquals(4, reversiBoard.getBoard().length);
    }

    @Test
    void updateBoard() {
        reversiBoard = new ReversiBoard();
        reversiBoard.updateBoard(1, 1, 1);
        Assertions.assertEquals(1, reversiBoard.getBoard()[1][1]);
    }
}