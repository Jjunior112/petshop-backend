package com.littlebirds.petshop.application.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class HandlerException {

    // 404 - Entidade não encontrada (Cliente, Pet, etc)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(EntityNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 400 - Erro de validação dos DTOs (Bean Validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldsErrors>> handleValidation(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors().stream()
                .map(FieldsErrors::new)
                .toList();
        return ResponseEntity.badRequest().body(errors);
    }

    // 400 - Erros de validação genéricos
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(ValidationException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // 400 - Erros de validação por ConstraintViolation
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // 400 - Erros em transações (validação dentro do JPA)
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<Map<String, Object>> handleTransaction(TransactionSystemException ex) {
        return buildError(HttpStatus.BAD_REQUEST, "Erro de validação de entidade: " + ex.getMostSpecificCause().getMessage());
    }

    // 409 - Violação de integridade (duplicidade, FK, etc)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(DataIntegrityViolationException ex) {
        return buildError(HttpStatus.CONFLICT, "Violação de integridade de dados.");
    }

    // 401 - Falha de autenticação (usuário não autenticado)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthentication(AuthenticationException ex) {
        return buildError(HttpStatus.UNAUTHORIZED, "Autenticação inválida ou ausente.");
    }

    // 403 - Falta de permissão (usuário autenticado, mas sem acesso)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        return buildError(HttpStatus.FORBIDDEN, "Acesso negado ao recurso.");
    }

    // 400 - Argumentos inválidos
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // 500 - Qualquer outro erro inesperado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor: " + ex.getMessage());
    }

    // ==== Classe auxiliar para formatar os erros de campo ====
    private record FieldsErrors(String field, String message) {
        public FieldsErrors(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    // ==== Método utilitário para padronizar o corpo do erro ====
    private ResponseEntity<Map<String, Object>> buildError(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }
}
