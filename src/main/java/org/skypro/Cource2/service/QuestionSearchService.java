//package org.skypro.Cource2.service;
//
//import org.skypro.Cource2.domain.Question;
//import org.skypro.Cource2.search.QuestionSearch;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//import java.util.List;
//
//@Service
//public class QuestionSearchService {
//
//    private final QuestionSearch questionSearch;
//
//    public QuestionSearchService(QuestionSearch questionSearch) {
//        this.questionSearch = questionSearch;
//    }
//
//    public List<Question> searchQuestions(Collection<Question> questions, String questionText, String answerText) {
//        return questionSearch.search(questions, questionText, answerText);
//    }
//}
