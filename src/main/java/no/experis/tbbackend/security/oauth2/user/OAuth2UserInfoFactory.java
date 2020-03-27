package no.experis.tbbackend.security.oauth2.user;


import no.experis.tbbackend.exception.OAuth2AuthenticationProcessingException;
import no.experis.tbbackend.model.AuthProvider;

import java.util.Map;

/**
 * The type Oauth2 user info factory.
 * Provides Google authentication.
 */
public class OAuth2UserInfoFactory {

    /**
     * Gets Oauth 2 user info.
     *
     * @param registrationId the registration id
     * @param attributes     the attributes
     * @return the Oauth2 user info
     */
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
