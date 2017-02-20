package com.seguriboxltvweb.jsf;

import com.seguriboxltvweb.domain.AlgorithmAsymmetric;
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
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Clase que contiene la funcionalidad de algoritmos asimétricos.
 *
 * @author Ing. Germán Hernández López.
 * @version 1.0
 */
@Named("algorithmAsymmetricController")
@SessionScoped
public class AlgorithmAsymmetricController implements Serializable {

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
    private boolean isEdit = false;

    /**
     * Variable que almacena el valor del sessionId, se inicializa en el
     * constructor.
     */
    private final String sessionId;
    private String txtTitle;
    private String txtButton;

    /**
     * Variable que almacena la lista de algoritmos asimétricos.
     */
    private List<AlgorithmAsymmetric> items = null;
    /**
     * Variable que almacena el objeto AlgorithmAsymmetric.
     */
    private AlgorithmAsymmetric selected;

    private final String source;

    /**
     * Constructor de la clase AlgorithmAsymmetricController.
     *
     * @throws IOException
     */
    public AlgorithmAsymmetricController() throws IOException {
        txtTitle = "Agregar";
        txtButton = "Agregar";
        sessionId = SessionUtils.getSessionId();
        client = ClientBuilder.newClient();
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
        webTarget = client.target(BASE_URI).path("algorithmasymmetric");
        source = "algorithmasymmetric";

    }

    public void setTxtTitle(String txtTitle) {
        this.txtTitle = txtTitle;
    }

    public void setTxtButton(String txtButton) {
        this.txtButton = txtButton;
    }

    public String getSessionId() {
        return sessionId;
    }

    public AlgorithmAsymmetric getSelected() {
        return selected;
    }

    public void setSelected(AlgorithmAsymmetric selected) {
        this.selected = selected;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    /**
     * Carga la tabla de algoritmos asimétricos.
     *
     * @return outcome que lleva a la página adminasimetricalgorithms.jsf.
     */
    public String goToAssy() {

        try {
            items = findAll();
            return "adminasimetricalgorithms?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }
    }

    /**
     * Redirige a la página de edición de algoritmos asimétricos.
     *
     * @param item elemento a editar.
     * @return cadena que lleva a la página adminasimetricalgorithm.jsf.
     */
    public String goToEdit(AlgorithmAsymmetric item) {

        setSelected(item);
        setIsEdit(true);
        return "adminasimetricalgorithm?faces-redirect=true";
    }

    /**
     * Asigna la lista obtenida de algoritmos asimétricos a la variable items.
     *
     * @return Lista de algoritmos asimétricos.
     */
    public List<AlgorithmAsymmetric> getItems() {
        if (items == null) {
            items = findAll();
        }
        return items;
    }

    /**
     * Método que devuelve una lista de todos los algoritmos asimétricos. Método
     * que consume un web service rest y procesa la información obtenida.
     *
     * @return lista de algoritmos asimétricos.
     */
    public List<AlgorithmAsymmetric> findAll() {
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

    /**
     * Convierte el JSON obtenido a una lista del tipo AlgorithmAsymmetric.
     *
     * @param jsonListString lista en formato JSON
     * @return lista de algoritmos asimétricos.
     */
    private List<AlgorithmAsymmetric> convertToList(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<AlgorithmAsymmetric> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                AlgorithmAsymmetric item = JsonUtil.AlgorithmAsymmetricFromJson(jsonList.get(i).toString());
                list.add(item);
            }
            return list;
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

//    public void remove(AlgorithmAsymmetric selected) {
//        String idString = selected.getAlgorithmAsymmetricId() + "";
//        try {
//
//            Response response = webTarget.path(java.text.MessageFormat.format("{0}/{1}",
//                    new Object[]{getSessionId(), idString})).request().delete();
//            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
//                items = findAll();
//                throw new Exception(response.readEntity(String.class));
//            } else {
//                items = findAll();
//                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Algorithmasymmetric").getString("AlgorithmAsymmetricDeleted"));
//
//            }
//        } catch (Exception e) {
//
//            JsfUtil.addErrorMessage(e.getMessage());
//
//        }
//    }
    /**
     * Valida que el tamaño de bits de algoritmos asimétricos este entre el
     * máximo y el mínimo y aparte que sea un módulo de delta bits.
     *
     * @return true si la validación fue correcta false si fue incorrecta.
     */
    private boolean SizeValidation() throws Exception {
        try {

            if (selected.getSize() == null) {

                throw new Exception("El tamaño no puede estar vacío");
            }
            if ((selected.getSize() >= selected.getMinBits() && selected.getSize() <= selected.getMaxBits())
                    && (selected.getSize() % selected.getDeltaBits() == 0)) {

                return true;
            } else {

                return false;

            }

        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * Edita información del algoritmo seleccionado.
     *
     * @param selected Algoritmo asimétrico a editar.
     * @return String que redirige a la página adminasimetricalgorithms.jsf si
     * el proceso fue exitoso.
     * @throws Exception
     */
    public String edit(AlgorithmAsymmetric selected) throws Exception {
        try {
            if (SizeValidation() == true) {

                String res = JsonUtil.JsonFromAlgorithmAsymmetric(selected);
                String path = MessageFormat.format(source + "/{0}", new Object[]{getSessionId()});
                wsUtil.ExecutePutRestService(true, BASE_URI, path, res);

                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Algorithmasymmetric").getString("AlgorithmAsymmetricUpdated"));
                items = findAll();
                setSelected(new AlgorithmAsymmetric());
                return "adminasimetricalgorithms?faces-redirect=true";

            } else {
                items = findAll();

                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Algorithmasymmetric").getString("ValidatorSize"));
                return null;
            }
        } catch (Exception e) {
            items = findAll();
            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }

    }
}
