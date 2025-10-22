package org.skypro.Cource2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skypro.Cource2.domain.Question;
import org.skypro.Cource2.exception.OperationNotAllowedException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MathQuestionServiceTests {

    private MathQuestionService mathService;

    @BeforeEach
    public void setUp() {
        mathService = new MathQuestionService();
    }

    @Test
    @DisplayName("Возвращает случайный вопрос из набора")
    public void testGetRandomQuestion_ReturnsQuestionFromList() {
        MathQuestionService service = new MathQuestionService();

        Question question = service.getRandomQuestion();

        assertNotNull(question);
        List<Question> expectedQuestions = Arrays.asList(
                new Question("Что такое сумма 2 + 2?", "4"),
                new Question("Как найти площадь круга?", "π * r^2"),
                new Question("Что такое производная?", "Модель скорости изменения функции"),
                new Question("Что такое интеграл?", "Общая сумма или площадь под графиком"),
                new Question("Как решить уравнение x + 3 = 7?", "x = 4")
        );

        boolean found = false;
        for (Question q : expectedQuestions) {
            if (q.getQuestion().equals(question.getQuestion()) &&
                    q.getAnswer().equals(question.getAnswer())) {
                found = true;
                break;
            }
        }

        assertTrue(found, "Вернулся вопрос, который должен быть из списка");
    }

    @Test
    @DisplayName("Метод getAll выбрасывает исключение")
    public void testGetAll_ThrowsException() {

        assertThrows(OperationNotAllowedException.class, () -> {
            mathService.getAll();
        });
    }

    @Test
    @DisplayName("Метод add(String, String) выбрасывает исключение")
    public void testAddString_Throws() {

        assertThrows(OperationNotAllowedException.class, () -> {
            mathService.add("Вопрос", "Ответ");
        });
    }

    @Test
    @DisplayName("Метод add(Question) выбрасывает исключение")
    public void testAddQuestion_Throws() {

        assertThrows(OperationNotAllowedException.class, () -> {
            mathService.add(new Question("Вопрос", "Ответ"));
        });
    }

    @Test
    @DisplayName("Метод remove выбрасывает исключение")
    public void testRemove_Throws() {

        Question q = new Question("Вопрос", "Ответ");

        assertThrows(OperationNotAllowedException.class, () -> {
            mathService.remove(q);
        });
    }

    @Test
    @DisplayName("Метод findQuestions выбрасывает исключение")
    public void testFindQuestions_Throws() {

        assertThrows(OperationNotAllowedException.class, () -> {
            mathService.findQuestions("слово");
        });
    }
}