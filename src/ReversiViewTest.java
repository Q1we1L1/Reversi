import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReversiViewTest {
    ReversiController reversiController;
    ReversiModel reversiModel;
    ReversiBoard reversiBoard;
    ReversiView reversiView;

    @BeforeEach
    void setUp() {
        reversiController = new ReversiController();
        reversiBoard = new ReversiBoard();
        reversiModel = new ReversiModel();
        reversiView = new ReversiView();
    }

    @Test
    void testAll() {
        Assertions.assertEquals(reversiView.getClass(), ReversiView.class);
    }


}