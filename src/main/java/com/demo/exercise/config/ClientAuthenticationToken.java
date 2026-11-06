package com.demo.exercise.config;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Transient;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;

import java.util.Map;

@Transient
public class ClientAuthenticationToken extends OAuth2ClientAuthenticationToken {

    public ClientAuthenticationToken(String clientId, ClientAuthenticationMethod clientAuthenticationMethod,
                                     @Nullable Object credentials, @Nullable Map<String, Object> additionalParameters) {
        super(clientId, clientAuthenticationMethod, credentials, additionalParameters);
    }
}