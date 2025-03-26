package com.example;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class RecentMistakesFirstSorterTest {

    @Test
    public void testOrganize() {
        List<Flashcard> cards = new ArrayList<>();

        Flashcard correctCard1 = new Flashcard("Q1", "A1");
        correctCard1.setCorrect(true);
        Flashcard correctCard2 = new Flashcard("Q1", "A1");
        correctCard2.setCorrect(true);
        
        Flashcard wrongCard1 = new Flashcard("Q2", "A2");
        wrongCard1.setCorrect(false);
        Flashcard wrongCard2 = new Flashcard("Q2", "A2");
        wrongCard2.setCorrect(false);
        
        cards.add(correctCard1);
        cards.add(wrongCard1);
        cards.add(wrongCard2);
        cards.add(correctCard2);

        CardOrganizer organizer = new RecentMistakesFirstSorter();
        List<Flashcard> result = organizer.organize(cards);
        
        assertEquals(wrongCard1, result.get(0)); // Буруу хариулсан карт эхэнд
        assertEquals(wrongCard2, result.get(1));
        assertEquals(correctCard1, result.get(2));
        assertEquals(correctCard2, result.get(3));
    }
    
}
