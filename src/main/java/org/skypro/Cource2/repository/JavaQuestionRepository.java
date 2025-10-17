package org.skypro.Cource2.repository;

import org.skypro.Cource2.repository.QuestionRepository;
import org.skypro.Cource2.domain.Question;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class JavaQuestionRepository implements QuestionRepository {
    private final List<Question> questions = new CopyOnWriteArrayList<>();

    @Override
    public void add(Question question) {
        if (question != null) {
            questions.add(question);
        }
    }

    @Override
    public boolean remove(Question question) {
        return questions.remove(question);
    }

    @Override
    public List<Question> getAll() {
        return List.copyOf(questions);
    }
}