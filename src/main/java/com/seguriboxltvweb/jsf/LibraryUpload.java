package com.seguriboxltvweb.jsf;

import com.seguriboxltvweb.domain.AlgorithmAsymmetric;
import com.seguriboxltvweb.domain.AlgorithmHash;
import com.seguriboxltvweb.domain.AlgorithmSignSymm;
import com.seguriboxltvweb.jsf.util.JsfUtil;
import com.seguriboxltvweb.jsf.util.JsonUtil;
import com.seguriboxltvweb.jsf.util.SeguriboxGetProperties;
import com.seguriboxltvweb.jsf.util.SessionUtils;
import com.seguriboxltvweb.jsf.util.wsUtil;
import com.seguridata.crypto.bean.AsymmAlgsJson;
import com.seguridata.crypto.bean.HashAlgsJson;
import com.seguridata.crypto.bean.SignAlgsJson;
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
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Método que contiene toda la funcionalidad de la carga de algoritmos.
 *
 * @author Germán Hernández López
 * @version 1.0
 */
@Named("libraryUpload")
@SessionScoped
public class LibraryUpload implements Serializable {

    private final WebTarget webTarget;
    private final WebTarget webTargetAssy;
    private final WebTarget webTargetHash;
    private final WebTarget webTargetSign;

    private final Client client;
    private final String BASE_URI;
    private boolean isEdit = false;
    private String sessionId;
    private String txtTitle;
    private String txtButton;
    private List<AsymmAlgsJson> items = null;
    private List<HashAlgsJson> itemsHash = null;
    private List<SignAlgsJson> itemsSign = null;
    private List<AlgorithmSignSymm> itemAlgorithmAssybd = null;
    private AsymmAlgsJson selected;
    private HashAlgsJson selectedHash;
    private SignAlgsJson selectedSignature;
    private AlgorithmAsymmetric algorithmAssy = new AlgorithmAsymmetric();
    private AlgorithmHash algorithmHash = new AlgorithmHash();
    private AlgorithmSignSymm algorithmSign = new AlgorithmSignSymm();
    private List<AlgorithmAsymmetric> listdbassy;
    private List<AlgorithmHash> listdbHash;
    private List<AlgorithmSignSymm> listdbSign;
    private final String sourceSdCrypto;
    private final String sourceAssymetric;
    private final String sourceHash;
    private final String sourceSign;

    /**
     * Constructor de la clase LibraryUpload.
     *
     * @throws IOException
     */
    public LibraryUpload() throws IOException {
        txtTitle = "Agregar";
        txtButton = "Agregar";
        //sessionId = SessionController.getSessionId();
        sessionId = SessionUtils.getSessionId();
        client = ClientBuilder.newClient();
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
        webTarget = client.target(BASE_URI).path("sdcryptomodule");
        webTargetAssy = client.target(BASE_URI).path("algorithmasymmetric");
        webTargetHash = client.target(BASE_URI).path("algorithmhash");
        webTargetSign = client.target(BASE_URI).path("algorithmsignsymm");
        sourceSdCrypto = "sdcryptomodule";
        sourceAssymetric = "algorithmasymmetric";
        sourceHash = "algorithmhash";
        sourceSign = "algorithmsignsymm";
    }

    /**
     * Método que redirige a la sección de de carga de algoritmo Hash en donde
     * se selecciona el algoritmo disponible en seguridata y te lleva a la
     * página donde podrás guardarlo o actualizarlo.
     *
     * @param item algoritmo Hash disponible en seguridata.
     * @return outcome que redirige a la página adminlibraryhash.jsf.
     */
    public String gotoHash(HashAlgsJson item) {

        algorithmHash = getByBinOidHash(item.getBinOId());
        setSelectedHash(item);

        return "adminlibraryhash?faces-redirect=true";
    }

    /**
     * Método que redirige a la sección de de carga de algoritmo de fiema en
     * donde se selecciona el algoritmo disponible en seguridata y te lleva a la
     * página donde podrás guardarlo o actualizarlo.
     *
     * @param item algoritmo de firma disponible en seguridata.
     * @return outcome que redirige a la página adminlibrarysign.jsf.
     */
    public String gotoSignAlgs(SignAlgsJson item) {

        AlgorithmSignSymm algorithmAss = getByBinOidSign(item.getBinOid());
        if (algorithmAss == null) {
            algorithmAss = new AlgorithmSignSymm();

        } else {
            algorithmSign = algorithmAss;
        }

        setSelectedSignature(item);

        return "adminlibrarysign?faces-redirect=true";
    }

