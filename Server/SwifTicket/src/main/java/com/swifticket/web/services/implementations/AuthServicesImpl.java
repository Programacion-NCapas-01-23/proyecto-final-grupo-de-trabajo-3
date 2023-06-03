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
import com.swifticket.web.services.UserStateServices;
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
    private final UserStateServices userStateServices;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final VerifyAccountTokenRepository accountTokenRepository;
    private final RandomCode randomCode;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    private final String ACTIVE = "Activo";
    private final String BLOCKED = "Bloqueado";
    private final String UNVERIFIED = "No-verificado";
    
    @Autowired
    public AuthServicesImpl(UserRepository userRepository, EmailServices emailService, UserStateServices userStateServices, TokenRepository tokenRepository, VerifyAccountTokenRepository accountTokenRepository, RandomCode randomCode) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.userStateServices = userStateServices;
        this.tokenRepository = tokenRepository;
		this.accountTokenRepository = accountTokenRepository;
        this.randomCode = randomCode;
    }

    @Override
    public Boolean isTokenValid(String id) {
        // TODO: When using spring security this method will be DEPRECATED
        /*
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
         */
        return true;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public User validateAccount(String code) throws Exception {
        VerifyAccountToken token = accountTokenRepository.findById(code).orElse(null);

        if (token == null) return null;
        if (token.getVerifiedAt() != null) return null;

        Date currentDate = new Date();
        // Validate if token is not expired
        if (token.getExpiresAt().compareTo(currentDate) < 0)
            return null;

        // if token is valid sign token with the verification date
        token.setVerifiedAt(currentDate);
        accountTokenRepository.save(token);

        return token.getUser();
    }
    
	@Override
	@Transactional(rollbackOn = Exception.class)
	public String generateVerifyAccountToken(String email) {
		try {
            User user = userRepository.findOneByEmail(email);
            if (user == null) return null;

			String code = randomCode.generateConfirmationCode();
			Date today = new Date();
			// TODO: For tests only is set to 1 hour
			Date tomorrowDate = new Date(today.getTime() + (1000 * 60 * 60 * 1));
			
			VerifyAccountToken token = new VerifyAccountToken(code, user, tomorrowDate);
			accountTokenRepository.save(token);
			
			return code;
		} catch (Exception e) {
			return null;
		}
	}

    @Override
    public User signIn(String email, String password) {
        User user = userRepository.findOneByEmail(email);
        
        if (user != null 
        		&& passwordEncoder.matches(password, user.getPassword()) 
        		&& user.getState().getName().equals(ACTIVE)) {
            return user;
        }
        return null;
    }

    @Override
    public String hashPassword(String password) {return passwordEncoder.encode(password);}
    
}
