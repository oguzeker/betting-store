package com.betting.store.util;

import com.betting.store.exception.StoreCoreError;
import com.betting.store.exception.StoreCoreException;
import lombok.experimental.UtilityClass;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;
import java.util.Optional;

@UtilityClass
public class MessageSourceUtil {

    private final String CLASSPATH_MESSAGES = "classpath:messages";
    private final MessageSource MESSAGE_SOURCE = getDefaultMessageSource();
    private final String UTF_8 = "UTF-8";

    public StoreCoreException prepareException(final StoreCoreException ex) {
        final StoreCoreError error = ex.getError();
        Optional.ofNullable(ex.getArgs()).ifPresentOrElse(
                objects -> ex.setMessage(MessageSourceUtil.getMessageFromSource(error.name(), objects)),
                () -> ex.setMessage(MessageSourceUtil.getMessageFromSource(error.name())));

        return ex;
    }

    private String getMessageFromSource(String name, Object... args) {
        return MESSAGE_SOURCE.getMessage(name, args, Locale.US);
    }

    private MessageSource getDefaultMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(MessageSourceUtil.CLASSPATH_MESSAGES);
        messageSource.setDefaultEncoding(UTF_8);
        return messageSource;
    }

}
