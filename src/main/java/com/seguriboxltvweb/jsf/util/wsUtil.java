package com.seguriboxltvweb.jsf.util;

import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import static javax.ws.rs.client.Entity.entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.LoggerFactory;

/**
 * Mpetodo que contiene la utileria para consumir web services rest mediante los
 * metodos GET,PUT,PORT y DELETE.
 *
 * @author MiguelADM
 */
public class wsUtil implements Serializable {

    static private final org.slf4j.Logger logger = LoggerFactory.getLogger(wsUtil.class);

    /**
     * Ejecutar staticamente un servicio REST con el método GET
     *
     * @param trace true si se requiere que se trace el método false si no se
     * requiere
     * @param BASE_URI url base con el que se consumen los web services.
     * @param path ruta que se una a la url base para consumir el web service
     * @return Cadena que regresa el JSON que regresa como respuesta el web
     * service
     * @throws Exception
     */
    public static String ExecuteGetRestService(boolean trace, String BASE_URI, String path) throws Exception {
        String resultado = "";
        try {
            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target(BASE_URI).path(path);
            //Se ejecuta un metodo Get
            Response response = webTarget.request(MediaType.APPLICATION_JSON + ";charset=utf-8").get(Response.class);
            resultado = response.readEntity(String.class); // Se cierrar la comunicacion con el cleinte y se puede reliar una nueva
            response.close();

            logger.info("[GET] Resultado al ejecutar el servicio web ==>" + resultado);
            logger.info("[GET] Respuesta del servicio web ==>" + response.getStatus());
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                throw new Exception(resultado);
            }
        } catch (Exception e) {
            logger.error("[GET] Error al ejecutar el servicio web ==>" + BASE_URI + "/" + path);
            logger.error("[GET] Error al ejecutar el servicio web ==> Error:= " + e.toString());
            logger.error("[GET] Resultado al ejecutar el servicio web ==>" + resultado);
            if (e.getMessage().contains("La session es inválida")) {
                HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                FacesContext.getCurrentInstance().getExternalContext().redirect(req.getContextPath() + "/logout.jsf?faces-redirect=true");
            } else {
                throw new Exception(e.toString().replace("java.lang.Exception:", ""));
            }
        }
        return resultado;
    }

    /**
     * Ejecutar staticamente un servicio REST con el metodo DELETE
     *
     * @param trace true si se requiere que se trace el método false si no se
     * requiere
     * @param BASE_URI url base con el que se consumen los web services.
     * @param path ruta que se una a la url base para consumir el web service
     * @return Cadena que regresa el JSON que regresa como respuesta el web
     * service
     * @throws Exception
     */
    public static String ExecuteDeleteRestService(boolean trace, String BASE_URI, String path) throws Exception {
        String resultado = "";
        try {
            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target(BASE_URI).path(path);
            //Se ejecuta un metodo Delete
            Response response = webTarget.request(MediaType.APPLICATION_JSON + ";charset=utf-8").delete();
            resultado = response.readEntity(String.class); // Se cierrar la comunicacion con el cleinte y se puede reliar una nueva
            response.close();
            logger.info("[DELETE] Resultado al ejecutar el servicio web ==>" + resultado);
            logger.info("[DELETE] Respuesta del servicio web ==>" + response.getStatus() + " " + resultado);
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                throw new Exception(resultado);
            }
        } catch (Exception e) {
            logger.error("[DELETE] Error al ejecutar el servicio web ==>" + BASE_URI + "/" + path);
            logger.error("[DELETE] Error al ejecutar el servicio web ==> Error:= " + e.toString());
            logger.error("[DELETE] Resultad al ejecutar el servicio web ==>" + resultado);
            if (e.getMessage().contains("La session es inválida")) {
                HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                FacesContext.getCurrentInstance().getExternalContext().redirect(req.getContextPath() + "/logout.jsf?faces-redirect=true");
            } else {
                throw new Exception(e.toString().replace("java.lang.Exception:", ""));
            }
        }
        return resultado;
    }

    /**
     * Ejecutar staticamente un servicio REST con el metodo PUT
     *
     * @param trace true si se requiere que se trace el método false si no se
     * requiere
     * @param BASE_URI url base con el que se consumen los web services.
     * @param path ruta que se una a la url base para consumir el web service
     * @return Cadena que regresa el JSON que regresa como respuesta el web
     * service
     * @throws Exception
     */
    public static String ExecutePutRestService(boolean trace, String BASE_URI, String path, String entityJson) throws Exception {
        String resultado = "";
        try {
            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target(BASE_URI).path(path);
            //Se ejecuta un metodo PUT
            Response response = webTarget.request(MediaType.APPLICATION_JSON + ";charset=utf-8").put(entity(entityJson, MediaType.APPLICATION_JSON + ";charset=utf-8"), Response.class);
            resultado = response.readEntity(String.class); // Se cierrar la comunicacion con el client y se puede reliar una nueva
            response.close();
            logger.info("[PUT] Entidad de envio ==>" + entityJson);
            logger.info("[PUT] Resultado al ejecutar el servicio web ==>" + resultado);
            logger.info("[PUT] Respuesta del servicio web ==>" + response.getStatus());
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                throw new Exception(resultado);
            }
        } catch (Exception e) {
            logger.error("[PUT] Error al ejecutar el servicio web ==>" + BASE_URI + "/" + path);
            logger.error("[PUT] Error al ejecutar el servicio web entity ==>" + entityJson);
            logger.error("[PUT] Error al ejecutar el servicio web ==> Error:= " + e.toString());
            logger.error("[PUT] Resultad al ejecutar el servicio web ==>" + resultado);
            if (e.getMessage().contains("La session es inválida")) {
                HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                FacesContext.getCurrentInstance().getExternalContext().redirect(req.getContextPath() + "/logout.jsf?faces-redirect=true");
            } else {
                throw new Exception(e.toString().replace("java.lang.Exception:", ""));
            }
        }
        return resultado;
    }

    /**
     * Ejecutar staticamente un servicio REST con el metodo POST
     *
     * @param trace true si se requiere que se trace el método false si no se
     * requiere
     * @param BASE_URI url base con el que se consumen los web services.
     * @param path ruta que se una a la url base para consumir el web service
     * @return Cadena que regresa el JSON que regresa como respuesta el web
     * service
     * @throws Exception
     */
    public static String ExecutePostRestService(boolean trace, String BASE_URI, String path, String entityJson) throws Exception {
        String resultado = "";
        try {
            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target(BASE_URI).path(path);
            //Se ejecuta un metodo Delete
            Response response = webTarget.request(MediaType.APPLICATION_JSON + ";charset=utf-8").post(entity(entityJson, MediaType.APPLICATION_JSON + ";charset=utf-8"), Response.class);
            resultado = response.readEntity(String.class); // Se cierrar la comunicacion con el cliente y se puede reliar una nueva
            response.close();
            logger.info("[POST] Entidad de envio ==>" + entityJson);
            logger.info("[POST] Resultad al ejecutar el servicio web ==>" + resultado);
            logger.info("[POST] Respuesta del servicio web ==>" + response.getStatus());
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                throw new Exception(resultado);
            }
        } catch (Exception e) {
            logger.error("[POST] Error al ejecutar el servicio web ==>" + BASE_URI + "/" + path);
            logger.error("[POST] Error al ejecutar el servicio web entity ==>" + entityJson);
            logger.error("[POST] Error al ejecutar el servicio web ==> Error:= " + e.toString());
            logger.error("[POST] Resultad al ejecutar el servicio web ==>" + resultado);
            if (e.getMessage().contains("La session es inválida")) {
                HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                FacesContext.getCurrentInstance().getExternalContext().redirect(req.getContextPath() + "/logout.jsf?faces-redirect=true");
            } else {
                throw new Exception(e.toString().replace("java.lang.Exception:", ""));
            }
        }
        return resultado;
    }
}
