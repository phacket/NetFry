package com.seguriboxltvweb.jsf;

//import com.seguriboxltvweb.jsf.dao.LoginDao;
import com.seguriboxltvweb.domain.UserSession;
import com.seguriboxltvweb.domain.UserState;
import com.seguriboxltvweb.jsf.util.JsfUtil;
import com.seguriboxltvweb.jsf.util.JsonUtil;
import com.seguriboxltvweb.jsf.util.SeguriboxGetProperties;
import com.seguriboxltvweb.jsf.util.SessionUtils;
import com.seguriboxltvweb.jsf.util.wsUtil;
import java.io.FileInputStream;
import javax.inject.Named;
import java.io.Serializable;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.ldap.LdapName;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

/**
 * Clase que contiene toda la funcionalidad para loguearse en el sistema.
 *
 * @author MiguelADM
 */
@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    static private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private String username;
    private String password;
    private String sessionid;
    private boolean validatePKIX = false;
    private String cacertsFile = "/home/ricky/glassfish3/glassfish/domains/domain1/config/cacerts.jks";
    private String cacertsPassword = "changeit";
    private KeyStore cacerts = null;
    private String userlogged;
    private final WebTarget webTarget;
    private final Client client;
    private String fullName;
    private final String BASE_URI;
    UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();

    /**
     * Constructor de la clase LoginController.
     *
     * @throws java.io.IOException
     */
    public LoginController() throws IOException {
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
        validatePKIX = false;
        cacertsFile = "/home/ricky/glassfish3/glassfish/domains/domain1/config/cacerts.jks";
        cacertsPassword = "changeit";
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI);
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     *
     * @param sessionid
     */
    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getUsername() {

        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Método que se utiliza para saber si un usuario esta logueado en el
     * sistema.
     *
     * @return true si esta logueado false si no lo esta
     */
    public boolean isLoggedIn() {
        return this.sessionid != null;
    }

    /**
     * Método que carga los certificados disponibles.
     */
    private synchronized void loadCaCerts() {
        if (validatePKIX && cacertsFile != null && cacertsPassword != null) {
            try {
                Security.setProperty("ocsp.enable", "true");
                System.setProperty("com.sun.security.enableCRLDP", "true");
                cacerts = KeyStore.getInstance(KeyStore.getDefaultType());
                cacerts.load(new FileInputStream(cacertsFile), cacertsPassword.toCharArray());
            } catch (Exception e) {
                logger.error("Error loading the certificates", e);
            }
        }
    }

    /**
     * Método que loguea al usuario en el sistema.
     *
     * @return @throws Exception
     */
    public String login() throws Exception {
        logger.debug("entering normal login");
        String nextpage;
        try {
            boolean result = this.ValidateUser(username, password);
            if (result == true) {
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("username", username);
                session.setAttribute("sessionid", sessionid);
                session.setAttribute("fullname", fullName);
                session.setMaxInactiveInterval(12000);
                nextpage = "index.jsf";

            } else {
                nextpage = "login.jsf";
                // nextpage = "login_2.jsf";

            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Usuario o contraseña incorrectos",
                            "Por favor ingrese correctamente la información de acceso"));
            nextpage = "login.jsf";
            //nextpage = "login_2.jsf";

        }
        return nextpage;
    }

    /**
     * Método que lee el certificado y obtiene sus datos.
     *
     * @param cert
     */
    public void trustedLogin(X509Certificate cert) {
        try {
            logger.debug("entering trustedLogin");
            // only check if user not logged in
            if (!isLoggedIn()) {
                // get CN part => it's the last one
                LdapName name = new LdapName(cert.getSubjectDN().getName());
                String username = name.getRdn(name.size() - 1).getValue().toString();
                String password = cert.getSerialNumber().toString();

                logger.debug("username: {}", username);
                logger.debug("password: {}", password);
                // validate via PKIX if configured

                boolean certOK = !validatePKIX || validatePKIX(cert);
                if (certOK) {
                    if (isLoggedIn() == false) {
                        // login without password
                        boolean result = this.ValidateUser(username, password);
                        logger.debug("Found: {}", username);

                        if (result == true) {
                            HttpSession session = SessionUtils.getSession();
                            session.setAttribute("username", username);
                            session.setAttribute("sessionid", sessionid);
                            session.setAttribute("fullname", fullName);
                            session.setMaxInactiveInterval(12000);
                        }
                    }

                }
            }
        } catch (Exception e) {
            logger.error("Error performing the login", e);
        }
    }

    /**
     * Método que valida que el certificado sea valido
     *
     * @param cert
     * @return
     */
    public boolean validatePKIX(X509Certificate cert) {
        try {
            if (cacerts == null) {
                loadCaCerts();
            }
            logger.debug("entering validatePKIX");
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            List<X509Certificate> certs = new ArrayList<X509Certificate>();
            certs.add(cert);
            CertPath certPath = cf.generateCertPath(certs);
            CertPathValidator cpv = CertPathValidator.getInstance("PKIX");
            PKIXParameters params = new PKIXParameters(cacerts);
            PKIXCertPathValidatorResult cpv_result
                    = (PKIXCertPathValidatorResult) cpv.validate(certPath, params);
        } catch (Exception e) {
            logger.error("Exception doing validation => false", e);
            return false;
        }
        return true;
    }

