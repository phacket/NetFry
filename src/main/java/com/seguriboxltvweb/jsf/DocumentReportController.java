package com.seguriboxltvweb.jsf;

import com.aspose.cells.Cells;
import com.aspose.cells.SaveOptions;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.XlsbSaveOptions;
import com.seguriboxltvweb.domain.DocumentReport;
import com.seguriboxltvweb.jsf.util.JsfUtil;
import com.seguriboxltvweb.jsf.util.JsonUtil;
import com.seguriboxltvweb.jsf.util.SeguriboxGetProperties;
import com.seguriboxltvweb.jsf.util.SessionUtils;
import static com.seguriboxltvweb.jsf.util.Util.toLong;
import com.seguriboxltvweb.jsf.util.wsUtil;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
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
 * Clase que contiene toda la funcionalidad de reporte de documentos.
 *
 * @author Germán Hernández López
 */
@Named("documentReportController")
@SessionScoped
public class DocumentReportController implements Serializable {

    private final WebTarget webTarget;
    private final Client client;
    private final String BASE_URI;
    private List<DocumentReport> items = null;
    private List<DocumentReport> itemsToExpire = null;
    private DocumentReport selected;
    private Date StartDate;
    private Date EndDate;
    private Date StartDateOld;
    private Date EndDateOld;
    private String SearchOld;
    private String SignedTypeOld;
    private String Search;
    private String SignedType;
    private String sessionId;
    private DocumentReport document;
    private int weeks;
    private int weeksOld;
    private boolean totalDocsToExpire;
    private int recorrido = 0;
    private boolean trace = true;

    @PostConstruct
    public void init() {
        items = null;
        StartDate = null;
        EndDate = null;
        Search = null;
        weeks = 1;
    }

    /**
     * Constructor de la clase
     *
     * @throws IOException
     */
    public DocumentReportController() throws IOException {
        sessionId = SessionUtils.getSessionId();
        client = ClientBuilder.newClient();
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
        webTarget = client.target(BASE_URI).path("documentreport");
    }

    /**
     * Método que asigna a la variable items el resultado de la búsqueda por
     * semana.
     */
    public void getItemsSearchToWeeks() {
        //totalDocsToExpire = false;
        itemsToExpire = searchToWeeks();
        //return itemsToExpire;
    }

