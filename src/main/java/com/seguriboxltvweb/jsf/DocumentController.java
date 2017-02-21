package com.seguriboxltvweb.jsf;

import com.seguriboxltvweb.domain.Document;
import com.seguriboxltvweb.domain.DocumentReport;

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
import java.util.ResourceBundle;
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
 * Clase que contiene toda la funcionalidad de documentos.
 *
 * @author Germán Hernández López
 * @version 1.0
 */
@Named("documentController")
@SessionScoped
public class DocumentController implements Serializable {

    /**
     * @see
     * <a href="https://jersey.java.net/apidocs/2.22/jersey/javax/ws/rs/client/WebTarget.html"/>
     * Jersey-WebTarget
     */
    private final WebTarget webTarget;
    /**
     * @see
     * <a href="https://jersey.java.net/apidocs/2.22/jersey/javax/ws/rs/client/WebTarget.html"/>
     * Jersey-WebTarget
     */
    private final WebTarget webTargetDocument;
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
     * Variable que almacena la lista de documentos
     */
    private List<DocumentReport> items = null;
    /**
     * Variable que almacena la fecha inicial, se ocupa en la búsqueda.
     */
    private Date StartDate = null;
    /**
     * Variable que almacena la fecha final, se ocupa en la búsqueda.
     */
    private Date EndDate = null;
    /**
     * Variable que almacena la fecha inicial temporalmente.
     */
    private Date StartDateOld;
    /**
     * Variable que almacena la fecha final temporalmente.
     */
    private Date EndDateOld;
    /**
     * variable que almacena el parametro de busqueda de la interface.
     */
    private String Search;
    /**
     * Variable que almacena el valor del sessionId, se inicializa en el
     * constructor.
     */
    private String sessionId;
    /**
     * Variable que almacena un objeto del tipo document.
     */
    private Document selected;
    /**
     * Variable que almacena un objeto del tipo DocumentReport.
     */
    private DocumentReport selectedReport;
    private final String resourceDoc;
    private final String resourceReport;

    /**
     * Constructor de la clase DocumentController
     *
     * @throws IOException
     */
    public DocumentController() throws IOException {
        sessionId = SessionUtils.getSessionId();
        client = ClientBuilder.newClient();
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
        webTarget = client.target(BASE_URI).path("documentreport");
        webTargetDocument = client.target(BASE_URI).path("document");
        resourceDoc = "document";
        resourceReport = "documentreport";
    }

    /**
     *
     * @return
     */
    public List<DocumentReport> getItemsSearch() {
        items = search();
        return items;
    }

    /**
     * Método que limpia la lista de documentos y las variables de startDate y
     * endDate.
     *
     * @return @throws IOException
     */
    public String fillControls() throws IOException {
        if (items != null) {
            this.items.clear();
        }
        StartDate = null;
        EndDate = null;
        Search = "";
        FacesContext.getCurrentInstance().getExternalContext().redirect("admin/admindocuments.jsf");
        return "admin/admindocuments?faces-redirect=true";
    }

    /**
     *
     * @return
     */
    public DocumentReport getSelectedReport() {
        return selectedReport;
    }

