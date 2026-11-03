package com.branch.StudentTeacherCRUD.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class EmailAlreadyExistsException extends DataIntegrityViolationException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
