package com.branch.StudentTeacherCRUD.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class InvalidContactNumberException extends DataIntegrityViolationException {
    public InvalidContactNumberException(String message) {
        super(message);
    }
}
