package org.skypro.Cource2.service;

import org.skypro.Cource2.domain.Question;
import org.skypro.Cource2.exception.QuestionBadRequestException;
import org.skypro.Cource2.exception.QuestionsNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final Map<String, QuestionServices> questionServicesMap;
    private final Random random;

    public ExaminerServiceImpl(@Qualifier("questionServicesMap") Map<String, QuestionServices> questionServicesMap) {
        this.questionServicesMap = questionServicesMap;
        this.random = new Random();
    }

    @Override
    public Question getRandomQuestion() {
        List<QuestionServices> services = new ArrayList<>(questionServicesMap.values());
        int index = random.nextInt(services.size());
        return services.get(index).getRandomQuestion();
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        if (amount < 0) {
            throw new QuestionBadRequestException("Количество вопросов должно быть положительным");
        }
        if (amount == 0) {
            return Collections.emptyList();
        }

        List<QuestionServices> services = new ArrayList<>(questionServicesMap.values());
        Set<Question> result = new HashSet<>();
        int maxAttempts = amount * 10;
        int attempts = 0;

        while (result.size() < amount && attempts < maxAttempts) {
            attempts++;
            int serviceIndex = random.nextInt(services.size());
            QuestionServices service = services.get(serviceIndex);
            try {
                Question q = service.getRandomQuestion();
                result.add(q);
            } catch (Exception e) {
            }
        }

        if (result.size() < amount) {
            throw new QuestionsNotFoundException("Запрошено больше вопросов, чем доступно");
        }

        return result;
    }
}