package com.seguriboxltvweb.jsf;

import com.seguriboxltvweb.domain.Groups;
import com.seguriboxltvweb.domain.HSMKey;
import com.seguriboxltvweb.domain.ProfileProcess;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 * Clase que contiene toda la funcionalidad de perfiles de proceso
 *
 * @author Germán Hernández López
 * @version 1.0
 */
@Named("profileProcessController")
@SessionScoped
public class ProfileProcessController implements Serializable {

    private final WebTarget webTarget;
    private final WebTarget webTargetGroups;
    private final Client client;
    private final String BASE_URI;
    private boolean isCopy = false;
    private List<ProfileProcess> items = null;
    private List<Groups> itemsGroups = null;
    private List<HSMKey> itemsHSMKeysGroup = null;
    private List<HSMKey> itemsHSMKeysProfile = null;
    private ProfileProcess selected = new ProfileProcess();
    private ProfileProcess selectedToCopy;
    private String sessionId = "sessionid";
    private DualListModel<ProfileProcess> profileProcess;
    private DualListModel<HSMKey> HSMKeys = new DualListModel<>();
    private String txtTitle = "CREAR";
    private String txtButton;
    private boolean disableconvertion;
    private boolean disableprevious;
    private boolean disableSignatureType;
    private boolean isEdit = false;
    private final String sourceProfile;
    private final String sourceGroups;
    private List<Groups> profileGroups;

    /**
     * Constructor de la clase ProfileProcessController.
     *
     * @throws IOException
     */
    public ProfileProcessController() throws IOException {
        sessionId = SessionUtils.getSessionId();
        client = ClientBuilder.newClient();
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
        webTarget = client.target(BASE_URI).path("profileprocess");
        webTargetGroups = client.target(BASE_URI).path("groups");
        sourceGroups = "groups";
        sourceProfile = "profileprocess";

    }

    /**
     *
     * @return
     */
    public DualListModel<ProfileProcess> getProfileProcess() {
        return profileProcess;
    }

    /**
     *
     * @param profileProcess
     */
    public void setProfileProcess(DualListModel<ProfileProcess> profileProcess) {
        this.profileProcess = profileProcess;
    }

    /**
     *
     * @param HSMKeys
     */
    public void setHSMKeys(DualListModel<HSMKey> HSMKeys) {
        this.HSMKeys = HSMKeys;
    }

    /**
     * Método que redirige a la sección para crear un perfil de proceso y llena
     * una lista con los grupos.
     *
     * @return
     */
    public String goToCreate() {
        selected = new ProfileProcess();
        isCopy = false;
        setTxtButton("Agregar");
        setTxtTitle("AGREGAR");
        fillControls();
        profileGroups = findAllGroupsUser();
        return "adminprofileprocessedit?faces-redirect=true";
    }

    /**
     *
     * @return
     */
    public List<Groups> getProfileGroups() {

        if (profileGroups == null) {

            profileGroups = findAllGroupsUser();
        }

        return profileGroups;
    }

    /**
     *
     * @return
     */
    public boolean isDisableSignatureType() {
        return disableSignatureType;
    }

    /**
     *
     * @return
     */
    public DualListModel<HSMKey> getHSMKeys() {

        return HSMKeys;
    }

    /**
     *
     * @return
     */
    public boolean isDisableconvertion() {
        return disableconvertion;
    }

    /**
     *
     * @return
     */
    public boolean isDisableprevious() {
        return disableprevious;
    }

    /**
     *
     * @param txtTitle
     */
    public void setTxtTitle(String txtTitle) {
        this.txtTitle = txtTitle;
    }

