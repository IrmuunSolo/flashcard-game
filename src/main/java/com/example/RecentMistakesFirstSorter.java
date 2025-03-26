package com.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RecentMistakesFirstSorter implements CardOrganizer {

    @Override
    public List<Flashcard> organize(List<Flashcard> cards) {
        //Буруу хариулсан картуудыг эхэнд нь байрлуулна
        
        cards.sort((card1, card2) -> {
                if (!card1.wasCorrect() && card2.wasCorrect()) {
                    return -1;
                } else if (card1.wasCorrect() && !card2.wasCorrect()) {
                    return 1;
                } else {
                return 0;
                }
        });
        
        return cards;
    }
}
