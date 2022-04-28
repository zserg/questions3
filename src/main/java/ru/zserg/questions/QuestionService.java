package ru.zserg.questions;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Value("${questions.filesPath:files}")
    private String path;

    Map<String, List<Question>> map = new HashMap<>();

    @PostConstruct
    private void init() throws IOException {
        map = createQuestionMap(path);
    }

    public Optional<Question> getRandomQuestion(List<String> tags) {
        List<Question> list = map.entrySet().stream()
                .filter(e -> tags.isEmpty() || tags.contains(e.getKey()))
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.toList());
        if(list.isEmpty()){
            return Optional.empty();
        }
        Random random = new Random();
        return Optional.of(list.get(random.nextInt(list.size())));
    }

    public Set<String> getTags(){
        return map.keySet();
    }

    private Map<String, List<Question>> createQuestionMap(String path) throws IOException {
        Map<String, List<Question>> qmap = new HashMap<>();
        File dir = new File(path);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                String tag = FilenameUtils.getBaseName(file.getName());
                try (FileInputStream fis = new FileInputStream(file);
                     InputStreamReader inputStreamReader = new InputStreamReader(fis);
                     BufferedReader reader = new BufferedReader(inputStreamReader)) {
                    List<String> list = new ArrayList<>();
                    while (reader.ready()) {
                        list.add(reader.readLine());
                    }
                    List<Question> questions = createListOfQuestions(list, tag);
                    qmap.put(tag, questions);
                }
            }
        }
        return qmap;
    }

    List<Question> createListOfQuestions(Collection<String> lines, String tag) {
        Pattern pattern = Pattern.compile("^#Q:(.*)");
        List<Question> list = new ArrayList<>();
        Question question = null;

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                if (question != null) {
                    list.add(question);
                }
                String questionStr = matcher.group(1);
                question = new Question(questionStr, tag);
            } else {
                if (question != null) {
                    question.addLineToAnswer(line);
                }
            }
        }
        if (question != null) {
            list.add(question);
        }

        return list;
    }
}
