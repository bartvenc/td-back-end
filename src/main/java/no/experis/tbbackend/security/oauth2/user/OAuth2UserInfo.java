package no.experis.tbbackend.security.oauth2.user;

import java.util.Map;

/**
 * The type Oauth2 user info.
 * Used to get the Oauth2 user specific information,
 * extended by other login supported classes.
 */
public abstract class OAuth2UserInfo {
    /**
     * The Attributes.
     */
    protected Map<String, Object> attributes;

    /**
     * Instantiates a new Oauth 2 user info.
     *
     * @param attributes the attributes
     */
    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    /**
     * Gets attributes.
     *
     * @return the attributes
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public abstract String getId();

    /**
     * Gets name.
     *
     * @return the name
     */
    public abstract String getName();

    /**
     * Gets email.
     *
     * @return the email
     */
    public abstract String getEmail();

    /**
     * Gets image url.
     *
     * @return the image url
     */
    public abstract String getImageUrl();
}
