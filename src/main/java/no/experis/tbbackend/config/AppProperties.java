package no.experis.tbbackend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * The type App properties.
 */
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();

    /**
     * The type Auth.
     */
    public static class Auth {
        private String tokenSecret;
        private long tokenExpirationMsec;

        /**
         * Gets token secret.
         *
         * @return the token secret
         */
        public String getTokenSecret() {
            return tokenSecret;
        }

        /**
         * Sets token secret.
         *
         * @param tokenSecret the token secret
         */
        public void setTokenSecret(String tokenSecret) {
            this.tokenSecret = tokenSecret;
        }

        /**
         * Gets token expiration msec.
         *
         * @return the token expiration msec
         */
        public long getTokenExpirationMsec() {
            return tokenExpirationMsec;
        }

        /**
         * Sets token expiration msec.
         *
         * @param tokenExpirationMsec the token expiration msec
         */
        public void setTokenExpirationMsec(long tokenExpirationMsec) {
            this.tokenExpirationMsec = tokenExpirationMsec;
        }
    }

    /**
     * The type Oauth2.
     */
    public static final class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();

        /**
         * Gets authorized redirect uris.
         *
         * @return the authorized redirect uris
         */
        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }

        /**
         * Authorized redirect uris Oauth 2.
         *
         * @param authorizedRedirectUris the authorized redirect uris
         * @return the o auth 2
         */
        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }

    /**
     * Gets auth.
     *
     * @return the auth
     */
    public Auth getAuth() {
        return auth;
    }

    /**
     * Gets oauth 2.
     *
     * @return the oauth 2
     */
    public OAuth2 getOauth2() {
        return oauth2;
    }
}
