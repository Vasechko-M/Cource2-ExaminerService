package org.skypro.Cource2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(ex.getReason());
    }

    @ExceptionHandler(QuestionAlreadyExistsException.class)
    public ResponseEntity<String> handleQuestionAlreadyExists(QuestionAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(QuestionsNotFoundException.class)
    public ResponseEntity<String> handleQuestionsNotFound(QuestionsNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(QuestionBadRequestException.class)
    public ResponseEntity<String> handleQuestionsBedRequestException(QuestionBadRequestException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    public ResponseEntity<String> handleOperationNotAllowed(OperationNotAllowedException ex) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ex.getMessage());
    }
}
