package com.swifticket.web.services;

public interface EmailServices {
    void sendVerificationAccountCode(String email, String confirmationCode);
    void sendVerificationTransactionCode(String email, String confirmationCode);
}
