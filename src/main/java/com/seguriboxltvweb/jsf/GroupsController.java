package com.seguriboxltvweb.jsf;

import com.seguriboxltvweb.domain.Groups;
import com.seguriboxltvweb.domain.HSMKey;
import com.seguriboxltvweb.domain.ProfileProcess;
import com.seguriboxltvweb.domain.Task;
import com.seguriboxltvweb.domain.Users;
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
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
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
 *Clase que contiene toda la funcionalidad de grupos
 * @author German Hernandez Lopez.
 */
@Named("groupsController")
@SessionScoped
public class GroupsController implements Serializable {

    private WebTarget webTarget;
    private Client client;
    private String txtTitle = "Agregar";
    private String txtButton = "Agregar";
    private String BASE_URI;
    private List<Groups> items = null;
    private Groups selected;
    private DualListModel<Users> users;
    private DualListModel<Task> task;
    private DualListModel<ProfileProcess> profileProcess;
    private DualListModel<HSMKey> hsmkey;
    private boolean isEdit = false;
    private String sessionId;
    private short groupId;
    private final String sourceGroups;
    UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();

    /**
     * Constructor de la clase GroupsController
     * @throws IOException 
     */
    public GroupsController() throws IOException {
        sessionId = SessionUtils.getSessionId();
        client = ClientBuilder.newClient();
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
        webTarget = client.target(BASE_URI).path("groups");
        sourceGroups = "groups";
    }

    @PostConstruct
    public void init() {

        items = findAll();
    }

    public void setUsers(DualListModel<Users> users) {
        this.users = users;
    }

    public void setTask(DualListModel<Task> task) {
        this.task = task;
    }

    public void setProfileProcess(DualListModel<ProfileProcess> profileProcess) {
        this.profileProcess = profileProcess;
    }

