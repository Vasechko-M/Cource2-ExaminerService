package org.skypro.Cource2.service;

import org.skypro.Cource2.domain.Question;
import org.skypro.Cource2.exception.QuestionBadRequestException;
import org.skypro.Cource2.exception.QuestionsNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final QuestionServices javaQuestionsService;
    private final QuestionServices mathQuestionsService;
    private final Random random;

    public ExaminerServiceImpl(
            @Qualifier("javaQuestionService") QuestionServices javaQuestionsService,
            @Qualifier("mathQuestionService") QuestionServices mathQuestionsService) {
        this.javaQuestionsService = javaQuestionsService;
        this.mathQuestionsService = mathQuestionsService;
        this.random = new Random();
    }
    public Question getRandomQuestionFromJava() {
        return javaQuestionsService.getRandomQuestion();
    }

    public Question getRandomQuestionFromMath() {
        return mathQuestionsService.getRandomQuestion();
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        List<Question> allQuestions = new ArrayList<>();
        allQuestions.addAll(javaQuestionsService.getAll());
        allQuestions.addAll(mathQuestionsService.getAll());
        if (amount < 0) {
            throw new QuestionBadRequestException("Количество вопросов должно быть положительным");
        }
        if (amount > allQuestions.size()) {
            throw new QuestionsNotFoundException(
                    "Запрошено больше вопросов, чем доступно. Запрошено: " + amount + ". Доступно: " + allQuestions.size());
        }

        Set<Question> result = new HashSet<>();

        while (result.size() < amount) {
            int index = random.nextInt(allQuestions.size());
            result.add(allQuestions.get(index));
        }
        return result;
    }
}