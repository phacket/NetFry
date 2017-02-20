package com.seguriboxltvweb.jsf;

import com.seguriboxltvweb.domain.UserLogReport;
import com.seguriboxltvweb.jsf.util.JsfUtil;
import com.seguriboxltvweb.jsf.util.JsonUtil;
import com.seguriboxltvweb.jsf.util.SeguriboxGetProperties;
import com.seguriboxltvweb.jsf.util.SessionUtils;
import static com.seguriboxltvweb.jsf.util.Util.toLong;
import com.seguriboxltvweb.jsf.util.wsUtil;
import java.io.IOException;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Clase que contiene toda la funcionalidad de log de repoorte de usuarios.
 *
 * @author Germán Hernández López
 * @version 1.0
 */
@Named("userLogReportControllerUser")
@SessionScoped
public class UserLogReportControllerUser implements Serializable {

    private final WebTarget webTarget;
    private final Client client;
    private List<UserLogReport> items = null;
    private UserLogReport selected;
    private UserLogReport userLog;
    private final String BASE_URI;
    private Date StartDateOld;
    private Date EndDateOld;
    private Date StartDate;
    private Date EndDate;
    private String Search;
    String SearchOld;
    private String sessionId;
    private boolean trace = true;

    @PostConstruct
    public void init() {
        items = null;
        StartDate = null;
        EndDate = null;
        Search = null;
    }

    /**
     *
     * @param StartDateOld
     */
    public void setStartDateOld(Date StartDateOld) {
        this.StartDateOld = StartDateOld;
    }

    /**
     *
     * @param EndDateOld
     */
    public void setEndDateOld(Date EndDateOld) {
        this.EndDateOld = EndDateOld;
    }

