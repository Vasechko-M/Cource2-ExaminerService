package org.skypro.Cource2.service;

import org.skypro.Cource2.domain.Question;
import org.skypro.Cource2.exception.QuestionAlreadyExistsException;
import org.skypro.Cource2.exception.QuestionsNotFoundException;
import org.skypro.Cource2.repository.MathQuestionRepository;
import org.skypro.Cource2.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("mathQuestionService")
public class MathQuestionService implements QuestionServices {

    private final MathQuestionRepository questionRepository;
    private final Random random = new Random();

    public MathQuestionService(MathQuestionRepository questionRepository) {
        this.questionRepository = questionRepository;

        questionRepository.add(new Question("Что такое сумма 2 + 2?", "4"));
        questionRepository.add(new Question("Как найти площадь круга?", "π * r^2"));
        questionRepository.add(new Question("Что такое производная?", "Модель скорости изменения функции"));
        questionRepository.add(new Question("Что такое интеграл?", "Общая сумма или площадь под графиком"));
        questionRepository.add(new Question("Как решить уравнение x + 3 = 7?", "x = 4"));
    }

    @Override
    public Question add(String question, String answer) {
        Question q = new Question(question, answer);
        return add(q);
    }

    @Override
    public Question add(Question question) {
        // Проверка на существование
        Collection<Question> allQuestions = questionRepository.getAll();
        boolean exists = allQuestions.stream()
                .anyMatch(x -> x.getQuestion().equals(question.getQuestion()) && x.getAnswer().equals(question.getAnswer()));
        if (exists) {
            throw new QuestionAlreadyExistsException("Этот вопрос с таким ответом уже был.");
        }
        questionRepository.add(question);
        return question;
    }

    @Override
    public Question remove(Question question) {
        boolean removed = questionRepository.remove(question);
        if (!removed) {
            throw new QuestionsNotFoundException("Вопрос не найден");
        }
        return question;
    }

    @Override
    public Collection<Question> getAll() {
        return questionRepository.getAll();
    }

    @Override
    public Question getRandomQuestion() {
        Collection<Question> allQuestions = questionRepository.getAll();
        if (allQuestions.isEmpty()) {
            throw new QuestionsNotFoundException("Нет вопросов для выбора");
        }
        List<Question> list = new ArrayList<>(allQuestions);
        int index = random.nextInt(list.size());
        return list.get(index);
    }

    public List<Question> findQuestions(String text) {
        Collection<Question> allQuestions = questionRepository.getAll();
        List<Question> results = allQuestions.stream()
                .filter(q -> q.getQuestion().toLowerCase().contains(text.toLowerCase())
                        || q.getAnswer().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            throw new QuestionsNotFoundException("Поиск не дал результатов");
        }
        return results;
    }
}