package com.example.newsblog.util;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class HtmlSanitizer {

    private static final PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);

    public static String sanitize(String value) {
        return policy.sanitize(value);
    }

}
