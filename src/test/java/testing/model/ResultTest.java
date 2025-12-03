package testing.model;

import org.enums.xapi.model.Result;
import org.enums.xapi.model.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResultTest {

    @DisplayName("Result with score")
    @Test
    public void testResultWithScore() {
        Score score = new Score(0.9, 90.0, 0.0, 100.0);

        Result result = new Result();
        result.setScore(score);
        result.setSuccess(true);
        result.setCompletion(true);
        result.setResponse("User answered: A");

        assertNotNull(result.getScore());
        assertEquals(0.9, result.getScore().getScaled());
        assertEquals(90.0, result.getScore().getRaw());
        assertEquals(0.0, result.getScore().getMin());
        assertEquals(100.0, result.getScore().getMax());

        assertTrue(result.getSuccess());
        assertTrue(result.getCompletion());
        assertEquals("User answered: A", result.getResponse());
    }

    @DisplayName("Result without score")
    @Test
    public void testResultWithoutScore() {
        Result result = new Result();
        result.setSuccess(false);
        result.setCompletion(false);
        result.setResponse("No answer");

        assertNull(result.getScore());
        assertFalse(result.getSuccess());
        assertFalse(result.getCompletion());
        assertEquals("No answer", result.getResponse());
    }
}
