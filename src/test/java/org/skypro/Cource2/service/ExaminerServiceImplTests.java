package org.skypro.Cource2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skypro.Cource2.domain.Question;
import org.skypro.Cource2.exception.QuestionBadRequestException;
import org.skypro.Cource2.exception.QuestionsNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExaminerServiceImplTests {

    private QuestionServices javaQuestionsService;
    private QuestionServices mathQuestionsService;
    private ExaminerServiceImpl examinerService;

    @BeforeEach
    public void setUp() {
        javaQuestionsService = mock(QuestionServices.class);
        mathQuestionsService = mock(QuestionServices.class);

        Map<String, QuestionServices> servicesMap = new HashMap<>();
        servicesMap.put("java", javaQuestionsService);
        servicesMap.put("math", mathQuestionsService);

        examinerService = new ExaminerServiceImpl(servicesMap);
    }

    @Test
    @DisplayName("При запросе большего количества вопросов, чем доступно, бросает исключение")
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
    @DisplayName("Возвращает правильное количество вопросов(при допустимом количестве)")
    public void testGetQuestions_ValidAmount_ReturnsCorrectNumberOfQuestions() {
        Question q1 = new Question("Q1", "A1");
        Question q2 = new Question("Q2", "A2");
        Question q3 = new Question("Q3", "A3");

        when(javaQuestionsService.getRandomQuestion())
                .thenReturn(q1)
                .thenReturn(q2);
        when(mathQuestionsService.getRandomQuestion())
                .thenReturn(q3);

        Collection<Question> result = examinerService.getQuestions(2);

        assertEquals(2, result.size(), "Должно вернуться ровно 2 вопроса");
        assertTrue(result.stream().allMatch(q -> q.equals(q1) || q.equals(q2) || q.equals(q3)),
                "Все вопросы должны быть из доступных");

        verify(javaQuestionsService, atLeast(1)).getRandomQuestion();
        verify(mathQuestionsService, atLeast(1)).getRandomQuestion();
    }


    @Test
    @DisplayName("Проверяет возвращаются ли уникальные вопросы")
    public void testGetQuestions_AmountEqualsAvailable_ReturnsUniqueQuestions() {
        Question q1 = new Question("Q1", "A1");
        Question q2 = new Question("Q2", "A2");
        Question q3 = new Question("Q3", "A3");
        Question q4 = new Question("Q4", "A4");

        when(javaQuestionsService.getRandomQuestion())
                .thenReturn(q1)
                .thenReturn(q2);

        when(mathQuestionsService.getRandomQuestion())
                .thenReturn(q3)
                .thenReturn(q4);

        Collection<Question> result = examinerService.getQuestions(2);

        assertEquals(2, result.size(), "Должно вернуться ровно 2 вопроса");

        Set<Question> resultSet = new HashSet<>(result);
        assertEquals(2, resultSet.size(), "Все вопросы должны быть уникальными");

        verify(javaQuestionsService, atLeast(1)).getRandomQuestion();
        verify(mathQuestionsService, atLeast(1)).getRandomQuestion();

    }


    @Test
    @DisplayName("При запросе 0 вопросов, возвращает пустую коллекцию")
    public void testGetQuestions_AmountZero_ReturnsEmptyCollection() {

        when(javaQuestionsService.getRandomQuestion()).thenReturn(new Question("Q1", "A1"));
        when(mathQuestionsService.getRandomQuestion()).thenReturn(new Question("Q2", "A2"));

        Collection<Question> result = examinerService.getQuestions(0);

        assertEquals(0, result.size(), "Должна вернуться пустая коллекция");

        verify(javaQuestionsService, times(0)).getRandomQuestion();
        verify(mathQuestionsService, times(0)).getRandomQuestion();
    }

    @Test
    @DisplayName("При запросе 1 вопроса, возвращает 1 вопрос")
    public void testGetQuestions_AmountOne_ReturnsOneQuestion() {

        Question q1 = new Question("Q1", "A1");
        Question q2 = new Question("Q2", "A2");
        Question q3 = new Question("Q3", "A3");

        List<Question> javaQuestions = Arrays.asList(q1, q2);
        List<Question> mathQuestions = Collections.singletonList(q3);

        when(javaQuestionsService.getAll()).thenReturn(javaQuestions);
        when(mathQuestionsService.getAll()).thenReturn(mathQuestions);
        when(javaQuestionsService.getRandomQuestion()).thenReturn(q1);

        Collection<Question> result = examinerService.getQuestions(1);

        assertEquals(1, result.size(), "Должен вернуться один вопрос");
        assertTrue(result.contains(q1) || result.contains(q2) || result.contains(q3), // вот в этом месте у меня тест периодически падает
                "Результат должен содержать один из вопросов");                                 // что только ни пробовала, не могу решение найти ((
                                                                                                // если я буду статически вопросы выбирать, то сам смысл рандомного (метод getRandomQuestion) выбора пропадает
        verify(javaQuestionsService, times(1)).getRandomQuestion();
        verify(mathQuestionsService, times(0)).getRandomQuestion();
    }


    @Test
    @DisplayName("При отрицательном запросе выбрасывается исключение")
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