//    /**
//     *Método que 
//     * @param groupname
//     * @return
//     */
//    public boolean isUserInGroup(String groupname) {
//        // check cache
//        return true;
//    }
    /**
     * Método que desloguea al usuario del sistema.y termina la sesión
     *
     * @return @throws Exception
     */
    public String logout() throws Exception {

        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {

            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

            if (session != null) {
                String path = MessageFormat.format("/usersecurity/signout/{0}", session.getAttribute("sessionid"));
                String jsonResult = wsUtil.ExecuteGetRestService(true, BASE_URI, path);
                session.invalidate();
                this.setSessionid(null);
                this.setUsername(null);
                this.setFullName(null);

                FacesContext.getCurrentInstance().getExternalContext().redirect(req.getContextPath() + "/logout.jsf?faces-redirect=true");
            }
        } catch (Exception e) {
            throw new Exception(e.toString());
        }

        return req.getContextPath() + "/logout.jsf?faces-redirect=true";
    }

    /**
     * Método que desloguea al usuario.
     *
     * @return @throws Exception
     */
    public String logoutIndex() throws Exception {
        String message = MessageFormat.format("/usersecurity/signout/{0}", new Object[]{sessionid});
        WebTarget resource = webTarget.path(message);

        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

            if (session != null) {
                session.invalidate();

                Response response = resource.request(MediaType.APPLICATION_JSON + ";charset=utf-8").get(Response.class);
                String loginpage = req.getContextPath() + "/logout.jsf?faces-redirect=true";
                //FacesContext.getCurrentInstance().getExternalContext().redirect("../SeguriboxAdmin/login.jsf?faces-redirect=true");
                FacesContext.getCurrentInstance().getExternalContext().redirect(loginpage);
                if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                    throw new Exception("No se puedo ejecutar el metodo de logout");
                }

            }
        } catch (Exception e) {
            throw new Exception(e.toString() + "  " + resource.toString());
        }

        //  return "../SeguriboxAdmin/login.jsf?faces-redirect=true";
        // return "../SeguriboxAdmin/login_2.jsf?faces-redirect=true";
        return req.getContextPath() + "/logout.jsf?faces-redirect=true";

//        return "login";
    }

    /**
     * Obtiene la IP del equipo que accesó a la aplicación
     *
     * @return
     */
    public String GetIp() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    /**
     * Método que valida que valida si el usuario esta en la base de datos y que
     * su contraseña es correcta.
     *
     * @param username
     * @param password
     * @return true si el usuario es valido y false si el usuario es invalido
     * @throws Exception
     */
    public boolean ValidateUser(String username, String password) throws Exception {
        String message = MessageFormat.format("/usersecurity/{0}/{1}/{2}", new Object[]{username, password, GetIp()});
        WebTarget resource = webTarget.path(message);
        try {

            Response response = resource.request(MediaType.APPLICATION_JSON + ";charset=utf-8").get(Response.class);
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                throw new Exception(response.readEntity(String.class));
            } else {
                String result = response.readEntity(String.class);//resource.request(MediaType.APPLICATION_JSON + ";charset=utf-8").get(String.class);
                UserSession user;
                user = JsonUtil.UserSessionFromJson(result);

                message = MessageFormat.format("/usersecurity/{0}/{1}", new Object[]{"userstate", user.getSessionId()});
                resource = webTarget.path(message);
                result = resource.request(MediaType.APPLICATION_JSON + ";charset=utf-8").get(String.class);

                UserState ustate = JsonUtil.UserStateFromJson(result);
                if (ustate.getUserType() == 2) {

                    throw new Exception("Los usuarios no pueden usar el portal de administradores");
                }

                user.setFullName(ustate.toString());
                this.setSessionid(user.getSessionId());
                this.setUsername(ustate.getUserName());
                this.setFullName(ustate.toString());
            }

            return true;
        } catch (Exception e) {
            logger.error("Exception doing validation => false", e);
            JsfUtil.addErrorMessage(e.getMessage());
            return false;
        }

    }
}
