package com.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class FlashcardSession {
    private List<Flashcard> cards;
    private CardOrganizer organizer;
    private int repetitions;
    private boolean invertCards;

    public FlashcardSession(List<Flashcard> cards, CardOrganizer organizer, int repetitions, boolean invertCards) {
        this.cards = organizer.organize(cards);
        this.organizer = organizer;
        this.repetitions = repetitions;
        this.invertCards = invertCards;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int correctCount = 0;
            int totalAttempts = 0;
            Map<Flashcard, Integer> attempts = new HashMap<>();

            for (Flashcard card : cards) {
                String question = invertCards ? card.getAnswer() : card.getQuestion();
                String answer = invertCards ? card.getQuestion() : card.getAnswer();

                System.out.println("Question: " + question);
                System.out.print("Answer: ");
                String userAnswer = scanner.nextLine();

                if (userAnswer.equalsIgnoreCase(answer)) {
                    System.out.println("Correct answer!");
                    card.setCorrect(true);
                    correctCount++;
                } else {
                    System.out.println("Wrong answer. Correct answer is: " + answer);
                    card.setCorrect(false);
                }

                attempts.put(card, attempts.getOrDefault(card, 0) + 1);
                totalAttempts++;
            }

            System.out.println("Your score: " + correctCount + "/" + totalAttempts);
            checkAchievements(correctCount, totalAttempts, attempts);
            
            // Дахин оролдох эсэхийг асуух
            System.out.print("Try again? (Yes/No): ");
            String response = scanner.nextLine();
            if (!response.equalsIgnoreCase("Yes")) {
                break;
            }

            // Дахин оролдсон тохиолдолд картуудыг зохион байгуулах
            this.cards = organizer.organize(cards);
        }
        scanner.close();
    }

    public void checkAchievements(int correctCount, int totalAttempts, Map<Flashcard, Integer> attempts) {
        if (correctCount == totalAttempts) {
            System.out.println("Achievement: CORRECT - All cards answered correctly in the last round.");
        }

        for (Map.Entry<Flashcard, Integer> entry : attempts.entrySet()) {
            if (entry.getValue() > 5) {
                System.out.println("Achievement: REPEAT - Answered the same card more than 5 times.");
                break;
            }
        }

        for (Map.Entry<Flashcard, Integer> entry : attempts.entrySet()) {
            if (entry.getKey().wasCorrect() && entry.getValue() >= 3) {
                System.out.println("Achievement: CONFIDENT - Answered one card at least 3 times correctly.");
                break;
            }
        }
    }
}
