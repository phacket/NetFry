package com.seguriboxltvweb.jsf;

import com.seguriboxltvweb.domain.AuditTrailReport;
import com.seguriboxltvweb.jsf.util.JsfUtil;
import com.seguriboxltvweb.jsf.util.JsonUtil;
import com.seguriboxltvweb.jsf.util.SeguriboxGetProperties;
import com.seguriboxltvweb.jsf.util.SessionUtils;
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
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Clase que contiene la funcionalidad de la auditoría de administradores.
 *
 * @author Germán Hernández López.
 * @version 1.0
 */
@Named("auditTrailReportControllerAdmin")
@SessionScoped
public class AuditTrailReportControllerAdmin implements Serializable {
    /**
     * Variable que almacena la lista de auditoría de administradores.
     */
    private List<AuditTrailReport> items;
    /**
     * Variable que almacena un objeto de AuditTrailReport que se ocupa para editar.
     */
    private AuditTrailReport selected;
    /**
     * @see <a href="https://jersey.java.net/apidocs/2.22/jersey/javax/ws/rs/client/WebTarget.html"/> Jersey-WebTarget
     */
    private final WebTarget webTarget;
    /**
     * @see <a href="https://jersey.java.net/documentation/latest/client.html"/> Jersey-Client
     */
    private final Client client;
    /**
     * Variable que almacena la url base, se inicializa en el constructor.
     */
    private final String BASE_URI;
    /**
     * Variable que almacena la fecha inicial, se ocupa en la búsqueda.
     */
    private Date StartDate;
      /**
     * Variable que almacena la fecha final, se ocupa en la búsqueda.
     */
    private Date EndDate;
      /**
     * Variable que almacena la fecha inicial temporalmente.
     */
    private Date StartDateOld;
      /**
     * Variable que almacena la fecha final temporalmente.
     */
    private Date EndDateOld;
    /**
     * Variable que almacena el valor del sessionId, se inicializa en el constructor.
     */
    private String sessionId;
    //private int isInPage;
    private boolean trace = true;

    /**
     * Constructor de la clase AuditTrailReportControllerAdmin.
     * @throws IOException 
     */
    public AuditTrailReportControllerAdmin() throws IOException {
        sessionId = SessionUtils.getSessionId();
        client = ClientBuilder.newClient();
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
        webTarget = client.target(BASE_URI).path("audittrailreport");
    }

    /**
     * Asigna el valor de la búsqueda a la variable items.
     */
    public void getItemsSearch() {
        items = search();
    }

