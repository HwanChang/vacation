package com.hwanchang.vacation.error;

import com.hwanchang.vacation.util.MessageUtils;
import org.apache.commons.lang3.StringUtils;

public class UserNotFoundException extends ServiceRuntimeException {

    static final String MESSAGE_KEY = "error.usernotfound";

    static final String MESSAGE_DETAILS = "error.usernotfound.details";

    public UserNotFoundException(Class<?> cls, Object... values) {
        this(cls.getSimpleName(), values);
    }

    public UserNotFoundException(String targetName, Object... values) {
        super(MESSAGE_KEY, MESSAGE_DETAILS, new String[]{targetName, (values != null && values.length > 0) ? StringUtils.join(values, ",") : ""});
    }

    @Override
    public String getMessage() {
        return MessageUtils.getMessage(getDetailKey(), getParams());
    }

    @Override
    public String toString() {
        return MessageUtils.getMessage(getMessageKey());
    }

}
