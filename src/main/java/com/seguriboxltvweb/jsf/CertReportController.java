package com.seguriboxltvweb.jsf;

import com.aspose.cells.Cells;
import com.aspose.cells.SaveOptions;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.XlsbSaveOptions;
import com.seguriboxltvweb.domain.CertReport;
import com.seguriboxltvweb.jsf.util.JsfUtil;
import com.seguriboxltvweb.jsf.util.JsonUtil;
import com.seguriboxltvweb.jsf.util.SeguriboxGetProperties;
import static com.seguriboxltvweb.jsf.util.SessionUtils.getSessionId;
import com.seguriboxltvweb.jsf.util.wsUtil;
import java.io.IOException;
import java.io.OutputStream;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;

@Named("certReportController")
@SessionScoped
public class CertReportController implements Serializable {

    //private Long id;
    /**
     * Variable que almacena el valor de la lista de reporte de certificados por vencer.
     */
    private List<CertReport> items;
     /**
     * Variable que almacena la url base, se inicializa en el constructor.
     */
    private final String BASE_URI;
    /**
     * Variable que almacena el valor del campo semanas en la interface.
     */
    private int weeks;
    /**
     * Variable que almacena el valor del campo semanas de manera temporal.
     */
    private int weeksOld;
    private int recorrido = 0;
    private boolean trace = true;

    @PostConstruct
    public void init() {
        items = null;

        weeks = 1;
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
     * Constructor de la clase CertReportController.
     * @throws IOException 
     */
    public CertReportController() throws IOException {

        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
    }

    /**
     *Método que limpia la lista de certificados por vencer y asina el 1 a la variable weeks;
     * @return outcome a la página 
     * @throws IOException certtoexpire.
     */
    public String fillControls() throws IOException {
        if (items != null) {
            this.items.clear();
        }
        weeks = 1;
        FacesContext.getCurrentInstance().getExternalContext().redirect("certtoexpire.jsf");
        return "certtoexpire?faces-redirect=true";
    }

    /**
     *Método que asigna la lista de certificados por vencer a la variable items.
     * @return lista de certificados por vencer
     */
    public List<CertReport> getItems() {
        if (items == null) {
            items = null;
        }
        return items;
    }

    /**
     *Método que devuelve una lista de certificados por vencer como el resultado de la búsqueda.
     * Este método consume un web service rest por el metodo GET y procesa el JSON obtenido.
     */
    public void search() {
        items = findAll();
    }

    /**
     *
     * @return
     */
    public int getWeeks() {
        return weeks;
    }

    /**
     *Método que retorna una lista de todos los certificados por vencer.
     *Este método consume un web service rest por el método GET y procesa el JSON obtenido.
     * @return
     */
    public List<CertReport> findAll() {
        String jsonListString;
        try {
            if (weeks != 0) {
                if (weeks <= 0 || weeks >= 100) {
                    throw new Exception("El campo semanas, debe tener valores entre 1 y 99");
                }
                String path = MessageFormat.format("userreport/usersexpiration/{0}/{1}", getSessionId(), weeks);
                String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);
                weeksOld = weeks;
                return convertToList(jsonResult);
            } else {
                //weeks = weeksOld;
                weeksOld = weeks;
                return new ArrayList();
            }
        } catch (Exception e) {
            //weeks = weeksOld;
            weeksOld = weeks;
            JsfUtil.addErrorMessage(e.getMessage().replace("java.lang.Exception:", ""));
            return new ArrayList();
        }
    }

    /**
     *Método que convierte el JSON obtenido a una lista de objetos del tipo CertReport.
     * @param jsonListString JSON  a procesar.
     * @return lista de certificados por vencer.
     */
    private List<CertReport> convertToList(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<CertReport> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                CertReport item = JsonUtil.CertReportFromJson(jsonList.get(i).toString());
                list.add(item);
            }
            return list;
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

    /**
     *Método que prepara el reporte de certificados por vencer para ser descargado en formato de hoja de calculo.
     */
    public void certExpired() {
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
            worksheet.setName("Certificados por vencer");
            List<CertReport> list = findAll();
            ArrayList<String> listArray = new ArrayList<String>();

            for (CertReport item : list) {
                recorrido = recorrido + 1;
                try {
                    String userId = Integer.toString(item.getUserId());
                    listArray.add(userId);
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    listArray.add(item.getFullName());
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    listArray.add(item.getEmail());
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    listArray.add(item.getCertificateSerie());
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String convertido = fechaHora.format(item.getExpirationDate());
                    listArray.add(convertido);
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    listArray.add(item.getUserTypeStr());
                } catch (Exception e) {
                    listArray.add(" ");
                }

                cells.importArrayList(listArray, recorrido, 0, false);
                listArray.clear();
            }

            recorrido = 0;
            cells.get("A1").putValue("ID USUARIO");
            cells.get("B1").putValue("NOMBRE DE USUARIO");
            cells.get("C1").putValue("CORREO ELECTRÓNICO");
            cells.get("D1").putValue("NÚMERO DE SERIE DE CERTIFICADO");
            cells.get("E1").putValue("FECHA DE VENCIMIENTO DE CERTIFICADO");
            cells.get("F1").putValue("TIPO DE USUARIO");
            HttpServletResponse response
                    = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();
            OutputStream output = response.getOutputStream();
            SaveOptions options = new XlsbSaveOptions(6);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + "Certificados por vencer.xls" + "\"");
            workbook.save(output, options);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

}
