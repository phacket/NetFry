package com.seguriboxltvweb.jsf;

import com.seguriboxltvweb.domain.AlgorithmAsymmetric;
import com.seguriboxltvweb.domain.AlgorithmSignSymm;
import com.seguriboxltvweb.domain.HSMKey;
import com.seguriboxltvweb.jsf.util.JsfUtil;
import com.seguriboxltvweb.jsf.util.JsonUtil;
import com.seguriboxltvweb.jsf.util.SeguriboxGetProperties;
import com.seguriboxltvweb.jsf.util.SessionUtils;
import com.seguriboxltvweb.jsf.util.wsUtil;
import com.seguridata.certificate.bean.Certificate;
import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.primefaces.model.DualListModel;
import static javax.ws.rs.client.Entity.entity;

/**
 * Clase que contiene toda la funcionalidad de HSMKeyController.
 *
 * @author Germán Hernández López.
 * @version 1.0
 */
@Named("hSMKeyController")
@SessionScoped
public class HSMKeyController implements Serializable {

    private List<HSMKey> items = null;
    private List<com.seguridata.hsm.bean.HSMKey> itemsd = null;
    private com.seguridata.hsm.bean.HSMKey sdSelected;
    private HSMKey selected;
    private AlgorithmAsymmetric algorithmAssy;
    private AlgorithmSignSymm algorithmSign;
    private List<AlgorithmSignSymm> itemSignAlg;
    private final WebTarget webTarget;
    private final WebTarget webTarget2;
    private final WebTarget webTarget3;
    private final WebTarget webTarget4;
    private final WebTarget webTargethsm;
    private final Client client;
    private final String BASE_URI;
    private boolean isEdit;
    private String sessionId;
    private DualListModel<HSMKey> hsmkey;
    private Date startDate;
    private Date endDate;
    private String search;
    private Certificate certificate = null;
    private int flagSelect;
    private final String sourcehsm;
    private final String sourcesdhsmkey;

    /**
     * Constructor de la clase HSMKeyController.
     *
     * @throws IOException
     */
    public HSMKeyController() throws IOException {
        client = ClientBuilder.newClient();
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
        webTarget = client.target(BASE_URI).path("hsmkey");
        webTargethsm = client.target(BASE_URI).path("hsmkey");
        webTarget2 = client.target(BASE_URI).path("sdhsmkey");
        webTarget3 = client.target(BASE_URI).path("algorithmasymmetric");
        webTarget4 = client.target(BASE_URI).path("algorithmsignsymm");
        sessionId = SessionUtils.getSessionId();
        sourcehsm = "hsmkey";
        sourcesdhsmkey = "sdhsmkey";
    }

    /**
     * Método que se ejecuta por AJAX cuando se selecciona una llave
     * HSMkey,carga el algoritmo asimétrico asociado,sus algoritmos de firma y
     * la fecha de vencimiento.
     *
     * @throws Exception
     */
    public void onChange() throws Exception {

        if ("--Seleccionar Llave--".equals(sdSelected.getLabel())) {

            setCertificate(new Certificate());
            setAlgorithmSign(new AlgorithmSignSymm());
            setSdSelected(new com.seguridata.hsm.bean.HSMKey());
            setAlgorithmAssy(new AlgorithmAsymmetric());
            setItemSignAlg(null);
        }

        AlgorithmAsymmetric assyAlgs = getByHSMKey(getSdSelected().getOid());

        try {

            if (assyAlgs != null) {
                Certificate cer = GetCertificateHSMKey(getSdSelected());
                if (cer != null) {

                    certificate = cer;

                } else {

                    certificate = null;
                }
                setAlgorithmAssy(assyAlgs);
                List<AlgorithmSignSymm> listSign = findAllSignSymm(getAlgorithmAssy().getBinOid());
                if (listSign.isEmpty()) {

                    itemSignAlg = null;
                } else {

                    itemSignAlg = listSign;

                }
            } else {
                setAlgorithmAssy(null);

            }

        } catch (Exception e) {

            throw e;
        }

    }