    /**
     * Método que asigna a la variable listdbSign la lista de algoritmos de
     * firma.
     *
     * @return lista de algoritmos de firma de la base de datos
     */
    public List<AlgorithmSignSymm> getListdbSign() {
        if (listdbSign == null) {
            listdbSign = findAlldbSign();
        }

        return listdbSign;
    }

    /**
     * Método que asigna a la variable itemsSign la lista de algoritmos de
     * firma.
     *
     * @return lista de algoritmos de firma de seguridata
     */
    public List<SignAlgsJson> getItemsSign() {
        if (itemsSign == null) {
            itemsSign = findAllSign();
        }
        return itemsSign;
    }

    /**
     * Método que asigna a la variable listdbHash la lista de algoritmos Hash.
     *
     * @return lista de algoritmos Hash de la base de datos
     */
    public List<AlgorithmHash> getListdbHash() {
        if (listdbHash == null) {
            listdbHash = findAllHashdb();
        }

        return listdbHash;
    }

    /**
     * Método que asigna a la variable itemsHash la lista de algoritmos Hash.
     *
     * @return lista de algoritmos Hash de seguridata.
     */
    public List<HashAlgsJson> getItemsHash() {
        if (itemsHash == null) {
            itemsHash = findAllHash();
        }
        return itemsHash;
    }

    /**
     * Método que asigna a la variable listdbassy la lista de algoritmos
     * asimétricos.
     *
     * @return lista de algoritmos asimétricos de la base de datos.
     */
    public List<AlgorithmAsymmetric> getListdbassy() {

        if (listdbassy == null) {
            listdbassy = findAllDBAssy();
        }

        return listdbassy;
    }

    /**
     * Método que asigna a la variable listdbassy la lista de algoritmos
     * asimétricos.
     *
     * @return lista de algoritmos asimétricos de seguridata.
     */
    public List<AsymmAlgsJson> getItems() {
        if (items == null) {
            items = findAllAssy();
        }
        return items;
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
     * @return
     */
    public AlgorithmHash getAlgorithmHash() {
        return algorithmHash;
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
     * @return
     */
    public AsymmAlgsJson getSelected() {
        return selected;
    }

    /**
     *
     * @param selected
     */
    public void setSelected(AsymmAlgsJson selected) {
        this.selected = selected;
    }

    /**
     *
     * @return
     */
    public HashAlgsJson getSelectedHash() {
        return selectedHash;
    }

    /**
     *
     * @param selectedHash
     */
    public void setSelectedHash(HashAlgsJson selectedHash) {
        this.selectedHash = selectedHash;
    }

    /**
     *
     * @return
     */
    public SignAlgsJson getSelectedSignature() {
        return selectedSignature;
    }

    /**
     *
     * @param selectedSignature
     */
    public void setSelectedSignature(SignAlgsJson selectedSignature) {
        this.selectedSignature = selectedSignature;
    }

    /**
     *
     * @return
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Método que redirige a la sección para poder cargar o actualizar los
     * algoritmos disponibles en seguridata.
     *
     * @return outcome a la página adminlibraryalgorithms.jsf.
     */
    public String goToUploadLibrary() {

        try {
            items = findAllAssy();
            itemAlgorithmAssybd = findAlldbSign();
            itemsHash = findAllHash();
            itemsSign = findAllSign();
            listdbHash = findAllHashdb();
            listdbSign = findAlldbSign();
            listdbassy = findAllDBAssy();
            return "adminlibraryalgorithms?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }
    }

    /**
     * Método que busca en la base de datos un algoritmo asimétrico en base a su
     * binOID .
     *
     * @param binOID id del algoritmo a buscar.
     * @return objeto de algoritmo asimétrico.
     */
    public AlgorithmAsymmetric getByBinOidAssy(String binOID) {

        try {

            String path = MessageFormat.format(sourceAssymetric + "/binoid/{0}/{1}", new Object[]{sessionId, binOID});
            String item = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return JsonUtil.AlgorithmAsymmetricFromJson(item);
        } catch (Exception e) {
            if (e.getMessage().contains("No existe el algoritmo")) {

            } else {
                JsfUtil.addErrorMessage(e.getMessage());
            }

            return null;
        }

    }

    /**
     * Método que busca en la base de datos un algoritmo Hash en base a su
     * binOID .
     *
     * @param binOID id del algoritmo a buscar.
     * @return objeto de algoritmo Hash.
     */
    public AlgorithmHash getByBinOidHash(String binOID) {

        try {
            String path = MessageFormat.format(sourceHash + "/binoid/{0}/{1}", new Object[]{sessionId, binOID});
            String item = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return JsonUtil.AlgorithmHashFromJson(item);
        } catch (Exception e) {

            if (e.getMessage().contains("No existe el algoritmo")) {

            } else {
                JsfUtil.addErrorMessage(e.getMessage());
            }
            return null;
        }

    }

    /**
     * Método que busca en la base de datos un algoritmo de firma en base a su
     * binOID .
     *
     * @param binOID id del algoritmo a buscar.
     * @return objeto de algoritmo de firma.
     */
    public AlgorithmSignSymm getByBinOidSign(String binOID) {
        try {

            String path = MessageFormat.format(sourceSign + "/signsymm/{0}/{1}", new Object[]{sessionId, binOID});
            String item = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return JsonUtil.AlgorithmSignSymmFromJson(item);
        } catch (Exception e) {

            if (e.getMessage().contains("No existe el algoritmo")) {

            } else {
                JsfUtil.addErrorMessage(e.getMessage());
            }
            return null;
        }
    }

    /**
     *Método que busca todos los algoritmos de firma disponibles en seguridata. 
     * @return lista de algoritmos asimétricos.
     */
    public List<AsymmAlgsJson> findAllAssy() {
        try {
            String path = MessageFormat.format(sourceSdCrypto + "/assymetricalgs2/{0}", new Object[]{getSessionId()});
            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return convertToListAssy(jsonListString);

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());

            return new ArrayList();
        }
    }

    /**
     *Método que busca todos los algoritmos de Hash disponibles en seguridata. 
     * @return lista de algoritmos Hash.
     */
    public List<HashAlgsJson> findAllHash() {
        try {
            String path = MessageFormat.format(sourceSdCrypto + "/hashalgs/{0}", new Object[]{getSessionId()});
            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return convertToListHash(jsonListString);

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return new ArrayList();
        }
    }

    /**
     *Método que busca todos los algoritmos de firma disponibles en seguridata. 
     * @return lista de algoritmos de firma.
     */
    public List<SignAlgsJson> findAllSign() {
        try {

            String path = MessageFormat.format(sourceSdCrypto + "/signaturealgs/{0}", new Object[]{getSessionId()});
            String jsonListString = wsUtil.ExecuteGetRestService(isEdit, BASE_URI, path);

            return convertToListSing(jsonListString);

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return new ArrayList();
        }
    }

    /**
     *Método que convierte una lista de algoritmos asimétricos en formato JSON
     * en una lista de objetos de AsymmAlgsJson.
     * @param jsonListString
     * @return lista de algoritmos asimétricos.
     */
    private List<AsymmAlgsJson> convertToListAssy(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<AsymmAlgsJson> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                AsymmAlgsJson item = JsonUtil.AssymetricAlgsFronJson(jsonList.get(i).toString());
                list.add(item);
            }
            return list;
        } catch (Exception e) {

            throw new JSONException(e.getMessage());
        }
    }

