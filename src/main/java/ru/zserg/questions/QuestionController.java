package ru.zserg.questions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping(value = "/q")
    public String getQuestion(Model model) {
        String question = questionService.getRandomQuestion();
        model.addAttribute("question", question);
        return "question";
    }


}