    /**
     * Método que se encarga de hacer la búsqueda con los parámetros especificados.
     * Este método consume un web service rest por el método GET y procesa los datos obtenidos.
     *
     * @return lista de auditoría del administrador.
     */
    public List<AuditTrailReport> search() {
        
        try {

            if (StartDate == null && StartDateOld != null) {
                long startDatel = toLong(StartDateOld);
                long endDatel = toLong(EndDateOld);
                if (startDatel > endDatel) {
                    throw new Exception("La fecha inicial debe ser menor a la fecha final");
                }
                String path = MessageFormat.format("audittrailreport/admin/{0}/{1}/{2}", getSessionId(), Long.toString(startDatel), Long.toString(endDatel));
                String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);

                return convertToList(jsonResult);
            } else if (StartDate != null) {
                long startDatel = toLong(StartDate);
                long endDatel = toLong(EndDate);
                if (startDatel > endDatel) {

                    throw new Exception("La fecha inicial debe ser menor a la fecha final");
                }
                String path = MessageFormat.format("audittrailreport/admin/{0}/{1}/{2}", getSessionId(), Long.toString(startDatel), Long.toString(endDatel));
                String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);

                StartDateOld = StartDate;
                EndDateOld = EndDate;
                return convertToList(jsonResult);
            } else {
                return new ArrayList();
            }
        } catch (Exception e) {

            JsfUtil.addErrorMessage(e.getMessage().replace("java.lang.Exception:", ""));
            return new ArrayList();
        }
    }

    /**
     *
     * @param StartDate asigna el valor del argumento a la variable StartDate.
     */
    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
    }

    /**
     *
     * @param EndDate asigna el valor del argumento a la variable EndDate.
     */
    public void setEndDate(Date EndDate) {
        this.EndDate = EndDate;
    }

    /**
     *
     * @param StartDateOld asigna el valor del argumento a la variable StartDateOld.
     */
    public void setStartDateOld(Date StartDateOld) {
        this.StartDateOld = StartDateOld;
    }

    /**
     *
     * @param EndDateOld asigna el valor del argumento a la variable EndDateOld.
     */
    public void setEndDateOld(Date EndDateOld) {
        this.EndDateOld = EndDateOld;
    }

    /**
     *limpia la lista de la auditoría de administradores y los campos de fechas de la interface.
     * @return Cadena que redirige a la página audittrailadmin.jsf.
     * @throws IOException
     */
    public String fillControls() throws IOException {
        if (items != null) {
            this.items.clear();
        }
        StartDate = null;
        EndDate = null;

        FacesContext.getCurrentInstance().getExternalContext().redirect("audittrailadmin.jsf");
        return "audittrailadmin?faces-redirect=true";
    }

    /**
     *
     * @return valor de StartDate
     */
    public Date getStartDate() {
        return StartDate;
    }

    /**
     *
     * @return Valor de endDate
     */
    public Date getEndDate() {
        return EndDate;
    }

    /**
     *
     * @return valor de sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     *
     * @return valor de selected
     */
    public AuditTrailReport getSelected() {
        return selected;
    }

    /**
     *Asigna el resultado de la lista a la variable items
     * @return lista de auditoría de administradores
     */
    public List<AuditTrailReport> getItems() {
        if (items == null) {
            items = null;
        }
        return items;
    }

    /**
     *Método que retorna una lista con los datos de la auditoría de administrador.
     * Este método consume un web service rest por el método GET y procesa los datos obtenido. 
     * @return lista de auditoría de administradores
     */
    public List<AuditTrailReport> findAll() {
        try {
            String path = MessageFormat.format("audittrailreport/admin/{0}", getSessionId());
            String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);

            return convertToList(jsonResult);

        } catch (Exception e) {
            JsfUtil.addErrorMessage("No se encontraron registros con los parámetros ingresados");
            return new ArrayList();
        }
    }

    /**
     *Convierte la lista en formato JSON a una lista del tipo AuditTrailReport
     * @param jsonListString JSON con la lista de auditoría de administradores.
     * @return lista de auditoría de administradores.
     * 
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
     *Busca elemento en base a su id.
     * @param id identificador del registro a buscar
     * @return un elemento de auditoría de administrador.
     */
    public AuditTrailReport getAuditTrailReport(int id) {
        try {
            String path = MessageFormat.format("audittrailreport/{0}", getSessionId());
            String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);

            WebTarget resource = webTarget;
            resource.path(MessageFormat.format("{0}", new Object[]{id}));
            String item = resource.request(MediaType.APPLICATION_JSON + ";charset=utf-8").get(String.class
            );
            return JsonUtil.AuditTrailReportFromJson(item);
        } catch (Exception e) {
            JsfUtil.addErrorMessage("No fue posible encontrar el parámetro" + id);
            return null;
        }
    }

//    @FacesConverter(forClass = AuditTrailReport.class)
//    public static class AuditTrailReportControllerConverter implements Converter {
//
//        @Override
//        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
//            if (value == null || value.length() == 0) {
//                return null;
//            }
//            AuditTrailReportControllerAdmin controller = (AuditTrailReportControllerAdmin) facesContext.getApplication().getELResolver().
//                    getValue(facesContext.getELContext(), null, "auditTrailReportControllerAdmin");
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
    /**
     *Convierte tipo Date a long.
     * @param fecha fecha
     * @return fecha en milisegundos
     */
    public static long toLong(Date fecha) {
        long miliseconds;
        miliseconds = fecha.getTime();
        return miliseconds;
    }
    
    public void cleanDate() {

        EndDate = null;
        EndDateOld=null;
        items = new ArrayList<>();
    }

}
