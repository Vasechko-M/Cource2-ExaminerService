//package org.skypro.Cource2.exception;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.server.ResponseStatusException;
//
//@ControllerAdvice
//public class CustomExceptionHandler {
//
//    @ExceptionHandler(ResponseStatusException.class)
//    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
//        return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());
//    }
//}
