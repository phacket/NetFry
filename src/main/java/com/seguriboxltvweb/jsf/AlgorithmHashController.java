package com.seguriboxltvweb.jsf;

import com.seguriboxltvweb.domain.AlgorithmHash;
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
 * Clase que contiene toda la funcionalidad de algoritmo Hash.
 *
 * @author Germán Hernandez López
 * @version 1.0
 */
@Named("algorithmHashController")
@SessionScoped
public class AlgorithmHashController implements Serializable {

    /**
     * Variable que almacena el valor booleano dependiendo si se redirige a la
     * página de edición o creación.
     */
    private boolean isEdit;
    /**
     * @see
     * <a href="https://jersey.java.net/apidocs/2.22/jersey/javax/ws/rs/client/WebTarget.html"/>
     * Jersey-WebTarget
     */
    private final WebTarget webTarget;
    /**
     * @see <a href="https://jersey.java.net/documentation/latest/client.html"/>
     * Jersey-Client
     */
    private final Client client;
    /**
     * Variable que almacena la url base.
     */
    private final String BASE_URI;

    //private AlgorithmHash algorithm = new AlgorithmHash();
    /**
     * Variable que almacena la lista de algoritmos Hash.
     */
    private List<AlgorithmHash> items = null;
    /**
     * Variable que almacena el objeto del tipo AlgorithmHash que se utiliza
     * tanto para editar, eliminar o crear.
     */
    private AlgorithmHash selected;
    /**
     * Variable que almacena el valor del sessionId, se inicializa en el
     * constructor.
     */
    private String sessionId;
    private final String source;

    /**
     * Constructor de la clase AlgorithmHashController.
     *
     * @throws IOException
     */
    public AlgorithmHashController() throws IOException {
        sessionId = SessionUtils.getSessionId();
        client = ClientBuilder.newClient();
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
        webTarget = client.target(BASE_URI).path("algorithmhash");
        source = "algorithmhash";
    }

    public String getSessionId() {
        return sessionId;
    }

    public AlgorithmHash getSelected() {
        return selected;
    }

    public void setSelected(AlgorithmHash selected) {
        this.selected = selected;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    /**
     * Carga la tabla de algoritmos Hash y redirige al modulo de algoritmo Hash .
     *
     * @return outcome que redirige a la página adminhashalgorithms.jsf.
     */
    public String goToHash() {

        try {
            items = findAll();
            return "adminhashalgorithms?faces-redirect=true";
        } catch (Exception e) {

            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }
    }

    /**
     * Asigna a la variable items la lista de algoritmos hash.
     *
     * @return lista de todos los algoritmos hash.
     */
    public List<AlgorithmHash> getItems() {
        if (items == null) {
            items = findAll();
        }
        return items;
    }

    /**
     * Edita la información del algoritmo Hash seleccionado .
     *
     * @param selected algoritmo a editar.
     * @return outcome que redirige a la página adminhashalgorithms
     * @throws Exception
     */
    public String edit(AlgorithmHash selected) throws Exception {
        if (isEdit == false) {
            selected.setAlgorithmHashId(0);

        }
        String res = JsonUtil.JsonFromAlgorithmHash(selected);
        String str = null;
        try {

            Response response = webTarget.path(MessageFormat.format("{0}",
                    new Object[]{getSessionId()})).request().put(entity(res, MediaType.APPLICATION_JSON + ";charset=utf-8"), Response.class);

            if (response.getStatus() != Response.Status.OK.getStatusCode()) {

                throw new Exception(response.readEntity(String.class));

            } else {
                items = findAll();
                setSelected(new AlgorithmHash());
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/algorithmHash").getString("AlgorithmHashUpdated"));
                str = "adminhashalgorithms?faces-redirect=true";

            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            str = null;
        }

        return str;
    }

    /**
     * Convierte el JSON de algoritmos Hash a una lista del tipo AlgorithmHash.
     *
     * @param jsonListString JSON con la lista de algorimos Hash
     * @return lista de algoritmos Hash.
     */
    private List<AlgorithmHash> convertToList(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<AlgorithmHash> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                AlgorithmHash item = JsonUtil.AlgorithmHashFromJson(jsonList.get(i).toString());
                list.add(item);
            }
            return list;
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

    /**
     *Método que devuelve una lista de todos los algoritmos Hash en base de datos.
     * Método que consume un web service y procesa la información.
     * @return lista de algoritmos hash.
     */
    public List<AlgorithmHash> findAll() {
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
