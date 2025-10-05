package org.skypro.Cource2.service;

import org.skypro.Cource2.domain.Question;

import java.util.Collection;

public interface ExaminerService {
    Collection<Question> getQuestions(int amount);
}