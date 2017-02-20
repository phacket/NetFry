
package com.seguriboxltvweb.jsf.util;

import com.seguriboxltvweb.domain.Area;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.joda.time.DateTime;
import org.json.JSONObject;

/**
 *Clase que contiene las utilerias que se usan en el sistema.
 * @author Germán Hernández López.
 */
public class Util {

    private final WebTarget webTarget;
    private final Client client;
    //TODO: Cambia esta configuracion a un archivo configuracion
    private final String BASE_URI;
    private final String sessionId = "sessionid";

    /**
     * Constructor de la clase Util.
     * @throws IOException 
     */
    public Util() throws IOException {
        client = ClientBuilder.newClient();
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
        webTarget = client.target(BASE_URI).path("systemparameter");

    }

    /**
     * Método que convierte una fecha en formato de milisegundos
     * @param fecha fecha a convertir
     * @return fecha en milisegundos.
     */
    public static long toLong(Date fecha) {
        long miliseconds;
        miliseconds = fecha.getTime();
        return miliseconds;
    }

    /**
     * Método que obtiene el nombre de un área en base a su identificador
     * @param areaid id del para buscar el nombre del area.
     * @return JSON de la respuesta del servicio
     * @throws IOException 
     */
    public static String getAreaName(String areaid) throws IOException {
        Client client2;
        String BASE_URI2 = new SeguriboxGetProperties().getPropValues("UrlBase");

        client2 = ClientBuilder.newClient();

        WebTarget webTarget2 = client2.target(BASE_URI2).path("area");

        String name = "";
        //TODO: Cambia esta configuracion a un archivo configuracion

        String session = SessionUtils.getSessionId();

        try {

            WebTarget resource = webTarget2;
            String rute = resource.getUri().toString();

            Response response = resource.path(MessageFormat.format("{0}/{1}", new Object[]{session, areaid})).request(MediaType.APPLICATION_JSON + ";charset=utf-8").get(Response.class);
            String item = response.readEntity(String.class);
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                throw new Exception(item);
            } else {

                Area area1 = JsonUtil.AreaFromJson(item);
                return area1.getAreaName();

            }

        } catch (Exception e) {
            //JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Area").getString("GetAreaId"));
            return null;
        }

    }
    /**
     * Método que convierte un nulo en un String.
     * @param item objeto a convertir en cadena
     * @return Cadena convertida
     */
    public static String NullToString(Object item) {
        String str = "" + item;
        if (item == null) {

            return "";
        }
        if (str.equals("null")) {

            return "";
        } else {

            return "" + item;
        }

    }
    /**
     * Método que convierte un nulo a un cero.
     * @param item objeto a convertir en un nulo.
     * @return entero convertido
     */
    public static int nullToZero(Object item) {

        if (item == null) {

            return 0;
        } else {

            return (int) item;
        }

    }
    /**
     * Método que convierte una fecha vacía a nula.
     * @param item fecha a convertir
     * @return fecha convertida
     */
    public static Date DateToNull(Object item) {

        if (item == null) {

            return null;
        } else {

            return (Date) item;
        }
    }
    /**
     * Método que convierte un nulo a booleano.
     * @param item método que convierte un vacio a booleano.
     * @return 
     */
    public static Boolean NUllToBoolean(Object item) {

        if (item == null) {
            return false;

        } else {

            return (boolean) item;
        }
    }
    /**
     * Método que convierte un vacio a false o a booleano
     * @param item
     * @return 
     */
    public static byte[] BytesToNUll(String item) {
        if (item == null) {

            return null;
        } else {

            return (byte[]) item.getBytes();
        }
    }
    /**
     * Método que sirve para poder obtener un system parameter de la base de datos.
     * @param keyBase Llave base para obtener el valor. 
     * @param key parametro para ver que elemtento del se requiere obtener el valor.
     * @return JSON con el system parameter.
     * @throws Exception 
     */
    public static String GetsystemParameter(String keyBase, String key) throws Exception {
        try {
            Client client = ClientBuilder.newClient();
            String BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
            WebTarget resource = client.target(BASE_URI).path("systemparameter");
            String sessionId = SessionUtils.getSessionId();
            String item = resource.path(MessageFormat.format("map/{0}", new Object[]{sessionId})).request(MediaType.APPLICATION_JSON + ";charset=utf-8").get(String.class);

            JSONObject jo = new JSONObject(item);
            JSONObject obj = jo.getJSONObject(keyBase);
            String str = obj.getString(key);

            return str;
        } catch (Exception e) {

            throw e;
        }
    }
    /**
     * Método que obtiene la fecha de hoy en formato UTC.
     * @return fecha en formato UTC. 
     */
    public static String GetTodayUTC() {

        String formatDate = "#YEAR-#MONTH-#DAYT00:00:00Z";
        DateTime dt = new DateTime();
        String year = dt.getYear() + "";
        String month = dt.getMonthOfYear() < 10 ? "0" + dt.getMonthOfYear() + "" : dt.getMonthOfYear() + "";
        String day = dt.getDayOfMonth() < 10 ? "0" + dt.getDayOfMonth() + "" : dt.getDayOfMonth() + "";
        formatDate = formatDate.replace("#YEAR", year);
        formatDate = formatDate.replace("#MONTH", month);
        formatDate = formatDate.replace("#DAY", day);

        return formatDate;
    }
    /**
     * Método que convierte una fecha en formato UTC
     * @param date fecha a convertir.
     * @return cadena con la fecha convertida.
     */
    public static String dateTooUtc(Date date) {
        String formatDate = "#YEAR-#MONTH-#DAYT00:00:00Z";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String year = cal.get(Calendar.YEAR) + "";
        String month = cal.get(Calendar.MONTH) < 10 ? "0" + (cal.get(Calendar.MONTH) + 1) : (cal.get(Calendar.MONTH) + 1) + "";
        String day = cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + cal.get(Calendar.DAY_OF_MONTH) : cal.get(Calendar.DAY_OF_MONTH) + "";
        formatDate = formatDate.replace("#YEAR", year);
        formatDate = formatDate.replace("#MONTH", month);
        formatDate = formatDate.replace("#DAY", day);
        return formatDate;
    }
    /**
     * 
     * @return 
     */
//    public static String ramdomNames() {
//
//        SecureRandom random = new SecureRandom();
//
//        return new BigInteger(15, random).toString(32);
//    }
    /**
     * Método que cambia el formato de una fecha a yyyy-MM-dd'T'HH:mm:ss.SSSZ".
     * @param date fecha para cambiar el formato
     * @return  fecha con el formato cambiado
     */
    public static String changeFormatDate(Date date) {

        Date myDate = date;
        if (date == null) {

            return null;
        } else {
            String str = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(myDate);

            return str;
        }
    }

}
