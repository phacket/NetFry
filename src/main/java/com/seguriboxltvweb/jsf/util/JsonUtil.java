package com.seguriboxltvweb.jsf.util;

import com.seguriboxltvweb.domain.AlgorithmAsymmetric;
import com.seguriboxltvweb.domain.AlgorithmHash;
import com.seguriboxltvweb.domain.AlgorithmHistory;
import com.seguriboxltvweb.domain.AlgorithmSignSymm;
import com.seguriboxltvweb.domain.Area;
import com.seguriboxltvweb.domain.AuditTrailReport;
import com.seguriboxltvweb.domain.CertReport;
import com.seguriboxltvweb.domain.Document;
import com.seguriboxltvweb.domain.DocumentReport;

import com.seguriboxltvweb.domain.Groups;
import com.seguriboxltvweb.domain.HSMKey;
import com.seguriboxltvweb.domain.ProfileProcess;
import com.seguriboxltvweb.domain.SystemParameter;
import com.seguriboxltvweb.domain.SystemParameters;
import com.seguriboxltvweb.domain.Task;
import com.seguriboxltvweb.domain.UserLogReport;
import com.seguriboxltvweb.domain.UserReport;
import com.seguriboxltvweb.domain.UserSession;
import com.seguriboxltvweb.domain.UserState;
import com.seguriboxltvweb.domain.Users;
import com.seguriboxltvweb.domain.Usersp12;
import com.seguridata.certificate.bean.Certificate;
import com.seguridata.crypto.bean.AsymmAlgsJson;
import com.seguridata.crypto.bean.HashAlgsJson;
import com.seguridata.crypto.bean.SignAlgsJson;
import java.text.SimpleDateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;

//12:56:00-12.326
//12:56:00-12.32:56
public class JsonUtil {

