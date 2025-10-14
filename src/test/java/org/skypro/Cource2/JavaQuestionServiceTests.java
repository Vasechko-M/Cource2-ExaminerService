package org.skypro.Cource2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.Cource2.domain.Question;
import org.skypro.Cource2.exception.QuestionsNotFoundException;
import org.skypro.Cource2.service.JavaQuestionService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class JavaQuestionServiceTests {

    private JavaQuestionService javaQuestionService;

    @BeforeEach
    public void setUp() {
        javaQuestionService = new JavaQuestionService();
    }

    @Test
    public void testInitialQuestionsNotEmpty() {
        Collection<Question> questions = javaQuestionService.getAll();
        assertFalse(questions.isEmpty(), "Начальный набор вопросов не должен быть пустым");
        assertTrue(questions.size() >= 5, "Должно содержать не менее 5 вопросов");
    }

    @Test
    public void testAddQuestionByStrings() {
        Question added = javaQuestionService.add("Новый вопрос?", "Новый ответ");
        Collection<Question> all = javaQuestionService.getAll();
        assertTrue(all.contains(added), "Коллекция должна содержать добавленный вопрос");
    }

    @Test
    public void testAddQuestionByQuestionObject() {
        Question question = new Question("Вопрос", "Ответ");
        Question added = javaQuestionService.add(question);
        assertSame(question, added, "Метод должен вернуть тот же объект вопроса");
        assertTrue(javaQuestionService.getAll().contains(question), "Коллекция должна содержать добавленный вопрос");
    }

    @Test
    public void testRemoveExistingQuestion() {
        Question question = new Question("Удаляемый вопрос", "Ответ");
        javaQuestionService.add(question);
        Question removed = javaQuestionService.remove(question);
        assertEquals(question, removed, "Метод должен вернуть удалённый вопрос");
        assertFalse(javaQuestionService.getAll().contains(question), "Коллекция не должна содержать удалённый вопрос");
    }

    @Test
    public void testRemoveNonExistingQuestion() {
        Question question = new Question("Отсутствующий вопрос", "Ответ");
        assertThrows(QuestionsNotFoundException.class, () -> {
            javaQuestionService.remove(question);
        }, "При удалении несуществующего вопроса должно выбрасываться QuestionsNotFoundException");
    }

    @Test
    public void testGetAllReturnsCopy() {
        Collection<Question> first = javaQuestionService.getAll();
        Collection<Question> second = javaQuestionService.getAll();
        assertNotSame(first, second, "Метод getAll должен возвращать новый объект коллекции");
        assertEquals(first.size(), second.size(), "Размеры коллекций должны совпадать");
    }

    @Test
    public void testGetRandomQuestionReturnsQuestion() {
        Question randomQuestion = javaQuestionService.getRandomQuestion();
        assertNotNull(randomQuestion, "getRandomQuestion не должен возвращать null");
        assertTrue(javaQuestionService.getAll().contains(randomQuestion), "Возвращённый вопрос должен принадлежать к коллекции");
    }

    @Test
    public void testGetRandomQuestionThrowsIfEmpty() {

        for (Question q : new ArrayList<>(javaQuestionService.getAll())) {
            javaQuestionService.remove(q);
        }

        assertThrows(QuestionsNotFoundException.class, () -> javaQuestionService.getRandomQuestion(),
                "При отсутствии вопросов должно выбрасываться QuestionsNotFoundException");
    }

}