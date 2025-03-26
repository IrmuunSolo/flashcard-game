package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FlashcardSessionTest {
    
    @Test
    public void testSessionInitialization() {

        List<Flashcard> cards = new ArrayList<>();
        cards.add(new Flashcard("Q1", "A1"));
        cards.add(new Flashcard("Q2", "A2"));
        
        CardOrganizer organizer = new RecentMistakesFirstSorter();
        FlashcardSession session = new FlashcardSession(cards, organizer, 2, false);
        
        assertNotNull(session);
    }

    @Test
    public void testCheckAchievements() {

        List<Flashcard> cards = new ArrayList<>();
        cards.add(new Flashcard("Q1", "A1"));
        
        CardOrganizer organizer = new RecentMistakesFirstSorter();
        FlashcardSession session = new FlashcardSession(cards, organizer, 1, false);
        
        // System.out-г redirect хийх
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        // Хэрэглэгчийн дэлгэцэнд тескс хэвлэж байгаа
        session.checkAchievements(1, 1, Map.of(cards.get(0), 1));
        session.checkAchievements(3, 10, Map.of(cards.get(0), 5));
        session.checkAchievements(3, 5, Map.of(cards.get(0), 3));
        
        // Хэвлэсэн текст дотор "CORRECT"-ыг агуулсан үгүйг шалгана
        assertTrue(outContent.toString().contains("CORRECT"));

    }
}
