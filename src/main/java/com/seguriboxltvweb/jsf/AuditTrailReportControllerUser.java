package com.seguriboxltvweb.jsf;

import com.seguriboxltvweb.domain.AuditTrailReport;
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
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Clase que contiene la funcionalidad para la auditoría de usuario.
 *
 * @author Germán Hernández López.
 * @version 1.0
 */
@Named("auditTrailReportControllerUser")
@SessionScoped
public class AuditTrailReportControllerUser implements Serializable {

    /**
     * Variable que almacena la lista de auditoría de usuarios.
     */
    private List<AuditTrailReport> items;
    //private AuditTrailReport selected;
    //private AuditTrailReport auditTrailReport;
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
     * Variable que almacena la fecha inicial temporalmente.
     */
    private Date StartDateOld;
    /**
     * Variable que almacena la fecha final temporalmente.
     */
    private Date EndDateOld;
    /**
     * Variable que almacena la fecha inicial, se ocupa en la búsqueda.
     */
    private Date StartDate;
    /**
     * Variable que almacena la fecha final, se ocupa en la búsqueda.
     */
    private Date EndDate;
    /**
     * Variable que almacena el valor del sessionId, se inicializa en el
     * constructor.
     */
    private String sessionId;
    private boolean trace = true;

    /**
     * Constructor de la clase AuditTrailReportControllerUser.
     *
     * @throws IOException
     */
    public AuditTrailReportControllerUser() throws IOException {
        sessionId = SessionUtils.getSessionId();
        client = ClientBuilder.newClient();
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
        webTarget = client.target(BASE_URI).path("audittrailreport");
    }

    /**
     * Método que asigna el resultado de la búsqueda a la variable items.
     */
    public void getItemsSearch() {
        items = search();
    }

    /**
     * Método que devuelve una lista de registros de auditoría de usuarios como
     * resultado de la búsqueda. Este método consume un web service rest por el
     * método GET y procesa los datos obtenidos.
     *
     * @return lista de auditoría de usuarios.
     */
    public List<AuditTrailReport> search() {
        String jsonListString;

        try {
            if (StartDate == null && StartDateOld != null) {
                long startDatel = toLong(StartDateOld);
                long endDatel = toLong(EndDateOld);
                if (startDatel > endDatel) {
                    throw new Exception("La fecha inicial debe ser menor a la fecha final");
                }
                String path = MessageFormat.format("audittrailreport/user/{0}/{1}/{2}", new Object[]{getSessionId(), Long.toString(startDatel), Long.toString(endDatel)});
                String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);
                return convertToList(jsonResult);
            } else if (StartDate != null) {
                long startDatel = toLong(StartDate);
                long endDatel = toLong(EndDate);
                if (startDatel > endDatel) {

                    throw new Exception("La fecha inicial debe ser menor a la fecha final");
                }
                String path = MessageFormat.format("audittrailreport/user/{0}/{1}/{2}", new Object[]{getSessionId(), Long.toString(startDatel), Long.toString(endDatel)});
                String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);
                StartDateOld = StartDate;
                EndDateOld = EndDate;
                return convertToList(jsonResult);
            } else {
                return new ArrayList();
            }
        } catch (Exception e) {

            JsfUtil.addErrorMessage(e.getMessage());
            return new ArrayList();
        }
    }

    /**
     *
     * @return
     */
    public Date getStartDateOld() {
        return StartDateOld;
    }

    /**
     *
     * @return
     */
    public Date getEndDateOld() {
        return EndDateOld;
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
     * limpia la lista de la auditoría de administradores y los campos de fechas
     * de la interface.
     *
     * @return Cadena que redirige a la página audittrailadmin.jsf.
     * @throws IOException
     *  
     */
    public String fillControls() throws IOException {
//        if (items != null) {
//            this.items.clear();
//        }
        items = new ArrayList();
        StartDate = null;
        EndDate = null;

        FacesContext.getCurrentInstance().getExternalContext().redirect("audittrailuser.jsf");
        return "audittrailuser?faces-redirect=true";
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
     *
     * @return
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Método que asigna el valor de la lista de auditoría de usuario a la
     * variable items.
     *
     * @return lista de auditoría de usuario
     */
    public List<AuditTrailReport> getItems() {
        if (items == null) {
            items = null;
        }
        return items;
    }

    /**
     * Método que retorna una lista de todos los registros de auditoría de
     * usuarios . Este método consume un web service rest por el método GET y
     * procesa los datos obtenidos.
     *
     * @return
     */
    public List<AuditTrailReport> findAll() {
        try {
            String path = MessageFormat.format("audittrailreport/user/{0}", getSessionId());
            String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);
            return convertToList(jsonResult);
        } catch (Exception e) {
            JsfUtil.addErrorMessage("No se encontraron registros con los parámetros ingresados");
            return new ArrayList();
        }
    }

    /**
     * Método que convierte el JSON obtenido por el web service y lo convierte a
     * una lista de objetos de AuditTrailReport.
     *
     * @param jsonListString JSON de la lista de auditoría de usuarios.
     * @return lista de auditoría de usuarios.
     */
    private List<AuditTrailReport> convertToList(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<AuditTrailReport> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                AuditTrailReport item = JsonUtil.AuditTrailReportFromJson(jsonList.get(i).toString());
                list.add(item);
            }
            return list;
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

    /**
     *
     * @param id
     * @return
     */
//    public AuditTrailReport getAuditTrailReport(int id) {
//        try {
//            String path = MessageFormat.format("audittrailreport/{0}", id);
//            String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);
//            return JsonUtil.AuditTrailReportFromJson(jsonResult);
//        } catch (Exception e) {
//            JsfUtil.addErrorMessage("No fue posible encontrar el parámetro" + id);
//            return null;
//        }
//    }
//
//    @FacesConverter(forClass = AuditTrailReport.class)
//    public static class AuditTrailReportControllerConverter implements Converter {
//
//        @Override
//        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
//            if (value == null || value.length() == 0) {
//                return null;
//            }
//            AuditTrailReportControllerUser controller = (AuditTrailReportControllerUser) facesContext.getApplication().getELResolver().
//                    getValue(facesContext.getELContext(), null, "auditTrailReportControllerUser");
//            return controller.getAuditTrailReport(getKey(value));
//        }
//
//        int getKey(String value) {
//            int key;
//            key = Integer.parseInt(value);
//            return key;
//        }
//
//        String getStringKey(int value) {
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
//            if (object instanceof AuditTrailReport) {
//                AuditTrailReport o = (AuditTrailReport) object;
//                return getStringKey(o.getRecordId());
//            } else {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AuditTrailReport.class.getName()});
//                return null;
//            }
//        }
//
//    }
    public void cleanDate() {

        EndDate = null;
        EndDateOld=null;
        items = new ArrayList<>();
    }
}
