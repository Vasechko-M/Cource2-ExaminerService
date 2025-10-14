package org.skypro.Cource2.service;

import org.skypro.Cource2.domain.Question;
import org.skypro.Cource2.exception.QuestionAlreadyExistsException;
import org.skypro.Cource2.exception.QuestionsNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        boolean exists = questions.stream()
                .anyMatch(x -> x.getQuestion().equals(question.getQuestion()) && x.getAnswer().equals(question.getAnswer()));
        if (exists) {
            throw new QuestionAlreadyExistsException("Этот вопрос с таким ответом уже был.");
        }
        questions.add(question);
        return question;
    }

    @Override
    public Question remove(Question question) {
        boolean removed = questions.remove(question);
        if (!removed) {
            throw new QuestionsNotFoundException("Вопрос не найден");
        }
        return question;
    }

    @Override
    public Collection<Question> getAll() {
        return new HashSet<>(questions);
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            throw new QuestionsNotFoundException("Нет вопросов для выбора");
        }
        List<Question> list = new ArrayList<>(questions);
        int index = random.nextInt(list.size());
        return list.get(index);
    }
    public List<Question>findQuestions(String text) {
        List<Question> results = questions.stream()
                .filter(q -> q.getQuestion().toLowerCase().contains(text.toLowerCase())
                        || q.getAnswer().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            throw new QuestionsNotFoundException("Поиск не дал результатов");
        }
        return results;
    }
}