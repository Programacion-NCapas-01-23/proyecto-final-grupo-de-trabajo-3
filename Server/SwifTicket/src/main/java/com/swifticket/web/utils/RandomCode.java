package com.swifticket.web.utils;

import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class RandomCode {
    public String generateConfirmationCode() {
        Random random = new Random();
        int codeLength = 10;
        StringBuilder confirmationCode = new StringBuilder();

        for (int i = 0; i < codeLength; i++) {
            int digit = random.nextInt(10);
            confirmationCode.append(digit);
        }

        return confirmationCode.toString();
    }
}
