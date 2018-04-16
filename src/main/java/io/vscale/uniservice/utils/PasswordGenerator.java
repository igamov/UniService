package io.vscale.uniservice.utils;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Component
public class PasswordGenerator {

    private Random random = new Random();
    private int length = 10;

    private static final String ALPHA_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*_=+-/";

    public String generate(){

        StringBuilder sb = new StringBuilder();
        sb.append(ALPHA_CHARS).append(ALPHA).append(NUMERIC).append(SPECIAL_CHARS);
        String possibleChars = sb.toString();

        return IntStream.range(0, this.length)
                        .mapToObj(i -> String.valueOf(possibleChars.charAt(this.random.nextInt(possibleChars.length()))))
                        .collect(Collectors.joining());

    }

}
