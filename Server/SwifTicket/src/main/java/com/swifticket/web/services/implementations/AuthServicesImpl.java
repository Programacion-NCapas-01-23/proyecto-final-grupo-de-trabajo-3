package com.swifticket.web.services.implementations;

import com.swifticket.web.models.entities.Token;
import com.swifticket.web.models.entities.User;
import com.swifticket.web.repositories.TokenRepository;
import com.swifticket.web.repositories.UserRepository;
import com.swifticket.web.services.AuthServices;
import com.swifticket.web.services.EmailServices;
import com.swifticket.web.utils.RandomCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
public class AuthServicesImpl implements AuthServices {
    private final EmailServices emailService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RandomCode randomCode;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Autowired
    public AuthServicesImpl(UserRepository userRepository, EmailServices emailService, TokenRepository tokenRepository, RandomCode randomCode) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;
        this.randomCode = randomCode;
    }

    @Override
    public Boolean isTokenValid(String id) {
    	try {
    		UUID _id = UUID.fromString(id);
            Token token = tokenRepository.findById(_id).orElse(null);
            if (token != null) {
                Timestamp createdAt = token.getCreatedAt();
                Timestamp expiresAt = token.getExpiresAt();
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                return currentTimestamp.before(expiresAt) && currentTimestamp.after(createdAt);
            }
            return false;
		} catch (Exception e) {
			return false;
		}
    }

    // TODO: This method requires a temporal attribute to the User for the confirmationCode
    /*
    @Override
    public Boolean validateAccount(String code) {
        User user = userRepository.findByConfirmationCode(code);
        String confirmationCode = randomCode.generateConfirmationCode();
        emailService.sendConfirmationCode(user.getEmail(), confirmationCode);
        return code.equals(confirmationCode);
    }
    */
    @Override
    public Boolean validateAccount(String code) {
        return true;
    }

    @Override
    public User signIn(String email, String password) {
        User user = userRepository.findByEmail(email);
        // TODO: Validate user state to sign in
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public String hashPassword(String password) {return passwordEncoder.encode(password);}
}