    //
//    // Método que parsea las fechas de formatro UTC
//    private static Date parse(final String str) {
//        Calendar c = DatatypeConverter.parseDateTime(str);
//        return c.getTime();
//    }
    /**
     * Método que parsea las fechas de formatro UTC
     *
     *
     * @param str
     * @return
     */
    public static Date parse(String str) {

        Long l = Long.parseLong(str);
        Date date = new Date(l);
        return date;
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo
     *
     * @param str JSON a deserializar
     * @return Objeto del tipo Date deserializado.
     */
    public static Date DateFromJson(String str) {
        DateTime result;
        try {

            if ("1900".equals(str.substring(0, 3))) {

                return null;
            }

            switch (str.length()) {
                case 21:
                    str = str.substring(0, 9) + "T" + str.substring(11, 19) + ".647-05:00";

                    break;
                case 22:
                    str = str.substring(0, 9) + "T" + str.substring(11, 19) + ".647-05:00";

                    break;
                case 23:
                    str = str.substring(0, 9) + "T" + str.substring(11, 19) + ".647-05:00";

                    break;
                case 25:
                    str = str.substring(0, 19) + ".647" + str.substring(19, 25);

                    break;
                case 27:
                    str = str.substring(0, 19) + ".647" + str.substring(21, 27);

                    break;
                case 28:
                    str = str.substring(0, 19) + ".647" + str.substring(22, 28);

                    break;
                case 29:
                    str = str.substring(0, 19) + ".647" + str.substring(23, 29);

                    break;
            }

            String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
            DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
            result = dtf.parseDateTime(str);

        } catch (Exception e) {
            return null;
        }
        return result.toDate();

    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo CertReport
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo CertReport deserializado.
     */
    public static CertReport CertReportFromJson(String JSONEntity) {

        CertReport result = new CertReport();
        JSONObject obj = new JSONObject(JSONEntity);

        try {
            result.setUserType(Util.nullToZero(obj.get("userType")));
        } catch (Exception e) {
            result.setUserType(2);
        }
        try {
            result.setEmail(Util.NullToString(obj.get("email")));
        } catch (Exception e) {
            result.setEmail(null);
        }
        try {
            result.setFullName(Util.NullToString(obj.get("fullName")));
        } catch (Exception e) {
            result.setFullName(null);
        }

        try {
            result.setCertificateSerie(Util.NullToString(obj.get("serie")));
        } catch (Exception e) {
            result.setCertificateSerie(null);
        }

        try {
            result.setUserId(Util.nullToZero(obj.get("userId")));
        } catch (Exception e) {
            result.setUserId(0);
        }
        try {
            Date certificateExpiration = Util.DateToNull(parse(obj.get("vencimiento").toString()));
            result.setExpirationDate(certificateExpiration);
        } catch (Exception e) {

            result.setExpirationDate(null);
        }

        return result;
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo CertReport
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo CertReport deserializado.
     */
    public static CertReport CertReportGetUser(String JSONEntity, CertReport cert) {
        CertReport result = cert;
        JSONObject obj = new JSONObject(JSONEntity);
        try {
            result.setFullName(obj.getString("firstName") + " "
                    + obj.getString("middleName") + " " + obj.getString("lastName"));
        } catch (Exception e) {
            result.setFullName(null);
        }
        return result;
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo UserSession
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo UserSession deserializado.
     */
    public static UserSession UserSessionFromJson(String JSONEntity, UserSession user) {
        UserSession result = user;
        JSONObject obj = new JSONObject(JSONEntity);

        try {
            result.setSessionId(obj.getString("sessionId"));
        } catch (Exception e) {
        }
        try {
            result.setUserName(obj.getString("userName"));
        } catch (Exception e) {
        }
        try {
            result.setFullName(obj.getString("firstName") + " " + obj.getString("middleName")
                    + " " + obj.getString("lastName"));
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo Document
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo Document deserializado.
     */
    public static Document DocumentsFromJson(String JSONEntity) {
        JSONObject obj = new JSONObject(JSONEntity);

        Document result = new Document();
        try {
            result.setDocumentId(Util.nullToZero(obj.get("documentId")));
        } catch (Exception e) {

        }
        try {
            result.setCreatedByUser(Util.NullToString(obj.get("fullName")));
        } catch (Exception e) {

        }
        try {
            result.setDocumentName(Util.NullToString(obj.get("documentName")));
        } catch (Exception e) {

        }
        try {
            result.setSignatureType(Util.NullToString(obj.get("tipoFirma")));
        } catch (Exception e) {

        }
        try {
            result.setDateCreated(Util.DateToNull(parse(obj.get("dateCreated").toString())));

        } catch (Exception e) {

        }
        try {
            result.setExpirationDate(Util.DateToNull(parse(obj.get("expirationDate").toString())));
        } catch (Exception e) {

        }
        return result;
    }

    /**
     * Método que se encarga de serializar un objeto del tipo Document a un
     * JSON.
     *
     * @param item Objeto del tipo Document
     * @return JSON resultado de la serialización.
     */
    public static String JsonFromDocument(Document item) {
        JSONObject obj = new JSONObject();
        obj.put("documentId", item.getDocumentId());
        obj.put("fullName", item.getCreatedByUser());
        obj.put("documentName", item.getDocumentName());
        obj.put("tipoFirma", item.getSignatureType());
        obj.put("dateCreated", new DateTime(item.getDateCreated()).withZone(DateTimeZone.UTC).toString());
        obj.put("expirationDate", new DateTime(item.getExpirationDate()).withZone(DateTimeZone.UTC).toString());

        return obj.toString();
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo HSMKey
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo HSMKey deserializado.
     */
    public static HSMKey HSMKeyFromJson(String JSONEntity) {
        HSMKey result = new HSMKey();
        JSONObject obj = new JSONObject(JSONEntity);

        try {
            try {
                Date referenceDate = Util.DateToNull(parse(obj.get("referenceDate").toString()));
                result.setReferenceDate(referenceDate);
            } catch (Exception e) {

                result.setReferenceDate(null);
            }

            try {
                result.setHSMKeyId(Util.nullToZero(obj.get("hSMKeyId")));

            } catch (Exception e) {
                result.setHSMKeyId(null);

            }
            try {
                result.setIsActive(Util.NUllToBoolean(obj.get("isActive")));

            } catch (Exception e) {
                result.setIsActive(false);

            }
            try {
                result.setKeySize(Util.nullToZero(obj.get("keySize")));

            } catch (Exception e) {
                result.setKeySize(null);

            }
            try {
                result.setKeyTag(Util.NullToString(obj.getString("keyTag")));

            } catch (Exception e) {
                result.setKeyTag(null);

            }
            try {
                result.setOid(Util.NullToString(obj.get("oid")));

            } catch (Exception e) {
                result.setOid(null);

            }
            try {
                Date certificateExpiration = Util.DateToNull(parse(obj.get("certificateExpiration").toString()));
                result.setCertificateExpiration(certificateExpiration);
            } catch (Exception e) {

                result.setCertificateExpiration(null);
            }

            try {
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] certificado = decoder.decodeBuffer(Util.NullToString(obj.getString("certificate")));
                result.setCertificate(certificado);
            } catch (Exception e) {
                result.setCertificate(null);
            }

            return result;
        } catch (JSONException e) {

            return null;
        }

    }

    /**
     * Método que se encarga de serializar un objeto del tipo HSMKey a un JSON.
     *
     * @param item Objeto del tipo HSMKey
     * @return JSON resultado de la serialización.
     */
    public static String JsonFromHSMKey(HSMKey item) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("hSMKeyId", item.getHSMKeyId());
            obj.put("isActive", item.getIsActive());
            obj.put("keySize", item.getKeySize());
            obj.put("keyTag", item.getKeyTag());
            obj.put("oid", item.getOid());
            obj.put("referenceDate", Util.changeFormatDate(item.getReferenceDate()));
        } catch (JSONException e) {

        }
        return obj.toString();

    }

    /**
     * Método que se encarga de serializar un objeto del tipo DocumentReport a
     * un JSON.
     *
     * @param item Objeto del tipo DocumentReport
     * @return JSON resultado de la serialización.
     */
    public static String JsonFromDocumentReport(DocumentReport item) {
        JSONObject obj = new JSONObject();
        obj.put("documentId", item.getDocumentId());
        obj.put("fullName", item.getFullName());
        obj.put("documentName", item.getDocumentName());
        obj.put("tipoFirma", item.getTipoFirma());
        obj.put("firmas", item.getFirmas());
        obj.put("dateCreated", Util.changeFormatDate(item.getDateCreated()));
        obj.put("expirationDate", Util.changeFormatDate(item.getExpirationDate()));

        return obj.toString();
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo com.seguridata.hsm.bean.HSMKey
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo com.seguridata.hsm.bean.HSMKey deserializado.
     */
    public static com.seguridata.hsm.bean.HSMKey SDHSMKeyFromJson(String JSONEntity) {
        com.seguridata.hsm.bean.HSMKey result = new com.seguridata.hsm.bean.HSMKey();
        JSONObject obj = new JSONObject(JSONEntity);
        result.setB64Cert(Util.NullToString(obj.get("b64Cert")));
        result.setKeySize(Util.nullToZero(obj.get("keySize")));
        result.setLabel(Util.NullToString(obj.get("label")));
        result.setOid(Util.NullToString(obj.get("oid")));
        return result;
    }

    /**
     * Método que se encarga de serializar un objeto del tipo -
     * com.seguridata.hsm.bean.HSMKey a un JSON.
     *
     * @param item Objeto del tipo - com.seguridata.hsm.bean.HSMKey
     * @return JSON resultado de la serialización.
     */
    public static String JsonFromSDHSMKey(com.seguridata.hsm.bean.HSMKey item) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("b64Cert", item.getB64Cert());
            obj.put("keySize", item.getKeySize());
            obj.put("label", item.getLabel());
            obj.put("oid", item.getOid());
        } catch (Exception e) {
        }
        return obj.toString();
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo Certificate
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo Certificate deserializado.
     */
    public static Certificate CertificateFromJson(String JSONEntity) {
        Certificate result = new Certificate();
        JSONObject obj = new JSONObject(JSONEntity);
        try {
            result.setSerialNumber(Util.NullToString(obj.get("serialNumber")));
        } catch (Exception e) {
            result.setSerialNumber(null);
        }
        try {
            result.setSignatureAlgorithm(Util.NullToString(obj.get("signatureAlgorithm")));
        } catch (Exception e) {
            result.setSignatureAlgorithm(null);
        }
        try {
            result.setVersion(Util.NullToString(obj.get("version")));
        } catch (Exception e) {
            result.setVersion(null);
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
            Date date = formatter.parse(Util.NullToString(obj.get("notAfter")));
            result.setNotAfter(date);
        } catch (Exception e) {
            result.setNotAfter(null);
        }
        return result;
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo Area
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo Area deserializado.
     */
    public static Area AreaFromJson(String JSONEntity) {
        Area result = new Area();
        JSONObject obj = new JSONObject(JSONEntity);
        try {
            result.setAreaId((short) Util.nullToZero(obj.get("areaId")));
        } catch (Exception e) {
            result.setAreaId((short) 0);
        }
        try {
            result.setAreaName(Util.NullToString(obj.get("areaName")));

        } catch (Exception e) {
            result.setAreaName(null);
        }
        try {
            result.setIsActive(Util.NUllToBoolean(obj.get("isActive")));

        } catch (Exception e) {
            result.setIsActive(true);
        }
        try {
            String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
            DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
            DateTime referencedate = dtf.parseDateTime(Util.NullToString(obj.get("referenceDate")));
            result.setReferenceDate(referencedate.toDate());
        } catch (Exception e) {
            result.setReferenceDate(null);
        }
        return result;
    }

    /**
     * Método que se encarga de serializar un objeto del tipo Area a un JSON.
     *
     * @param item Objeto del tipo Area
     * @return JSON resultado de la serialización.
     */
    public static String JsonFromArea(Area item) {
        JSONObject obj = new JSONObject();
        obj.put("areaId", item.getAreaId());
        obj.put("areaName", item.getAreaName());
        obj.put("isActive", item.getIsActive());
        obj.put("referenceDate", item.getReferenceDate());
        return obj.toString();
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo UserReport
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo UserReport deserializado.
     */
    public static UserReport UserReportFromJson(String JSONEntity) {
        JSONObject obj = new JSONObject(JSONEntity);
        UserReport result = new UserReport();
        try {
            result.setUserId(Util.nullToZero(obj.get("userId")));
        } catch (Exception e) {
            result.setUserId(0);
        }
        try {
            result.setUserId(Util.nullToZero(obj.get("userId")));
        } catch (Exception e) {
            result.setUserId(0);
        }
        try {
            result.setEmail(Util.NullToString(obj.get("email")));
        } catch (Exception e) {
            result.setEmail(null);
        }
        try {
            result.setFullName(Util.NullToString(obj.get("fullName")));
        } catch (Exception e) {
            result.setFullName(null);
        }
        try {
            result.setHashCertificate(Util.NullToString(obj.get("hashCertificate")));
        } catch (Exception e) {
            result.setHashCertificate(null);
        }
        try {
            result.setTipoInicio(Util.NullToString(obj.get("tipoInicio")));
        } catch (Exception e) {
            result.setTipoInicio(null);
        }
        try {
            result.setSerie(Util.NullToString(obj.get("serie")));
        } catch (Exception e) {
            result.setSerie(null);
        }
        try {
            result.setTipoUsuario(Util.NullToString(obj.get("tipoUsuario")));
        } catch (Exception e) {
            result.setTipoUsuario(null);
        }
//        try {
//            //Date reference = parse(Util.NullToString(obj.get("vencimiento")));
//            String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
//            DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
//            DateTime referencedate = dtf.parseDateTime(Util.NullToString(obj.get("vencimiento")));
//            result.setVencimiento(Util.DateToNull(referencedate));
//        } catch (Exception e) {
//            result.setVencimiento(null);
//        }

        try {
            result.setVencimiento((Util.DateToNull(parse(obj.get("vencimiento").toString()))));
        } catch (Exception e) {
            result.setVencimiento(null);
        }

        try {
            result.setUserName(Util.NullToString(obj.get("userName")));
        } catch (Exception e) {
            result.setUserName(null);
        }
        try {
            result.setAreaName(Util.NullToString(obj.get("areaName")));
        } catch (Exception e) {
            result.setAreaName(null);
        }
        return result;
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo DocumentReport
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo DocumentReport deserializado.
     */
    public static DocumentReport DocumentReportFromJson(String JSONEntity) {

        DocumentReport result = new DocumentReport();
        JSONObject obj = new JSONObject(JSONEntity);

        try {
            result.setDocumentId(Util.nullToZero(obj.get("documentId")));
        } catch (Exception e) {
            result.setDocumentId(0);
        }
        try {
            result.setFullName(Util.NullToString(obj.get("fullName")));
        } catch (Exception e) {
            result.setFullName(null);
        }
        try {
            result.setDocumentName(Util.NullToString(obj.get("documentName")));
        } catch (Exception e) {
            result.setDocumentName(null);
        }
        try {
            result.setTipoFirma(Util.NullToString(obj.get("tipoFirma")));
        } catch (Exception e) {
            result.setTipoFirma(null);
        }
        try {
            result.setFirmas((short) Util.nullToZero(obj.get("firmas")));
        } catch (Exception e) {
            result.setFirmas((short) 0);
        }
        try {
            result.setDateCreated(Util.DateToNull(parse(obj.get("dateCreated").toString())));

        } catch (Exception e) {
            result.setDateCreated(null);
        }
        try {
            result.setExpirationDate(Util.DateToNull(parse(obj.get("expirationDate").toString())));
        } catch (Exception e) {
            result.setExpirationDate(null);
        }
        return result;
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo AuditTrailReport
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo AuditTrailReport deserializado.
     */
    public static AuditTrailReport AuditTrailReportFromJson(String JSONEntity) {

        JSONObject obj = new JSONObject(JSONEntity);
        AuditTrailReport result = new AuditTrailReport();
        try {
            result.setCategoryCode(Util.NullToString(obj.get("categoryCode")));
        } catch (Exception e) {
            result.setCategoryCode(null);
        }
        try {
            result.setEventCode(Util.NullToString(obj.get("eventCode")));
        } catch (Exception e) {
            result.setEventCode(null);
        }

        try {
            result.setFullNameUser(Util.NullToString(obj.get("fullNameUser")));
        } catch (Exception e) {
            result.setFullNameUser(null);
        }
        try {
            result.setInstanceName(Util.NullToString(obj.get("instanceName")));
        } catch (Exception e) {
            result.setInstanceName(null);
        }
        try {
            result.setUserHostIP(Util.NullToString(obj.get("userHostIP")));
        } catch (Exception e) {
            result.setUserHostIP(null);
        }
        try {
            result.setNotes(Util.NullToString(obj.get("notes")));
        } catch (Exception e) {
            result.setNotes(null);
        }
        try {
            result.setObjectId(Util.nullToZero(obj.get("objectId")));
        } catch (Exception e) {
            result.setObjectId(0);
        }
        try {
            result.setObjectName(Util.NullToString(obj.get("objectName")));
        } catch (Exception e) {
            result.setObjectName(null);
        }
        try {
            result.setRecordId(Util.nullToZero(obj.get("recordId")));
        } catch (Exception e) {
            result.setRecordId(0);
        }
        //fechas
        try {
            result.setEventDate(Util.DateToNull(parse(obj.get("eventDate").toString())));
        } catch (Exception e) {
            result.setEventDate(null);
        }
        return result;
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo UserLogReport
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo UserLogReport deserializado.
     */
    public static UserLogReport UserLogReportFromJson(String JSONEntity) {
        UserLogReport result = new UserLogReport();

        JSONObject obj = new JSONObject(JSONEntity);
        // result.setActionType((short)Util.nullToZero(obj.get("actionType")));
        //  result.setDuration(Util.nullToZero(obj.get("duration")));
        try {
            result.setFullName(Util.NullToString(obj.get("fullName")));
        } catch (Exception e) {
            result.setFullName(null);
        }
        try {
            result.setHashcertificate(Util.NullToString(obj.get("hashcertificate")));
        } catch (Exception e) {
            result.setHashcertificate(null);
        }
        try {
            result.setiPAddress(Util.NullToString(obj.get("ipAddress")));
        } catch (Exception e) {
            result.setiPAddress(null);
        }
        try {
            result.setRecordId(Util.nullToZero(obj.get("recordId")));
        } catch (Exception e) {
            result.setRecordId(null);
        }
        try {
            result.setUserName(Util.NullToString(obj.get("userName")));
        } catch (Exception e) {
            result.setUserName(null);
        }
        //fechas
        try {
            result.setEventDate((Util.DateToNull(parse(obj.get("eventDate").toString()))));
        } catch (Exception e) {
            result.setEventDate(null);
        }
        return result;
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo HSMKey
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo HSMKey deserializado.
     */
    public static HSMKey listHSM(String JSONEntity) {
        JSONObject obj = new JSONObject(JSONEntity);
        HSMKey hsm = new HSMKey();
        hsm.setHSMKeyId(Util.nullToZero(obj.get("hSMKeyId")));
        hsm.setKeyTag(Util.NullToString(obj.get("keyTag")));
        return hsm;
    }

    /**
     * Método que se encarga de serializar un objeto del tipo SystemParameter a
     * un JSON.
     *
     * @param item Objeto del tipo SystemParameter
     * @return JSON resultado de la serialización.
     */
    public static String JsonFromSystemParameters(SystemParameter item) {
        JSONObject obj = new JSONObject();
        obj.put("category", item.getCategory());
        obj.put("parameterName", item.getParameterName());
        obj.put("parameterValue", item.getParameterValue());
        return obj.toString();
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo SystemParameters
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo SystemParameters deserializado.
     */
    public static SystemParameters systemParametersFromJson(String JSONEntity, SystemParameters systemParameters) {
        JSONObject obj = new JSONObject(JSONEntity);
        SystemParameters result = systemParameters;
        String parameterName = obj.getString("parameterName");
        try {
            if (parameterName.equals("STORAGETIME")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }

                result.setSTORAGETIME(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("ORGANIZATION")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setORGANIZATION(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("KEYAGENT")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setKEYAGENT(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("SEGURIBOXAPIPASSWORD")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setSEGURIBOXAPIPASSWORD(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("SEGURIBOXAPIUSERNAME")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setSEGURIBOXAPIUSERNAME(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("SEGURIBOXAPIURL")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setSEGURIBOXAPIURL(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("SEGURISERVERPASSWORD")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setSEGURISERVERPASSWORD(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("SEGURISERVERUSERNAME")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setSEGURISERVERUSERNAME(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("SEGURISERVERURL")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setSEGURISERVERURL(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("SEGURISIGNPASSWORD")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setSEGURISIGNPASSWORD(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("SEGURISIGNUSERNAME")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setSEGURISIGNUSERNAME(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("SEGURISIGNURL")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setSEGURISIGNURLs(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("ARCHIVEPASSWORD")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setARCHIVEPASSWORD(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("ARCHIVEUSERNAME")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setARCHIVEUSERNAME(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("ARCHIVEURL")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setARCHIVEURL(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("DOPKCS11PASSWORD")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setDOPKCS11PASSWORD(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("DOPKCS11USERNAME")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setDOPKCS11USERNAME(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("DOPKCS11URL")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setDOPKCS11URL(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("SMTPSSLS")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setSMTPSSLS(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("SMTPPASSWORD")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setSMTPPASSWORD(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("SMTPUSER")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setSMTPUSER(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("SMTPANONIMOUS")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setSMTPANONIMOUS(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("SMTPPORT")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setSMTPPORT(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("FILEEXTENSIONS")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setFILEEXTENSIONS(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("FILESIZEMAX")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setFILESIZEMAX(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("EMAILSTORAGEDAYS")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setEMAILSTORAGEDAYS(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("SMTPSERVER")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setSMTPSERVER(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("CRYPTOURL")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setCRYPTOURL(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("CRYPTOUSERNAME")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setCRYPTOUSERNAME(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("CRYPTOPASSWORD")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setCRYPTOPASSWORD(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("HSMKEYURL")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setHSMKEYURL(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("HSMKEYUSERNAME")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setHSMKEYUSERNAME(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("HSMKEYPASSWORD")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setHSMKEYPASSWORD(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("SIGNEDALGORITHM")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setSIGNEDALGORITHM(systemParameter);
            }
        } catch (Exception e) {
        }
        //----------------------------
        try {
            if (parameterName.equals("EVENTDAYS")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setEVENTDAYS(systemParameter);
            }
        } catch (Exception e) {
        }
        try {
            if (parameterName.equals("DOPKCS1URL")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setDOPKCS1URL(systemParameter);
            }
        } catch (Exception e) {
        }
        try {
            if (parameterName.equals("DOPKCS1USERNAME")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setDOPKCS1USERNAME(systemParameter);
            }
        } catch (Exception e) {
        }
        try {
            if (parameterName.equals("DOPKCS1PASSWORD")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setDOPKCS1PASSWORD(systemParameter);
            }
        } catch (Exception e) {
        }
        try {
            if (parameterName.equals("DOPKCS7URL")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setDOPKCS7URL(systemParameter);
            }
        } catch (Exception e) {
        }
        try {
            if (parameterName.equals("DOPKCS7USERNAME")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setDOPKCS7USERNAME(systemParameter);
            }
        } catch (Exception e) {
        }
        try {
            if (parameterName.equals("DOPKCS7PASSWORD")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setDOPKCS7PASSWORD(systemParameter);

            }
        } catch (Exception e) {
        }
        try {
            if (parameterName.equals("FROMEMAIL")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setFROMEMAIL(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("ASPOSELIC")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setASPOSELIC(systemParameter);
            }
        } catch (Exception e) {
        }
        try {
            if (parameterName.equals("EMAILTEMPLATE")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setEMAILTEMPLATE(systemParameter);
            }
        } catch (Exception e) {
        }
        try {
            if (parameterName.equals("FILESIZEZIP")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setFILESIZEZIP(systemParameter);
            }
        } catch (Exception e) {
        }
        try {
            if (parameterName.equals("FILESIZEZIPDAYS")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setFILESIZEZIPDAYS(systemParameter);
            }
        } catch (Exception e) {
        }

        try {
            if (parameterName.equals("URLUSERCERT")) {
                SystemParameter systemParameter = new SystemParameter();
                try {
                    systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
                } catch (Exception e) {
                    systemParameter.setParameterName("");
                }
                try {
                    systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
                } catch (Exception e) {
                    systemParameter.setParameterValue("");
                }
                try {
                    systemParameter.setCategory(Util.NullToString(obj.get("category")));
                } catch (Exception e) {
                    systemParameter.setCategory("");
                }
                result.setURLUSERCERT(systemParameter);
            }
        } catch (Exception e) {
        }

        return result;
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo AlgorithmSignSymm
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo AlgorithmSignSymm deserializado.
     */
    public static AlgorithmSignSymm listAlgorithm(String JSONEntity) {
        JSONObject obj = new JSONObject(JSONEntity);
        AlgorithmSignSymm algorithm = new AlgorithmSignSymm();
        try {
            algorithm.setAlgorithmSignId(Util.nullToZero(obj.get("algorithmSignId")));
        } catch (Exception e) {

        }
        try {
            algorithm.setAlgorithmName(Util.NullToString(obj.get("algorithmName")));
        } catch (Exception e) {

        }
        return algorithm;
    }

    /**
     * Método que se encarga de serializar un objeto del tipo
     * AlgorithmAsymmetric a un JSON.
     *
     * @param item Objeto del tipo AlgorithmAsymmetric
     * @return JSON resultado de la serialización.
     */
    public static String JsonFromAlgorithmAsymmetric(AlgorithmAsymmetric item) {
        try {

            JSONObject obj = new JSONObject();
            obj.put("algorithmAsymmetricId", item.getAlgorithmAsymmetricId());
            obj.put("algorithmName", item.getAlgorithmName());
            obj.put("isActive", item.getIsActive());
            obj.put("binOid", item.getBinOid());
            obj.put("deltaBits", item.getDeltaBits());
            obj.put("maxBits", item.getMaxBits());
            obj.put("minBits", item.getMinBits());
            obj.put("minSecureBits", item.getMinSecureBits());
            obj.put("oid", item.getOid());
            obj.put("referenceDate", Util.changeFormatDate(item.getReferenceDate()));
            obj.put("secure", item.getSecure());
            obj.put("size", item.getSize());
            obj.put("expirationDate", Util.changeFormatDate(item.getExpirationDate()));

            return obj.toString();
        } catch (Exception e) {

            return null;
        }

    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo AlgorithmAsymmetric
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo AlgorithmAsymmetric deserializado.
     */
    public static AlgorithmAsymmetric AlgorithmAsymmetricFromJson(String JSONEntity) {
        try {
            AlgorithmAsymmetric result = new AlgorithmAsymmetric();
            JSONObject obj = new JSONObject(JSONEntity);

            try {
                result.setAlgorithmAsymmetricId(Util.nullToZero(obj.get("algorithmAsymmetricId")));

            } catch (Exception e) {
                result.setAlgorithmAsymmetricId(null);
            }
            try {
                result.setAlgorithmName(Util.NullToString(obj.get("algorithmName")));
            } catch (Exception e) {
                result.setAlgorithmName(null);
            }
            try {
                result.setIsActive(Util.NUllToBoolean(obj.get("isActive")));

            } catch (Exception e) {
                result.setIsActive(false);

            }
            try {
                result.setBinOid(Util.NullToString(obj.getString("binOid")));

            } catch (Exception e) {
                result.setBinOid(null);

            }
            try {
                result.setDeltaBits(Util.nullToZero(obj.get("deltaBits")));

            } catch (Exception e) {
                result.setDeltaBits(0);

            }
            try {
                result.setMaxBits(Util.nullToZero(obj.get("maxBits")));
            } catch (Exception e) {
                result.setMaxBits(0);
            }
            try {
                result.setMinBits(Util.nullToZero(obj.get("minBits")));
            } catch (Exception e) {
                result.setMinBits(0);
            }
            try {
                result.setMinSecureBits(Util.nullToZero(obj.get("minSecureBits")));
            } catch (Exception e) {
                result.setMinSecureBits(0);
            }
            try {
                result.setOid(Util.NullToString(obj.get("oid")));
            } catch (Exception e) {
                result.setOid(null);
            }
            try {
                result.setSize(Util.nullToZero(obj.get("size")));
            } catch (Exception e) {
                result.setSize(0);
            }
            try {
                result.setSecure(obj.getInt("secure"));
            } catch (Exception e) {
                result.setSecure(0);
            }

            try {
                Date expiration = Util.DateToNull(parse(obj.get("expirationDate").toString()));
                result.setExpirationDate(expiration);
            } catch (Exception e) {

                result.setExpirationDate(null);
            }
            try {
                Date reference = Util.DateToNull(parse(obj.get("referenceDate").toString()));
                result.setReferenceDate(reference);
            } catch (Exception e) {
                result.setReferenceDate(null);
            }

            return result;
        } catch (Exception e) {

            return null;
        }
    }

    /**
     * Método que se encarga de serializar un objeto del tipo AlgorithmHash a un
     * JSON.
     *
     * @param item Objeto del tipo AlgorithmHash
     * @return JSON resultado de la serialización.
     */
    public static String JsonFromAlgorithmHash(AlgorithmHash item) {
        try {

            JSONObject obj = new JSONObject();
            obj.put("algorithmHashId", item.getAlgorithmHashId());
            obj.put("algorithmName", item.getAlgorithmName());
            obj.put("binOid", item.getBinOid());
            obj.put("bits", item.getBits());
            obj.put("bytes", item.getBytes());
            obj.put("isActive", item.getIsActive());
            obj.put("oid", item.getOid());
            obj.put("referenceDate", Util.changeFormatDate(item.getReferenceDate()));
            obj.put("secure", item.getSecure());

            return obj.toString();
        } catch (Exception e) {

            return null;
        }
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo AlgorithmHash
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo AlgorithmHash deserializado.
     */
    public static AlgorithmHash AlgorithmHashFromJson(String JSONEntity) {
        try {
            AlgorithmHash result = new AlgorithmHash();
            String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
            DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);

            JSONObject obj = new JSONObject(JSONEntity);

            try {
                result.setAlgorithmHashId(Util.nullToZero(obj.get("algorithmHashId")));

            } catch (Exception e) {
                result.setAlgorithmHashId(null);

            }
            try {
                result.setAlgorithmName(Util.NullToString(obj.get("algorithmName")));

            } catch (Exception e) {
                result.setAlgorithmName(null);

            }
            try {
                result.setBinOid(Util.NullToString(obj.get("binOid")));

            } catch (Exception e) {
                result.setBinOid(null);

            }
            try {
                result.setBits(Util.nullToZero(obj.get("bits")));

            } catch (Exception e) {
                result.setBits(null);

            }
            try {
                result.setBytes(Util.nullToZero(obj.get("bytes")));
            } catch (Exception e) {
                result.setBytes(null);
            }
            try {
                result.setIsActive(Util.NUllToBoolean(obj.get("isActive")));
            } catch (Exception e) {
                result.setIsActive(false);
            }
            try {
                result.setOid(Util.NullToString(obj.get("oid")));

            } catch (Exception e) {
                result.setOid(null);

            }
            try {

                Date reference = Util.DateToNull(parse(obj.get("referenceDate").toString()));
                result.setReferenceDate(reference);

            } catch (Exception e) {
                result.setReferenceDate(null);
            }

            try {

                Date expiration = Util.DateToNull(parse(obj.get("referenceDate").toString()));
                result.setReferenceDate(expiration);

            } catch (Exception e) {
                result.setReferenceDate(null);
            }

            try {
                result.setSecure(Util.nullToZero(obj.get("secure")));

            } catch (Exception e) {
                result.setSecure(null);

            }

            return result;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * Método que se encarga de serializar un objeto del tipo AlgorithmSignSymm
     * a un JSON.
     *
     * @param item Objeto del tipo AlgorithmSignSymm
     * @return JSON resultado de la serialización.
     */
    public static String JsonFromAlgorithSymm(AlgorithmSignSymm item) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("algorithmAsymmetricId", item.getAlgorithmAsymmetricId());
            obj.put("algorithmHashId", item.getAlgorithmHashId());
            obj.put("algorithmName", item.getAlgorithmName());
            obj.put("algorithmSignId", item.getAlgorithmSignId());
            obj.put("asymmBin", item.getAsymmBin());
            obj.put("binOid", item.getBinOid());
            obj.put("hashBin", item.getHashBin());
            obj.put("isActive", item.getIsActive());
            obj.put("oid", item.getOid());
            obj.put("referenceDate", Util.changeFormatDate(item.getReferenceDate()));
            obj.put("asymetricName", item.getAsymetricName());
            obj.put("hashName", item.getHashName());

            return obj.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo AlgorithmSignSymm
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo AlgorithmSignSymm deserializado.
     */
    public static AlgorithmSignSymm AlgorithmSignSymmFromJson(String JSONEntity) {
        try {
            AlgorithmSignSymm result = new AlgorithmSignSymm();
            JSONObject obj = new JSONObject(JSONEntity);

            try {
                result.setAlgorithmAsymmetricId(Util.nullToZero(obj.get("algorithmAsymmetricId")));

            } catch (Exception e) {
                result.setAlgorithmAsymmetricId(0);
            }
            try {
                result.setAlgorithmHashId(Util.nullToZero(obj.get("algorithmHashId")));

            } catch (Exception e) {
                result.setAlgorithmHashId(0);
            }
            try {
                result.setAlgorithmName(Util.NullToString(obj.get("algorithmName")));

            } catch (Exception e) {
                result.setAlgorithmName(null);
            }
            try {
                result.setAlgorithmSignId(Util.nullToZero(obj.get("algorithmSignId")));

            } catch (Exception e) {
                result.setAlgorithmSignId(0);
            }
            try {
                result.setAsymmBin(Util.NullToString(obj.get("asymmBin")));
            } catch (Exception e) {
                result.setAsymmBin(null);
            }
            try {
                result.setBinOid(Util.NullToString(obj.get("binOid")));

            } catch (Exception e) {
                result.setBinOid(null);
            }
            try {
                result.setHashBin(Util.NullToString(obj.get("hashBin")));
            } catch (Exception e) {
                result.setHashBin(null);
            }
            try {
                result.setIsActive(Util.NUllToBoolean(obj.get("isActive")));
            } catch (Exception e) {
                result.setIsActive(false);
            }
            try {
                result.setOid(Util.NullToString(obj.get("oid")));
            } catch (Exception e) {
                result.setOid(null);
            }
            try {
                Date reference = Util.DateToNull(parse(obj.get("referenceDate").toString()));
                result.setReferenceDate(Util.DateToNull(reference));

            } catch (Exception e) {

                result.setReferenceDate(null);
            }
            try {
                Date expiration = Util.DateToNull(parse(obj.get("expirationDate").toString()));
                result.setExpirationDate(expiration);
            } catch (Exception e) {
                result.setExpirationDate(null);
            }

            try {
                result.setAsymetricName(Util.NullToString(obj.get("asymetricName")));
            } catch (Exception e) {
                result.setAsymetricName(null);
            }

            try {
                result.setHashName(Util.NullToString(obj.get("hashName")));

            } catch (Exception e) {
                result.setHashName(null);
            }

            return result;
        } catch (Exception e) {

            return null;
        }
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo AlgorithmHistory
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo AlgorithmHistory deserializado.
     */
    public static AlgorithmHistory AlgorithmHistoryFromJson(String JSONEntity) {
        try {
            String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
            DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
            AlgorithmHistory result = new AlgorithmHistory();
            JSONObject obj = new JSONObject(JSONEntity);
            try {
                result.setAction(Util.NullToString(obj.get("action")));

            } catch (Exception e) {
                result.setAction(null);

            }
            try {
                result.setDetails(Util.NullToString(obj.get("details")));

            } catch (Exception e) {
                result.setDetails(null);

            }
            try {
                result.setEventId(Util.nullToZero(obj.get("eventId")));

            } catch (Exception e) {
                result.setEventId(0);

            }
            try {
                result.setHostName(Util.NullToString(obj.get("hostName")));

            } catch (Exception e) {
                result.setHostName(null);

            }
            try {
                result.setUserId(Util.nullToZero(obj.get("userId")));
            } catch (Exception e) {
                result.setUserId(null);
            }
            try {
                Date referencedate = Util.DateToNull(parse(obj.get("referenceDate").toString()));
                result.setReferenceDate(referencedate);
            } catch (Exception e) {

                result.setReferenceDate(null);
            }

            return result;
        } catch (Exception e) {

            return null;
        }
    }

    /**
     * Método que se encarga de serializar un objeto del tipo AlgorithmHistory a
     * un JSON.
     *
     * @param item Objeto del tipo AlgorithmHistory
     * @return JSON resultado de la serialización.
     */
    public static String JsonFromAlgorithmHistory(AlgorithmHistory item) {
        try {

            JSONObject obj = new JSONObject();
            obj.put("action", item.getAction());
            obj.put("details", item.getDetails());
            obj.put("eventId", item.getEventId());
            obj.put("hostName", item.getHostName());
            obj.put("referenceDate", item.getReferenceDate());
            obj.put("userId", item.getUserId());

            return obj.toString();
        } catch (Exception e) {

            return null;
        }
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo AsymmAlgsJson
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo AsymmAlgsJson deserializado.
     */
    public static AsymmAlgsJson AssymetricAlgsFronJson(String JSONEntity) {
        AsymmAlgsJson result = new AsymmAlgsJson();
        JSONObject obj = new JSONObject(JSONEntity);

        result.setName(Util.NullToString(obj.get("name")));
        result.setBinOid(Util.NullToString(obj.get("binOid")));
        result.setDeltaBits(Util.nullToZero(obj.get("deltaBits")));
        result.setMaxBits(Util.nullToZero(obj.get("maxBits")));
        result.setMinBits(Util.nullToZero(obj.get("minBits")));
        result.setMinSecureBits(Util.nullToZero(obj.get("minSecureBits")));
        result.setSecure(Util.NUllToBoolean(obj.get("secure")));
        result.setStrOid(Util.NullToString(obj.get("strOid")));
        return result;
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo SignAlgsJson
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo SignAlgsJson deserializado.
     */
    public static SignAlgsJson SignatureAlgsFromJson(String JSONEntity) {
        SignAlgsJson result = new SignAlgsJson();
        JSONObject obj = new JSONObject(JSONEntity);

        result.setAsymmBinAlg(Util.NullToString(obj.get("asymmBinAlg")));
        result.setBinOid(Util.NullToString(obj.get("binOid")));
        result.setHashBinAlg(Util.NullToString(obj.get("hashBinAlg")));
        result.setName(Util.NullToString(obj.get("name")));
        result.setStrOid(Util.NullToString(obj.get("strOid")));
        return result;
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo HashAlgsJson
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo HashAlgsJson deserializado.
     */
    public static HashAlgsJson HashAlgsFromJson(String JSONEntity) {
        HashAlgsJson result = new HashAlgsJson();
        JSONObject obj = new JSONObject(JSONEntity);

        result.setBinOId(Util.NullToString(obj.get("binOId")));
        result.setBits(Util.nullToZero(obj.get("bits")));
        result.setBytes(Util.nullToZero(obj.get("bytes")));
        result.setName(Util.NullToString(obj.get("name")));
        result.setSecure(Util.NUllToBoolean(obj.get("secure")));
        result.setStrOid(Util.NullToString(obj.get("strOid")));
        return result;
    }

    /**
     * Método que se encarga de serializar un objeto del tipo AsymmAlgsJson a un
     * JSON.
     *
     * @param item Objeto del tipo AsymmAlgsJson
     * @return JSON resultado de la serialización.
     */
    public static String JsonFromAssymetricAlgs(AsymmAlgsJson item) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("binOid", item.getBinOid());
            obj.put("deltaBits", item.getDeltaBits());
            obj.put("maxBits", item.getMaxBits());
            obj.put("minBits", item.getMinBits());
            obj.put("minSecureBits", item.getMinSecureBits());
            obj.put("name", item.getName());
            obj.put("secure", item.isSecure());
            obj.put("strOId", item.getStrOId());
        } catch (Exception e) {

        }
        return obj.toString();
    }

    /**
     * Método que se encarga de serializar un objeto del tipo HashAlgsJson a un
     * JSON.
     *
     * @param item Objeto del tipo HashAlgsJson
     * @return JSON resultado de la serialización.
     */
    public static String JsonFromHashAlgs(HashAlgsJson item) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("binOId", item.getBinOId());
            obj.put("bits", item.getBits());
            obj.put("bytes", item.getBytes());
            obj.put("name", item.getName());
            obj.put("secure", item.isSecure());
            obj.put("strOId", item.getStrOid());
        } catch (Exception e) {

        }

        return obj.toString();
    }

    /**
     * Método que se encarga de serializar un objeto del tipo SignAlgsJson a un
     * JSON.
     *
     * @param item Objeto del tipo SignAlgsJson
     * @return JSON resultado de la serialización.
     */
    public static String JsonFromSignatureAlgs(SignAlgsJson item) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("asymmBinAlg", item.getAsymmBinAlg());
            obj.put("binOId", item.getBinOid());
            obj.put("hashBinAlg", item.getHashBinAlg());
            obj.put("name", item.getName());
            obj.put("strOid", item.getStrOid());
        } catch (Exception e) {

        }
        return obj.toString();
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo Groups
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo Groups deserializado.
     */
    public static Groups GroupsFromJson(String JSONEntity) {
        Groups result = new Groups();
        JSONObject obj = new JSONObject(JSONEntity);
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);

        try {

            try {
                result.setCreatedByUserId(Util.nullToZero(obj.get("createdByUserId")));

            } catch (Exception e) {
                result.setCreatedByUserId(null);
            }

            try {
                result.setDescription(Util.NullToString(obj.get("description")));

            } catch (Exception e) {
                result.setDescription(null);

            }

            try {
                result.setGroupId((short) Util.nullToZero(obj.get("groupId")));

            } catch (Exception e) {
                result.setGroupId(null);

            }

            try {
                result.setGroupName(Util.NullToString(obj.get("groupName")));

            } catch (Exception e) {
                result.setGroupName(null);

            }

            try {
                result.setIsActive(Util.NUllToBoolean(obj.get("isActive")));

            } catch (Exception e) {
                result.setIsActive(null);

            }

            try {
                result.setPreconfigured(obj.getBoolean("preconfigured"));

            } catch (Exception e) {
                result.setPreconfigured(false);

            }
            try {
                result.setReferenceDate(Util.DateToNull(parse(obj.get("referenceDate").toString())));

            } catch (Exception e) {
                result.setReferenceDate(null);

            }

            try {
                result.setProfileType((short) Util.nullToZero(obj.get("profileType")));
            } catch (Exception e) {
                result.setProfileType((short) 0);
            }

        } catch (JSONException e) {

        }
        return result;
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo -Users
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo Users deserializado.
     */
    public static Users UsersFromJson(String JSONEntity) {
        try {
            Users result = new Users();
            JSONObject obj = new JSONObject(JSONEntity);
            String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
            DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
            try {
                Date certVencimiento = Util.DateToNull(parse(obj.get("certVencimiento").toString()));
                result.setCertVencimiento(certVencimiento);
            } catch (Exception e) {

                result.setCertVencimiento(null);
            }
            try {
                result.setAreaName(Util.NullToString(obj.get("areaName")));
            } catch (Exception e) {
                result.setAreaName(null);
            }
            try {
                result.setAuthenticationMode((short) Util.nullToZero(obj.get("authenticationMode")));
            } catch (Exception e) {
                result.setAuthenticationMode((short) 0);
            }

            try {
                result.setCalle(Util.NullToString(obj.get("calle")));
            } catch (Exception e) {
                result.setCalle(null);
            }

            try {
                result.setCodigoPostal(Util.NullToString(obj.get("codigoPostal")));
            } catch (Exception e) {
                result.setCodigoPostal(null);
            }

            try {
                result.setCountryCode(Util.NullToString(obj.get("countryCode")));
            } catch (Exception e) {

                result.setCountryCode(null);
            }

            try {
                result.setColonia(Util.NullToString(obj.get("colonia")));
            } catch (Exception e) {
                result.setColonia(null);
            }

            try {
                result.setCurp(Util.NullToString(obj.get("curp")));
            } catch (Exception e) {
                result.setCurp(null);
            }

            try {
                result.setEmail(Util.NullToString(obj.get("email")));
            } catch (Exception e) {
                result.setEmail("");
            }

            try {
                result.setEstado(Util.NullToString(obj.get("estado")));
            } catch (Exception e) {
                result.setEstado(null);
            }

            try {
                result.setFirstName(Util.NullToString(obj.get("firstName")));
            } catch (Exception e) {
                result.setFirstName("");
            }

            try {
                result.setIdentificador(Util.NullToString(obj.get("identificador")));
            } catch (Exception e) {
                result.setIdentificador(null);
            }
            try {
                result.setIsActive(Util.NUllToBoolean(obj.get("isActive")));

            } catch (Exception e) {
                result.setIsActive(false);

            }

            try {
                result.setIsLockedOut(Util.NUllToBoolean(obj.get("isLockedOut")));
            } catch (Exception e) {
                result.setIsLockedOut(false);
            }

            try {
                result.setIPAddress(Util.NullToString(obj.get("IPAddress")));
            } catch (Exception e) {
                result.setIPAddress(null);
            }
            try {
                Date lastActivityDate = Util.DateToNull(parse(obj.get("lastActivityDate").toString()));
                result.setLastActivityDate(lastActivityDate);
            } catch (Exception e) {
                result.setLastActivityDate(null);
            }
            try {
                result.setLastName(Util.NullToString(obj.get("lastName")));
            } catch (Exception e) {
                result.setLastName("");
            }
            try {
                Date lastSigninDate = Util.DateToNull(parse(obj.get("lastSigninDate").toString()));
                result.setLastSigninDate(lastSigninDate);

            } catch (Exception e) {
                result.setLastSigninDate(null);

            }
            try {
                result.setLastUserLogRecordId(Util.nullToZero(obj.get("lastUserLogRecordId")));
            } catch (Exception e) {
                result.setLastUserLogRecordId(null);
            }
            try {
                result.setMiddleName(Util.NullToString(obj.get("middleName")));
            } catch (Exception e) {
                result.setMiddleName("");
            }

            try {
                result.setMunicipio(Util.NullToString(obj.get("municipio")));
            } catch (Exception e) {
                result.setMunicipio(null);
            }

            try {
                result.setNumero(Util.NullToString(obj.get("numero")));
            } catch (Exception e) {
                result.setNumero(null);
            }

            try {
                result.setOnlyReader(Util.NUllToBoolean(obj.get("onlyReader")));
            } catch (Exception e) {
                result.setOnlyReader(false);
            }

            try {
                result.setPassword(obj.getString("password"));
            } catch (Exception e) {
                result.setPassword(null);
            }

            try {
                result.setPuesto(Util.NullToString(obj.get("puesto")));
            } catch (Exception e) {
                result.setPuesto(null);
            }

            try {
                Date referenceDate = Util.DateToNull(parse(obj.get("referenceDate").toString()));

                result.setReferenceDate(referenceDate);
            } catch (Exception e) {
                result.setReferenceDate(null);

            }
            try {
                result.setRfc(Util.NullToString(obj.get("rfc")));
            } catch (Exception e) {
                result.setRfc(null);
            }

            try {
                result.setSessionId(Util.NullToString(obj.get("sessionId")));
            } catch (Exception e) {
                result.setSessionId(null);
            }

            try {
                result.setStatusId((short) Util.nullToZero(obj.get("statusId")));
            } catch (Exception e) {
                result.setStatusId((short) 0);
            }

            try {
                result.setTelNumber(Util.NullToString(obj.get("telNumber")));
            } catch (Exception e) {
                result.setTelNumber(null);
            }

            try {
                result.setUserId(Util.nullToZero(obj.get("userId")));
            } catch (Exception e) {
                result.setUserId(null);
            }

            try {
                result.setUsername(Util.NullToString(obj.get("username")));
            } catch (Exception e) {
                result.setUsername(null);
            }

            try {
                result.setAreaId((short) Util.nullToZero(obj.get("areaId")));
            } catch (Exception e) {
                result.setAreaId((short) 0);
            }
            return result;
        } catch (Exception e) {

            return null;
        }
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo Usersp12
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo Usersp12 deserializado.
     */
    public static Usersp12 Usersp12FromJson(String JSONEntity) {
        try {
            Usersp12 result = new Usersp12();
            JSONObject obj = new JSONObject(JSONEntity);
            String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
            DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
            try {
                DateTime certVencimiento = dtf.parseDateTime(Util.NullToString(obj.get("certVencimiento")));
                result.setCertVencimiento(certVencimiento.toDate());
            } catch (Exception e) {

                result.setCertVencimiento(null);
            }

            try {
                result.setAuthenticationMode((short) Util.nullToZero(obj.get("authenticationMode")));
            } catch (Exception e) {
                result.setAuthenticationMode((short) 0);
            }

            try {
                result.setCalle(Util.NullToString(obj.get("calle")));
            } catch (Exception e) {
                result.setCalle(null);
            }

            try {
                result.setCodigoPostal(Util.NullToString(obj.get("codigoPostal")));
            } catch (Exception e) {
                result.setCodigoPostal(null);
            }

            try {
                result.setCountryCode(Util.NullToString(obj.get("countryCode")));
            } catch (Exception e) {

                result.setCountryCode(null);
            }

            try {
                result.setColonia(Util.NullToString(obj.get("colonia")));
            } catch (Exception e) {
                result.setColonia(null);
            }

            try {
                result.setCurp(Util.NullToString(obj.get("curp")));
            } catch (Exception e) {
                result.setCurp(null);
            }

            try {
                result.setEmail(Util.NullToString(obj.get("email")));
            } catch (Exception e) {
                result.setEmail(null);
            }

            try {
                result.setEstado(Util.NullToString(obj.get("estado")));
            } catch (Exception e) {
                result.setEstado(null);
            }

            try {
                result.setFirstName(Util.NullToString(obj.get("firstName")));
            } catch (Exception e) {
                result.setFirstName(null);
            }

            try {
                result.setIdentificador(Util.NullToString(obj.get("identificador")));
            } catch (Exception e) {
                result.setIdentificador(null);
            }
            try {
                result.setIsActive(Util.NUllToBoolean(obj.get("isActive")));

            } catch (Exception e) {
                result.setIsActive(false);

            }

            try {
                result.setIsLockedOut(Util.NUllToBoolean(obj.get("isLockedOut")));
            } catch (Exception e) {
                result.setIsLockedOut(false);
            }

            try {
                result.setIPAddress(Util.NullToString(obj.get("IPAddress")));
            } catch (Exception e) {
                result.setIPAddress(null);
            }
            try {
                DateTime lastActivityDate = dtf.parseDateTime(obj.getString("lastActivityDate"));
                result.setLastActivityDate(Util.DateToNull(lastActivityDate.toDate()));
            } catch (Exception e) {
                result.setLastActivityDate(null);
            }
            try {
                result.setLastName(Util.NullToString(obj.get("lastName")));
            } catch (Exception e) {
                result.setLastName(null);
            }
            try {
                DateTime lastSigninDate = dtf.parseDateTime(obj.getString("lastSigninDate"));
                result.setLastSigninDate(Util.DateToNull(lastSigninDate.toDate()));

            } catch (Exception e) {
                result.setLastSigninDate(null);

            }
            try {
                result.setLastUserLogRecordId(Util.nullToZero(obj.get("lastUserLogRecordId")));
            } catch (Exception e) {
                result.setLastUserLogRecordId(null);
            }
            try {
                result.setMiddleName(Util.NullToString(obj.get("middleName")));
            } catch (Exception e) {
                result.setMiddleName(null);
            }

            try {
                result.setMunicipio(Util.NullToString(obj.get("municipio")));
            } catch (Exception e) {
                result.setMunicipio(null);
            }

            try {
                result.setNumero(Util.NullToString(obj.get("numero")));
            } catch (Exception e) {
                result.setNumero(null);
            }

            try {
                result.setOnlyReader(Util.NUllToBoolean(obj.get("onlyReader")));
            } catch (Exception e) {
                result.setOnlyReader(false);
            }

            try {
                result.setPassword(obj.getString("password"));
            } catch (Exception e) {
                result.setPassword(null);
            }

            try {
                result.setPuesto(Util.NullToString(obj.get("puesto")));
            } catch (Exception e) {
                result.setPuesto(null);
            }

            try {
                DateTime referenceDate = dtf.parseDateTime(obj.getString("referenceDate"));

                result.setReferenceDate(Util.DateToNull(referenceDate.toDate()));
            } catch (Exception e) {
                result.setReferenceDate(null);

            }
            try {
                result.setRfc(Util.NullToString(obj.get("rfc")));
            } catch (Exception e) {
                result.setRfc(null);
            }

            try {
                result.setSessionId(Util.NullToString(obj.get("sessionId")));
            } catch (Exception e) {
                result.setSessionId(null);
            }

            try {
                result.setStatusId((short) Util.nullToZero(obj.get("statusId")));
            } catch (Exception e) {
                result.setStatusId((short) 0);
            }

            try {
                result.setTelNumber(Util.NullToString(obj.get("telNumber")));
            } catch (Exception e) {
                result.setTelNumber(null);
            }

            try {
                result.setUserId(Util.nullToZero(obj.get("userId")));
            } catch (Exception e) {
                result.setUserId(null);
            }

            try {
                result.setUsername(Util.NullToString(obj.get("username")));
            } catch (Exception e) {
                result.setUsername(null);
            }

            try {
                result.setAreaName(Util.NullToString(obj.get("areaName")));
            } catch (Exception e) {
                result.setAreaName(null);
            }
            return result;
        } catch (Exception e) {

            return null;
        }
    }

//    public static ProfileProcess ProfileProcessFromJson(String JSONEntity) {
//        ProfileProcess result = new ProfileProcess();
//        JSONObject obj = new JSONObject(JSONEntity);
//        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
//        DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
//        try {
//            result.setConversionType((short) Util.nullToZero(obj.get("conversionType")));
//
//        } catch (Exception e) {
//            result.setConversionType((short) 0);
//
//        }
//        try {
//            result.setDeactivatedByUserId(Util.nullToZero(obj.get("deactivatedByUserId")));
//
//        } catch (Exception e) {
//            result.setDeactivatedByUserId(0);
//
//        }
//        try {
//            result.setGroupId((short) Util.nullToZero(obj.get("groupId")));
//
//        } catch (Exception e) {
//            result.setGroupId((short) 0);
//
//        }
//        try {
//            result.setHasDocuments(Util.NUllToBoolean(obj.get("hasDocuments")));
//
//        } catch (Exception e) {
//            result.setHasDocuments(true);
//
//        }
//        try {
//            result.setIsActive(Util.NUllToBoolean(obj.get("isActive")));
//        } catch (Exception e) {
//            result.setIsActive(true);
//        }
//        try {
//            result.setPreconfigured(Util.NUllToBoolean(obj.get("preconfigured")));
//
//        } catch (Exception e) {
//            result.setPreconfigured(false);
//
//        }
//        try {
//            result.setPreviousSignatureType((short) Util.nullToZero(obj.get("signatureType")));
//
//        } catch (Exception e) {
//            result.setPreviousSignatureType((short) 0);
//
//        }
//        try {
//            result.setProfileDescription(Util.NullToString(obj.get("profileDescription")));
//
//        } catch (Exception e) {
//            result.setProfileDescription(null);
//
//        }
//
//        try {
//            result.setProfileName(Util.NullToString(obj.get("profileName")));
//
//        } catch (Exception e) {
//            result.setProfileName(null);
//
//        }
//        try {
//            result.setProfileProcessId(Util.nullToZero(obj.get("profileProcessId")));
//
//        } catch (Exception e) {
//            result.setProfileProcessId(0);
//
//        }
//
//        try {
//            DateTime lastUpdated = dtf.parseDateTime(Util.NullToString(obj.get("lastUpdated")));
//
//            result.setLastUpdated(Util.DateToNull(lastUpdated.toDate()));
//        } catch (Exception e) {
//
//            result.setLastUpdated(null);
//        }
//
//        try {
//
//            DateTime referenceDate = dtf.parseDateTime(Util.NullToString(obj.get("referenceDate")));
//
//            result.setReferenceDate(Util.DateToNull(referenceDate.toDate()));
//
//        } catch (Exception e) {
//            result.setReferenceDate(null);
//            
//        }
//        result.setSignatureType((short) Util.nullToZero(obj.get("signatureType")));
//        result.setStorageTime((short) Util.nullToZero(obj.get("storageTime")));
//        result.setUserId(Util.nullToZero(obj.get("userId")));
//
//        return result;
//    }
    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo Task
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo Task deserializado.
     */
    public static Task TaskFromJson(String JSONEntity) {
        Task result = new Task();
        JSONObject obj = new JSONObject(JSONEntity);

        try {
            result.setMenu(Util.NullToString(obj.get("menu")));

        } catch (Exception e) {
            result.setMenu(null);

        }
        try {
            result.setPortalAdmin(Util.NUllToBoolean(obj.get("portalAdmin")));

        } catch (Exception e) {
            result.setPortalAdmin(null);

        }
        try {
            result.setPortalUser(Util.NUllToBoolean(obj.get("portalUser")));

        } catch (Exception e) {
            result.setPortalUser(null);

        }
        try {
            result.setTaskCode(Util.NullToString(obj.get("taskCode")));

        } catch (Exception e) {
            result.setTaskCode(null);

        }
        try {
            result.setTaskId((short) Util.nullToZero(obj.get("taskId")));

        } catch (Exception e) {
            result.setTaskId((short) 0);

        }
        try {
            result.setTaskName(Util.NullToString(obj.get("taskName")));

        } catch (Exception e) {
            result.setTaskName(null);

        }

        return result;

    }

    /**
     * Método que se encarga de serializar un objeto del tipo Groups a un JSON.
     *
     * @param item Objeto del tipo Groups
     * @return JSON resultado de la serialización.
     */
    public static String JsonFromGroups(Groups item) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("createdByUserId", item.getCreatedByUserId());
            obj.put("description", item.getDescription());
            obj.put("groupId", item.getGroupId());
            obj.put("groupName", item.getGroupName());
            obj.put("isActive", item.getIsActive());
            obj.put("preconfigured", item.getPreconfigured());
            obj.put("profileType", item.getProfileType());
            obj.put("referenceDate", Util.changeFormatDate(item.getReferenceDate()));
        } catch (JSONException e) {

        }
        return obj.toString();
    }

    /**
     * Método que se encarga de serializar un objeto del tipo Users a un JSON.
     *
     * @param item Objeto del tipo Users
     * @return JSON resultado de la serialización.
     */
    public static String JsonFromUsers(Users item) {
        try {

            JSONObject obj = new JSONObject();
            obj.put("certB64", item.getCertB64());
//            obj.put("certificate", item.getCertificate());
            obj.put("authenticationMode", item.getAuthenticationMode());
            obj.put("calle", item.getCalle());
            obj.put("certVencimiento", Util.changeFormatDate(item.getCertVencimiento()));
            obj.put("codigoPostal", item.getCodigoPostal());
            obj.put("colonia", item.getColonia());
            obj.put("countryCode", item.getCountryCode());
            obj.put("curp", item.getCurp());
            obj.put("email", item.getEmail());
            obj.put("estado", item.getEstado());
            obj.put("firstName", item.getFirstName());
            obj.put("IPAddress", item.getIPAddress());
            obj.put("identificador", item.getIdentificador());
            obj.put("isActive", item.getIsActive());
            obj.put("isLockedOut", item.getIsLockedOut());
            obj.put("lastActivityDate", Util.changeFormatDate(item.getLastActivityDate()));
            obj.put("lastName", item.getLastName());
            obj.put("lastSigninDate", Util.changeFormatDate(item.getLastSigninDate()));
            obj.put("lastUserLogRecordId", item.getLastUserLogRecordId());
            obj.put("middleName", item.getMiddleName());
            obj.put("municipio", item.getMunicipio());
            obj.put("numero", item.getNumero());
            obj.put("onlyReader", item.getOnlyReader());
            obj.put("password", item.getPassword());
            obj.put("puesto", item.getPuesto());
            obj.put("referenceDate", Util.changeFormatDate(item.getReferenceDate()));
            obj.put("rfc", item.getRfc());
            obj.put("sessionId", item.getSessionId());
            obj.put("statusId", item.getStatusId());
            obj.put("telNumber", item.getTelNumber());
            obj.put("userId", item.getUserId());
            obj.put("userType", item.getUserType());
            obj.put("username", item.getUsername());
            obj.put("areaId", item.getAreaId());
            obj.put("noSerie", item.getNoSerie());
            obj.put("p12", item.getP12());
            obj.put("areaName", item.getAreaName());

            return obj.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo Groups
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo Groups deserializado.
     */
    public static Groups GroupsProfileFromJson(String JSONEntity) {
        Groups result = new Groups();
        JSONObject obj = new JSONObject(JSONEntity);
        try {
            result.setGroupId((short) obj.getInt("groupId"));
        } catch (Exception e) {
        }
        try {
            result.setGroupName(obj.getString("groupName"));
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * Método que se encarga de serializar un objeto del tipo ProfileProcess a
     * un JSON.
     *
     * @param item Objeto del tipo ProfileProcess
     * @return JSON resultado de la serialización.
     */
    public static String JsonFromProfileProcess(ProfileProcess item) {

        JSONObject obj = new JSONObject();

        obj.put("conversionType", item.getConversionType());
        obj.put("deactivatedByUserId", item.getDeactivatedByUserId());
        obj.put("deactivatedDate", Util.changeFormatDate(item.getDeactivatedDate()));
        obj.put("groupId", item.getGroupId());
        obj.put("hasDocuments", item.getHasDocuments());
        obj.put("isActive", item.getIsActive());
        obj.put("lastUpdated", Util.changeFormatDate(item.getLastUpdated()));
        obj.put("preconfigured", item.getPreconfigured());
        obj.put("previousSignatureType", item.getPreviousSignatureType());
        obj.put("profileDescription", item.getProfileDescription());
        obj.put("profileName", item.getProfileName());
        obj.put("profileProcessId", item.getProfileProcessId());
        obj.put("referenceDate", Util.changeFormatDate(item.getDeactivatedDate()));
        obj.put("signatureType", item.getSignatureType());
        obj.put("storageTime", item.getStorageTime());
        obj.put("userId", item.getUserId());
        return obj.toString();
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo ProfileProcess
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo ProfileProcess deserializado.
     */
    public static ProfileProcess ProfileProcessFromJson(String JSONEntity) {

        ProfileProcess result = new ProfileProcess();
        JSONObject obj = new JSONObject(JSONEntity);
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
        try {
            result.setConversionType((short) Util.nullToZero(obj.get("conversionType")));

        } catch (Exception e) {
            result.setConversionType((short) 0);

        }
        try {
            result.setDeactivatedByUserId(Util.nullToZero(obj.get("deactivatedByUserId")));

        } catch (Exception e) {
            result.setDeactivatedByUserId(0);

        }
        try {
            result.setGroupId((short) Util.nullToZero(obj.get("groupId")));

        } catch (Exception e) {
            result.setGroupId((short) 0);

        }
        try {
            result.setHasDocuments(Util.NUllToBoolean(obj.get("hasDocuments")));

        } catch (Exception e) {
            result.setHasDocuments(true);

        }
        try {
            result.setIsActive(Util.NUllToBoolean(obj.get("isActive")));
        } catch (Exception e) {
            result.setIsActive(true);
        }
        try {
            result.setPreconfigured(Util.NUllToBoolean(obj.get("preconfigured")));

        } catch (Exception e) {
            result.setPreconfigured(false);

        }
        try {
            result.setPreviousSignatureType((short) Util.nullToZero(obj.get("previousSignatureType")));

        } catch (Exception e) {
            result.setPreviousSignatureType((short) 0);

        }
        try {
            result.setProfileDescription(Util.NullToString(obj.get("profileDescription")));

        } catch (Exception e) {
            result.setProfileDescription(null);

        }

        try {
            result.setProfileName(Util.NullToString(obj.get("profileName")));

        } catch (Exception e) {
            result.setProfileName(null);

        }
        try {
            result.setProfileProcessId(Util.nullToZero(obj.get("profileProcessId")));

        } catch (Exception e) {
            result.setProfileProcessId(0);

        }

        try {

            Date lastUpdated = Util.DateToNull(parse(obj.get("lastUpdated").toString()));

            result.setLastUpdated(lastUpdated);
        } catch (Exception e) {

            result.setLastUpdated(null);
        }

        try {

            Date referenceDate = Util.DateToNull(parse(obj.get("referenceDate").toString()));

            result.setReferenceDate(referenceDate);

        } catch (Exception e) {
            result.setReferenceDate(null);

        }
        try {
            result.setSignatureType((short) Util.nullToZero(obj.get("signatureType")));
        } catch (Exception e) {
            result.setSignatureType((short) 0);
        }

        try {
            result.setStorageTime((short) Util.nullToZero(obj.get("storageTime")));
        } catch (Exception e) {
            result.setStorageTime((short) 0);
        }

        try {
            result.setUserId(Util.nullToZero(obj.get("userId")));
        } catch (Exception e) {
            result.setUserId(0);
        }

        return result;
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo -
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo - deserializado.
     */
    public static UserState UserStateFromJson(String JSONEntity) {
        UserState result = new UserState();
        JSONObject obj = new JSONObject(JSONEntity);
        try {
            result.setFirstName(obj.getString("firstName"));
        } catch (Exception e) {
        }
        try {
            result.setMiddleName(obj.getString("middleName"));
        } catch (Exception e) {
        }
        try {
            result.setLastName(obj.getString("lastName"));
        } catch (Exception e) {
        }
        try {
            result.setUserId(obj.getInt("userId"));
        } catch (Exception e) {
        }

        try {
            result.setUserName(obj.getString("userName"));
        } catch (Exception e) {
        }
        try {
            result.setUserType((short) obj.getInt("userType"));
        } catch (Exception e) {
        }
        try {
            result.setAuthenticationMode((short) obj.getInt("authenticationMode"));
        } catch (Exception e) {
        }
        try {
            result.setIPAddress(obj.getString("IPAddress"));
        } catch (Exception e) {
        }
        try {
            result.setStatusId((short) obj.getInt("statusId"));
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * Método que se encarga de la deserialización del JSON para convertirlo en
     * un objeto del tipo UserSession
     *
     * @param JSONEntity JSON a deserializar
     * @return Objeto del tipo UserSession deserializado.
     */
    public static UserSession UserSessionFromJson(String JSONEntity) {
        UserSession result = new UserSession();
        JSONObject obj = new JSONObject(JSONEntity);
        try {
            result.setSessionId(obj.getString("sessionId"));
        } catch (Exception e) {
        }
        try {
            result.setUserName(obj.getString("userName"));
        } catch (Exception e) {
        }
        try {
            result.setFullName(obj.getString("firstName") + " " + obj.getString("middleName")
                    + " " + obj.getString("lastName"));
        } catch (Exception e) {
        }
        return result;
    }
}
