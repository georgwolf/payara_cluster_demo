package demo.cluster;

import java.util.Arrays;
import java.util.HashSet;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

@ApplicationScoped
public class AppIdentityStore implements IdentityStore{
    public CredentialValidationResult validate(final UsernamePasswordCredential credential){
        if(credential.compareTo("test", "password")){
            System.out.println("Valid login");
            return new CredentialValidationResult("test", new HashSet<>(Arrays.asList("access", "admin")));
        }
        return CredentialValidationResult.INVALID_RESULT;
    }
}
