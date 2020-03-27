package no.experis.tbbackend.security.oauth2.user;

import java.util.Map;

/**
 * The type Google Oauth2 user info, extends Oauth2 class.
 * Used to retrieve google specific user information.
 */
public class GoogleOAuth2UserInfo extends no.experis.tbbackend.security.oauth2.user.OAuth2UserInfo {

    /**
     * Instantiates a new Google Oauth2 user info.
     *
     * @param attributes the attributes
     */
    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }
}