    /**
     *
     * @param selectedReport
     */
    public void setSelectedReport(DocumentReport selectedReport) {
        this.selectedReport = selectedReport;
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
     * @param StartDate
     */
    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
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
     * @param EndDate
     */
    public void setEndDate(Date EndDate) {
        this.EndDate = EndDate;
    }

    public String getSearch() {
        return Search;
    }

    public void setSearch(String Search) {
        this.Search = Search;
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
    public Document getSelected() {
        return selected;
    }

    /**
     * Método que asigna la lista de todos los documentos a la variable items.
     *
     * @return lista de documentos
     */
    public List<DocumentReport> getItems() {
        if (items == null) {
            items = findAll();
        }
        return items;
    }

    /**
     * Método que devuelve una lista de documentos como resultado de la
     * búsqueda. Este método que consume un web service rest y procesa la
     * información obtenida.
     *
     * @return
     */
    public List<DocumentReport> search() {
        String oldSearch = Search;

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
                String path = MessageFormat.format(resourceReport + "/{0}/{1}/{2}/{3}",
                        new Object[]{getSessionId(), Long.toString(startDatel), Long.toString(endDatel), getSearch()});

                jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

                Search = oldSearch;

                return convertToList(jsonListString);
            } else if (StartDate != null) {
                long startDatel = toLong(StartDate);
                long endDatel = toLong(EndDate);
                if (startDatel > endDatel) {

                    throw new Exception("La fecha inicial debe ser menor a la fecha final");
                }

                String path = MessageFormat.format(resourceReport + "/{0}/{1}/{2}/{3}",
                        new Object[]{getSessionId(), Long.toString(startDatel), Long.toString(endDatel), getSearch()});

                jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);

                Search = oldSearch;

                return convertToList(jsonListString);
            } else {
                return new ArrayList();
            }
        } catch (Exception e) {
            Search = oldSearch;
            JsfUtil.addSuccessMessage(e.getMessage());
            return new ArrayList();
        }
    }

    /**
     * Método que devuelve una lista de todos los documentos. Método que consume
     * un web service rest y procesa la información obtenida.
     *
     * @return
     */
    public List<DocumentReport> findAll() {
        try {
            if (StartDate != null && EndDate != null) {
                setStartDate(new Date());
                setEndDate(new Date());
                long startDatel = toLong(StartDate);
                long endDatel = toLong(EndDate);
                WebTarget resource = webTarget;
                String message = MessageFormat.format("{0}/{1}/{2}/{3}",
                        new Object[]{getSessionId(), Long.toString(startDatel), Long.toString(endDatel),
                            getSearch()});
                String jsonListString = resource.path(message)
                        .request(MediaType.APPLICATION_JSON + ";charset=utf-8").get(String.class);
                return convertToList(jsonListString);
            } else {
                return items;
            }
        } catch (Exception e) {
            String message = ResourceBundle.getBundle("/AdminDocuments").getString("DocumentListError");
            JsfUtil.addErrorMessage(message);
            return new ArrayList();
        }
    }

    /**
     * Método que Convierte el JSON obtenido a una lista del tipo
     * DocumentReport.
     *
     * @param jsonListString lista en formato JSON.
     * @return lista de reporte de documentos
     */
    private List<DocumentReport> convertToList(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<DocumentReport> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                DocumentReport item = JsonUtil.DocumentReportFromJson(jsonList.get(i).toString());
                list.add(item);
            }
            return list;
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

    /**
     * Método que redirige a la página de edición de documentos
     *
     * @param item documento a editar
     * @return outcome a la página editdocument.jsf.
     */
    public String goToEdit(DocumentReport item) {
        setSelectedReport(item);
        return "editdocument?faces-redirect=true";
    }

    /**
     * Método que edita el documentos seleccionado, este metodo condume un web
     * service rest mediante el metodo put.
     *
     * @param selected documento a editar
     * @return outcome que redirige a la página admindocuments.jsf.
     */
    public String edit(DocumentReport selected) {

        long referenceDate;
        long expirationDate;
        try {
            String res = JsonUtil.JsonFromDocumentReport(selectedReport);

            referenceDate = toLong(selectedReport.getDateCreated());
            expirationDate = toLong(selectedReport.getExpirationDate());
            if (referenceDate < expirationDate) {
                String path = MessageFormat.format(resourceDoc + "/{0}", new Object[]{getSessionId()});

                wsUtil.ExecutePutRestService(true, BASE_URI, path, res);

                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/AdminDocuments").getString("DocumentUpdated"
                        + ""));
                items = search();
                setSelectedReport(new DocumentReport());
                return "admindocuments?faces-redirect=true";

            } else {
                items = new ArrayList();
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/AdminDocuments").getString("DocumentEditValidateDate"));

                return null;
            }

        } catch (Exception e) {
            items = search();
            JsfUtil.addErrorMessage(e.getMessage());
        }
        return null;
    }

    public void cleanDate() {

        EndDate = null;
        EndDateOld = null;
        items = new ArrayList<>();
    }

    public String resetFrom() throws IOException {

        items = new ArrayList<>();
        EndDate = null;
        StartDate = null;
        Search = "";
        
         FacesContext.getCurrentInstance().getExternalContext().redirect("admindocuments.jsf");
        return "admindocuments?faces-redirect=true";
    }
}
