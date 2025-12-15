package model;

import org.enums.model.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreTest {

    @DisplayName("Score values should be set correctly")
    @Test
    public void testScoreValues() {
        Score score = new Score(0.85, 85.0, 0.0, 100.0);

        assertEquals(0.85, score.getScaled());
        assertEquals(85.0, score.getRaw());
        assertEquals(0.0, score.getMin());
        assertEquals(100.0, score.getMax());
    }

    @DisplayName("Score default constructor with setters")
    @Test
    public void testScoreSetters() {
        Score score = new Score();

        score.setScaled(0.6);
        score.setRaw(60.0);
        score.setMin(0.0);
        score.setMax(100.0);

        assertEquals(0.6, score.getScaled());
        assertEquals(60.0, score.getRaw());
        assertEquals(0.0, score.getMin());
        assertEquals(100.0, score.getMax());
    }
}
