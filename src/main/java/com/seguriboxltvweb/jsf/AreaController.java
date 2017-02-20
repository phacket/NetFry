package com.seguriboxltvweb.jsf;

import com.seguriboxltvweb.domain.Area;
import com.seguriboxltvweb.jsf.util.JsfUtil;
import com.seguriboxltvweb.jsf.util.JsonUtil;
import com.seguriboxltvweb.jsf.util.SeguriboxGetProperties;
import com.seguriboxltvweb.jsf.util.SessionUtils;
import com.seguriboxltvweb.jsf.util.wsUtil;
import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.LoggerFactory;

/**
 * Clase que contiene toda la funcionalidad de áreas.
 *
 * @author Germán Hernández López
 * @version 1.0
 */
@ManagedBean(name = "areaController")
@SessionScoped
public class AreaController implements Serializable {

    static private final org.slf4j.Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * @see
     * <a href="https://jersey.java.net/apidocs/2.22/jersey/javax/ws/rs/client/WebTarget.html"/>
     * Jersey-WebTarget
     */
    private final WebTarget webTarget;
    /**
     * @see <a href="https://jersey.java.net/documentation/latest/client.html"/> Jersey-Client
     */
    private final Client client;
        /**
     * Variable que almacena la url base, se inicializa en el constructor.
     */
    private final String BASE_URI;
    private final String resource;
    private final boolean trace;
    /**
     * Variable que almacena la lista de áreas.
     */
    private List<Area> items = null;
    /**
     * Variable que almacena un objeto de área que se utiliza para crear, eliminar y borrar.
     */
    private Area selected = null;
    private boolean isEdit = false;
    /**
     * Variable que almacena el valor del sessionId, se inicializa en el constructor.
     */
    private final String sessionId;
    //private String txtAction;
    private String txtTitle = "Agregar";
    private String txtButton = "Agregar";

    /**
     * Constructor de la clase AreaController que inicializa principalmente
     * sessionId y la UrlBase de los web services.
     *
     * @throws IOException
     */
    public AreaController() throws IOException {
        sessionId = SessionUtils.getSessionId();
        client = ClientBuilder.newClient();
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
        resource = "area";
        trace = true;
        webTarget = client.target(BASE_URI).path("area");
        items = findAll();

    }

    /**
     *
     * @return valor de la variable sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     *
     * @param txtTitle asigna valor del argumento a la variable.
     */
    public void setTxtTitle(String txtTitle) {
        this.txtTitle = txtTitle;
    }

    /**
     *
     * @return valor de txtButton.
     */
    public String getTxtButton() {
        return txtButton;
    }

    /**
     *
     * @param txtButton asigna a la variable el valor del argumento.
     */
    public void setTxtButton(String txtButton) {
        this.txtButton = txtButton;
    }

    /**
     *
     * @param isEdit asigna a la variable el valor del argumento.
     */
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    /**
     *
     * @return valor de selected.
     */
    public Area getSelected() {
        return selected;
    }

    /**
     *
     * @param selected asigna a la variable el valor del argumento.
     */
    public void setSelected(Area selected) {
        this.selected = selected;
    }

    /**
     *Método que carga la lista de áreas al momento de entrar a la sección de área.
     */
    public void fillControls() {

        items = findAll();

    }
    
    public String goToArea(){
    
        return "adminareas?faces-redirect=true";
    }

    /**
     * Asigna a la variable items el valor de la lista de áreas.
     *
     * @return lista de todas las áreas.
     */
    public List<Area> getItems() {
        if (items == null) {
            items = findAll();
        }
        return items;
    }

    /**
     * Te redirige a la página para editar un área.
     *
     * @param item objeto de tipo Area a editar.
     * @return Cadena que redirige a adminareaedit.jsf.
     */
    public String goToEdit(Area item) {
        setIsEdit(true);
        setTxtTitle("EDITAR");
        setTxtButton("Guardar");
        setSelected(item);
        return "adminareaedit?faces-redirect=true";
    }

    /**
     * Te redirige a la página para crear un área.
     *
     * @return Cadena que redirige a la página adminarea.jsf
     */
    public String goToCreate() {
        setIsEdit(false);
        setTxtTitle("AGREGAR");
        setTxtButton("Agregar");
        setSelected(new Area());
        return "adminarea?faces-redirect=true";
    }

    /**
     * Método que retorna una lista de todas la áreas almacenadas en la base de datos. 
     * Este método consume un web service y procesa los datos obtenidos.
     *
     * @return lisa de todas las áreas.
     */
    public List<Area> findAll() {
        try {
            //URL del web service a ejecutar
            String path = MessageFormat.format("{0}/{1}", resource, getSessionId());
            String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);
            return convertToList(jsonResult);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return new ArrayList();
        }
    }

    /**
     * Convierte el JSON con la lista de áreas a una lista del tipo Area.
     *
     * @param jsonListString JSON con la lista de áreas.
     * @return lista de áreas.
     */
    private List<Area> convertToList(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<Area> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                Area item = JsonUtil.AreaFromJson(jsonList.get(i).toString());
                list.add(item);
            }
            return list;
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

    /**
     * Elimina el área seleccionada.
     *Este método consume un web service por el método delete.
     * @param selected objeto Area seleccionado.
     * @throws Exception
     */
    public void remove(Area selected) throws Exception {
        String idString = selected.getAreaId() + "";
        try {
            String path = MessageFormat.format("{0}/{1}/{2}", resource, getSessionId(), idString);
            String jsonResult = wsUtil.ExecuteDeleteRestService(trace, BASE_URI, path);
            items = findAll();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/AdminArea").getString("AreaDeleted"));

        } catch (Exception e) {
            items = findAll();
            if (e.getMessage().contains("constraint")) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/AdminArea").getString("constrainDelete"));
            } else {
                JsfUtil.addErrorMessage(e.getMessage());
            }

        }
    }

    /**
     * Edita un área seleccionada.
     *Este método consume un web service rest por el método put. 
     * @param selected objeto Area a editar.
     * @return
     */
    public String edit(Area selected) {
        String str = null;
        String messageSave = "";
        if (isEdit == false) {
            messageSave = ResourceBundle.getBundle("/AdminArea").getString("AreaCreated");
        } else {
            messageSave = ResourceBundle.getBundle("/AdminArea").getString("AreaUpdated");
        }
        if (isEdit == false) {
            selected.setAreaId((short) 0);
        }
        String res = JsonUtil.JsonFromArea(selected);
        try {
            String path = MessageFormat.format("{0}/{1}", resource, getSessionId());
            String jsonResult = wsUtil.ExecutePutRestService(trace, BASE_URI, path, res);

            items = findAll();
            setSelected(new Area());
            JsfUtil.addSuccessMessage(messageSave);
            str = "adminareas?faces-redirect=true";
        } catch (Exception e) {
            logger.error("Exception doing validation => false", e);
            items = findAll();
            JsfUtil.addErrorMessage(e.getMessage());
            str = null;

        }
        return str;
    }

}
