package com.example.quizapp.service;

import com.example.quizapp.model.QuizQuestion;
import com.example.quizapp.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {

    private final QuizRepository repository;

    public QuizService(QuizRepository repository) {
        this.repository = repository;
    }

    public List<Map<String, Object>> getQuestions() {
        List<QuizQuestion> questions = repository.findAll();
        List<Map<String, Object>> response = new ArrayList<>();

        for (QuizQuestion q : questions) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", q.getId());
            map.put("question", q.getQuestion());
            map.put("options", List.of(q.getOptionA(), q.getOptionB(), q.getOptionC(), q.getOptionD()));
            response.add(map);
        }

        return response;
    }

    public Map<String, Integer> submitAnswers(Map<Integer, String> answers) {
        int score = 0;
        int total = answers.size();

        for (Map.Entry<Integer, String> entry : answers.entrySet()) {
            Optional<QuizQuestion> opt = repository.findById(entry.getKey());
            if (opt.isPresent()) {
                QuizQuestion q = opt.get();
                if (q.getCorrectOption().equalsIgnoreCase(entry.getValue())) {
                    score++;
                }
            }
        }

        return Map.of("score", score, "total", total);
    }

    public void addQuestion(QuizQuestion q) {
        repository.save(q);
    }
}
