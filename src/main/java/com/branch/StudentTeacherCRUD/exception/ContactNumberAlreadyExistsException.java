package com.branch.StudentTeacherCRUD.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class ContactNumberAlreadyExistsException extends DataIntegrityViolationException {
    public ContactNumberAlreadyExistsException(String message) {
        super(message);
    }
}
