package org.skypro.Cource2.repositoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.Cource2.domain.Question;
import org.skypro.Cource2.repository.JavaQuestionRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JavaQuestionRepositoryTests {

    private JavaQuestionRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new JavaQuestionRepository();
    }

    @Test
    public void testAddQuestion_shouldAddQuestion() {
        Question question = new Question("Вопрос 1", "Ответ 1");
        repository.add(question);

        List<Question> all = repository.getAll();
        assertTrue(all.contains(question), "Добавленный вопрос должен присутствовать в репозитории");
    }

    @Test
    public void testAddNullQuestion_shouldNotThrowOrAdd() {
        assertDoesNotThrow(() -> repository.add(null), "Добавление null не должно выбрасывать исключение");

        List<Question> all = repository.getAll();
        assertTrue(all.isEmpty(), "Репозиторий должен быть пустым после попытки добавления null");
    }

    @Test
    public void testRemoveExistingQuestion_shouldReturnTrueAndRemove() {
        Question question = new Question("Вопрос 2", "Ответ 2");
        repository.add(question);

        boolean removed = repository.remove(question);

        assertTrue(removed, "Удаление существующего вопроса должно вернуть true");
        assertFalse(repository.getAll().contains(question), "Вопрос должен быть удалён из репозитория");
    }

    @Test
    public void testRemoveNonExistingQuestion_shouldReturnFalse() {
        Question question = new Question("Вопрос отсутствует", "Ответ");

        boolean removed = repository.remove(question);

        assertFalse(removed, "Удаление отсутствующего вопроса должно вернуть false");
    }

    @Test
    public void testGetAll_shouldReturnCopy() {
        Question question = new Question("Вопрос 3", "Ответ 3");
        repository.add(question);

        List<Question> first = repository.getAll();
        List<Question> second = repository.getAll();

        assertNotSame(first, second, "Метод getAll должен возвращать новый объект списка");
        assertEquals(first, second, "Возвращаемые списки должны быть равны по содержимому");

        assertThrows(UnsupportedOperationException.class, () -> first.add(new Question("x", "y")));
    }
}