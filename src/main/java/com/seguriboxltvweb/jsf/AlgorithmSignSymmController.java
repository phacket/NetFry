package com.seguriboxltvweb.jsf;

import com.seguriboxltvweb.domain.AlgorithmSignSymm;
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
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import static javax.ws.rs.client.Entity.entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *Clase que contiene toda la funcionalidad para algoritmos de firma.
 * @author inggerman
 * @version 1.0
 */
@Named("algorithmSignSymmController")
@SessionScoped
public class AlgorithmSignSymmController implements Serializable {

    //private AlgorithmSignSymm algorithm = new AlgorithmSignSymm();
     /**
     * @see <a href="https://jersey.java.net/apidocs/2.22/jersey/javax/ws/rs/client/WebTarget.html"/> Jersey-WebTarget
     */
    private final WebTarget webTarget;
    /**
     * @see <a href="https://jersey.java.net/documentation/latest/client.html"/> Jersey-Client
     */
    private final Client client;
    //private boolean isEdit;
    /**
     * Variable que almacena la url base, se inicializa en el constructor.
     */
    private final String BASE_URI;
    /**
     * Variable que almacena la lista de algoritmos de firma.
     */
    private List<AlgorithmSignSymm> items = null;
    /**
     * Variable que almacena un objeto del tipo  AlgorithmSignSymm-
     */
    private AlgorithmSignSymm selected;
     /**
     * Variable que almacena el valor del sessionId, se inicializa en el constructor.
     */
    private final String sessionId;
    private final String source;

    /**
     * Constructor de la clase AlgorithmSignSymmController
     * @throws IOException 
     */
    public AlgorithmSignSymmController() throws IOException {
        sessionId = SessionUtils.getSessionId();
        client = ClientBuilder.newClient();
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
        webTarget = client.target(BASE_URI).path("algorithmsignsymm");
        source = "algorithmsignsymm";
    }

    /**
     *
     * @return valor de sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     *
     * @return valor de selected
     */
    public AlgorithmSignSymm getSelected() {
        return selected;
    }

    /**
     *
     * @param selected 
     */
    public void setSelected(AlgorithmSignSymm selected) {
        this.selected = selected;
    }

   
//    public void setIsEdit(boolean isEdit) {
//        this.isEdit = isEdit;
//    }

    /**
     *
     * @return Outcome que redirige a la sección de algoritmos de firma.
     */
    public String goToSign() {

        try {
            items=findAll();
            return "adminalgorithms?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }
    }

    /**
     *  Asigna la lista de algoritmos de firma a la variable items.
     * @return lista de todos los algoritmos de firma. 
     */
    public List<AlgorithmSignSymm> getItems() {
        if (items == null) {
            items = findAll();
        }
        return items;
    }

    /**
     *Edita el algoritmo de firma seleccionado.
     * @param selected Algoritmo a editar
     * @return String que redirige a la página adminalgorithms.jsf.
     * @throws Exception
     */
    public String edit(AlgorithmSignSymm selected) throws Exception {
        String str = null;
        try {

            String res = JsonUtil.JsonFromAlgorithSymm(selected);

            Response response = webTarget.path(MessageFormat.format("edit/{0}",
                    new Object[]{getSessionId()})).request(MediaType.APPLICATION_JSON + ";charset=utf-8")
                    .put(entity(res, MediaType.APPLICATION_JSON + ";charset=utf-8"), Response.class);
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                throw new Exception(response.readEntity(String.class));
            } else {
                setSelected(new AlgorithmSignSymm());
                items = findAll();
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/algorithmSignSymm").getString("AlgorithmSignSymmUpdated"));
                str = "adminalgorithms?faces-redirect=true";
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            str = null;
        }

        return str;
    }

    /**
     *Convierte la lista en formato JSON de algoritmos de firma en una lista del tipo AlgorithmSignSymm.
     * @param jsonListString JSON con la lista de algoritmos de firma.
     * @return lista de todos los algoritmos de firma. 
     */
    private List<AlgorithmSignSymm> convertToList(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<AlgorithmSignSymm> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                AlgorithmSignSymm item = JsonUtil.AlgorithmSignSymmFromJson(jsonList.get(i).toString());
                list.add(item);
            }
            return list;
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

    /**
     *Método que devuelve una lista de todos los  algoritmos de firma-
     * Este método consume un web service y procesa los datos obtenidos.
     * @return
     */
    public List<AlgorithmSignSymm> findAll() {
        try {

            String path = MessageFormat.format(source + "/{0}",
                    new Object[]{getSessionId()});
            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return convertToList(jsonListString);

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return new ArrayList();
        }
    }

}
