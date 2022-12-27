package com.krsoft.zedpay.exception;


import com.krsoft.zedpay.utils.MessageUtils;
import lombok.Data;

@Data
public class MiscellaneaException extends RuntimeException {

    private final ErrorTypeEnum errorTypeEnum;

    public MiscellaneaException(ErrorTypeEnum errorTypeEnum, String messageRessourceCode, Object... paramsMessages) {
        super(MessageUtils.getInstance().getMessage(messageRessourceCode, paramsMessages));
        this.errorTypeEnum = errorTypeEnum;
    }

    public MiscellaneaException(Throwable cause, ErrorTypeEnum errorTypeEnum, String messageRessourceCode, Object... paramsMessages) {
        super(MessageUtils.getInstance().getMessage(messageRessourceCode, paramsMessages), cause);
        this.errorTypeEnum = errorTypeEnum;
    }
}
