package com.williamsdreams.vl_learning.auth.domain;

import com.williamsdreams.vl_learning.shared.domain.BaseException;

public class UserInvalidCredentialsError extends BaseException {

    public static final String CODE = "USR002";
    public static final String MESSAGE = "User credentials not valid.";

    public UserInvalidCredentialsError() {
        super(MESSAGE);
        addError(CODE, MESSAGE);
    }
}
