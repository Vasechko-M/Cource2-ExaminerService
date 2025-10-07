package org.skypro.Cource2.service;

import org.skypro.Cource2.domain.Question;
import org.springframework.stereotype.Service;

import java.util.*;
@Service

public class JavaQuestionService implements QuestionServices {

    private final Set<Question> questions = new HashSet<>();
    private final Random random = new Random();

    public JavaQuestionService() {
        questions.add(new Question("Что такое JVM?", "Java Virtual Machine"));
        questions.add(new Question("Как объявить переменную типа int?", "int x;"));
        questions.add(new Question("Как создать объект класса?", "new ClassName()"));
        questions.add(new Question("Что значит 'static'?", "Статический член класса"));
        questions.add(new Question("Как написать однострочный комментарий?", "// комментарий"));
    }

    @Override
    public Question add(String question, String answer) {
        Question q = new Question(question, answer);
        questions.add(q);
        return q;
    }

    @Override
    public Question add(Question question) {
        questions.add(question);
        return question;
    }

    @Override
    public Question remove(Question question) {
        boolean removed = questions.remove(question);
        return removed ? question : null;
    }

    @Override
    public Collection<Question> getAll() {
        return new HashSet<>(questions);
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            throw new NoSuchElementException("Нет вопросов для выбора"); //здесь тоже можно свое исключение?
        }
        List<Question> list = new ArrayList<>(questions);
        int index = random.nextInt(list.size());
        return list.get(index);
    }
}