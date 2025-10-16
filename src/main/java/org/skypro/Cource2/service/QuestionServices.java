package org.skypro.Cource2.service;

import org.skypro.Cource2.domain.Question;

import java.util.Collection;
import java.util.List;

public interface QuestionServices {
    Question add(String question, String answer);
    Question add(Question question);
    Question remove(Question question);
    Collection<Question> getAll();
    Question getRandomQuestion();
    List<Question> findQuestions(String text);
}