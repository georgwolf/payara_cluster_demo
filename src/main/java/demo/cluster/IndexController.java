package demo.cluster;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;

import jakarta.enterprise.inject.Model;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpSession;

@Model
public class IndexController{

    @Inject
    private FacesContext ctx;

    public String getHostName(){
        return System.getProperty("com.sun.aas.hostName");
    }

    public String getHostIp(){
        try{
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch(final UnknownHostException e){
            return null;
        }
    }

    public String getSessionCreated(){
        return Instant.ofEpochMilli(((HttpSession) this.ctx.getExternalContext().getSession(false)).getCreationTime())
                .toString();
    }
}
