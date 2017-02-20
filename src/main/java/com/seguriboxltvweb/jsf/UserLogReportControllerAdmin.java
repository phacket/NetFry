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
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author Germán Hernández López
 * @version 1.0
 */
@Named("userLogReportControllerAdmin")
@SessionScoped
public class UserLogReportControllerAdmin implements Serializable {

    private List<UserLogReport> items = null;
    private UserLogReport selected;
    private UserLogReport userLog;
    private final String BASE_URI;
    private Date StartDate;
    private Date EndDate;
    private Date StartDateOld;
    private Date EndDateOld;
    private String Search;
    private String sessionId;
    private boolean trace = true;
    String SearchOld;

    /**
     * Constructor de la clase UserLogReportControllerAdmin.
     *
     * @throws IOException
     */
    public UserLogReportControllerAdmin() throws IOException {
        sessionId = SessionUtils.getSessionId();
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
    }

    /**
     *
     */
    public void getItemsSearch() {
        items = search();
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

    /**
     * Método que resetea la tabla y los campos de búsqueda.
     *
     * @return outcome a la página
     * @throws IOException userlogadmin.jsf
     */
    public String fillControls() throws IOException {
        if (items != null) {
            this.items.clear();
        }
        StartDate = null;
        EndDate = null;
        Search = "";
        FacesContext.getCurrentInstance().getExternalContext().redirect("userlogadmin.jsf");
        return "userlogadmin?faces-redirect=true";
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
     *
     * @return
     */
    public List<UserLogReport> getItems() {
        if (items == null) {
            items = null;

        }
        return items;
    }

    /**
     * Método que obtiene una lista de registros de usuarios con el resultado de
     * la búsqueda
     *
     * @return lista de usuarios.
     */
    public List<UserLogReport> search() {

        String jsonListString;
        SearchOld = Search;
        try {

            if (Search == null || Search.equals("")) {
                Search = "%";
            }

            if (StartDate == null && StartDateOld != null) {
                long startDatel = toLong(StartDateOld);
                long endDatel = toLong(EndDateOld);
//                if(StartDate==null){
//                    throw new Exception("");
//                }
//                if(EndDate==null){
//                
//                }
                if (startDatel > endDatel) {
                    throw new Exception("La fecha inicial debe ser menor a la fecha final");
                }
                String path = MessageFormat.format("userlogreport/admin/{0}/{1}/{2}/{3}", new Object[]{getSessionId(), Long.toString(startDatel), Long.toString(endDatel), getSearch()});
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
                String path = MessageFormat.format("userlogreport/admin/{0}/{1}/{2}/{3}", new Object[]{getSessionId(), Long.toString(startDatel), Long.toString(endDatel), getSearch()});
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
            JsfUtil.addErrorMessage(e.getMessage().replace("java.lang.Exception:", ""));
            return new ArrayList();
        }
    }

    /**
     * Método que convierte una lista de logueo de usuarios en una lista de
     * objetos de UserLogReport
     *
     * @param jsonListString lista en formato JSON
     * @return lista de reposte de logueo de usuario.
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

    public void cleanDate() {

        EndDate = null;
        EndDateOld = null;
        StartDateOld = null;
        items = new ArrayList<>();
    }

}