    /**
     * Método que hace la búsqueda de documentos en base a las semanas definida
     * en el cuadro de búsqueda
     *
     * @return lista de documentos en base a semanas
     */
    public List<DocumentReport> searchToWeeks() {
        SearchOld = Search;

        try {
            if (Search == null || Search.equals("")) {
                Search = "%";
            }
            if (weeks != 0) {
                WebTarget resource = webTarget;
                if (weeks <= 0 || weeks >= 100) {
                    throw new Exception("El campo semanas debe tener valores entre 1 y 99");
                }
                String path = MessageFormat.format("documentreport/expiration/{0}/{1}/{2}", new Object[]{getSessionId(), getWeeks(), getSearch()});
                String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);
                Search = SearchOld;

                //weeks = weeksOld;
                weeksOld = weeks;
                return convertToList(jsonResult);
            } else {
                //Search = SearchOld;
                Search = SearchOld;

                weeksOld = weeks;
                //weeks = weeksOld
                return new ArrayList();
            }
        } catch (Exception e) {
            //Search = SearchOld;
            Search = SearchOld;

            weeksOld = weeks;
            //weeks = weeksOld;
            JsfUtil.addErrorMessage(e.getMessage().replace("java.lang.Exception:", ""));
            return new ArrayList();
        }
    }

    /**
     *
     * @return
     */
    public WebTarget getWebTarget() {
        return webTarget;
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
     * @return
     */
    public String getSearchOld() {
        return SearchOld;
    }

    /**
     *
     * @return
     */
    public int getWeeksOld() {
        return weeksOld;
    }

    /**
     *
     * @param StartDate
     */
    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
    }

    /**
     * Método que obtiene el resultado de la búsqueda ya sea por fecha o fecha y
     * tipo de firma
     */
    public void getItemsSearch() {
        if (SignedType.contains("100")) {
            items = search();
        } else {
            items = searchToSignedType();
        }
    }

    /**
     * Método que retorna una lista de de documentos en base al tipo de firma.
     * Este método consume un web service rest mediante el método GET y procesa
     * los datos obtenidos.
     *
     * @return lista de documentos en base a su firma.
     */
    public List<DocumentReport> searchToSignedType() {
        SearchOld = Search;
        try {
            //
            //
            if (Search == null || Search.equals("")) {
                Search = "%";
            }
//            if (SignedType == null || SignedType.equals("100")) {
//                throw new Exception("Se debe especificar el tipo de firma");
//            }

            if (StartDate != null && EndDate != null) {

                long startDatel = toLong(StartDate);
                long endDatel = toLong(EndDate);

                if (startDatel > endDatel) {
                    throw new Exception("La fecha inicial debe ser menor a la fecha final");
                }
                //documentreport

                String path = MessageFormat.format("documentreport/signature/{0}/{1}/{2}/{3}/{4}",
                        new Object[]{getSessionId(), Long.toString(startDatel),
                            Long.toString(endDatel), getSignedType(), getSearch()});
                String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);

                Search = SearchOld;
                //SearchOld = Search;
                StartDateOld = StartDate;
                EndDateOld = EndDate;
                //SignedType = "--";
                SignedTypeOld = SignedType;
                return convertToList(jsonResult);
            } else {
                return new ArrayList();
            }
        } catch (Exception e) {
            Search = SearchOld;
            //SearchOld = Search;

            StartDateOld = StartDate;
            EndDateOld = EndDate;
            //SignedType = "--";
            SignedTypeOld = SignedType;

            //String message = ResourceBundle.getBundle("/AdminDocuments").getString("DocumentNotFound");
            JsfUtil.addErrorMessage(e.getMessage().replace("java.lang.Exception:", ""));
            return new ArrayList();
        }
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
     * @param EndDate
     */
    public void setEndDate(Date EndDate) {
        this.EndDate = EndDate;
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
     * @param SearchOld
     */
    public void setSearchOld(String SearchOld) {
        this.SearchOld = SearchOld;
    }

    public void setSignedTypeOld(String SignedTypeOld) {
        this.SignedTypeOld = SignedTypeOld;
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
     * @param SignedType
     */
    public void setSignedType(String SignedType) {
        this.SignedType = SignedType;
    }

    /**
     *
     * @param weeks
     */
    public void setWeeks(int weeks) {
        this.weeks = weeks;
    }

    /**
     *
     * @param weeksOld
     */
    public void setWeeksOld(int weeksOld) {
        this.weeksOld = weeksOld;
    }

    /**
     * Método que limpia los campos de búsqueda de la interface de usuario y
     * limpia también la tabla.
     *
     * @return
     * @throws IOException
     */
    public String fillControls() throws IOException {
        if (items != null) {
            this.items.clear();
            //this.itemsToExpire.clear();

        }
        StartDate = null;
        EndDate = null;
        Search = null;
        SignedType = null;
     
        FacesContext.getCurrentInstance().getExternalContext().redirect("docsinplatform.jsf");
        return "docsinplatform?faces-redirect=true";
    }

    /**
     * Método que limpia los campos de búsqueda de la interface de usuario y
     * limpia también la tabla.
     *
     * @return
     * @throws IOException
     */
    public String fillControlsExpire() throws IOException {
        itemsToExpire = new ArrayList<>();
        StartDate = null;
        EndDate = null;
        Search = null;
        SignedType = null;
        weeks = 1;
        FacesContext.getCurrentInstance().getExternalContext().redirect("docstoexpire.jsf");
        return "docstoexpire?faces-redirect=true";
    }

    /**
     *
     * @return
     */
    public List<DocumentReport> getItems() {
        return items;
    }

    /**
     *
     * @return
     */
    public List<DocumentReport> getItemsToExpire() {
        totalDocsToExpire = true;
        return itemsToExpire;
    }

    /**
     *
     * @return
     */
    public int getWeeks() {
        return weeks;
    }

    /**
     *
     * @return
     */
    public String getSignedType() {
        return SignedType;
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
     * Método que realiza la búsqueda de documentos en base a los parámetros de
     * fecha, este método consume un web service rest mediante el método GET y
     * procesa los datos obtenidos.
     *
     * @return lista de documentos
     */
    public List<DocumentReport> search() {
        String jsonListString;
        SearchOld = Search;
        try {
            if (Search == null || Search.equals("")) {
                Search = "%";
            }
            if (StartDate == null && StartDateOld != null) {
                long startDatel = toLong(StartDate);
                long endDatel = toLong(EndDate);

                if (startDatel > endDatel) {
                    throw new Exception("La fecha inicial debe ser menor a la fecha final");
                }

                String path = MessageFormat.format("documentreport/{0}/{1}/{2}/{3}",
                        new Object[]{getSessionId(), Long.toString(startDatel), Long.toString(endDatel), getSearch()});
                String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);

                Search = SearchOld;
                StartDateOld = StartDate;
                EndDateOld = EndDate;
                SignedType = "--";
                return convertToList(jsonResult);
            } else if (StartDate != null) {
                long startDatel = toLong(StartDate);
                long endDatel = toLong(EndDate);

                if (startDatel > endDatel) {
                    throw new Exception("La fecha inicial debe ser menor a la fecha final");
                }
                String path = MessageFormat.format("documentreport/{0}/{1}/{2}/{3}",
                        new Object[]{getSessionId(), Long.toString(startDatel), Long.toString(endDatel), getSearch()});
                String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);

                Search = SearchOld;
                StartDateOld = StartDate;
                EndDateOld = EndDate;
                SignedType = "--";
                return convertToList(jsonResult);

            } else {
                long startDatel = toLong(StartDate);
                long endDatel = toLong(EndDate);

                if (startDatel > endDatel) {
                    throw new Exception("La fecha inicial debe ser menor a la fecha final");
                }
                String path = MessageFormat.format("documentreport/{0}/{1}/{2}/{3}",
                        new Object[]{getSessionId(), Long.toString(startDatel), Long.toString(endDatel), getSearch()});
                String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);

                Search = SearchOld;
                StartDateOld = StartDate;
                EndDateOld = EndDate;
                SignedType = "--";
                return convertToList(jsonResult);
            }
        } catch (Exception e) {
            Search = SearchOld;
            StartDateOld = StartDate;
            EndDateOld = EndDate;
            SignedType = "--";
            //String message = ResourceBundle.getBundle("/AdminDocuments").getString("DocumentNotFound");
            JsfUtil.addErrorMessage(e.getMessage().replace("java.lang.Exception:", ""));
            return new ArrayList();
        }
    }

    /**
     * Método que convierte el JSON obtenido en una lista del tipo
     * DocumentReport.
     *
     * @param jsonListString JSON obtenido al consumir web service rest
     * @return lista de documentos
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
     * Método que prepara el reporte de documentos para poder descargarlo en
     * formato de hoja de calculo
     */
    public void docReport() {
        try {
            if (items == null) {
                throw new Exception("No se puede descargar el reporte, porque no existen datos en la búsqueda");
            }
            if (items.isEmpty() == true) {
                throw new Exception("No se puede descargar el reporte, porque no existen datos en la búsqueda");
            }

            Workbook workbook = new Workbook();
            Worksheet worksheet = workbook.getWorksheets().get(0);
            Cells cells = worksheet.getCells();
            worksheet.setName("Reporte de documentos");
            ArrayList<String> listArray = new ArrayList<String>();

            for (DocumentReport item : items) {
                recorrido = recorrido + 1;
                try {
                    String documentId = Integer.toString(item.getDocumentId());
                    listArray.add(documentId);
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    listArray.add(item.getFullName());
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    listArray.add(item.getDocumentName());
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    listArray.add(item.getTipoFirma());
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String convertido = fechaHora.format(item.getDateCreated());
                    listArray.add(convertido);
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String convertido2 = fechaHora.format(item.getExpirationDate());
                    listArray.add(convertido2);
                } catch (Exception e) {
                    listArray.add(" ");
                }

                cells.importArrayList(listArray, recorrido, 0, false);
                listArray.clear();
            }
            recorrido = 0;
            cells.get("A1").putValue("ID DOCUMENTO");
            cells.get("B1").putValue("NOMBRE DE USUARIO");
            cells.get("C1").putValue("NOMBRE DE DOCUMENTO");
            cells.get("D1").putValue("TIPO DE FIRMA");
            cells.get("E1").putValue("FECHA DE REGISTRO");
            cells.get("F1").putValue("FECHA DE VENCIMIENTO");
            HttpServletResponse response
                    = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();
            OutputStream output = response.getOutputStream();
            SaveOptions options = new XlsbSaveOptions(6);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + "Reporte de documentos.xls" + "\"");
            workbook.save(output, options);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    /**
     * Método que prepara el reporte de documentos expirados para poder
     * descargarlo en formato de hoja de calculo
     */
    public void docExpiredReport() {
        try {
            if (itemsToExpire == null) {
                throw new Exception("No se puede descargar el reporte, porque no existen datos en la búsqueda");
            }
            if (itemsToExpire.isEmpty() == true) {
                throw new Exception("No se puede descargar el reporte, porque no existen datos en la búsqueda");
            }
            Workbook workbook = new Workbook();
            Worksheet worksheet = workbook.getWorksheets().get(0);
            Cells cells = worksheet.getCells();
            worksheet.setName("Documentos por vencer");
            ArrayList<String> listArray = new ArrayList<String>();

            for (DocumentReport item : itemsToExpire) {
                recorrido = recorrido + 1;
                try {
                    String documentId = Integer.toString(item.getDocumentId());
                    listArray.add(documentId);
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    listArray.add(item.getFullName());
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    listArray.add(item.getDocumentName());
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    listArray.add(item.getTipoFirma());
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String convertido = fechaHora.format(item.getDateCreated());
                    listArray.add(convertido);
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String convertido2 = fechaHora.format(item.getExpirationDate());
                    listArray.add(convertido2);
                } catch (Exception e) {
                    listArray.add(" ");
                }

                cells.importArrayList(listArray, recorrido, 0, false);
                listArray.clear();
            }
            recorrido = 0;
            cells.get("A1").putValue("ID DOCUMENTO");
            cells.get("B1").putValue("NOMBRE DE USUARIO");
            cells.get("C1").putValue("NOMBRE DE DOCUMENTO");
            cells.get("D1").putValue("TIPO DE FIRMA");
            cells.get("E1").putValue("FECHA DE REGISTRO");
            cells.get("F1").putValue("FECHA DE VENCIMIENTO");
            HttpServletResponse response
                    = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();
            OutputStream output = response.getOutputStream();
            SaveOptions options = new XlsbSaveOptions(6);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + "Reporte de documentos por vencer.xls" + "\"");
            workbook.save(output, options);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public void cleanDate() {

        EndDate = null;
        EndDateOld = null;
        items = new ArrayList<>();
    }
}
