package org.skypro.Cource2.controller;

import org.skypro.Cource2.domain.Question;
import org.skypro.Cource2.service.QuestionServices;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/exam/java")
public class JavaQuestionController {

    private final QuestionServices service;

    public JavaQuestionController(QuestionServices service) {
        this.service = service;
    }


    @GetMapping("/add")
    public String addQuestion(
            @RequestParam @NotBlank(message = "Вопрос не может быть пустым") String question,
            @RequestParam @NotBlank(message = "Ответ не может быть пустым") String answer) {
        Question addedQuestion = service.add(new Question(question, answer));
        return "Добавлен вопрос: " + addedQuestion.getQuestion() + " и ответ: " + addedQuestion.getAnswer();
    }


    @GetMapping("/remove")
    public String removeQuestion(
            @RequestParam @NotBlank(message = "Вопрос не может быть пустым") String question,
            @RequestParam @NotBlank(message = "Ответ не может быть пустым") String answer) {
        Question toRemove = new Question(question, answer);
        service.remove(toRemove);
        return "Вопрос: " + question + ", Ответ: " + answer + ". Удален";
    }


    @GetMapping("/find")
    public List<Question> findQuestions(@RequestParam @NotBlank(message = "Текст поиска не может быть пустым") String text) {
        return service.findQuestions(text);
    }


    @GetMapping("/random")
    public Question getRandomQuestion() {
        return service.getRandomQuestion();
    }

    @GetMapping
    public Collection<Question> getQuestions() {
        return service.getAll();
    }
}
