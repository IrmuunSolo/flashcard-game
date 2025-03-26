package com.example;

class Flashcard {
    private String question;
    private String answer;
    private boolean wasCorrect;

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.wasCorrect = false;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean wasCorrect() {
        return wasCorrect;
    }

    public void setCorrect(boolean correct) {
        this.wasCorrect = correct;
    }
}
