package org.skypro.Cource2.service;

import org.skypro.Cource2.domain.Question;
import org.skypro.Cource2.exception.QuestionBadRequestException;
import org.skypro.Cource2.exception.QuestionsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final QuestionServices questionService;
    private final Random random;

    public ExaminerServiceImpl(QuestionServices questionService) {
        this.questionService = questionService;
        this.random = new Random();
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        if (amount < 0) {
            throw new QuestionBadRequestException("Количество вопросов должно быть положительным");
        }
        Collection<Question> allQuestions = questionService.getAll();

        if (amount > allQuestions.size()) {

            throw new QuestionsNotFoundException(
                    "Запрошено больше вопросов, чем доступно. Запрошено: " + amount + ". Доступно: " + allQuestions.size());
        }

        Set<Question> result = new HashSet<>();

        while (result.size() < amount) {
            Question randomQuestion = questionService.getRandomQuestion();
            result.add(randomQuestion);
        }
        return result;
    }
}