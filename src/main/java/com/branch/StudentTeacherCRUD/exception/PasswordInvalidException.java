package com.branch.StudentTeacherCRUD.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class PasswordInvalidException extends DataIntegrityViolationException{
    public PasswordInvalidException(String message) {
        super(message);
    }
}
