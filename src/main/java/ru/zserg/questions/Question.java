package ru.zserg.questions;

import lombok.Getter;

@Getter
public class Question {
    private final String question;
    private String answer;
    private String tag;

    public void addLineToAnswer(String line){
        answer = answer == null ? line : String.join("\n", answer, line);
    }

    public Question(String question, String tag) {
        this.question = question;
        this.tag = tag;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}