    /**
     * Método que retorna un certificado en base a el Hsmkey.
     *
     * @param sdSelected HSMkey para obtener el certificado.
     * @return Certificado del HSMkey
     * @throws Exception
     */
    public Certificate GetCertificateHSMKey(com.seguridata.hsm.bean.HSMKey sdSelected) throws Exception {
        Certificate cer = null;
        String res = JsonUtil.JsonFromSDHSMKey(sdSelected);
        try {

            Response response = webTarget2.path(MessageFormat.format("{0}", new Object[]{sessionId})).request(MediaType.APPLICATION_JSON + ";charset=utf-8").put(entity(res, MediaType.APPLICATION_JSON + ";charset=utf-8"), Response.class);
            String jsonString = response.readEntity(String.class);
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                throw new Exception(response.readEntity(String.class));
            }
            //Response response = webTarget2.path(MessageFormat.format("{0}", new Object[]{sessionId})).request(MediaType.APPLICATION_JSON+ ";charset=utf-8").put(entity(res, MediaType.APPLICATION_JSON+ ";charset=utf-8"), String.class);
            cer = JsonUtil.CertificateFromJson(jsonString);
        } catch (Exception e) {

            JsfUtil.addErrorMessage(e.getMessage());
        }

        return cer;
    }

    /**
     * Método que retorna una lista de algoritmos de firma en base a su binoid.
     *
     * @param binoid
     * @return lista de algoritmos de firma.
     */
    public List<AlgorithmSignSymm> findAllSignSymm(String binoid) {

        try {
            WebTarget resource = webTarget4;

            Response response = resource.path(MessageFormat.format("binoid/{0}/{1}", new Object[]{sessionId, binoid})).request(MediaType.APPLICATION_JSON + ";charset=utf-8").get(Response.class);
            String jsonListString = response.readEntity(String.class);
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                throw new Exception(jsonListString);
            }

            return convertToListAlgorithmSign(jsonListString);

        } catch (Exception e) {
            if (e.getMessage().equals("No hay registros en la lista")) {

            } else {

                JsfUtil.addErrorMessage(e.getMessage());

            }

            return new ArrayList();
        }
    }

    /**
     * Método que convierte la lista en formato JSON en una lista del tipo
     * AlgorithmSignSymm
     *
     * @param jsonListString JSON a convertir en lista de objetos
     * AlgorithmSignSymm.
     * @return lista de algoritmos de firma
     */
    private List<AlgorithmSignSymm> convertToListAlgorithmSign(String jsonListString) {
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
     * Método que devuelve un algoritmo asimétrico en base a su oid.
     *
     * @param oid
     * @return Algoritmo asimétrico.
     */
    public AlgorithmAsymmetric getByHSMKey(String oid) {

        try {
            WebTarget resource = webTarget3;

            Response response = resource.path(MessageFormat.format("getbyhsmkey/{0}/{1}", new Object[]{sessionId, oid})).request(MediaType.APPLICATION_JSON + ";charset=utf-8").get(Response.class);
            String item = response.readEntity(String.class);
            return JsonUtil.AlgorithmAsymmetricFromJson(item);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }
    }

    /**
     * Método que le asigna a la variable items la lista de HSMkey que se
     * consumen del servicio web de SEGURIDATA.
     *
     * @return lista de HSMkey
     */
    public List<com.seguridata.hsm.bean.HSMKey> getItemsd() throws Exception {
        if (itemsd == null) {
            itemsd = sdfindAll();
        }

        return itemsd;
    }

    /**
     * Método que retorna una lista de todos los HSMkey almacenados en
     * Seguridata.
     *
     * @return lista de HSMkey
     */
    public List<com.seguridata.hsm.bean.HSMKey> sdfindAll() throws Exception {
        try {
            String path = MessageFormat.format(sourcesdhsmkey + "/{0}", new Object[]{getSessionId()});

            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return convertToListSD(jsonListString);

        } catch (Exception e) {
            //JsfUtil.addErrorMessage(e.getMessage());
            throw e;
        }
    }

    /**
     * Método que convierte la lista en formato JSON a una lista del tipo
     * HSMKey.
     *
     * @param jsonListString
     * @return lista de HSMkey
     */
    private List<com.seguridata.hsm.bean.HSMKey> convertToListSD(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<com.seguridata.hsm.bean.HSMKey> list = new ArrayList();

            for (int i = 0; i < jsonList.length(); i++) {
                com.seguridata.hsm.bean.HSMKey item = JsonUtil.SDHSMKeyFromJson(jsonList.get(i).toString());
                boolean add = true;
                for (HSMKey it : items) {

                    if (item.getLabel().equals(it.getKeyTag())) {

                        add = false;
                        break;
                    }
                }
                if (add) {

                    list.add(item);
                }

            }
            com.seguridata.hsm.bean.HSMKey newItem = new com.seguridata.hsm.bean.HSMKey();
            newItem.setLabel("--Seleccionar Llave--");
            list.add(0, newItem);
            return list;
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

    /**
     *
     * @return
     */
    public Certificate getCertificate() {
        return certificate;
    }

    /**
     *
     * @param certificate
     */
    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    /**
     *
     * @return
     */
    public List<AlgorithmSignSymm> getItemSignAlg() {
        return itemSignAlg;
    }

    /**
     *
     * @param itemSignAlg
     */
    public void setItemSignAlg(List<AlgorithmSignSymm> itemSignAlg) {
        this.itemSignAlg = itemSignAlg;
    }

    /**
     *
     * @return
     */
    public AlgorithmSignSymm getAlgorithmSign() {
        return algorithmSign;
    }

    /**
     *
     * @param algorithmSign
     */
    public void setAlgorithmSign(AlgorithmSignSymm algorithmSign) {
        this.algorithmSign = algorithmSign;
    }

    /**
     *
     * @return
     */
    public AlgorithmAsymmetric getAlgorithmAssy() {
        return algorithmAssy;
    }

    /**
     *
     * @param algorithmAssy
     */
    public void setAlgorithmAssy(AlgorithmAsymmetric algorithmAssy) {
        this.algorithmAssy = algorithmAssy;
    }

    /**
     *
     * @return
     */
    public com.seguridata.hsm.bean.HSMKey getSdSelected() {
        return sdSelected;
    }

    /**
     *
     * @param sdSelected
     */
    public void setSdSelected(com.seguridata.hsm.bean.HSMKey sdSelected) {
        this.sdSelected = sdSelected;
    }

    /**
     *
     * @param itemsd
     */
    public void setItemsd(List<com.seguridata.hsm.bean.HSMKey> itemsd) {

        this.itemsd = itemsd;
    }

    /**
     *
     * @return
     */
    public String getSearch() {
        return search;
    }

    /**
     *
     * @return
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     *
     * @return
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     *
     * @return
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     *
     * @param selected
     */
    public void setSelected(HSMKey selected) {
        this.selected = selected;
    }

    /**
     * Método que redirige a la sección HSM y limpia las variables.
     *
     * @return Outcome que redirige a la página adminhsmkeys.jsf.
     */
    public String goToHsm() {
        items = findAll();

        setCertificate(new Certificate());
        setAlgorithmSign(new AlgorithmSignSymm());
        setSdSelected(new com.seguridata.hsm.bean.HSMKey());
        setAlgorithmAssy(new AlgorithmAsymmetric());
        return "adminhsmkeys?faces-redirect=true";
    }

    /**
     * Método para crear un HSMkey y limpia las variables.
     *
     * @return outcome que redirige a la página adminhsmkey.jsf.
     */
    public String goToCreate() {
        try {
            if(sdfindAll().isEmpty() || sdfindAll().size()==1){
            
                throw new Exception("Todas las llaves HSM han sido agregadas");
            }
            setSdSelected(new com.seguridata.hsm.bean.HSMKey());
            setSelected(new HSMKey());
            setItemSignAlg(null);
            setItemsd(null);
            setAlgorithmAssy(null);
            setCertificate(null);

            return "adminhsmkey?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }

    }

    /**
     * Método que asigna la lista de HSMkey a la variable items.
     *
     * @return lista de HSMkey
     */
    public List<HSMKey> getItems() {
        if (items == null) {
            items = findAll();
        }
        return items;
    }

    /**
     * Método que devuelve una lista de todos los HSMkey en la base de datos.
     *
     * @return
     */
    public List<HSMKey> findAll() {
        try {
            String path = MessageFormat.format(sourcehsm + "/{0}", new Object[]{sessionId});

            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return convertToList(jsonListString);

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return new ArrayList();
        }
    }

    /**
     * Método que convierte la lista en formato JSON obtenida en una lista del
     * tipo HSMKey.
     *
     * @param jsonListString lista en formato JSON de hsmkey
     * @return lista de HSMkey
     */
    private List<HSMKey> convertToList(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<HSMKey> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                HSMKey item = JsonUtil.HSMKeyFromJson(jsonList.get(i).toString());

                list.add(item);
            }
            return list;
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

    /**
     * Método que elimina un HSMkey de la base de datos.
     *
     * @param selected HSMkey a eliminar
     */
    public void remove(HSMKey selected) {
        String idString = selected.getHSMKeyId() + "";

        try {

            Response response = webTarget.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{sessionId, idString})).request().delete();

            if (response.getStatus() != Response.Status.OK.getStatusCode()) {

                throw new Exception(response.readEntity(String.class));
            } else {
                items = findAll();
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/HsmKey").getString("HSMKeyDeleted"));

            }
        } catch (Exception e) {

            JsfUtil.addErrorMessage(e.getMessage());
            items = findAll();

        }
    }

    /**
     * Método que edita un HSMkey
     *
     * @param selected hsmkey a editar
     * @throws Exception
     */
    public void edit(HSMKey selected) throws Exception {

        try {

            selected.setIsActive(false);
            String res = JsonUtil.JsonFromHSMKey(selected);

            String path = MessageFormat.format(sourcehsm + "/{0}", new Object[]{sessionId});

            String msj = wsUtil.ExecutePutRestService(true, BASE_URI, path, res);

            items = findAll();
            setSelected(new HSMKey());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/HsmKey").getString("HSMKeyDeleted"));

        } catch (Exception e) {
            items = findAll();
            JsfUtil.addErrorMessage(e.getMessage());
        }

    }

    /**
     * Método que inserta un HSMkey a la base de datos.
     *
     * @param sdSelected Hsmkey a insertar
     * @param algorithmAssy algoritmo asimétrico que se le va a asignar a la
     * llave.
     * @return outcome que redirige a la página adminhsmkeys.jsf.
     * @throws Exception
     */
    public String insert(com.seguridata.hsm.bean.HSMKey sdSelected, AlgorithmAsymmetric algorithmAssy) throws Exception {

        String str = null;
        try {
            if (sdSelected.getLabel() == null) {

                throw new Exception("No se ha seleccionado ninguna llave");
            }

            if (sdSelected.getLabel().equals("--Seleccionar Llave--")) {

                throw new Exception("No se ha seleccionado la llave");
            }

            if (algorithmAssy != null) {

            } else {

                throw new Exception("No existen algoritmos asimétricos en plataforma");
            }

            if (algorithmSign != null) {

            } else {
                throw new Exception("No existen algoritmos de firma en plataforma ");
            }

            String res = JsonUtil.JsonFromSDHSMKey(sdSelected);

            String id = algorithmSign.getAlgorithmSignId().toString();

            String path = MessageFormat.format(sourcehsm + "/{0}/{1}", new Object[]{sessionId, id});

            wsUtil.ExecutePostRestService(true, BASE_URI, path, res);
            items = findAll();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/HsmKey").getString("HSMKeyCreated"));
            str = "adminhsmkeys?faces-redirect=true";

        } catch (Exception e) {
            items = findAll();
            JsfUtil.addErrorMessage(e.getMessage());
            str = null;
        }

        return str;
    }

    /**
     * Método que obtiene un registro del tipo HSMkey en base a su id
     *
     * @param id con el que se buscara el HSMkey
     * @return HSMkey
     */
    public HSMKey getHSMKey(java.lang.Integer id) {
        try {
            WebTarget resource = webTarget;
            Response response = resource.path(MessageFormat.format("{0}", new Object[]{id})).request(MediaType.APPLICATION_JSON + ";charset=utf-8").get(Response.class);
            String item = response.readEntity(String.class);
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                throw new Exception(response.readEntity(String.class));
            }
            return JsonUtil.HSMKeyFromJson(item);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }
    }

//    @FacesConverter(forClass = HSMKey.class)
//    public static class HSMKeyControllerConverter implements Converter {
//
//        @Override
//        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
//            if (value == null || value.length() == 0) {
//                return null;
//            }
//            HSMKeyController controller = (HSMKeyController) facesContext.getApplication().getELResolver().
//                    getValue(facesContext.getELContext(), null, "hSMKeyController");
//            return controller.getHSMKey(getKey(value));
//        }
//
//        java.lang.Integer getKey(String value) {
//            java.lang.Integer key;
//            key = Integer.valueOf(value);
//            return key;
//        }
//
//        String getStringKey(java.lang.Integer value) {
//            StringBuilder sb = new StringBuilder();
//            sb.append(value);
//            return sb.toString();
//        }
//
//        @Override
//        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
//            if (object == null) {
//                return null;
//            }
//            if (object instanceof HSMKey) {
//                HSMKey o = (HSMKey) object;
//                return getStringKey(o.getHSMKeyId());
//            } else {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), HSMKey.class.getName()});
//                return null;
//            }
//        }
//
//    }
}
