package org.skypro.Cource2.controller;

import org.skypro.Cource2.domain.Question;
import org.skypro.Cource2.service.QuestionServices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
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
        Question q = new Question(question, answer);
        boolean exists = service.getAll().stream()
                .anyMatch(x -> x.getQuestion().equals(q.getQuestion()) && x.getAnswer().equals(q.getAnswer()));

        if (exists) {
            return ResponseEntity.status(409).body("Этот вопрос с таким ответом уже был.");
        }

        Question added = service.add(q);
        return ResponseEntity.ok(added);
    }

    /**
     * /exam/java/remove?question=...&answer=...
     */
    @GetMapping("/remove")
    public ResponseEntity<String> removeQuestion(@RequestParam String question,
                                                 @RequestParam String answer) {
        Question toRemove = new Question(question, answer);

        Question removed = service.remove(toRemove);
        if (removed != null) {
            return ResponseEntity.ok("Вопрос: " + question + ", Ответ: " + answer + ". Удален");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Вопрос не найден");
        }
    }
    /**
     * /exam/java/find?text=...
     */
    @GetMapping("/find")
    public ResponseEntity<?> findQuestions(@RequestParam String text) {
        List<Question> results = service.getAll().stream()
                .filter(q -> q.getQuestion().toLowerCase().contains(text.toLowerCase())
                        || q.getAnswer().toLowerCase().contains(text.toLowerCase()))
                .toList();
        if (results.isEmpty()) {
            return ResponseEntity.ok("Поиск не дал результатов");
        }
        return ResponseEntity.ok(results);
    }


    @GetMapping
    public Collection<Question> getQuestions() {
        return service.getAll();
    }

}
