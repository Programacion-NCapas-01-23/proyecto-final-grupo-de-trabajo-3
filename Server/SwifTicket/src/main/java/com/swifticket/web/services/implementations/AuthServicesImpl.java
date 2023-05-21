package com.swifticket.web.services.implementations;

import com.swifticket.web.models.entities.Token;
import com.swifticket.web.models.entities.User;
import com.swifticket.web.models.entities.UserState;
import com.swifticket.web.models.entities.VerifyAccountToken;
import com.swifticket.web.repositories.TokenRepository;
import com.swifticket.web.repositories.UserRepository;
import com.swifticket.web.repositories.VerifyAccountTokenRepository;
import com.swifticket.web.services.AuthServices;
import com.swifticket.web.services.EmailServices;
import com.swifticket.web.utils.RandomCode;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthServicesImpl implements AuthServices {
    private final EmailServices emailService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final VerifyAccountTokenRepository accountTokenRepository;
    private final RandomCode randomCode;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    private final String ACTIVE = "Activo";
    private final String BLOCKED = "Bloqueado";
    private final String UNVERIFIED = "No-verificado";
    
    @Autowired
    public AuthServicesImpl(UserRepository userRepository, EmailServices emailService, TokenRepository tokenRepository, VerifyAccountTokenRepository accountTokenRepository, RandomCode randomCode) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;
		this.accountTokenRepository = accountTokenRepository;
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
        try {
        	UUID _code = UUID.fromString(code);
        	VerifyAccountToken token = accountTokenRepository.findById(_code).orElse(null);
        	
        	if (token == null)
        		return false;
        	
        	Date currentDate = new Date();
        	// Validate if token is not expired
        	if (token.getExpiresAt().compareTo(currentDate) >= 0)
        		return false;
        	
        	// TODO: update user status to "active"
        	token.setVerifiedAt(currentDate);
        	accountTokenRepository.save(token);
        	
			return true;
		} catch (Exception e) {
			return false;
		}
    }
    
	@Override
	@Transactional(rollbackOn = Exception.class)
	public String generateVerifyAccountToken(User user) {
		try {
			String code = randomCode.generateConfirmationCode();
			Date today = new Date();
			// TODO: Verify expiration date. 24 hours??
			Date tomorrowDate = new Date(today.getTime() + (1000 * 60 * 60 * 24));
			
			VerifyAccountToken token = new VerifyAccountToken(code, user, tomorrowDate);
			accountTokenRepository.save(token);
			
			return code;
		} catch (Exception e) {
			return null;
		}
	}

    @Override
    public User signIn(String email, String password) {
        User user = userRepository.findByEmail(email);
        
        if (user != null 
        		&& passwordEncoder.matches(password, user.getPassword()) 
        		&& user.getState().getName() == ACTIVE) {
            return user;
        }
        return null;
    }

    @Override
    public String hashPassword(String password) {return passwordEncoder.encode(password);}
    
}
