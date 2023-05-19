package com.swifticket.web.services;

public interface EmailServices {
    void sendConfirmationCode(String email, String confirmationCode);
}