    /**
     *
     * @param StartDate
     */
    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
    }

    /**
     *
     * @param EndDate
     */
    public void setEndDate(Date EndDate) {
        this.EndDate = EndDate;
    }

    /**
     *
     * @param Search
     */
    public void setSearch(String Search) {
        this.Search = Search;
    }

    /**
     *
     * @param SearchOld
     */
    public void setSearchOld(String SearchOld) {
        this.SearchOld = SearchOld;
    }

    public void getItemsSearch() {
        items = search();

    }

    /**
     * Método que limpia la lista de log de usuarios.
     *
     * @return outcome que redirige a la página userloguser.jsf.
     * @throws IOException
     */
    public String fillControls() throws IOException {
        if (items != null) {
            this.items.clear();
        }
        StartDate = null;
        EndDate = null;
        Search = "";
        FacesContext.getCurrentInstance().getExternalContext().redirect("userloguser.jsf");
        return "userloguser?faces-redirect=true";
    }

    /**
     *
     * @return
     */
    public Date getStartDate() {
        return StartDate;
    }

    /**
     *
     * @return
     */
    public Date getEndDate() {
        return EndDate;
    }

    /**
     * Constructor de la clase UserLogReportControllerUser.
     *
     * @throws IOException
     */
    public UserLogReportControllerUser() throws IOException {
        sessionId = SessionUtils.getSessionId();
        client = ClientBuilder.newClient();
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
        webTarget = client.target(BASE_URI).path("userlogreport");
    }

    /**
     *
     * @return
     */
    public UserLogReport getUserLog() {
        return userLog;
    }

    /**
     *
     * @return
     */
    public String getSearch() {
        return Search;
    }

    /**
     *
     * @return
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Método que asigna a la variable items la lista de log de usuario.
     *
     * @return lista de log de usuario.
     */
    public List<UserLogReport> getItems() {
        if (items == null) {
            items = null;

        }
        return items;

    }

    /**
     * Método que obtiene una lista de log de usuario como resultado de la
     * búsqueda.
     *
     * @return lista de log de usuario.
     */
    public List<UserLogReport> search() {
        SearchOld = Search;
        String jsonListString;
        try {
            if (Search == null || Search.equals("")) {
                Search = "%";
            }

            if (StartDate == null && StartDateOld != null) {
                long startDatel = toLong(StartDateOld);
                long endDatel = toLong(EndDateOld);
                if (startDatel > endDatel) {

                    throw new Exception("La fecha inicial debe ser menor a la fecha final");
                }
                String path = MessageFormat.format("userlogreport/user/{0}/{1}/{2}/{3}", new Object[]{getSessionId(), Long.toString(startDatel), Long.toString(endDatel), Search});
                String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);

                StartDateOld = StartDate;
                EndDateOld = EndDate;
                //Search = "";
                Search = SearchOld;
                return convertToList(jsonResult);
            } else if (StartDate != null) {
                long startDatel = toLong(StartDate);
                long endDatel = toLong(EndDate);
                if (startDatel > endDatel) {

                    throw new Exception("La fecha inicial debe ser menor a la fecha final");
                }

                String path = MessageFormat.format("userlogreport/user/{0}/{1}/{2}/{3}", new Object[]{getSessionId(), Long.toString(startDatel), Long.toString(endDatel), Search});
                String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);

                StartDateOld = StartDate;
                EndDateOld = EndDate;
                //Search = "";
                Search = SearchOld;
                return convertToList(jsonResult);
            } else {
                //Search = "";
                Search = SearchOld;
                return new ArrayList();
            }
        } catch (Exception e) {
            //Search = "";
            Search = SearchOld;
            JsfUtil.addErrorMessage(e.getMessage());
            return new ArrayList();
        }
    }

    /**
     * Método que obtiene toda la lista de registros de log de usuario de la
     * base de datos.
     *
     * @return lista de log de usuarios.
     */
    public List<UserLogReport> findAll() {
        try {
            String path = MessageFormat.format("userlogreport/user/{0}", getSessionId());
            String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);

            return convertToList(jsonResult);
        } catch (Exception e) {
            JsfUtil.addErrorMessage("No existen registros con los parámetros especificados");
            return new ArrayList();
        }
    }

    /**
     * Método que convierte una lista de log de usuario en formato JSON a una
     * lista de objetos del tipo UserLogReport.
     *
     * @param jsonListString lista de log de usuarios en formato JSON.
     * @returnlista de log de usuarios.
     */
    private List<UserLogReport> convertToList(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<UserLogReport> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                UserLogReport item = JsonUtil.UserLogReportFromJson(jsonList.get(i).toString());
                list.add(item);
            }

            return list;
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

    /**
     * Método que obtiene un registro de log de usuario y lo convierte en un
     * objeto del tipo UserLogReport.
     *
     * @param id de l objeto a buscar.
     * @return objeto del tipo UserLogReport.
     */
    public UserLogReport getUserLogReport(java.lang.Integer id) {
        try {
            String path = MessageFormat.format("userlogreport/{0}", id);
            String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);
            return JsonUtil.UserLogReportFromJson(jsonResult);
        } catch (Exception e) {
            JsfUtil.addErrorMessage("No fue posible encontrar el parámetro" + id);
            return null;
        }
    }

//    @FacesConverter(forClass = UserLogReport.class)
//    public static class UserLogReportControllerConverter implements Converter {
//
//        @Override
//        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
//            if (value == null || value.length() == 0) {
//                return null;
//            }
//            UserLogReportControllerUser controller = (UserLogReportControllerUser) facesContext.getApplication().getELResolver().
//                    getValue(facesContext.getELContext(), null, "userLogReportControllerUser");
//            return controller.getUserLogReport(getKey(value));
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
//            if (object instanceof UserLogReport) {
//                UserLogReport o = (UserLogReport) object;
//                return getStringKey(o.getRecordId());
//            } else {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), UserLogReport.class.getName()});
//                return null;
//            }
//        }
//
//    }
    /**
     *
     * @param fecha
     * @return
     */
    public void cleanDate() {

        EndDate = null;
        EndDateOld=null;
        items = new ArrayList<>();
    }
    
    public String datoAutenticacion(UserLogReport us) {
        String dato;
        if(us.getHashcertificate() == null || us.getHashcertificate().equals("")) {
            dato = us.getUserName();
        } else {
            dato = us.getHashcertificate();
        }
        return dato;
    }
}
