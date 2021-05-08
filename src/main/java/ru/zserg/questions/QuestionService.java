package ru.zserg.questions;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

@Service
public class QuestionService {

    private final ArrayList<String> list = new ArrayList<>();

    @PostConstruct
    private void init() throws IOException {
        InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream("questions.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        while (reader.ready()){
            list.add(reader.readLine());
        }
    }

    public String getRandomQuestion(){
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }
}
