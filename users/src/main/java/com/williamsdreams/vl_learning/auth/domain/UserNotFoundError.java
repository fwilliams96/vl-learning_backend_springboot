package com.williamsdreams.vl_learning.auth.domain;

import com.williamsdreams.vl_learning.shared.domain.BaseException;

public class UserNotFoundError extends BaseException {

    public static final String CODE = "USR003";
    public static final String MESSAGE = "User with email %s does not exist.";

    public UserNotFoundError(String email) {
        super(String.format(MESSAGE, email));
        addError(CODE, String.format(MESSAGE, email));
    }
}