    /**
     *
     * @param txtButton
     */
    public void setTxtButton(String txtButton) {
        this.txtButton = txtButton;
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
    public ProfileProcess getSelected() {
        return selected;
    }

    /**
     *
     * @param selected
     */
    public void setSelected(ProfileProcess selected) {
        this.selected = selected;
    }

    /**
     * Método que carga la lista de perfiles de proceso y limpia el formulario.
     */
    public void fillControls() {

        items = findAll();
        selected.setPreviousSignatureType((short) 0);
        selected.setSignatureType((short) 0);
        selected.setProfileName("");
        selected.setConversionType((short) 0);
        selected.setStorageTime((short) 0);
        disableSignatureType = false;
        disableconvertion = false;
        disableprevious = false;
    }

    /**
     * Método que carga la lista de perfiles de proceso y te redirige a la
     * sección de perfiles de proceso.
     *
     * @return outcome que redirige a la página adminprofileprocesses.jsf.
     */
    public String goToProfileProcess() {

        try {
            items = findAll();
            selected.setPreviousSignatureType((short) 0);
            selected.setSignatureType((short) 0);
            selected.setProfileName("");
            selected.setConversionType((short) 0);
            selected.setStorageTime((short) 0);
            disableSignatureType = false;
            disableconvertion = false;
            disableprevious = false;
            return "adminprofileprocesses?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }
    }

    /**
     * Método que copia un perfil de proceso, y actualiza la tabla de perfiles
     * de proceso.
     *
     * @param item perfil de proceso a copiar
     * @throws Exception
     */
    public void goToCopy(ProfileProcess item) throws Exception {

        try {

            copy(item);
            items = findAll();

        } catch (Exception e) {
            items = findAll();
            JsfUtil.addErrorMessage(e.getMessage());
        }

    }

    /**
     * Metodo que edita un perfil de proceso y actualiza la tabla.
     *
     * @param item perfil de proceso a copiar
     * @return
     */
    public String goToEdit(ProfileProcess item) {
        try {
            isEdit = true;
            setSelected(item);
            setTxtTitle("EDITAR");
            HSMKeys = new DualListModel<>(findHSMKeyGroup(), findHSMKeyProfile());

            return "adminprofileprocess?faces-redirect=true";

        } catch (Exception e) {
            HSMKeys = new DualListModel<>();
            return null;
        }

    }

    /**
     * Método que se ejecuta por AJAX por medio del pickList y se ejecuta al
     * agregar una llave al perfil de proceso
     *
     * @param event
     */
    public void ontransfer(TransferEvent event) {
        if (event.isAdd()) {
            for (Object item : event.getItems()) {
                try {
                    //
                    addhsmkeyProfileprocess((HSMKey) item);
                } catch (Exception e) {
                    JsfUtil.addErrorMessage(e.getMessage());
                }
            }
        }
        if (event.isRemove()) {

            for (Object item : event.getItems()) {
                try {
                    deletehsmkeyProfileprocess((HSMKey) item);
                } catch (Exception e) {
                    JsfUtil.addErrorMessage(e.getMessage());
                }
            }
        }
         HSMKeys = new DualListModel<>(findHSMKeyGroup(), findHSMKeyProfile());
    }

    /**
     * Método que ordena las llaves según su jerarquía de firmado.
     */
    public void onReorder() {

        List<HSMKey> target = HSMKeys.getTarget();
        int count = 1;
        for (HSMKey item : target) {
            int idhsmk = item.getHSMKeyId();
            setOrder(idhsmk, count);
            count++;
        }
        HSMKeys = new DualListModel<>(findHSMKeyGroup(), findHSMKeyProfile());

    }

    /**
     * Método que consume el servicio web para ordenar las llaves.
     *
     * @param hsmkeyId
     * @param index
     */
    public void setOrder(int hsmkeyId, int index) {

        try {
            String pid = selected.getProfileProcessId() + "";
            String hid = hsmkeyId + "";

            String path = MessageFormat.format("profileprocess/hsmkeysetorder/{0}/{1}/{2}/{3}", new Object[]{sessionId, pid, hid, index});
            String jsonResult = wsUtil.ExecutePutRestService(true, BASE_URI, path, "");

        } catch (Exception e) {

            JsfUtil.addErrorMessage(e.getMessage());
        }
//        
    }

    /**
     *
     */
    public void Change2() {

        switch (selected.getConversionType()) {
            case 0:

                disableprevious = false;
                disableSignatureType = false;
                break;

            case 1:

                selected.setPreviousSignatureType((short) 0);
                selected.setSignatureType((short) 1);
                disableprevious = true;
                disableSignatureType = true;

                break;
            case 2:
                selected.setSignatureType((short) 1);

                selected.setPreviousSignatureType((short) 0);
                disableprevious = true;
                disableSignatureType = true;

                break;
        }
    }

    /**
     *
     */
    public void Change() {

        switch (selected.getPreviousSignatureType()) {
            case 0:

                disableSignatureType = false;
                disableconvertion = false;

                break;
            case 1:
                disableSignatureType = true;
                disableconvertion = true;
                selected.setConversionType((short) 0);
                selected.setSignatureType((short) 0);
                break;
            case 2:
                disableSignatureType = true;
                disableconvertion = true;
                selected.setConversionType((short) 0);
                selected.setSignatureType((short) 0);

                break;
            case 3:
                disableSignatureType = true;
                disableconvertion = true;
                selected.setConversionType((short) 0);
                selected.setSignatureType((short) 0);

                break;

        }
    }

    /**
     * Método que devuelve una lista de grupos en base al tipo de perfil.
     *
     * @return lista de grupos
     */
    public List<Groups> findAllGroupsUser() {
        try {
            String id = "2";
            String path = MessageFormat.format(sourceGroups + "/profiletype/{0}/{1}", new Object[]{getSessionId(), id});
            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return convertToListGroupsProfile(jsonListString);

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return new ArrayList();
        }
    }

    /**
     * Método que convierte una lista de grupos en formato JSON a una lista de
     * objetos del tipo Group
     *
     * @param jsonListString Lista en formato JSON
     * @return lista de grupos.
     */
    private List<Groups> convertToListGroupsProfile(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<Groups> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                Groups item = JsonUtil.GroupsFromJson(jsonList.get(i).toString());
                list.add(item);
            }
            return list;
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

    /**
     * Método que agrega una llave HSM al perfil de proceso seleccionado.
     *
     * @param hsmkey ha agregar
     */
    public void addhsmkeyProfileprocess(HSMKey hsmkey) {

        try {
            String pid = selected.getProfileProcessId() + "";
            String hid = hsmkey.getHSMKeyId() + "";
            //String hid=selected.get
            String path = MessageFormat.format(sourceProfile + "/addhsmkey/{0}/{1}/{2}", new Object[]{sessionId, pid, hid});
            wsUtil.ExecutePutRestService(true, BASE_URI, path, "");

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/ProfileProcess").getString("AddHsm"));

        } catch (Exception e) {
            HSMKeys = new DualListModel<>(findHSMKeyGroup(), findHSMKeyProfile());
            JsfUtil.addErrorMessage(e.getMessage());
        }

    }

    /**
     * Método que elimina una llave del perfil de proceso seleccionado.
     *
     * @param hsmkey a eliminar del perfil.
     */
    public void deletehsmkeyProfileprocess(HSMKey hsmkey) {

        try {
            String pid = selected.getProfileProcessId() + "";
            String hid = hsmkey.getHSMKeyId() + "";

            String path = MessageFormat.format(sourceProfile + "/delhsmkey/{0}/{1}/{2}", new Object[]{sessionId, pid, hid});
            wsUtil.ExecuteDeleteRestService(true, BASE_URI, path);

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/ProfileProcess").getString("RemoveHSMKey"));

        } catch (Exception e) {
            HSMKeys = new DualListModel<>(findHSMKeyGroup(), findHSMKeyProfile());
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    /**
     * Método que asigna la lista de perfiles de proceso a la variable items.
     *
     * @return
     */
    public List<ProfileProcess> getItems() {
        if (items == null) {
            items = findAll();
        }
        return items;
    }

    /**
     * Método que obtiene todos los registros de perfiles de proceso de la base
     * de datos.
     *
     * @return
     */
    public List<ProfileProcess> findAll() {
        try {

            String path = MessageFormat.format(sourceProfile + "/{0}", new Object[]{sessionId});

            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);
            return convertToList(jsonListString);
        } catch (Exception e) {

            JsfUtil.addErrorMessage(e.getMessage());
            return new ArrayList();
        }
    }

    /**
     * Método que obtiene una lista de registros de llaves HSMkey que estan
     * asignadas al perfil de proceso en base a su id
     *
     * @return lista de hsmkey
     */
    public List<HSMKey> findHSMKeyProfile() {
        try {
            String profileId = selected.getProfileProcessId() + "";

            String path = MessageFormat.format("profileprocess/profilehsmkeyselected/{0}/{1}", new Object[]{sessionId, profileId});
            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);
            return convertToListHSM(jsonListString);
        } catch (Exception e) {
            
            return new ArrayList();
        }
    }

    /**
     * Metodo que obtiene todos los registros de HSMkey disponibles para agregar
     *
     * @return lista de HSMkey
     */
    public List<HSMKey> findHSMKeyGroup() {
        try {
            String profileId = selected.getProfileProcessId() + "";
            String groupId = selected.getGroupId() + "";
            
            String path = MessageFormat.format("profileprocess/profilehsmkeyavailable/{0}/{1}/{2}", new Object[]{sessionId, profileId, groupId});
            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return convertToListHSM(jsonListString);
        } catch (Exception e) {
            
            return new ArrayList();
        }
    }

    /**
     * Método que convierte una lista de perfiles de proceso en formato JSON en
     * una lista de objetos del tipo ProfileProcess.
     *
     * @param jsonListString lista de perfiles de proceso en JSON
     * @return lista de perfiles de proceso
     */
    private List<ProfileProcess> convertToList(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<ProfileProcess> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                ProfileProcess item = JsonUtil.ProfileProcessFromJson(jsonList.get(i).toString());
                list.add(item);
            }
            return list;
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

    /**
     * Método que convierte una lista de HSMkey en formato JSON en una lista de
     * objetos del tipo HSMKey.
     *
     * @param jsonListString lista de HSMkey en JSON
     * @return lista de HSMkey.
     */
    private List<HSMKey> convertToListHSM(String jsonListString) {
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
     * Método que elimina un perfil de proceso de la base de datos.
     *
     * @param selected perfil de proceso a eliminar.
     */
    public void remove(ProfileProcess selected) {
        String idString = selected.getProfileProcessId().toString();

        try {
            Response response = webTarget.path(java.text.MessageFormat.format("{0}/{1}",
                    new Object[]{sessionId, idString})).request().delete();
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                items = findAll();
                throw new Exception(response.readEntity(String.class));
            } else {
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/ProfileProcess").getString("ProfileProcessDeleted"));
                items = findAll();

            }
        } catch (Exception e) {
            items = findAll();

            JsfUtil.addErrorMessage(e.getMessage());

        }

    }

    /**
     * Método para copiar un perfil de proceso
     *
     * @param selected perfil de proceso a copiar
     * @throws Exception
     */
    public void copy(ProfileProcess selected) throws Exception {

        try {
            String id = selected.getProfileProcessId().toString();

            String path = MessageFormat.format(sourceProfile + "/copy/{0}/{1}", new Object[]{sessionId, id});
            wsUtil.ExecutePutRestService(true, BASE_URI, path, "");

            items = findAll();
            setSelected(new ProfileProcess());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/ProfileProcess").getString("ProfileProcessCopy"));

        } catch (Exception e) {
            items = findAll();

            JsfUtil.addErrorMessage(e.getMessage());
        }

    }

    /**
     * Método para insertar un perfil de proceso
     *
     * @param selected perfil de proceso a insertar
     * @return outcome a la página adminprofileprocesses si el insertado fue
     * correcto.
     */
    public String insert(ProfileProcess selected) {

        String str = null;
        try {
            if (selected.getStorageTime() == 0) {

                throw new Exception("El periodo de retención debe ser igual o mayor a 1 mes");
            }
            if (selected.getPreviousSignatureType() == 0 && selected.getConversionType() == 0 && selected.getSignatureType() == 0) {

                throw new Exception("El tipo de firma a aplicar debe ser diferente a ninguna");
            }
            String res = JsonUtil.JsonFromProfileProcess(selected);
            String path = MessageFormat.format(sourceProfile + "/{0}", new Object[]{sessionId});
            wsUtil.ExecutePostRestService(true, BASE_URI, path, res);

            items = findAll();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/ProfileProcess").getString("ProfileProcessCreated"));
            setSelected(new ProfileProcess());
            str = "adminprofileprocesses?faces-redirect=true";

        } catch (Exception e) {
            items = findAll();

            JsfUtil.addErrorMessage(e.getMessage());
            str = null;
        }

        return str;
    }

    /**
     * Método para editar un perfil de proceso.
     *
     * @param selected perfil de proceso a editar.
     * @return outcome a la página adminprofileprocesses si el insertado fue
     * correcto.
     * @throws Exception
     */
    public String update(ProfileProcess selected) throws Exception {

        String str = null;
        try {
            String res = JsonUtil.JsonFromProfileProcess(selected);
            String path = MessageFormat.format(sourceProfile + "/{0}", new Object[]{sessionId});
            wsUtil.ExecutePutRestService(true, BASE_URI, path, res);

            items = findAll();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/ProfileProcess").getString("ProfileProcessUpdated"));
            setSelected(new ProfileProcess());
            str = "adminprofileprocesses?faces-redirect=true";

        } catch (Exception e) {
            items = findAll();

            JsfUtil.addErrorMessage(e.getMessage());
            str = null;
        }

        return str;
    }
    
    public String storageTimeToString(Short time) {
        String timeString;
        if(time == 1) {
            timeString = "1 mes";
        } else {
            timeString = time + " meses";
        }
        return timeString;
    }
    
    public String findGroupInList(Short i) {
        List<Groups> grupos = getProfileGroups();
        String groupName = "";
        for(Groups g : grupos) {
            if(g.getGroupId() == i) {
                groupName = g.getGroupName();
            }
        }
        return groupName;
    }

    public String conversionTypeToString(Short i){
        String conversion = "";
        if(i == 0) {
            conversion = "Sin conversión";
        } else if(i == 1) {
            conversion = "Office a PDF/A";
        } else if(i == 2) {
            conversion = "PDF/A";
        }
        return conversion;
    }
    
    public String signatureTypeToString(Short i){
        String conversion = "";
        if(i == 0) {
            conversion = "Ninguna";
        } else if(i == 1) {
            conversion = "PDF";
        } else if(i == 2) {
            conversion = "CMS";
        } else if(i == 4) {
            conversion = "XMLSignature";
        }
        return conversion;
    }
}