package org.skypro.Cource2.controller;

import org.skypro.Cource2.domain.Question;
import org.skypro.Cource2.exception.QuestionAlreadyExistsException;
import org.skypro.Cource2.exception.QuestionsNotFoundException;
import org.skypro.Cource2.service.QuestionServices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/exam/java")
public class JavaQuestionController {

    private final QuestionServices service;

    public JavaQuestionController(QuestionServices service) {
        this.service = service;
    }

    /**
     * /exam/java/add?question=...&answer=...
     */
    @GetMapping("/add")
    public ResponseEntity<?> addQuestion(@RequestParam String question,
                                         @RequestParam String answer) {
        try {
            Question added = service.add(question, answer);
            return ResponseEntity.ok(added);
        } catch (QuestionAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * /exam/java/remove?question=...&answer=...
     */
    @GetMapping("/remove")
    public ResponseEntity<String> removeQuestion(@RequestParam String question,
                                                 @RequestParam String answer) {
        try {
            Question toRemove = new Question(question, answer);
            service.remove(toRemove);
            return ResponseEntity.ok("Вопрос: " + question + ", Ответ: " + answer + ". Удален");
        } catch (QuestionsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    /**
     * /exam/java/find?text=...
     */
    @GetMapping("/find")
    public ResponseEntity<?> findQuestions(@RequestParam String text) {
        try {
            List<Question> results = service.findQuestions(text);
            return ResponseEntity.ok(results);
        } catch (QuestionsNotFoundException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
    /**
     * /exam/java/random
     */

    @GetMapping("/random")
    public ResponseEntity<?> getRandomQuestion() {
        try {
            Question randomQuestion = service.getRandomQuestion();
            return ResponseEntity.ok(randomQuestion);
        } catch (QuestionsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping
    public Collection<Question> getQuestions() {
        return service.getAll();
    }
}
