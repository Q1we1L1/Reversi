import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReversiConstantsTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testConstant() {
        assertEquals(ReversiConstants.BOARD_SIZE, 8);
        assertEquals(ReversiConstants.COMPUTER_FLAG, "B");
        assertEquals(ReversiConstants.HUMAN_FLAG, "W");
        assertEquals(ReversiConstants.SIZE_GUI, 384);
    }
}