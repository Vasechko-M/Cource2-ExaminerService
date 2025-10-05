//package org.skypro.Cource2.search;
//
//import org.skypro.Cource2.domain.Question;
//import org.skypro.Cource2.search.QuestionSearch;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//public class SearchEngine implements QuestionSearch {
//
//    @Override
//    public List<Question> search(Collection<Question> questions, String questionText, String answerText) {
//        return questions.stream()
//                .filter(q -> matches(q, questionText, answerText))
//                .collect(Collectors.toList());
//    }
//
//    private boolean matches(Question q, String questionText, String answerText) {
//        boolean questionMatches = (questionText == null || q.getQuestion().toLowerCase().contains(questionText.toLowerCase()));
//        boolean answerMatches = (answerText == null || q.getAnswer().toLowerCase().contains(answerText.toLowerCase()));
//        return questionMatches && answerMatches;
//    }
//}