package ru.zserg.questions;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping(value = "/q")
    public String getQuestion(@RequestParam(required = false) List<String> tags, Model model) {
        Question question = questionService.getRandomQuestion(Objects.requireNonNullElse(tags, List.of()))
                .orElse(new Question("", ""));
        model.addAttribute("question", question);
        Set<String> allTags = questionService.getTags();
        List<Tag> tagsOut = new ArrayList<>();
        for (String tagName : allTags) {
                tagsOut.add(new Tag(tagName, tags != null && tags.contains(tagName)));
        }

        model.addAttribute("tags", tagsOut);
        return "question";
    }


}
