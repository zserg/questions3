package ru.zserg.questions;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionServiceTest {

    @Test
    void createListOfQuestions() {
        QuestionService service = new QuestionService();
        String content = """
        #Q:Is it a question?
        It is the answer.
        Valid answer
        #Q: Is it another question?
        #Q: 2 * 2 = 4?
        5
        """;
        List<String> lines = Arrays.asList(content.split("\n"));
        List<Question> questions = service.createListOfQuestions(lines, "tag");

        assertEquals(3, questions.size());
        assertEquals("Is it a question?", questions.get(0).getQuestion());
        assertEquals("It is the answer.\nValid answer", questions.get(0).getAnswer());

        assertEquals(" Is it another question?", questions.get(1).getQuestion());

        assertEquals(" 2 * 2 = 4?", questions.get(2).getQuestion());
        assertEquals("5", questions.get(2).getAnswer());
    }


}