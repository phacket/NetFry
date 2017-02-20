package com.seguriboxltvweb.jsf;

import com.aspose.cells.Cells;
import com.aspose.cells.SaveOptions;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.XlsbSaveOptions;
import com.seguriboxltvweb.domain.UserReport;
import com.seguriboxltvweb.jsf.util.JsfUtil;
import com.seguriboxltvweb.jsf.util.JsonUtil;
import com.seguriboxltvweb.jsf.util.SeguriboxGetProperties;
import com.seguriboxltvweb.jsf.util.SessionUtils;
import com.seguriboxltvweb.jsf.util.wsUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * Define toda la funcionalidad de reporte de administradores.
 *
 * @author Ing. Germán Hernández López.
 * @version 1.0
 */
@Named("adminReportController")
@SessionScoped
public class AdminReportController implements Serializable {

    /**
     * Variable que almacena una lista de usuarios administradores.
     */
    private List<UserReport> items = null;
    //private List<UserReport> itemsUsers = null;
    /**
     * Variable que almacena el valor de sessionId que se inicializa en el
     * constructor
     */
    private String sessionId;
    /**
     * Variable que almacena el valor de la URL base que se inicializa en el
     * constructor
     */
    private final String BASE_URI;
    /**
     * Variable que almacena el valor del parámetro de búsqueda.
     */
    private String searchadmin;
    /**
     * Variable que almacena temporalmente el valor del parámetro de búsqueda.
     */
    private String SearchOld;
    private boolean trace = true;

    /**
     * Constructor de la clase que inicializa algunas variables.
     *
     * @throws IOException
     */
    public AdminReportController() throws IOException {
        sessionId = SessionUtils.getSessionId();
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
    }

    /**
     * Asigna a la variable items el resultado de la búsqueda de usuarios
     * administradores.
     */
    public void getItemsSearch() {

        items = search();
    }

    /**
     * Método que devuelve una lista de usuarios administradores resultado de la
     * búsqueda.
     *
     * @return una lista de usuarios administradores.
     */
    public List<UserReport> search() {
        SearchOld = searchadmin;
        try {
            if (searchadmin == null || searchadmin.equals("")) {
                searchadmin = "%";
            }

            String path = MessageFormat.format("userreport/adminsbyusername/{0}/{1}", getSessionId(), searchadmin);
            String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);
            if (searchadmin.equals("%")) {

                searchadmin = "";
            } else {
                SearchOld = searchadmin;
            }
            return convertToList(jsonResult);

        } catch (Exception e) {
            items = new ArrayList<>();
            if (searchadmin.equals("%")) {

                searchadmin = "";
            } else {
                SearchOld = searchadmin;
            }
            JsfUtil.addErrorMessage(e.getMessage().replace("java.lang.Exception:", ""));
            return new ArrayList();
        }

    }

    public void setSearchadmin(String searchadmin) {
        this.searchadmin = searchadmin;
    }

    public String getSearchadmin() {
        return searchadmin;
    }

    /**
     * Método que carga la tabla de reporte de administradores y limpia el campo
     * de de búsqueda.
     *
     * @return Outcome que redirige a la página admininplatform.jsf
     */
    public String fillControls() {
        searchadmin = "";
        items = findAll();

        return "admininplatform?faces-redirect=true";
    }

    public String cleanData() {
        searchadmin = "";
        items = new ArrayList<>();

        return "admininplatform?faces-redirect=true";
    }

    /**
     * Método que asigna una lista de usuarios administradores a la variable
     * items.
     *
     * @return Una lista de todos los registros de usuarios.
     */
    public List<UserReport> getItems() {
        if (items == null) {
            items = findAll();
        }
        return items;
    }

    public String getSessionId() {
        return sessionId;
    }

    /**
     * Método que devuelve una lista de todos los registros de administradores.
     * Este método consume un web service rest.
     *
     * @return Una lista de todos los registros de administradores.
     */
    public List<UserReport> findAll() {
        try {
            String path = MessageFormat.format("userreport/admins/{0}", new Object[]{getSessionId()});
            String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);
            return convertToList(jsonResult);
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error al tratar de leer un rango de objetos");
            return new ArrayList();
        }
    }

    /**
     * Convierte el JSON obtenido al consumir el web service en una lista de
     * objetos del tipo UserReport.
     *
     * @param jsonListString lista de UserReport en formato JSON.
     * @return Una lista de todos los registros de administradores
     */
    private List<UserReport> convertToList(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<UserReport> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                UserReport item = JsonUtil.UserReportFromJson(jsonList.get(i).toString());
                list.add(item);
            }
            return list;
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

    /**
     * Prepara el reporte de administradores para ser descargado en formato de
     * hoja de calculo. Utiliza la librería de ASPOSE para la conversión.
     *
     * @param jsonListString lista de administradores en formato JSON.
     * @throws Exception
     */
    public void userReportAspose(String jsonListString) throws Exception {
        Workbook workbook = new Workbook();
        Cells cells = null;
        JSONArray jsonList = new JSONArray(jsonListString);
        try {
            ArrayList<String> listArray = new ArrayList<String>();
            listArray.clear();
            for (int i = 0; i < jsonList.length(); i++) {

                UserReport item = JsonUtil.UserReportFromJson(jsonList.get(i).toString());

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
                    listArray.add(item.getAreaName());
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    listArray.add(item.getEmail());
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    listArray.add(item.getTipoInicio());
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    listArray.add(item.getSerie());
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String convertido = fechaHora.format(item.getVencimiento());
                    listArray.add(convertido);
                } catch (Exception e) {
                    listArray.add(" ");
                }
                Worksheet worksheet = workbook.getWorksheets().get(0);
                worksheet.setName("Reporte de Usuarios");
                cells = worksheet.getCells();
                cells.importArrayList(listArray, 0, 0, true);

            }
            HttpServletResponse response
                    = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();
            OutputStream output = response.getOutputStream();
            SaveOptions options = new XlsbSaveOptions(6);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + "Reporte de Usuarios.xls" + "\"");

            workbook.save(output, options);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Prepara el reporte de de usuarios administradores para descargarlo en
     * formato de hoja de calculo. utiliza la librería de ASPOSE para la
     * conversión.
     */
    public void adminReportAspose() {
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
            int recorrido = 0;
            worksheet.setName("Reporte de Administradores");
            ArrayList<String> listArray = new ArrayList<String>();

            for (UserReport item : items) {

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
                    listArray.add(item.getAreaName());
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    listArray.add(item.getEmail());
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String convertido = fechaHora.format(item.getVencimiento());
                    listArray.add(convertido);
                } catch (Exception e) {
                    listArray.add(" ");
                }
                try {
                    listArray.add(item.getSerie());
                } catch (Exception e) {
                    listArray.add(" ");
                }

                cells.importArrayList(listArray, recorrido, 0, false);
                listArray.clear();
            }
            recorrido = 0;
            cells.get("A1").putValue("ID USUARIO");
            cells.get("B1").putValue("NOMBRE DE USUARIO");
            cells.get("C1").putValue("ÁREA");
            cells.get("D1").putValue("CORREO ELECTRÓNICO");
            cells.get("E1").putValue("FECHA DE VENCIMIENTO DE CERTIFICADO");
            cells.get("F1").putValue("NÚMERO DE SERIE DE CERTIFICADO");
            HttpServletResponse response
                    = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();
            OutputStream output = response.getOutputStream();
            SaveOptions options = new XlsbSaveOptions(6);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + "Reporte de Administradores.xls" + "\"");

            workbook.save(output, options);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

}
