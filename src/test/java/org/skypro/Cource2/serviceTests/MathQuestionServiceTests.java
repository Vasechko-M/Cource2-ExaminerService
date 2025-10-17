package org.skypro.Cource2.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.Cource2.domain.Question;
import org.skypro.Cource2.exception.QuestionAlreadyExistsException;
import org.skypro.Cource2.exception.QuestionsNotFoundException;
import org.skypro.Cource2.repository.MathQuestionRepository;
import org.skypro.Cource2.service.MathQuestionService;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MathQuestionServiceTests {

    private MathQuestionService mathService;

    @BeforeEach
    public void setUp() {
        MathQuestionRepository repository = new MathQuestionRepository();
        mathService = new MathQuestionService(repository);
    }

    @Test
    public void testInitialQuestionsLoaded() {
        Collection<Question> questions = mathService.getAll();
        assertNotNull(questions);
        assertFalse(questions.isEmpty(), "Коллекция вопросов не пустая");
        assertTrue(questions.size() >= 5);
    }

    @Test
    public void testAddNewQuestion() {
        String qText = "Что такое гипотенуза?";
        String aText = "Наименьшая сторона в прямоугольном треугольнике";

        Question newQuestion = mathService.add(qText, aText);

        assertNotNull(newQuestion);
        assertEquals(qText, newQuestion.getQuestion());
        assertEquals(aText, newQuestion.getAnswer());
        assertTrue(mathService.getAll().contains(newQuestion));
    }

    @Test
    public void testAddDuplicateQuestionThrows() {

        Question existing = mathService.getAll().iterator().next();
        assertThrows(QuestionAlreadyExistsException.class, () -> {
            mathService.add(existing);
        });
    }

    @Test
    public void testRemoveExistingQuestion() {
        Question q = new Question("Удаляемый вопрос", "Ответ");
        mathService.add(q);
        Question removed = mathService.remove(q);
        assertEquals(q, removed);
        assertFalse(mathService.getAll().contains(q));
    }

    @Test
    public void testRemoveNonExistingQuestionThrows() {
        Question q = new Question("Некоторый вопрос", "Некоторый ответ");
        assertThrows(QuestionsNotFoundException.class, () -> {
            mathService.remove(q);
        });
    }

    @Test
    public void testGetRandomQuestionReturnsQuestion() {
        Question q = mathService.getRandomQuestion();
        assertNotNull(q);
        assertTrue(mathService.getAll().contains(q));
    }

    @Test
    public void testGetRandomQuestionThrowsWhenEmpty() {

        for (Question q : mathService.getAll()) {
            mathService.remove(q);
        }
        assertThrows(QuestionsNotFoundException.class, () -> {
            mathService.getRandomQuestion();
        });
    }
    @Test
    public void testGetAllReturnsCopy() {
        Collection<Question> first = mathService.getAll();
        Collection<Question> second = mathService.getAll();
        assertNotSame(first, second, "Метод getAll должен возвращать новый объект коллекции");
        assertEquals(first.size(), second.size(), "Размеры коллекций должны совпадать");
    }

    @Test
    public void testFindQuestionsReturnsMatching() {

        String searchText = "площадь";
        List<Question> results = mathService.findQuestions(searchText);
        assertFalse(results.isEmpty());
        for (Question q : results) {
            String combined = q.getQuestion() + " " + q.getAnswer();
            assertTrue(combined.toLowerCase().contains(searchText.toLowerCase()));
        }
    }
    @Test
    public void testAddQuestionByQuestionObject() {
        Question question = new Question("Вопрос", "Ответ");
        Question added = mathService.add(question);
        assertSame(question, added, "Метод должен вернуть тот же объект вопроса");
        assertTrue(mathService.getAll().contains(question), "Коллекция должна содержать добавленный вопрос");
    }

    @Test
    public void testFindQuestionsNoResultsThrows() {
        String searchText = "такого слова нет в вопросах";
        assertThrows(QuestionsNotFoundException.class, () -> {
            mathService.findQuestions(searchText);
        });
    }
}