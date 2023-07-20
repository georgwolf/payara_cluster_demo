package demo.cluster;

import jakarta.enterprise.inject.Model;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Model
public class LoginController{

    @Inject
    private SecurityContext securityContext;

    @Inject
    private FacesContext    ctx;

    private String          username;

    private String          password;

    public String getUsername(){
        return this.username;
    }

    public void setUsername(final String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(final String password){
        this.password = password;
    }

    public String login(){
        final Credential credential = new UsernamePasswordCredential(this.username, new Password(this.password));
        final AuthenticationStatus status = this.securityContext.authenticate(
                (HttpServletRequest) this.ctx.getExternalContext().getRequest(),
                (HttpServletResponse) this.ctx.getExternalContext().getResponse(),
                AuthenticationParameters.withParams().credential(credential));

        switch(status) {
            case SEND_CONTINUE:
                this.ctx.responseComplete();
                return null;
            case SUCCESS:
                return "/index?faces-redirect=true";
            case SEND_FAILURE:
            case NOT_DONE:
            default:
                this.ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Authentication failed", ""));
                return null;
        }
    }
}
