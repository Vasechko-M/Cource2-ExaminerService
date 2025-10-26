package org.skypro.Cource2.service;

import org.skypro.Cource2.domain.Question;
import org.skypro.Cource2.exception.OperationNotAllowedException;

import org.springframework.stereotype.Service;

import java.util.*;

@Service("mathQuestionService")
public class MathQuestionService implements QuestionServices {

    private final List<Question> questions = new ArrayList<>();
    private final Random random = new Random();

    public MathQuestionService() {
        questions.add(new Question("Что такое сумма 2 + 2?", "4"));
        questions.add(new Question("Как найти площадь круга?", "π * r^2"));
        questions.add(new Question("Что такое производная?", "Модель скорости изменения функции"));
        questions.add(new Question("Что такое интеграл?", "Общая сумма или площадь под графиком"));
        questions.add(new Question("Как решить уравнение x + 3 = 7?", "x = 4"));
    }

    @Override
    public Question add(String question, String answer) {
        throw new OperationNotAllowedException("Метод добавления вопросов отключен для математического сервиса");
    }

    @Override
    public Question add(Question question) {
        throw new OperationNotAllowedException("Метод добавления вопросов отключен для математического сервиса");
    }

    @Override
    public Question remove(Question question) {
        throw new OperationNotAllowedException("Метод удаления вопросов отключен для математического сервиса");
    }

    @Override
    public Collection<Question> getAll() {
        throw new OperationNotAllowedException("Получение вопросов отключено для математического сервиса");
    }

    @Override
    public Question getRandomQuestion() {
        int index = random.nextInt(questions.size());
        return questions.get(index);
    }

    public List<Question> findQuestions(String text) {
        throw new OperationNotAllowedException("Метод поиска вопросов отключен для математического сервиса");
    }
}