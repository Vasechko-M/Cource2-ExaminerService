package org.skypro.Cource2.service;

import org.skypro.Cource2.domain.Question;

import java.util.Collection;

public interface ExaminerService {
    //    public Question getRandomQuestionFromJava() {
    //        QuestionServices javaService = questionServicesMap.get("javaQuestionService");
    //        return javaService.getRandomQuestion();
    //    }
    //
    //    public Question getRandomQuestionFromMath() {
    //        QuestionServices mathService = questionServicesMap.get("mathQuestionService");
    //        return mathService.getRandomQuestion();
    //    }
    Question getRandomQuestion();

    Collection<Question> getQuestions(int amount);
}