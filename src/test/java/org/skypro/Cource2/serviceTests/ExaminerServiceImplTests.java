package org.skypro.Cource2.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.Cource2.domain.Question;
import org.skypro.Cource2.exception.QuestionBadRequestException;
import org.skypro.Cource2.exception.QuestionsNotFoundException;
import org.skypro.Cource2.service.ExaminerServiceImpl;
import org.skypro.Cource2.service.QuestionServices;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.*;


import java.util.*;

import static org.mockito.Mockito.*;

public class ExaminerServiceImplTests {

    private QuestionServices javaQuestionsService;
    private QuestionServices mathQuestionsService;
    private ExaminerServiceImpl examinerService;

    @BeforeEach
    public void setUp() {
        javaQuestionsService = mock(QuestionServices.class);
        mathQuestionsService = mock(QuestionServices.class);
        examinerService = new ExaminerServiceImpl(javaQuestionsService, mathQuestionsService);
    }



    @Test
    public void testGetQuestions_LargerAmountThanAvailable_ThrowsException() {
        List<Question> questions = Arrays.asList(
                new Question("Q1", "A1"),
                new Question("Q2", "A2")
        );
        when(javaQuestionsService.getAll()).thenReturn(questions);
        when(mathQuestionsService.getAll()).thenReturn(Collections.emptyList());

        QuestionsNotFoundException thrown = assertThrows(QuestionsNotFoundException.class, () -> {
            examinerService.getQuestions(3);
        });

        assertTrue(thrown.getMessage().contains("Запрошено больше вопросов"));
    }



    @Test
    public void testGetQuestions_ValidAmount_ReturnsCorrectNumberOfQuestions() {
        Question q1 = new Question("Q1", "A1");
        Question q2 = new Question("Q2", "A2");
        Question q3 = new Question("Q3", "A3");

        List<Question> javaQuestions = Arrays.asList(q1, q2);
        List<Question> mathQuestions = Arrays.asList(q3);

        when(javaQuestionsService.getAll()).thenReturn(javaQuestions);
        when(mathQuestionsService.getAll()).thenReturn(mathQuestions);

        when(javaQuestionsService.getRandomQuestion())
                .thenReturn(q1)
                .thenReturn(q2);
        when(mathQuestionsService.getRandomQuestion())
                .thenReturn(q3);

        Collection<Question> result = examinerService.getQuestions(2);

        assertEquals(2, result.size(), "Должно вернуться ровно 2 вопроса");
        assertTrue(result.contains(q1) || result.contains(q2), "Должен содержать Q1 или Q2");

        verify(javaQuestionsService, times(1)).getAll();
        verify(mathQuestionsService, times(1)).getAll();
    }


    @Test
    public void testGetQuestions_AmountEqualsAvailable_ReturnsUniqueQuestions() {
        Question q1 = new Question("Q1", "A1");
        Question q2 = new Question("Q2", "A2");
        Question q3 = new Question("Q3", "A3");
        List<Question> javaQuestions = Arrays.asList(q1, q2);
        List<Question> mathQuestions = Arrays.asList(q3);

        when(javaQuestionsService.getAll()).thenReturn(javaQuestions);
        when(mathQuestionsService.getAll()).thenReturn(mathQuestions);

        Collection<Question> result = examinerService.getQuestions(3);

        assertEquals(3, result.size(), "Должно вернуться ровно 3 вопроса");

        Set<Question> resultSet = new HashSet<>(result);
        assertEquals(3, resultSet.size(), "Все вопросы должны быть уникальными");

        verify(javaQuestionsService, times(1)).getAll();
        verify(mathQuestionsService, times(1)).getAll();
    }


    @Test
    public void testGetQuestions_AmountZero_ReturnsEmptyCollection() {
        List<Question> javaQuestions = Arrays.asList(
                new Question("Q1", "A1"),
                new Question("Q2", "A2")
        );
        when(javaQuestionsService.getAll()).thenReturn(javaQuestions);
        when(mathQuestionsService.getAll()).thenReturn(Collections.emptyList());

        Collection<Question> result = examinerService.getQuestions(0);

        assertEquals(0, result.size(), "Должна вернуться пустая коллекция");

        verify(javaQuestionsService, times(1)).getAll();
        verify(javaQuestionsService, times(0)).getRandomQuestion();
        verify(mathQuestionsService, times(0)).getRandomQuestion();
    }

@Test
    public void testGetQuestions_AmountOne_ReturnsOneQuestion() {

        Question q1 = new Question("Q1", "A1");
        Question q2 = new Question("Q2", "A2");
        Question q3 = new Question("Q3", "A3");

        List<Question> javaQuestions = Arrays.asList(q1, q2);
        List<Question> mathQuestions = Collections.singletonList(q3);

        when(javaQuestionsService.getAll()).thenReturn(javaQuestions);
        when(mathQuestionsService.getAll()).thenReturn(mathQuestions);

        Collection<Question> result = examinerService.getQuestions(1);

        assertEquals(1, result.size(), "Должен вернуться один вопрос");
        assertTrue(result.contains(q1) || result.contains(q2) || result.contains(q3),
                "Результат должен содержать один из вопросов");

        verify(javaQuestionsService, times(1)).getAll();
        verify(mathQuestionsService, times(1)).getAll();
    }


    @Test
    public void testGetQuestions_NegativeAmount_ThrowsException() {
        QuestionBadRequestException exception = assertThrows(QuestionBadRequestException.class, () -> {
            examinerService.getQuestions(-1);
        });
        assertTrue(exception.getMessage().contains("Количество вопросов должно быть положительным"));

        verify(javaQuestionsService, never()).getAll();
        verify(mathQuestionsService, never()).getAll();
        verify(javaQuestionsService, never()).getRandomQuestion();
        verify(mathQuestionsService, never()).getRandomQuestion();
    }

}
