package com.seguriboxltvweb.jsf;

import com.seguriboxltvweb.domain.AlgorithmSignSymm;
import com.seguriboxltvweb.domain.HSMKey;
import com.seguriboxltvweb.domain.SystemParameters;
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
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import org.json.JSONArray;

/**
 *
 * @author Germán Hernández López.
 * @version 1.0.
 */
@Named("systemParametersController")
@SessionScoped
public class SystemParametersController implements Serializable {

    private SystemParameters items;
    private List<HSMKey> HSMKeys;
    private List<AlgorithmSignSymm> algorithms;
    private SystemParameters selected;
    private String sessionId;
    private final String BASE_URI;
    private boolean trace = true;

    /**
     * Constructor de la clase SystemParametersController.
     *
     * @throws IOException
     */
    public SystemParametersController() throws IOException {
        sessionId = SessionUtils.getSessionId();
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
    }

    /**
     * Método que asigna a la variable algorithms la lista de algoritmos de
     * firma.
     *
     * @return
     */
    public List<AlgorithmSignSymm> getAlgorithms() {
        algorithms = findAllAlgorithms();
//        AlgorithmSignSymm p = new AlgorithmSignSymm();
//        for (int i = 0; i < algorithms.size(); i++) {
//            p = algorithms.get(i);
//        }
        return algorithms;
    }

