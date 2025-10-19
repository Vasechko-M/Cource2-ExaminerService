package org.skypro.Cource2.repository;

import org.skypro.Cource2.domain.Question;
import java.util.Collection;

public interface QuestionRepository {
    void add(Question question);
    boolean remove(Question question);
    Collection<Question> getAll();
}