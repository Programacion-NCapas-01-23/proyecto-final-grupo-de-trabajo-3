package com.swifticket.web.services.implementations;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.swifticket.web.models.dtos.auth.GoogleUserDTO;
import com.swifticket.web.models.dtos.auth.IdTokenRequestDTO;
import com.swifticket.web.models.entities.*;
import com.swifticket.web.repositories.UserRepository;
import com.swifticket.web.repositories.VerifyAccountTokenRepository;
import com.swifticket.web.services.*;
import com.swifticket.web.utils.RandomCode;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;

@Service
public class AuthServicesImpl implements AuthServices {
    private final EmailServices emailService;
    private final UserStateServices userStateServices;
    private final UserRepository userRepository;
    private final UserServices userServices;
    private final VerifyAccountTokenRepository accountTokenRepository;
    private final RandomCode randomCode;
    private final AvatarServices avatarServices;
    private final RoleServices roleServices;
    public final PasswordEncoder passwordEncoder;
    private final GoogleIdTokenVerifier verifier;

    private final String ACTIVE = "Activo";
    private final String BLOCKED = "Bloqueado";
    private final String UNVERIFIED = "No-verificado";
    private final int USER_ROLE = 2;
    private static final String CLIENT_ID = "893111957431-h36mol3osmc1ajq441slto5mrha4vv9i.apps.googleusercontent.com";
    
    @Autowired
    public AuthServicesImpl(UserRepository userRepository, EmailServices emailService, UserStateServices userStateServices, UserServices userServices, VerifyAccountTokenRepository accountTokenRepository, RandomCode randomCode, AvatarServices avatarServices, RoleServices roleServices, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.userStateServices = userStateServices;
        this.userServices = userServices;
		this.accountTokenRepository = accountTokenRepository;
        this.randomCode = randomCode;
        this.avatarServices = avatarServices;
        this.roleServices = roleServices;
        this.passwordEncoder = passwordEncoder;

        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        this.verifier =  new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();
    }

    public User loginOAuthGoogle(IdTokenRequestDTO requestBody) {
        GoogleUserDTO account = verifyIDToken(requestBody.getIdToken());
        if (account == null) {
            // throw new IllegalArgumentException("Invalid token ");
            return null;
        }
        return createOrUpdateUser(account);
    }

    @Transactional
    public User createOrUpdateUser(GoogleUserDTO account) {
        User existingAccount = userRepository.findOneByEmail(account.getEmail());
        if (existingAccount == null) {
            try {
                return googleRegister(account);
            } catch (Exception e) {
                // System.out.println("ERROR createOrUpdateUser" + e.getMessage());
                return null;
            }
        }
        // Update user's data if necessary
        // existingAccount.setName(account.getName());
        // userRepository.save(existingAccount);
        return existingAccount;
    }

    @Transactional(rollbackOn = Exception.class)
    public User googleRegister(GoogleUserDTO data) throws Exception {
        Avatar avatar = avatarServices.findById(1);
        // TODO: after tests update this ID to 3
        UserState state = userStateServices.findById(1);
        String password = randomCode.generateConfirmationCode();

        User newUser = new User(state, avatar, data.getName(), data.getEmail(), passwordEncoder.encode(password));
        User user = userRepository.save(newUser);

        // Add user role by default
        Role role = roleServices.findById(USER_ROLE);
        userServices.assignRole(user, role);
        
        return user;
    }

    private GoogleUserDTO verifyIDToken(String idToken) {
        try {
            GoogleIdToken idTokenObj = verifier.verify(idToken);
            if (idTokenObj == null) {
                return null;
            }

            GoogleIdToken.Payload payload = idTokenObj.getPayload();
            String name = (String) payload.get("given_name");
            String email = payload.getEmail();

            /*
            System.out.println("DEBUG");
            System.out.println(name);
            System.out.println(email);
             */

            return new GoogleUserDTO(email, name);

        } catch (GeneralSecurityException | IOException e) {
            // System.out.println("ERROR verifyIDToken" + e.getMessage());
            return null;
        }
    }
    @Override
    public Boolean isTokenValid(String id) { return true; }

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