       /**
     *Método que convierte una lista de algoritmos Hash en formato JSON
     * en una lista de objetos de HashAlgsJson.
     * @param jsonListString
     * @return lista de algoritmos Hash.
     */
    private List<HashAlgsJson> convertToListHash(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<HashAlgsJson> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                HashAlgsJson item = JsonUtil.HashAlgsFromJson(jsonList.get(i).toString());
                list.add(item);
            }
            return list;
        } catch (Exception e) {

            throw new JSONException(e.getMessage());
        }
    }

         /**
     *Método que convierte una lista de algoritmos de firma en formato JSON
     * en una lista de objetos de SignAlgsJson.
     * @param jsonListString
     * @return lista de algoritmos de firma.
     */
    private List<SignAlgsJson> convertToListSing(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<SignAlgsJson> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                SignAlgsJson item = JsonUtil.SignatureAlgsFromJson(jsonList.get(i).toString());
                list.add(item);
            }
            return list;
        } catch (Exception e) {

            throw new JSONException(e.getMessage());
        }
    }

    /**
     *Método que agrega un algoritmo asimétrico a la base de datos.
     * @param selected algoritmo asimétrico a insertar
     * @return outcome a la paǵina adminlibraryalgorithms.jsf
     * @throws Exception
     */
    public String insertAssym(AsymmAlgsJson selected) throws Exception {
        String str = null;

        String res = JsonUtil.JsonFromAssymetricAlgs(selected);

        try {
            //String res=JsonUtil.JsonFromAssymetricAlgs(selected);
            String path = MessageFormat.format(sourceAssymetric + "/{0}", new Object[]{sessionId});
            wsUtil.ExecutePostRestService(true, BASE_URI, path, res);

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Algorithmasymmetric").getString("AlgorithmAsymmetricCreated"));

            listdbassy = findAllDBAssy();

            str = "adminlibraryalgorithms?faces-redirect=true";

        } catch (Exception e) {

            JsfUtil.addErrorMessage(e.getMessage());
            str = null;

        }
        return str;
    }

    /**
     *Método que agrega un algoritmo hash a la base de datos.
     * @param selectedHash algoritmo hash a insertar
     * @return outcome a la paǵina adminlibraryalgorithms.jsf
     * @throws Exception
     */
    public String insertHash(HashAlgsJson selectedHash) throws Exception {
        String str = null;
        String res = JsonUtil.JsonFromHashAlgs(selectedHash);

        try {

            String path = MessageFormat.format(sourceHash + "/{0}", new Object[]{sessionId});
            wsUtil.ExecutePostRestService(true, BASE_URI, path, res);

            listdbHash = findAllHashdb();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/algorithmHash").getString("AlgorithmHashCreated"));
            str = "adminlibraryalgorithms?faces-redirect=true";

        } catch (Exception e) {

            JsfUtil.addErrorMessage(e.getMessage());
            str = null;
        }

        return str;
    }

   /**
     *Método que agrega un algoritmo de firma a la base de datos.
     * @param selectedSignature algoritmo de firma a insertar
     * @return outcome a la paǵina adminlibraryalgorithms.jsf
     * @throws Exception
     */
    public String insertSign(SignAlgsJson selectedSignature) throws Exception {
        String str = null;
        String res = JsonUtil.JsonFromSignatureAlgs(selectedSignature);

        try {

            String path = MessageFormat.format(sourceSign + "/{0}", new Object[]{sessionId});
            wsUtil.ExecutePostRestService(true, BASE_URI, path, res);

            listdbSign = findAlldbSign();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/algorithmSignSymm").getString("AlgorithmSignSymmCreated"));
            str = "adminlibraryalgorithms?faces-redirect=true";

        } catch (Exception e) {

            JsfUtil.addErrorMessage(e.getMessage());
            str = null;
        }
        return str;
    }

    /**
     *  Método que obtiene todos los registros de algoritmos asimétricos de la base de datos.
     * 
     * @return lista de algoritmos asimétricos
     */
    public List<AlgorithmAsymmetric> findAllDBAssy() {
        try {

            String path = MessageFormat.format(sourceAssymetric + "/{0}", new Object[]{getSessionId()});
            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return convertToListDBAssy(jsonListString);

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return new ArrayList();
        }
    }

    /**
     *Método que convierte la lista en formato JSON de algoritmos asimétricos en una lista de objetos de 
     * algoritmos asimétricos.
     * @param jsonListString JSON de algoritmos asimétricos
     * @return lista de algoritmos asimétricos.
     */
    private List<AlgorithmAsymmetric> convertToListDBAssy(String jsonListString) {
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

    /**
     *Método que convierte la lista en formato JSON de algoritmos hash en una lista de objetos de 
     * algoritmos hash.
     * @param jsonListString JSON de algoritmos hash
     * @return lista de algoritmos hash.
     */
    private List<AlgorithmHash> convertToListDBHash(String jsonListString) {
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
     *Método que regresa una lista de algoritmos hash de la base de datos.
     * 
     * @return llista de algoritmos hash
     */
    public List<AlgorithmHash> findAllHashdb() {
        try {

            String path = MessageFormat.format(sourceHash + "/{0}", new Object[]{getSessionId()});
            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);
            return convertToListDBHash(jsonListString);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return new ArrayList();
        }
    }

    /**
     *  Convierte el JSON obtenido a una lista del tipo AlgorithmSignSymm.
     * @param jsonListString lista en formato JSON
     * @return lista de algoritmos de firma.
     */
    private List<AlgorithmSignSymm> convertToListDBSignature(String jsonListString) {
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
     * Método que redirige a la sección de de carga de algoritmo asimétrico en donde
     * se selecciona el algoritmo disponible en seguridata y te lleva a la
     * página donde podrás guardarlo o actualizarlo.
     *
     * @param item algoritmo asimétrico disponible en seguridata.
     * @return outcome que redirige a la página adminlibraryasimetric.jsf.
     */
    public String gotoAssyAlg(AsymmAlgsJson item) {

        algorithmAssy = getByBinOidAssy(item.getBinOid());

        setSelected(item);

        return "adminlibraryasimetric?faces-redirect=true";
    }

    /**
     *Método que obtiene todos los registros de algoritmo de firma de la base de datos
     * 
     * @return Lista de algoritmos de firma.
     */
    public List<AlgorithmSignSymm> findAlldbSign() {
        try {

            String path = MessageFormat.format(sourceSign + "/{0}", new Object[]{getSessionId()});
            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return convertToListDBSignature(jsonListString);

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return new ArrayList();
        }
    }
}
