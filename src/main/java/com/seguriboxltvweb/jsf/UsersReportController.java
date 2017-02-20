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
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Clase que contiene toda la funcionalidad de reporte de usuario.
 *
 * @author Germán Hernández López
 * @version 1.0
 */
@Named("userReportController")
@SessionScoped
public class UsersReportController implements Serializable {

    private Long id;

    private List<UserReport> items = null;
    private List<UserReport> itemsUsers = null;
    private String sessionId;
    private final String BASE_URI;
    private String searchadmin;
    private String SearchOld;
    private boolean trace = true;

    /**
     * Constructor de la clase UsersReportController.
     *
     * @throws IOException
     */
    public UsersReportController() throws IOException {
        sessionId = SessionUtils.getSessionId();
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
    }

    @PostConstruct
    public void init() {
        items = null;
    }

    public String getSearchadmin() {
        return searchadmin;
    }

    public void setSearchadmin(String searchadmin) {
        this.searchadmin = searchadmin;
    }

    public String getSearchOld() {
        return SearchOld;
    }

    public void setSearchOld(String SearchOld) {
        this.SearchOld = SearchOld;
    }

    /**
     * Método que llena la tabla de reporte de usuarios y limpia el campo de
     * búsqueda.
     *
     * @return outcome a la página usersinplatform.jsf.
     */
    public String fillControls() {
        searchadmin = "";
        items = findAll();
        return "usersinplatform?faces-redirect=true";
    }

    public String cleanPage() {
        searchadmin = "";
        items = new ArrayList<>();

        return "usersinplatform?faces-redirect=true";
    }

    public List<UserReport> getItemsUsers() {
        if (itemsUsers == null) {
            itemsUsers = findAllUsers();
        }
        return itemsUsers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItemsUsers(List<UserReport> itemsUsers) {
        this.itemsUsers = itemsUsers;
    }

    public List<UserReport> getItems() {
        if (items == null) {
            items = findAll();
        }
        return items;
    }

    public void setItems(List<UserReport> items) {
        this.items = items;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void getItemsSearch() {
        items = search();

    }

    /**
     * Método que obtiene una lista como resultado de la búsqueda de reporte de
     * usuario.
     *
     * @return lista de reporte de usuario.
     */
    public List<UserReport> search() {
        SearchOld = searchadmin;

        try {
            if (searchadmin == null || searchadmin.equals("")) {
                searchadmin = "%";
            }
            String path = MessageFormat.format("userreport/usersbyusername/{0}/{1}", getSessionId(), searchadmin);
            String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);
            if (searchadmin.equals("%")) {

                searchadmin = "";
            } else {
                SearchOld = searchadmin;
            }
            return convertToList(jsonResult);

        } catch (Exception e) {
            if (searchadmin.equals("%")) {

                searchadmin = "";
            } else {
                SearchOld = searchadmin;
            }
            JsfUtil.addErrorMessage(e.getMessage().replace("java.lang.Exception:", ""));
            return new ArrayList();
        }

    }

    /**
     * Método que obtiene una lista de todos los registros para el reporte de
     * usuarios desde la base de datos
     *
     * @return lista de reporte de usuarios.
     */
    public List<UserReport> findAll() {
        try {
            String path = MessageFormat.format("userreport/users/{0}", getSessionId());
            String jsonResult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);
            return convertToList(jsonResult);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage().replace("java.lang.Exception:", ""));
            return new ArrayList();
        }
    }

    /**
     * Método que obtiene una lista de todos los registros para el reporte de
     * usuarios desde la base de datos
     *
     * @return lista de reporte de usuarios.
     */
    public List<UserReport> findAllUsers() {
        try {
            String path = MessageFormat.format("userreport/users/{0}", getSessionId());
            String jsonresult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);
            return convertToList(jsonresult);
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error al tratar de leer un rango de objetos");
            return new ArrayList();
        }
    }

    /**
     * Método que convierte una lista de reporte de usuarios en formato JSON a
     * una lista de objetos del tipo UserReport.
     *
     * @param jsonListString lista de reporte de usuarios en formato JSON .
     * @return lista de reporte de usuarios
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
     * Método que prepara el reporte de usuarios para ser descargado en formato
     * de hoja de calculo, este método utiliza la librería de ASPOSE.
     *
     * @param jsonListString
     */
    public void userReportAspose(String jsonListString) {
        try {
            if (items == null) {
                throw new Exception("No se puede descargar el reporte, porque no existen datos en la búsqueda");
            }
            if (items.isEmpty() == true) {
                throw new Exception("No se puede descargar el reporte, porque no existen datos en la búsqueda");
            }
            Workbook workbook = new Workbook();
            Cells cells = null;
            JSONArray jsonList = new JSONArray(jsonListString);
            List<UserReport> list = new ArrayList();
            ArrayList<String> listArray = new ArrayList<String>();

            for (int i = 0; i < jsonList.length(); i++) {

                UserReport item = JsonUtil.UserReportFromJson(jsonList.get(i).toString());
                try {
                    String userId = Integer.toString(item.getUserId());
                    DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String convertido = fechaHora.format(item.getVencimiento());
                    listArray.add(userId);
                    listArray.add(item.getFullName());
                    listArray.add(item.getAreaName());
                    listArray.add(item.getEmail());
                    listArray.add(item.getTipoInicio());
                    listArray.add(item.getSerie());
                    listArray.add(convertido);
                    Worksheet worksheet = workbook.getWorksheets().get(0);
                    worksheet.setName("Reporte de Usuarios");
                    cells = worksheet.getCells();
                    cells.importArrayList(listArray, 0, 0, true);
                } catch (Exception e) {
                    listArray.add(" ");
                }
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
//            workbook.save("C:\\Users\\MARLUX\\Desktop\\aspose\\ReporteUsuarios.xls");

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage().replace("java.lang.Exception:", ""));
        }
    }

    public void userReport() {
        try {
            String path = MessageFormat.format("userreport/users/{0}", getSessionId());
            String jsonresult = wsUtil.ExecuteGetRestService(trace, BASE_URI, path);
            userReportAspose(jsonresult);
        } catch (Exception e) {
        }
    }

    public void adminReportAspose() {
        Workbook workbook = new Workbook();
        Worksheet worksheet = workbook.getWorksheets().get(0);
        Cells cells = worksheet.getCells();
        int recorrido = 0;
        worksheet.setName("Reporte de usuarios");

        try {
            if (items == null) {
                throw new Exception("No se puede descargar el reporte, porque no existen datos en la búsqueda");
            }
            if (items.isEmpty() == true) {
                throw new Exception("No se puede descargar el reporte, porque no existen datos en la búsqueda");
            }

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

                cells.importArrayList(listArray, recorrido, 0, false);
                listArray.clear();
            }
            recorrido = 0;
            cells.get("A1").putValue("ID USUARIO");
            cells.get("B1").putValue("NOMBRE DE USUARIO");
            cells.get("C1").putValue("ÁREA");
            cells.get("D1").putValue("CORREO ELECTRÓNICO");
            cells.get("E1").putValue("TIPO DE AUTENTICACIÓN");
            cells.get("F1").putValue("NÚMERO DE SERIE DE CERTIFICADO");
            cells.get("G1").putValue("FECHA DE VENCIMIENTO DE CERTIFICADO");
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
//            workbook.save("C:\\Users\\MARLUX\\Desktop\\aspose\\ReporteUsuarios.xls");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage().replace("java.lang.Exception:", ""));
        }
    }

}