    /**
     * Método que llena campos de algoritmos y Llaves Hsm
     *
     * @return @throws IOException
     */
    public String fillControls() throws IOException {
        try {
            algorithms = null;
            algorithms = getAlgorithms();
            items = null;
            items = findAll();
            HSMKeys = null;
            HSMKeys = getHSMKeys();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("admin/systemparameters.jsf");
        return "admin/systemparameters?faces-redirect=true";
    }

    /**
     *
     * @return
     */
    public List<HSMKey> getHSMKeys() {
        HSMKeys = findAllHSM();
        return HSMKeys;
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
     * @return
     */
    public SystemParameters getSelected() {
        return selected;
    }

    /**
     *
     * @return
     */
    public SystemParameters getItems() {
        if (items == null) {
            items = findAll();
        }
        return items;
    }

    /**
     * Método que obtiene todos los parámetros del sistema para cargarlos a la
     * página.
     *
     * @return objeto de system parameter.
     */
    public SystemParameters findAll() {
        try {
            String path = MessageFormat.format("systemparameter/{0}", getSessionId());
            String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);
            return convertFromJson(jsonResult);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return new SystemParameters();
        }
    }

    /**
     * Método que Convierte el JSON obtenido a una lista del tipo
     * SystemParameters.
     *
     * @param jsonListString
     * @return objeto de system parameter
     */
    private SystemParameters convertFromJson(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);

            SystemParameters systemParameters = new SystemParameters();
            for (int i = 0; i < jsonList.length(); i++) {
                systemParameters = JsonUtil.systemParametersFromJson(jsonList.get(i).toString(), systemParameters);
            }
            systemParameters.setAnonymous(systemParameters.getSMTPANONIMOUS().getParameterValue().equals("true"));
            systemParameters.setSsl(systemParameters.getSMTPSSLS().getParameterValue().equals("true"));
            return systemParameters;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }
    }

    /**
     *Método que guarda las modificaciones de system parameter. 
     */
    public void edit() {

        if (items.isAnonymous()) {

            items.getSMTPANONIMOUS().setParameterValue("true");
        } else {
            items.getSMTPANONIMOUS().setParameterValue("false");
        }

        if (items.isSsl()) {

            items.getSMTPSSLS().setParameterValue("true");
        } else {
            items.getSMTPSSLS().setParameterValue("false");
        }

        try {
            String STORAGETIME = JsonUtil.JsonFromSystemParameters(items.getSTORAGETIME());
            String ORGANIZATION = JsonUtil.JsonFromSystemParameters(items.getORGANIZATION());
            String KEYAGENT = JsonUtil.JsonFromSystemParameters(items.getKEYAGENT());
            String SEGURIBOXAPIPASSWORD = JsonUtil.JsonFromSystemParameters(items.getSEGURIBOXAPIPASSWORD());
            String SEGURIBOXAPIUSERNAME = JsonUtil.JsonFromSystemParameters(items.getSEGURIBOXAPIUSERNAME());
            String SEGURIBOXAPIURL = JsonUtil.JsonFromSystemParameters(items.getSEGURIBOXAPIURL());
            String SEGURISERVERPASSWORD = JsonUtil.JsonFromSystemParameters(items.getSEGURISERVERPASSWORD());
            String SEGURISERVERUSERNAME = JsonUtil.JsonFromSystemParameters(items.getSEGURISERVERUSERNAME());
            String SEGURISERVERURL = JsonUtil.JsonFromSystemParameters(items.getSEGURISERVERURL());
            String SEGURISIGNPASSWORD = JsonUtil.JsonFromSystemParameters(items.getSEGURISIGNPASSWORD());
            String SEGURISIGNUSERNAME = JsonUtil.JsonFromSystemParameters(items.getSEGURISIGNUSERNAME());
            String SEGURISIGNURL = JsonUtil.JsonFromSystemParameters(items.getSEGURISIGNURLs());
            String ARCHIVEPASSWORD = JsonUtil.JsonFromSystemParameters(items.getARCHIVEPASSWORD());
            String ARCHIVEUSERNAME = JsonUtil.JsonFromSystemParameters(items.getARCHIVEUSERNAME());
            String ARCHIVEURL = JsonUtil.JsonFromSystemParameters(items.getARCHIVEURL());
            String DOPKCS11PASSWORD = JsonUtil.JsonFromSystemParameters(items.getDOPKCS11PASSWORD());
            String DOPKCS11USERNAME = JsonUtil.JsonFromSystemParameters(items.getDOPKCS11USERNAME());
            String DOPKCS11URL = JsonUtil.JsonFromSystemParameters(items.getDOPKCS11URL());
            String SMTPSSLS = JsonUtil.JsonFromSystemParameters(items.getSMTPSSLS());
            String SMTPUSSMTPPASSWORDER = JsonUtil.JsonFromSystemParameters(items.getSMTPPASSWORD());
            String SMTPUSER = JsonUtil.JsonFromSystemParameters(items.getSMTPUSER());
            String SMTPANONIMOUS = JsonUtil.JsonFromSystemParameters(items.getSMTPANONIMOUS());
            String SMTPPORT = JsonUtil.JsonFromSystemParameters(items.getSMTPPORT());
            String SMTPSERVER = JsonUtil.JsonFromSystemParameters(items.getSMTPSERVER());
            String EMAILSTORAGEDAYS = JsonUtil.JsonFromSystemParameters(items.getEMAILSTORAGEDAYS());
            String FILESIZEMAX = JsonUtil.JsonFromSystemParameters(items.getFILESIZEMAX());
            String FILEEXTENSIONS = JsonUtil.JsonFromSystemParameters(items.getFILEEXTENSIONS());
            String CRYPTOPASSWORD = JsonUtil.JsonFromSystemParameters(items.getCRYPTOPASSWORD());
            String CRYPTOURL = JsonUtil.JsonFromSystemParameters(items.getCRYPTOURL());
            String CRYPTOUSERNAME = JsonUtil.JsonFromSystemParameters(items.getCRYPTOUSERNAME());
            String HSMKEYPASSWORD = JsonUtil.JsonFromSystemParameters(items.getHSMKEYPASSWORD());
            String HSMKEYURL = JsonUtil.JsonFromSystemParameters(items.getHSMKEYURL());
            String HSMKEYUSERNAME = JsonUtil.JsonFromSystemParameters(items.getHSMKEYUSERNAME());
            String SIGNEDALGORITHM = JsonUtil.JsonFromSystemParameters(items.getSIGNEDALGORITHM());
            String EVENTDAYS = JsonUtil.JsonFromSystemParameters(items.getEVENTDAYS());
            String DOPKCS1URL = JsonUtil.JsonFromSystemParameters(items.getDOPKCS1URL());
            String DOPKCS1USERNAME = JsonUtil.JsonFromSystemParameters(items.getDOPKCS1USERNAME());
            String DOPKCS1PASSWORD = JsonUtil.JsonFromSystemParameters(items.getDOPKCS1PASSWORD());
            String DOPKCS7URL = JsonUtil.JsonFromSystemParameters(items.getDOPKCS7URL());
            String DOPKCS7USERNAME = JsonUtil.JsonFromSystemParameters(items.getDOPKCS7USERNAME());
            String DOPKCS7PASSWORD = JsonUtil.JsonFromSystemParameters(items.getDOPKCS7PASSWORD());
            String FROMEMAIL = JsonUtil.JsonFromSystemParameters(items.getFROMEMAIL());
            String ASPOSELIC = JsonUtil.JsonFromSystemParameters(items.getASPOSELIC());
            String EMAILTEMPLATE = JsonUtil.JsonFromSystemParameters(items.getEMAILTEMPLATE());
            String FILESIZEZIP = JsonUtil.JsonFromSystemParameters(items.getFILESIZEZIP());
            String FILESIZEZIPDAYS = JsonUtil.JsonFromSystemParameters(items.getFILESIZEZIPDAYS());
            String URLUSERCERT = JsonUtil.JsonFromSystemParameters(items.getURLUSERCERT());
            String res = "[" + URLUSERCERT + "," + FILESIZEZIPDAYS + "," + FILESIZEZIP + "," + EMAILTEMPLATE + "," + ASPOSELIC + "," + CRYPTOPASSWORD + "," + CRYPTOURL + "," + CRYPTOUSERNAME + ","
                    + STORAGETIME + "," + ORGANIZATION + "," + KEYAGENT + ","
                    + SEGURIBOXAPIPASSWORD + "," + SEGURIBOXAPIUSERNAME + "," + SEGURIBOXAPIURL + ","
                    + SEGURISERVERPASSWORD + "," + SEGURISERVERUSERNAME + "," + SEGURISERVERURL + ","
                    + SEGURISIGNPASSWORD + "," + SEGURISIGNUSERNAME + "," + SEGURISIGNURL + ","
                    + ARCHIVEPASSWORD + "," + ARCHIVEUSERNAME + "," + ARCHIVEURL + ","
                    + DOPKCS11PASSWORD + "," + DOPKCS11USERNAME + "," + DOPKCS11URL + ","
                    + SMTPSSLS + "," + SMTPUSSMTPPASSWORDER + "," + SMTPUSER + ","
                    + SMTPANONIMOUS + "," + SMTPPORT + "," + SMTPSERVER + ","
                    + EMAILSTORAGEDAYS + "," + FILESIZEMAX + "," + FILEEXTENSIONS + ","
                    + HSMKEYPASSWORD + "," + HSMKEYURL + "," + EVENTDAYS + "," + DOPKCS1URL + "," + DOPKCS1USERNAME + ","
                    + HSMKEYUSERNAME + "," + DOPKCS1PASSWORD + "," + DOPKCS7URL + "," + DOPKCS7USERNAME + "," + DOPKCS7PASSWORD + "," + SIGNEDALGORITHM + "," + FROMEMAIL + "]";

            String path = MessageFormat.format("systemparameter/{0}", getSessionId());

            String jsonResult = wsUtil.ExecutePostRestService(trace, BASE_URI, path, res);
            items = findAll();
            JsfUtil.addSuccessMessage(jsonResult);
        } catch (Exception e) {
            items = findAll();
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    /**
     *Método que obtiene los registros de HSM activos de la base de datos.
     * @return lista de HSMkey
     */
    public List<HSMKey> findAllHSM() {
        try {
            String path = MessageFormat.format("hsmkey/active/{0}", getSessionId());
            String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);
            return convertListHSM(jsonResult);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }
    }

    /**
     *Método que obtiene una lista de algoritmos de firma.
     * @return
     */
    public List<AlgorithmSignSymm> findAllAlgorithms() {
        try {
            String path = MessageFormat.format("algorithmsignsymm/{0}", getSessionId());
            String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);
            return convertListAlgorithms(jsonResult);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return algorithms;
        }
    }

    /**
     *Método que convierte la lista de HSMkey en formato JSON en una lista de objetos del tipo HSMKey
     * @param jsonListString lista en formato JSON.
     * @return lista de Hsmkey
     */
    private List<HSMKey> convertListHSM(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            HSMKey hsm = null;
            List<HSMKey> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                hsm = JsonUtil.listHSM(jsonList.get(i).toString());
                list.add(hsm);
            }
            return list;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }
    }

  /**
     *Método que convierte la lista de algoritmo de firma en formato JSON en una lista de objetos del tipo AlgorithmSignSymm
     * @param jsonListString lista en formato JSON.
     * @return lista de algoritmos de firma.
     */
    private List<AlgorithmSignSymm> convertListAlgorithms(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            AlgorithmSignSymm algorithm = null;
            List<AlgorithmSignSymm> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {

                algorithm = JsonUtil.listAlgorithm(jsonList.get(i).toString());
                list.add(algorithm);

            }
            return list;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }
    }

}