    public void setHsmkey(DualListModel<HSMKey> hsmkey) {
        this.hsmkey = hsmkey;
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
    public DualListModel<Users> getUsers() {
        return users;
    }

    /**
     *
     * @return
     */
    public DualListModel<Task> getTask() {
        return task;
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
     * @return
     */
    public DualListModel<HSMKey> getHsmkey() {
        return hsmkey;
    }

    /**
     *
     * @param isEdit
     */
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    /**
     *
     * @return
     */
    public Groups getSelected() {
        return selected;
    }

    /**
     *
     * @param selected
     */
    public void setSelected(Groups selected) {
        this.selected = selected;
    }

    /**
     *Método que te lleva a la sección de grupos
     * @return outcome que redirige a la página admingroups.jsf.
     */
    public String goToGroups() {

        try {
            items = findAll();
            return "admingroups?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return "";
        }
    }

    /**
     * Método que redirige a la sección de edición de grupos
     *
     * @param item elemento a editar
     * @return outcome que redirige a la página admingroup.jsf.
     */
    public String goToEdit(Groups item) {

        setSelected(item);
        setIsEdit(true);
        setTxtTitle("EDITAR");
        users = new DualListModel<>(userAvailable(), userSelected());
        task = new DualListModel<>(taskAvailable(), taskSelected());
        profileProcess = new DualListModel<>(profileProcessAvailable(), profileProcessSelected());
        hsmkey = new DualListModel<>(HSMKeyAvailable(), HSMKeySelected());
        return "admingroup?faces-redirect=true";
    }

    /**
     * Método que redirige a la sección de creado y asigna valores a las
     * variables txtTitle,txtButton y editar.
     *
     * @return outcome que redirige a la página admingroupedit.jsf.
     */
    public String goToCreate() {
        setIsEdit(false);
        setTxtTitle("AGREGAR");
        setTxtButton("Agregar");
        setSelected(new Groups());
        return "admingroupedit?faces-redirect=true";
    }

    /**
     * Método que asigna la lista de grupos obtenida a la variable items.
     *
     * @return lista de grupos.
     */
    public List<Groups> getItems() {
        if (items == null) {
            items = findAll();
        }
        return items;
    }

    /**
     * Método que devuelve una lista de grupos a donde pertenezca el usuario que
     * inicio sesión este método consume un web service rest mediante el método
     * GET y procesa los datos obtenidos.
     *
     * @return
     */
    public List<Users> userAvailable() {

        try {
            String id = selected.getGroupId().toString();
            String path = MessageFormat.format(sourceGroups + "/groupuseravailable/{0}/{1}", new Object[]{getSessionId(), id});

            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return convertToListUsers(jsonListString);

        } catch (Exception e) {
            if (e.getMessage().contains("No hay registros en la lista")) {

            } else {

                JsfUtil.addErrorMessage(e.getMessage());
            }

            return new ArrayList();
        }
    }

    /**
     * Método que devuelve una lista de usuarios seleccionados pertenecientes al
     * un grupo determinado, este se ocupa para llenar el pickList del lado del
     * front en el apartado de editar grupos. se consume un web service rest y
     * se procesan los datos obtenidos.
     *
     * @return lista de grupos
     */
    public List<Users> userSelected() {

        try {
            String id = selected.getGroupId().toString();
            String path = MessageFormat.format(sourceGroups + "/groupuserselected/{0}/{1}", new Object[]{getSessionId(), id});
            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return convertToListUsers(jsonListString);

        } catch (Exception e) {
            if (e.getMessage().contains("No hay registros en la lista")) {

            } else {

                JsfUtil.addErrorMessage(e.getMessage());
            }
            return new ArrayList();
        }
    }

    /**
     * Método que devuelve una lista de tareas disponibles pertenecientes al un
     * grupo determinado, este se ocupa para llenar el pickList del lado del
     * front en el apartado de editar grupos. se consume un web service rest y
     * se procesan los datos obtenidos.
     *
     * @return lista de tareas
     */
    public List<Task> taskAvailable() {

        try {
            String id = selected.getGroupId().toString();
            String path = MessageFormat.format(sourceGroups + "/grouptaskavailable/{0}/{1}", new Object[]{getSessionId(), id});
            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return convertToListTask(jsonListString);

        } catch (Exception e) {
            if (e.getMessage().contains("No hay registros en la lista")) {

            } else {

                JsfUtil.addErrorMessage(e.getMessage());
            }
            return new ArrayList();
        }
    }

    /**
     * Método que devuelve una lista de tareas seleccionados pertenecientes al
     * un grupo determinado, este se ocupa para llenar el pickList del lado del
     * front en el apartado de editar grupos. se consume un web service rest y
     * se procesan los datos obtenidos.
     *
     * @return lista de tareas
     */
    public List<Task> taskSelected() {
        try {
            String id = selected.getGroupId().toString();
            String path = MessageFormat.format(sourceGroups + "/grouptaskselected/{0}/{1}", new Object[]{getSessionId(), id});
            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return convertToListTask(jsonListString);
        } catch (Exception e) {
            if (e.getMessage().contains("No hay registros en la lista")) {

            } else {

                JsfUtil.addErrorMessage(e.getMessage());
            }
            return new ArrayList();
        }
    }

    /**
     * Método que devuelve una lista de HSMkeys disponibles pertenecientes al un
     * grupo determinado, este se ocupa para llenar el pickList del lado del
     * front en el apartado de editar grupos. se consume un web service rest y
     * se procesan los datos obtenidos.
     *
     * @return lista de HSMkeys
     */
    public List<HSMKey> HSMKeyAvailable() {

        try {
            String id = selected.getGroupId().toString();
            String path = MessageFormat.format(sourceGroups + "/grouphsmkeysavailable/{0}/{1}", new Object[]{getSessionId(), id});
            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return convertToListHSMKey(jsonListString);

        } catch (Exception e) {

            if (e.getMessage().contains("No hay registros en la lista")) {

            } else {

                JsfUtil.addErrorMessage(e.getMessage());
            }
            return new ArrayList();
        }
    }

    /**
     * Método que devuelve una lista de HSMkeys seleccionados pertenecientes al
     * un grupo determinado, este se ocupa para llenar el pickList del lado del
     * front en el apartado de editar grupos. se consume un web service rest y
     * se procesan los datos obtenidos.
     *
     * @return lista de HSMkeys
     */
    public List<HSMKey> HSMKeySelected() {

        try {
            String id = selected.getGroupId().toString();
            String path = MessageFormat.format(sourceGroups + "/grouphsmkeyselected/{0}/{1}", new Object[]{getSessionId(), id});
            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return convertToListHSMKey(jsonListString);

        } catch (Exception e) {

            if (e.getMessage().contains("No hay registros en la lista")) {

            } else {

                JsfUtil.addErrorMessage(e.getMessage());
            }
            return new ArrayList();
        }
    }

    /**
     * Método que devuelve una lista de perfiles de procesos disponibles
     * pertenecientes al un grupo determinado, este se ocupa para llenar el
     * pickList del lado del front en el apartado de editar grupos. se consume
     * un web service rest y se procesan los datos obtenidos.
     *
     * @return lista de perfiles de proceso
     */
    public List<ProfileProcess> profileProcessAvailable() {

        try {
            String id = selected.getGroupId().toString();
            String path = MessageFormat.format(sourceGroups + "/groupprofileprocessavailable/{0}/{1}", new Object[]{getSessionId(), id});
            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return convertToListProfileProcess(jsonListString);

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return new ArrayList();
        }
    }

    /**
     * Método que devuelve una lista de perfiles de proceso seleccionados
     * pertenecientes al un grupo determinado, este se ocupa para llenar el
     * pickList del lado del front en el apartado de editar grupos. se consume
     * un web service rest y se procesan los datos obtenidos.
     *
     * @return lista de perfiles de proceso.
     */
    public List<ProfileProcess> profileProcessSelected() {

        try {
            String id = selected.getGroupId().toString();
            String path = MessageFormat.format(sourceGroups + "/groupprofileprocesselected/{0}/{1}", new Object[]{getSessionId(), id});
            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return convertToListProfileProcess(jsonListString);

        } catch (Exception e) {

            if (e.getMessage().contains("No hay registros en la lista")) {

            } else {

                JsfUtil.addErrorMessage(e.getMessage());
            }
            return new ArrayList();
        }
    }

    /**
     * Método que retorna una lista de todos los grupos disponibles en base de
     * datos, este método consume un web service rest mediante el método GET y
     * procesa los datos obtenidos.
     *
     * @return lista de grupos
     */
    public List<Groups> findAll() {
        try {
            String path = MessageFormat.format(sourceGroups + "/{0}", new Object[]{getSessionId()});
            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

            return convertToList(jsonListString);

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return new ArrayList();
        }
    }

    /**
     * Método que convierte la lista de grupos en formato JSON obtenido a una
     * lista de objetos de Groups.
     *
     * @param jsonListString Lista en formato JSON de grupos
     * @return lista de grupos.
     */
    private List<Groups> convertToList(String jsonListString) {
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
     * Método que convierte la lista de usuarios en formato JSON obtenido a una
     * lista de objetos de Users.
     *
     * @param jsonListString Lista en formato JSON de users
     * @return lista de users.
     */
    private List<Users> convertToListUsers(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<Users> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                Users item = JsonUtil.UsersFromJson(jsonList.get(i).toString());
                list.add(item);
            }
            return list;
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

    /**
     * Método que convierte la lista de tareas en formato JSON obtenido a una
     * lista de objetos de Task.
     *
     * @param jsonListString Lista en formato JSON de tareas
     * @return lista de tareas.
     */
    private List<Task> convertToListTask(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<Task> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                Task item = JsonUtil.TaskFromJson(jsonList.get(i).toString());
                list.add(item);
            }
            return list;
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

    /**
     * Método que convierte la lista de perfiles de proceso en formato JSON
     * obtenido a una lista de objetos de ProfileProcess.
     *
     * @param jsonListString Lista en formato JSON de perfiles de procesos.
     * @return lista de perfiles de proceso.
     */
    private List<ProfileProcess> convertToListProfileProcess(String jsonListString) {
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
     * Método que convierte la lista de HSMKey en formato JSON obtenido a una
     * lista de objetos de HSMKey.
     *
     * @param jsonListString Lista en formato JSON de HSMKey.
     * @return lista de HSMKey.
     */
    private List<HSMKey> convertToListHSMKey(String jsonListString) {
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
     *Método que agrega una Llave HSM al grupo.
     * @param group grupo a agregar llave
     * @param hsmkeys llave a agregar
     * @throws Exception
     */
    public void groupHsmkey(Groups group, HSMKey hsmkeys) throws Exception {
        try {

            String idGroup = group.getGroupId().toString();
            String idHsm = hsmkeys.getHSMKeyId().toString();
            String path = MessageFormat.format(sourceGroups + "/addhsmkey/{0}/{1}/{2}", new Object[]{sessionId, idGroup, idHsm});

            wsUtil.ExecutePutRestService(true, BASE_URI, path, "");

            hsmkey = new DualListModel<>(HSMKeyAvailable(), HSMKeySelected());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Groups").getString("AddHsm"));

        } catch (Exception e) {
            hsmkey = new DualListModel<>(HSMKeyAvailable(), HSMKeySelected());
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    /**
     * Método que agrega una nueva tarea al grupo, se agrega desde el pickList
     * del front.
     *
     * @param group recibe el objeto grupo para obtener su id
     * @param tasks recibe el objeto Hsmkey para obtener su id
     * @throws Exception
     */
    public void groupTask(Groups group, Task tasks) throws Exception {
        try {
            String idGroup = group.getGroupId().toString();
            String idTask = tasks.getTaskId().toString();
            String path = MessageFormat.format(sourceGroups + "/addtask/{0}/{1}/{2}", new Object[]{sessionId, idGroup, idTask});
            wsUtil.ExecutePutRestService(true, BASE_URI, path, "");

            task = new DualListModel<>(taskAvailable(), taskSelected());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Groups").getString("AddTask"));

        } catch (Exception e) {
            task = new DualListModel<>(taskAvailable(), taskSelected());
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    /**
     * Método que agrega una nueva tarea al grupo, se agrega desde el pickList
     * del front.
     *
     * @param group recibe el objeto grupo para obtener su id
     * @param profile recibe el objeto profile para obtener su id
     * @throws Exception
     */
    public void groupProfileProcess(Groups group, ProfileProcess profile) throws Exception {

        try {
            String idGroup = group.getGroupId().toString();
            String idProfileProcess = profile.getProfileProcessId().toString();
            String path = MessageFormat.format(sourceGroups + "/addprofile/{0}/{1}/{2}", new Object[]{sessionId, idGroup, idProfileProcess});
            wsUtil.ExecutePutRestService(true, BASE_URI, path, "");

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Groups").getString("Add"));

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    /**
     * Método que agrega una nueva tarea al grupo, se agrega desde el pickList
     * del front.
     *
     * @param group recibe el objeto grupo para obtener su id
     * @param user recibe el objeto user para obtener su id
     * @throws Exception
     */
    public void groupUsers(Groups group, Users user) throws Exception {

        String groupid = group.getGroupId().toString();
        String UserId = user.getUserId().toString();
        try {
            String path = MessageFormat.format(sourceGroups + "/adduser/{0}/{1}/{2}", new Object[]{sessionId, groupid, UserId});
            wsUtil.ExecutePutRestService(true, BASE_URI, path, "");

            items = findAll();
            users = new DualListModel<>(userAvailable(), userSelected());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Groups").getString("AddUser"));

        } catch (Exception e) {
            users = new DualListModel<>(userAvailable(), userSelected());
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    /**
     * Método que elimina la tarea del grupo, se elimina desde el pickList del
     * front.
     *
     * @param selected recibe el objeto grupo para obtener su id
     * @param tasks recibe el objeto task para obtener su id
     *
     */
    public void removeTask(Groups selected, Task tasks) {
        String idGroup = selected.getGroupId() + "";
        String idTask = tasks.getTaskId() + "";
        try {
            String path = MessageFormat.format(sourceGroups + "/deltask/{0}/{1}/{2}", new Object[]{sessionId, idGroup, idTask});
            wsUtil.ExecuteDeleteRestService(true, BASE_URI, path);

            items = findAll();
            task = new DualListModel<>(taskAvailable(), taskSelected());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Groups").getString("RemoveTask"));

        } catch (Exception e) {
            task = new DualListModel<>(taskAvailable(), taskSelected());
            JsfUtil.addErrorMessage(e.getMessage());

        }
    }

    /**
     * Método que elimina la tarea del grupo, se elimina desde el pickList del
     * front.
     *
     * @param selected recibe el objeto grupo para obtener su id
     * @param hsmkeys recibe el objeto HSMKey para obtener su id
     *
     */
    public void removeHSMKey(Groups selected, HSMKey hsmkeys) {
        String idGroup = selected.getGroupId() + "";
        String idhsmkey = hsmkeys.getHSMKeyId() + "";
        try {
            String path = MessageFormat.format(sourceGroups + "/delhsmkey/{0}/{1}/{2}", new Object[]{sessionId, idGroup, idhsmkey});
            wsUtil.ExecuteDeleteRestService(true, BASE_URI, path);

            items = findAll();
            hsmkey = new DualListModel<>(HSMKeyAvailable(), HSMKeySelected());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Groups").getString("RemoveHSMKey"));

        } catch (Exception e) {
            hsmkey = new DualListModel<>(HSMKeyAvailable(), HSMKeySelected());
            JsfUtil.addErrorMessage(e.getMessage());

        }
    }

    /**
     * Método que elimina el perfile de proceso del grupo, se elimina desde el
     * pickList del front.
     *
     * @param selected recibe el objeto grupo para obtener su id
     * @param profile recibe el objeto ProfileProcess para obtener su id
     *
     */
    public void removeProfileProcess(Groups selected, ProfileProcess profile) {
        String idGroup = selected.getGroupId() + "";
        String idhsmkey = profile.getProfileProcessId() + "";
        try {
            String path = MessageFormat.format("delprofile/{0}/{1}/{2}", new Object[]{sessionId, idGroup, idhsmkey});
            wsUtil.ExecuteDeleteRestService(true, BASE_URI, path);

            items = findAll();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Groups").getString("RemoveProfileProcess"));

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());

        }
    }

    /**
     * Método que elimina el usuario del grupo, se elimina desde el pickList del
     * front.
     *
     * @param selected recibe el objeto grupo para obtener su id
     * @param user recibe el objeto user para obtener su id
     *
     */
    public void removeUsers(Groups selected, Users user) {
        String idGroup = selected.getGroupId() + "";
        String idhsmkey = user.getUserId() + "";
        try {
            String path = MessageFormat.format(sourceGroups + "/deluser/{0}/{1}/{2}", new Object[]{sessionId, idGroup, idhsmkey});
            wsUtil.ExecuteDeleteRestService(true, BASE_URI, path);

            items = findAll();
            users = new DualListModel<>(userAvailable(), userSelected());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Groups").getString("RemoveUsers"));

        } catch (Exception e) {
            users = new DualListModel<>(userAvailable(), userSelected());
            JsfUtil.addErrorMessage(e.getMessage());

        }
    }

    /**
     * Método que elimina un grupo de la base de datos.
     *
     * @param selected grupos a eliminar
     */
    public void remove(Groups selected) {
        String idString = selected.getGroupId() + "";
        try {
            String path = MessageFormat.format(sourceGroups + "/{0}/{1}", new Object[]{sessionId, idString});
            wsUtil.ExecuteDeleteRestService(true, BASE_URI, path);
            items = findAll();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Groups").getString("GroupsDeleted"));

        } catch (Exception e) {
            if (e.getMessage().contains("REFERENCE constraint")) {
                items = findAll();
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Groups").getString("constrainDelete"));
            } else {
                items = findAll();
                JsfUtil.addErrorMessage(e.getMessage());
            }
        }
    }

    /**
     * Método que inserta un grupo a la base de datos.
     *
     * @param selected grupo a insertar
     * @return outcome que redirige a la página admingroups.jsf.
     * @throws Exception
     */
    public String insert(Groups selected) throws Exception {

        if (isEdit == false) {

            selected.setGroupId((short) 0);
        }
        String str = null;
        try {

            String res = JsonUtil.JsonFromGroups(selected);
            String path = MessageFormat.format(sourceGroups + "/{0}", new Object[]{sessionId});
            wsUtil.ExecutePostRestService(true, BASE_URI, path, res);
            setSelected(new Groups());
            items = findAll();
            if (isEdit == true) {
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Groups").getString("GroupsUpdated"));

            } else {
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Groups").getString("GroupsCreated"));
            }
            
            str = "admingroups?faces-redirect=true";

        } catch (Exception e) {
            items = findAll();

            JsfUtil.addErrorMessage(e.getMessage());
            str = null;
        }
        return str;
    }

    /**
     * Método que regresa un objeto del tipo user por su id.
     *
     * @param id del grupo que se quiero búsqueda.
     * @return
     */
    public Groups getGroups(java.lang.Short id) {
        try {

            Response response = webTarget.path(MessageFormat.format("{0}", new Object[]{id})).request(MediaType.APPLICATION_JSON + ";charset=utf-8").get(Response.class);
            String item = response.readEntity(String.class);
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                throw new Exception(response.readEntity(String.class));
            }
            return JsonUtil.GroupsFromJson(item);
        } catch (Exception e) {

            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }
    }

    /**
     * Método que se se llama mediante AJAX cuando se ejecuta el evento al
     * agregar un usuario al grupo mediante el pickList.
     *
     * @param event este parámetro recibe en evento que se ejecuta en el
     * pickList
     */
    public void onTransferUsers(TransferEvent event) {
        if (event.isAdd()) {
            for (Object item : event.getItems()) {
                try {
                    groupUsers(selected, (Users) item);

                } catch (Exception e) {
                    JsfUtil.addErrorMessage(e.getMessage());
                }
            }
        } else {

            for (Object item : event.getItems()) {
                try {
                    removeUsers(selected, (Users) item);
                } catch (Exception e) {
                    JsfUtil.addErrorMessage(e.getMessage());
                }
            }
        }

    }

    /**
     * Método que se se llama mediante AJAX cuando se ejecuta el evento al
     * agregar un HSMkey al grupo mediante el pickList.
     *
     * @param event este parámetro recibe en evento que se ejecuta en el
     * pickList
     */
    public void onTransferHsmkey(TransferEvent event) {
        if (event.isAdd()) {
            for (Object item : event.getItems()) {
                try {

                    groupHsmkey(selected, (HSMKey) item);
                } catch (Exception e) {
                    JsfUtil.addErrorMessage(e.getMessage());
                }
            }
        } else {

            for (Object item : event.getItems()) {
                try {
                    removeHSMKey(selected, (HSMKey) item);
                } catch (Exception e) {
                    JsfUtil.addErrorMessage(e.getMessage());
                }
            }
        }
    }

    /**
     * Método que se se llama mediante AJAX cuando se ejecuta el evento al
     * agregar un perfil de proceso al grupo mediante el pickList.
     *
     * @param event este parámetro recibe en evento que se ejecuta en el
     * pickList
     */
    public void onTransferProfileProcess(TransferEvent event) {

        if (event.isAdd()) {
            for (Object item : event.getItems()) {
                try {
                    groupProfileProcess(selected, (ProfileProcess) item);
                } catch (Exception e) {
                    JsfUtil.addErrorMessage(e.getMessage());
                }
            }
        } else {

            for (Object item : event.getItems()) {
                try {
                    removeProfileProcess(selected, (ProfileProcess) item);
                } catch (Exception e) {
                    JsfUtil.addErrorMessage(e.getMessage());
                }
            }
        }

    }

    /**
     * Método que se se llama mediante AJAX cuando se ejecuta el evento al
     * agregar una tarea al grupo mediante el pickList.
     *
     * @param event este parámetro recibe en evento que se ejecuta en el
     * pickList
     */
    public void onTransferProfileTask(TransferEvent event) {

        if (event.isAdd()) {
            for (Object item : event.getItems()) {
                try {
                    groupTask(selected, (Task) item);
                } catch (Exception e) {
                    JsfUtil.addErrorMessage(e.getMessage());
                }
            }
        } else {
            for (Object item : event.getItems()) {
                try {
                    removeTask(selected, (Task) item);
                } catch (Exception e) {
                    JsfUtil.addErrorMessage(e.getMessage());
                }
            }
        }
    }

//    @FacesConverter(forClass = Groups.class)
//    public static class GroupsControllerConverter implements Converter {
//
//        @Override
//        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
//            if (value == null || value.length() == 0) {
//                return null;
//            }
//            GroupsController controller = (GroupsController) facesContext.getApplication().getELResolver().
//                    getValue(facesContext.getELContext(), null, "groupsController");
//            return controller.getGroups(getKey(value));
//        }
//
//        java.lang.Short getKey(String value) {
//            java.lang.Short key;
//            key = Short.valueOf(value);
//            return key;
//        }
//
//        String getStringKey(java.lang.Short value) {
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
//            if (object instanceof Groups) {
//                Groups o = (Groups) object;
//                return getStringKey(o.getGroupId());
//            } else {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Groups.class.getName()});
//                return null;
//            }
//        }
//
//    }
}
