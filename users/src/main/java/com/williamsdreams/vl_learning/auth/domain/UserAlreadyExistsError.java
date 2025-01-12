package com.williamsdreams.vl_learning.auth.domain;

import com.williamsdreams.vl_learning.shared.domain.BaseException;

public class UserAlreadyExistsError extends BaseException {

    public static final String CODE = "USR001";
    public static final String MESSAGE = "User with email %s already exists.";

    public UserAlreadyExistsError(String email) {
        super(String.format(MESSAGE, email));
        addError(CODE, String.format(MESSAGE, email));
    }
}
