package com.example.quizapp.controller;

import com.example.quizapp.model.QuizQuestion;
import com.example.quizapp.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService service;

    public QuizController(QuizService service) {
        this.service = service;
    }

    @GetMapping("/questions")
    public ResponseEntity<List<Map<String, Object>>> getQuestions() {
        return ResponseEntity.ok(service.getQuestions());
    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, Integer>> submitAnswers(@RequestBody Map<Integer, String> answers) {
        return ResponseEntity.ok(service.submitAnswers(answers));
    }

    @PostMapping("/question")
    public ResponseEntity<Void> addQuestion(@RequestBody QuizQuestion question) {
        service.addQuestion(question);
        return ResponseEntity.status(201).build();
    }
}
