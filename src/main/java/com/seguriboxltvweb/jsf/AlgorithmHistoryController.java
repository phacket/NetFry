package com.seguriboxltvweb.jsf;

import com.seguriboxltvweb.domain.AlgorithmHistory;
import com.seguriboxltvweb.jsf.util.JsfUtil;
import com.seguriboxltvweb.jsf.util.JsonUtil;
import com.seguriboxltvweb.jsf.util.SeguriboxGetProperties;
import com.seguriboxltvweb.jsf.util.SessionUtils;
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
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Funcionalidad del historial de algoritmos.
 *
 * @author Germán Hernández López
 * @version 1.0
 */
@Named("algorithmHistoryController")
@SessionScoped
public class AlgorithmHistoryController implements Serializable {

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
     * Variable que almacena la url base, se inicializa en el constructor.
     */
    private final String BASE_URI;
    /**
     * Variable que almacena la lista del historial de algoritmos
     */
    private List<AlgorithmHistory> items = null;
    /**
     * Variable que almacena un objeto del tipo AlgorithmHistory que se utiliza
     * al borrar, editar o insertar.
     */
    private AlgorithmHistory selected;
    //private AlgorithmHistory algorithm = new AlgorithmHistory();
    //private boolean isEdit;
    /**
     * Variable que almacena el valor del sessionId, se inicializa en el
     * constructor.
     */
    private String sessionId;
    

    

    /**
     * Constructor de la clase AlgorithmHistoryController
     *
     * @throws IOException
     */
    public AlgorithmHistoryController() throws IOException {
        sessionId = SessionUtils.getSessionId();
        client = ClientBuilder.newClient();
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
        webTarget = client.target(BASE_URI).path("algorithmhistory");
    }

    
    

    public String getSessionId() {
        return sessionId;
    }

    public AlgorithmHistory getSelected() {
        return selected;
    }

//    public void setAlgorithm(AlgorithmHistory algorithm) {
//        this.algorithm = algorithm;
//    }
//    public void setIsEdit(boolean isEdit) {
//        this.isEdit = isEdit;
//    }
    /**
     *
     * @return outcome que redirige a la sección de historial de algoritmos.
     */
    public String goToHistoryAlgs() {

        try {
            return "algorithmhistory?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }
    }

    /**
     *Método que asigna a la variable items la lista de historial de algoritmo.
     * @return lista de historial de algoritmos
     */
    public List<AlgorithmHistory> getItems() {
        if (items == null) {
            items = findAll();
        }
        return items;
    }

    /**
     * Convierte la lista del historial de algoritmos en formato JSON a una
     * lista del tipo AlgorithmHistory
     *
     * @param jsonListString lista en formato JSON
     * @return lista de historial de algoritmos.
     */
    private List<AlgorithmHistory> convertToList(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<AlgorithmHistory> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                AlgorithmHistory item = JsonUtil.AlgorithmHistoryFromJson(jsonList.get(i).toString());
                String[] detailSplit=item.getDetails().split(",");
                item.setDetailsSplit(detailSplit);
                list.add(item);
            }
            return list;
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

    /**
     * Método que retorna una lista de todos los registros de historial de algoritmos en base de datos.
     * Este método consume un web service rest y procesa la información obtenida
     *
     * @return lista de historial de algoritmos.
     */
    public List<AlgorithmHistory> findAll() {
        try {

            Response response = webTarget.path(MessageFormat.format("{0}", new Object[]{getSessionId()})).request(MediaType.APPLICATION_JSON + ";charset=utf-8").get(Response.class);
            String jsonListString = response.readEntity(String.class);
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                throw new Exception(jsonListString);
            }
            return convertToList(jsonListString);

        } catch (Exception e) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/algorithmhistory").getString("ListAlgorithmHistoryEmpty"));
            return new ArrayList();
        }
    }

}
