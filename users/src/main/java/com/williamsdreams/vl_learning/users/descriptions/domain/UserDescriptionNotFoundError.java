package com.williamsdreams.vl_learning.users.descriptions.domain;

import com.williamsdreams.vl_learning.shared.domain.BaseException;

public class UserDescriptionNotFoundError extends BaseException {

    public static final String CODE = "USD001";
    public static final String MESSAGE = "User description with id %s does not exist.";

    public UserDescriptionNotFoundError(String email) {
        super(String.format(MESSAGE, email));
        addError(CODE, String.format(MESSAGE, email));
    }
}
