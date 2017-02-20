package com.seguriboxltvweb.jsf.util;
import java.io.IOException;
import java.text.MessageFormat;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *Clase que contiene la funcionalidad para obtener distintos parametros en base al sessionId del usuario logeado
 * @author MiguelADM
 */
public class SessionUtils {

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }
      /**
     * método que obtiene el userName en base al sessionId.
     * @return  cadena con el userName.
     */
    public static String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        if (session != null) {
            return session.getAttribute("username").toString();
        } else {
            return null;
        }

    }
    /**
     * Método que obtiene el userId en base al sessiónId
     * @return user id 
     */
    public static String getUserId() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        if (session != null) {
            return (String) session.getAttribute("userid");
        } else {
            return null;
        }
    }
 /**
     * Método que obtiene la sesión del usuario logeado.
     * @return  cadena de la sesión del usuario
     */
    public static String getSessionId() throws IOException {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (session != null) {
            try {

                String BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
                Client client = ClientBuilder.newClient();
                WebTarget webTarget = client.target(BASE_URI).path("/usersecurity");

                String message = MessageFormat.format("/{0}/{1}", new Object[]{"userstate", (String) session.getAttribute("sessionid")});
                WebTarget resource = webTarget.path(message);
                Response response = resource.request(MediaType.APPLICATION_JSON + ";charset=utf-8").get(Response.class);
                if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                    throw new Exception(response.readEntity(String.class));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Te encuentras conectado desde otro equipo o tienes uns sesión activa.",
                                e.toString()));
                if (session != null) {
                    session.invalidate();
                    FacesContext.getCurrentInstance().getExternalContext().redirect(req.getContextPath() + "/login.jsf");

                }
            }
            return (String) session.getAttribute("sessionid");
        } else {
            return null;
        }
    }
      /**
     * Método que obtiene el fullName de usuario logeado.
     * @return  nombre completo del usuario
     */
    public static String getFullName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        if (session != null) {
            return (String) session.getAttribute("fullname");
        } else {
            return null;
        }
    }
}