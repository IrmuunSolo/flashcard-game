package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class FlashcardTest {

    @Test
    public void testFlashcardCreation() {
        Flashcard card = new Flashcard("Question", "Answer");
        assertEquals("Question", card.getQuestion());
        assertEquals("Answer", card.getAnswer());
        assertFalse(card.wasCorrect());
    }

    @Test
    public void testSetCorrect() {
        Flashcard card = new Flashcard("Q", "A");
        card.setCorrect(true);
        assertTrue(card.wasCorrect());
    }
    
}
