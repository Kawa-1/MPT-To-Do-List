package pl.kt.agh.edu.common.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

public class SecurityContextConverter {
    public final static String ID_CLAIM = "uid";
    public final static String ROLE_CLAIM = "role";

    @SuppressWarnings("rawtypes")
    public <T> T resolveSecurityContextClaim(Class<T> expectedClass, String claim) {
        UsernamePasswordAuthenticationToken upat = unwrapSecurityAuthentication(UsernamePasswordAuthenticationToken.class);
        if (upat == null) {
            return null;
        }
        Object details = upat.getDetails();
        if (!(details instanceof Map detailsMap)) {
            return null;
        }
        Object expcetedObject = detailsMap.get(claim);
        if (expectedClass.isInstance(expcetedObject)) {
            return expectedClass.cast(expcetedObject);
        }
        return null;
    }

    public <T extends Authentication> T unwrapSecurityAuthentication(Class<T> expectedClass) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (expectedClass.isInstance(authentication)) {
            return expectedClass.cast(authentication);
        }
        return null;
    }

    public String unwrapSecurityToken(){
        UsernamePasswordAuthenticationToken uwap = unwrapSecurityAuthentication(UsernamePasswordAuthenticationToken.class);
        return (String) uwap.getCredentials();
    }

}
