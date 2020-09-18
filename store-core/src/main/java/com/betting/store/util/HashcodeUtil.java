package com.betting.store.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@UtilityClass
public class HashcodeUtil {

    public final String REGEX = "[^0-9]";
    public final int BASE_TWO = 2;
    public final int NUMBER_ZERO = 0;

    public int buildHashcode(final int PRIME, String documentId) {
        String extractedId = documentId.replaceAll(REGEX, StringUtils.EMPTY);
        int id = Integer.parseInt(extractedId.subSequence(NUMBER_ZERO, extractedId.length() / BASE_TWO).toString());

        return new HashCodeBuilder(isNumberEven(id) ? ++id : id, PRIME).toHashCode();
    }

    private boolean isNumberEven(int number) {
        return number % BASE_TWO == NUMBER_ZERO;
    }

}
