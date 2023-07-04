package com.swifticket.web.services;

public interface EmailServices {
    void sendVerificationAccountCode(String email, String confirmationCode);
    void sendVerificationAccountCode(String email, String confirmationCode, String password);
    void sendVerificationTransactionCode(String email, String confirmationCode);
}
