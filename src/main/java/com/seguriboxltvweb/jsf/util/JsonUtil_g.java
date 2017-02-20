///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.seguriboxltvweb.jsf.util;
//
//import com.seguriboxltvweb.domain.AlgorithmHash;
//import com.seguriboxltvweb.domain.AlgorithmHistory;
//import com.seguriboxltvweb.domain.AlgorithmAsymmetric;
//import com.seguriboxltvweb.domain.AlgorithmSignSymm;
//import com.seguriboxltvweb.domain.Area;
//import com.seguriboxltvweb.domain.AuditTrailReport;
//import com.seguriboxltvweb.domain.DocumentReport;
//import com.seguriboxltvweb.domain.Groups;
//import com.seguriboxltvweb.domain.HSMKey;
//import com.seguriboxltvweb.domain.ProfileProcess;
//import com.seguriboxltvweb.domain.SystemParameter;
//import com.seguriboxltvweb.domain.SystemParameters;
//import com.seguriboxltvweb.domain.Task;
//import com.seguriboxltvweb.domain.UserLogReport;
//import com.seguriboxltvweb.domain.Users;
//import com.seguridata.certificate.bean.Certificate;
//import com.seguridata.crypto.bean.AssymetricAlgs;
//import com.seguridata.crypto.bean.AsymmAlgsJson;
//import com.seguridata.crypto.bean.HashAlgs;
//import com.seguridata.crypto.bean.HashAlgsJson;
//import com.seguridata.crypto.bean.SignAlgsJson;
//import com.seguridata.crypto.bean.SignatureAlgs;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import org.joda.time.DateTime;
//import org.joda.time.format.DateTimeFormat;
//import org.joda.time.format.DateTimeFormatter;
//import org.json.JSONException;
//import org.json.JSONObject;
//import sun.misc.BASE64Decoder;
//
///**
// *
// * @author inggerman
// */
//public class JsonUtil {
//
//    
//    // Método Nuevo
//    // Obtener los parámetros de SystemParameters
//    public static SystemParameters systemParametersFromJson(String JSONEntity, SystemParameters systemParameters) {
//        JSONObject obj = new JSONObject(JSONEntity);
//        SystemParameters result = systemParameters;
//        String parameterName = obj.getString("parameterName");
//        if (parameterName.equals("STORAGETIME")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setSTORAGETIME(systemParameter);
//        }
//        if (parameterName.equals("ORGANIZATION")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setORGANIZATION(systemParameter);
//        }
//        if (parameterName.equals("KEYAGENT")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setKEYAGENT(systemParameter);
//        }
//        if (parameterName.equals("SEGURIBOXAPIPASSWORD")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setSEGURIBOXAPIPASSWORD(systemParameter);
//        }
//        if (parameterName.equals("SEGURIBOXAPIUSERNAME")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setSEGURIBOXAPIUSERNAME(systemParameter);
//        }
//        if (parameterName.equals("SEGURIBOXAPIURL")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setSEGURIBOXAPIURL(systemParameter);
//        }
//        if (parameterName.equals("SEGURISERVERPASSWORD")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setSEGURISERVERPASSWORD(systemParameter);
//        }
//        if (parameterName.equals("SEGURISERVERUSERNAME")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setSEGURISERVERUSERNAME(systemParameter);
//        }
//        if (parameterName.equals("SEGURISERVERURL")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setSEGURISERVERURL(systemParameter);
//        }
//        if (parameterName.equals("SEGURISIGNPASSWORD")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setSEGURISIGNPASSWORD(systemParameter);
//        }
//        if (parameterName.equals("SEGURISIGNUSERNAME")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setSEGURISIGNUSERNAME(systemParameter);
//        }
//        if (parameterName.equals("SEGURISIGNURLs")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setSEGURISIGNURLs(systemParameter);
//        }
//        if (parameterName.equals("ARCHIVEPASSWORD")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setARCHIVEPASSWORD(systemParameter);
//        }
//        if (parameterName.equals("ARCHIVEUSERNAME")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setARCHIVEUSERNAME(systemParameter);
//        }
//        if (parameterName.equals("ARCHIVEURL")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setARCHIVEURL(systemParameter);
//        }
//        if (parameterName.equals("DOPKCS11PASSWORD")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setDOPKCS11PASSWORD(systemParameter);
//        }
//        if (parameterName.equals("DOPKCS11USERNAME")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setDOPKCS11USERNAME(systemParameter);
//        }
//        if (parameterName.equals("DOPKCS11URL")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setDOPKCS11URL(systemParameter);
//        }
//        if (parameterName.equals("SMTPSSLS")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setSMTPSSLS(systemParameter);
//        }
//        if (parameterName.equals("SMTPPASSWORD")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setSMTPPASSWORD(systemParameter);
//        }
//        if (parameterName.equals("SMTPUSER")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setSMTPUSER(systemParameter);
//        }
//        if (parameterName.equals("SMTPANONIMOUS")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setSMTPANONIMOUS(systemParameter);
//        }
//        if (parameterName.equals("SMTPPORT")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setSMTPPORT(systemParameter);
//        }
//        if (parameterName.equals("FILEEXTENSIONS")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setFILEEXTENSIONS(systemParameter);
//        }
//        if (parameterName.equals("FILESIZEMAX")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setFILESIZEMAX(systemParameter);
//        }
//        if (parameterName.equals("EMAILSTORAGEDAYS")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setEMAILSTORAGEDAYS(systemParameter);
//        }
//        if (parameterName.equals("SMTPSERVER")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setSMTPSERVER(systemParameter);
//        }
//        if (parameterName.equals("CRYPTOURL")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setCRYPTOURL(systemParameter);
//        }
//        if (parameterName.equals("CRYPTOUSERNAME")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setCRYPTOUSERNAME(systemParameter);
//        }
//        if (parameterName.equals("CRYPTOPASSWORD")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setCRYPTOPASSWORD(systemParameter);
//        }
//        if (parameterName.equals("HSMKEYURL")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setHSMKEYURL(systemParameter);
//        }
//        if (parameterName.equals("HSMKEYUSERNAME")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setHSMKEYUSERNAME(systemParameter);
//        }
//        if (parameterName.equals("HSMKEYPASSWORD")) {
//            SystemParameter systemParameter = new SystemParameter();
//            systemParameter.setParameterName(Util.NullToString(obj.get("parameterName")));
//            systemParameter.setParameterValue(Util.NullToString(obj.get("parameterValue")));
//            systemParameter.setCategory(Util.NullToString(obj.get("category")));
//            result.setHSMKEYPASSWORD(systemParameter);
//        }
//        return result;
//    }
//
//    // Método que convierte al systemparameters en json
//    public static String JsonFromSystemParameters(SystemParameter item) {
//        JSONObject obj = new JSONObject();
//        obj.put("category", item.getCategory());
//        obj.put("parameterName", item.getParameterName());
//        obj.put("parameterValue", item.getParameterValue());
//        return obj.toString();
//    }
//
//    public static HSMKey listHSM(String JSONEntity) {
//        JSONObject obj = new JSONObject(JSONEntity);
//        HSMKey hsm = new HSMKey();
//        hsm.setHSMKeyId(Util.nullToZero(obj.get("HSMKeyId")));
//        hsm.setKeyTag(Util.NullToString(obj.get("keyTag")));
//        return hsm;
//    }
//
//    public static AssymetricAlgs AssyJsontoAssySD(AsymmAlgsJson item) {
//
//        AssymetricAlgs result = new AssymetricAlgs();
//
//        return result;
//
//    }
//
//    public static com.seguridata.hsm.bean.HSMKey SDHSMKeyFromJson(String JSONEntity) {
//
//        com.seguridata.hsm.bean.HSMKey result = new com.seguridata.hsm.bean.HSMKey();
//        JSONObject obj = new JSONObject(JSONEntity);
//        
//
//        result.setB64Cert(Util.NullToString(obj.get("b64Cert")));
//        result.setKeySize(Util.nullToZero(obj.get("keySize")));
//        result.setLabel(Util.NullToString(obj.get("label")));
//        result.setOid(Util.NullToString(obj.get("oid")));
//
//        return result;
//    }
//
//    public static String JsonFromSDHSMKey(com.seguridata.hsm.bean.HSMKey item) {
//
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("b64Cert", item.getB64Cert());
//            obj.put("keySize", item.getKeySize());
//            obj.put("label", item.getLabel());
//            obj.put("oid", item.getOid());
//        } catch (Exception e) {
//            
//        }
//
//        return obj.toString();
//
//    }
//
//    public static AsymmAlgsJson AssymetricAlgsFronJson(String JSONEntity) {
//
//        AsymmAlgsJson result = new AsymmAlgsJson();
//        JSONObject obj = new JSONObject(JSONEntity);
//        
//        result.setName(Util.NullToString(obj.get("name")));
//        result.setBinOid(Util.NullToString(obj.get("binOid")));
//        result.setDeltaBits(Util.nullToZero(obj.get("deltaBits")));
//        result.setMaxBits(Util.nullToZero(obj.get("maxBits")));
//        result.setMinBits(Util.nullToZero(obj.get("minBits")));
//        result.setMinSecureBits(Util.nullToZero(obj.get("minSecureBits")));
//        result.setSecure(Util.NUllToBoolean(obj.get("secure")));
//        result.setStrOid(Util.NullToString(obj.get("strOId")));
//
//        return result;
//
//    }
//
//    public static String JsonFromAssymetricAlgs(AsymmAlgsJson item) {
//
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("binOId", item.getBinOid());
//            obj.put("deltaBits", item.getDeltaBits());
//            obj.put("maxBits", item.getMaxBits());
//            obj.put("minBits", item.getMinBits());
//            obj.put("minSecurebits", item.getMinSecureBits());
//            obj.put("name", item.getName());
//            obj.put("secure", item.isSecure());
//            obj.put("strOid", item.getStrOId());
//        } catch (Exception e) {
//            
//        }
//
//        return obj.toString();
//
//    }
//
//    public static HashAlgsJson HashAlgsFromJson(String JSONEntity) {
//        HashAlgsJson result = new HashAlgsJson();
//        JSONObject obj = new JSONObject(JSONEntity);
//        
//        
//        result.setBinOId(Util.NullToString(obj.get("binOId")));
//        result.setBits(Util.nullToZero(obj.get("bits")));
//        result.setBytes(Util.nullToZero(obj.get("bytes")));
//        result.setName(Util.NullToString(obj.get("name")));
//        result.setSecure(Util.NUllToBoolean(obj.get("secure")));
//        result.setStrOid(Util.NullToString(obj.get("strOid")));
//
//        return result;
//    }
//
//    public static String JsonFromHashAlgs(HashAlgsJson item) {
//
//        JSONObject obj = new JSONObject();
//
//        try {
//
//            obj.put("binOId", item.getBinOId());
//            obj.put("bits", item.getBits());
//            obj.put("bytes", item.getBytes());
//            obj.put("name", item.getName());
//            obj.put("secure", item.isSecure());
//            obj.put("strOId", item.getStrOid());
//
//        } catch (Exception e) {
//            
//        }
//
//        
//        return obj.toString();
//    }
//
//    public static SignAlgsJson SignatureAlgsFromJson(String JSONEntity) {
//
//        SignAlgsJson result = new SignAlgsJson();
//        JSONObject obj = new JSONObject(JSONEntity);
//        
//        result.setAsymmBinAlg(Util.NullToString(obj.get("asymmBinAlg")));
//        result.setBinOid(Util.NullToString(obj.get("binOid")));
//        result.setHashBinAlg(Util.NullToString(obj.get("hashBinAlg")));
//        result.setName(Util.NullToString(obj.get("name")));
//        result.setStrOid(Util.NullToString(obj.get("strOid")));
//
//        return result;
//    }
//
//    public static String JsonFromSignatureAlgs(SignAlgsJson item) {
//
//        JSONObject obj = new JSONObject();
//
//        try {
//            obj.put("asymmBinAlg", item.getAsymmBinAlg());
//            obj.put("binOId", item.getBinOid());
//            obj.put("hashBinAlg", item.getHashBinAlg());
//            obj.put("name", item.getName());
//            obj.put("strOid", item.getStrOid());
//        } catch (Exception e) {
//            
//        }
//        
//        return obj.toString();
//    }
//
//    public static Certificate CertificateFronJson(String JSONEntity) {
//
//        Certificate result = new Certificate();
//        JSONObject obj = new JSONObject(JSONEntity);
//        
//        try {
//            result.setSerialNumber(Util.NullToString(obj.get("serialNumber")));
//        } catch (Exception e) {
//            result.setSerialNumber(null);
//        }
//        try {
//            result.setSignatureAlgorithm(Util.NullToString(obj.get("signatureAlgorithm")));
//        } catch (Exception e) {
//            result.setSignatureAlgorithm(null);
//        }
//
//        try {
//            result.setVersion(Util.NullToString(obj.get("version")));
//        } catch (Exception e) {
//            result.setVersion(null);
//        }
//
//        try {
//            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
//            Date date = formatter.parse(Util.NullToString(obj.get("notAfter")));
//            
//            result.setNotAfter(date);
//            
//        } catch (Exception e) {
//            
//            result.setNotAfter(null);
//        }
//
//        return result;
//
//    }
//
//    public static HSMKey HSMKeyFromJson(String JSONEntity) {
//        HSMKey result = new HSMKey();
//        JSONObject obj = new JSONObject(JSONEntity);
//        
//        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
//        DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
//        try {
//            try {
//                DateTime referenceDate = dtf.parseDateTime(Util.NullToString(obj.get("referenceDate")));
//                result.setReferenceDate(Util.DateToNull(referenceDate.toDate()));
//            } catch (Exception e) {
//                
//                result.setReferenceDate(null);
//            }
//
//            try {
//                result.setHSMKeyId(Util.nullToZero(obj.get("HSMKeyId")));
//
//            } catch (Exception e) {
//                result.setHSMKeyId(null);
//
//            }
//            try {
//                result.setIsActive(Util.NUllToBoolean(obj.get("isActive")));
//
//            } catch (Exception e) {
//                result.setIsActive(false);
//
//            }
//            try {
//                result.setKeySize(Util.nullToZero(obj.get("keySize")));
//
//            } catch (Exception e) {
//                result.setKeySize(null);
//
//            }
//            try {
//                result.setKeyTag(Util.NullToString(obj.getString("keyTag")));
//
//            } catch (Exception e) {
//                result.setKeyTag(null);
//
//            }
//            try {
//                result.setOid(Util.NullToString(obj.get("oid")));
//
//            } catch (Exception e) {
//                result.setOid(null);
//
//            }
//            try {
//                Date certificateExpiration=Util.parse(Util.NullToString(obj.get("certificateExpiration")));
//                result.setCertificateExpiration(Util.DateToNull(certificateExpiration));
//            } catch (Exception e) {
//                
//                result.setCertificateExpiration(null);
//            }
//
//            try {
//                BASE64Decoder decoder = new BASE64Decoder();
//                byte[] certificado = decoder.decodeBuffer(Util.NullToString(obj.getString("certificate")));
//                result.setCertificate(certificado);
//            } catch (Exception e) {
//                result.setCertificate(null);
//            }
//
//            return result;
//        } catch (JSONException e) {
//            
//            return null;
//        }
//
//    }
//
//    public static String JsonFromHSMKey(HSMKey item) {
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("HSMKeyId", item.getHSMKeyId());
//            obj.put("isActive", item.getIsActive());
//            obj.put("keySize", item.getKeySize());
//            obj.put("keyTag", item.getKeyTag());
//            obj.put("oid", item.getOid());
//            obj.put("referenceDate", item.getReferenceDate());
//        } catch (JSONException e) {
//            
//        }
//        return obj.toString();
//
//    }
//
//    //Entity Groups
//    public static String JsonFromGroups(Groups item) {
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("createdByUserId", item.getCreatedByUserId());
//            obj.put("description", item.getDescription());
//            obj.put("groupId", item.getGroupId());
//            obj.put("groupName", item.getGroupName());
//            obj.put("isActive", item.getIsActive());
//            obj.put("preconfigured", item.getPreconfigured());
//            obj.put("profileType", item.getProfileType());
//            obj.put("referenceDate", item.getReferenceDate());
//        } catch (JSONException e) {
//            
//        }
//        return obj.toString();
//    }
//    
//     public static Groups GroupsProfileFromJson(String JSONEntity) {
//        Groups result = new Groups();
//        JSONObject obj = new JSONObject(JSONEntity);
//        try {
//            result.setGroupId((short) obj.getInt("groupId"));
//        } catch (Exception e) {
//        }
//        try {
//            result.setGroupName(obj.getString("groupName"));
//        } catch (Exception e) {
//        }
//        return result;
//    }
//
//
//    public static Groups GroupsFromJson(String JSONEntity) {
//        Groups result = new Groups();
//        JSONObject obj = new JSONObject(JSONEntity);
//        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
//        DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
//
//        try {
//            DateTime referenceDate = dtf.parseDateTime(obj.getString("referenceDate"));
//            try {
//                result.setCreatedByUserId(Util.nullToZero(obj.get("createdByUserId")));
//
//            } catch (Exception e) {
//                result.setCreatedByUserId(null);
//            }
//
//            try {
//                result.setDescription(Util.NullToString(obj.get("description")));
//
//            } catch (Exception e) {
//                result.setDescription(null);
//
//            }
//
//            try {
//                result.setGroupId((short) Util.nullToZero(obj.get("groupId")));
//
//            } catch (Exception e) {
//                result.setGroupId(null);
//
//            }
//
//            try {
//                result.setGroupName(Util.NullToString(obj.get("groupName")));
//
//            } catch (Exception e) {
//                result.setGroupName(null);
//
//            }
//
//            try {
//                result.setIsActive(Util.NUllToBoolean(obj.get("isActive")));
//
//            } catch (Exception e) {
//                result.setIsActive(null);
//
//            }
//
//            try {
//                result.setPreconfigured(obj.getBoolean("preconfigured"));
//
//            } catch (Exception e) {
//                result.setPreconfigured(false);
//
//            }
//            try {
//                result.setReferenceDate(referenceDate.toDate());
//
//            } catch (Exception e) {
//                result.setReferenceDate(null);
//
//            }
//
//            try {
//                result.setProfileType((short) Util.nullToZero(obj.get("profileType")));
//            } catch (Exception e) {
//                result.setProfileType((short) 0);
//            }
//
//        } catch (JSONException e) {
//            
//        }
//        return result;
//    }
//
//    public static AlgorithmAsymmetric AlgorithmAsymmetricFromJson(String JSONEntity) {
//        
//        try {
//
//            AlgorithmAsymmetric result = new AlgorithmAsymmetric();
//            JSONObject obj = new JSONObject(JSONEntity);
//            try {
//                result.setAlgorithmAsymmetricId(Util.nullToZero(obj.get("algorithmAsymmetricId")));
//
//            } catch (Exception e) {
//                result.setAlgorithmAsymmetricId(null);
//
//            }
//            try {
//                result.setAlgorithmName(Util.NullToString(obj.get("algorithmName")));
//
//            } catch (Exception e) {
//                result.setAlgorithmName(null);
//
//            }
//            try {
//                result.setIsActive(Util.NUllToBoolean(obj.get("isActive")));
//
//            } catch (Exception e) {
//                result.setIsActive(false);
//
//            }
//            try {
//                result.setBinOid(Util.BytesToNUll(obj.getString("binOid")));
//
//            } catch (Exception e) {
//                result.setBinOid(null);
//
//            }
//            try {
//                result.setDeltaBits(Util.nullToZero(obj.get("deltaBits")));
//
//            } catch (Exception e) {
//                result.setDeltaBits(0);
//
//            }
//            try {
//                result.setMaxBits(Util.nullToZero(obj.get("maxBits")));
//            } catch (Exception e) {
//                result.setMaxBits(0);
//            }
//            try {
//                result.setMinBits(Util.nullToZero(obj.get("minBits")));
//            } catch (Exception e) {
//                result.setMinBits(0);
//            }
//            try {
//                result.setMinSecureBits(Util.nullToZero(obj.get("minSecureBits")));
//            } catch (Exception e) {
//                result.setMinSecureBits(0);
//            }
//            try {
//                result.setOid(Util.NullToString(obj.get("oid")));
//            } catch (Exception e) {
//                result.setOid(null);
//            }
//            try {
//                result.setSize(Util.nullToZero(obj.get("size")));
//            } catch (Exception e) {
//                result.setSize(0);
//            }
//            try {
//                result.setSecure(obj.getInt("secure"));
//            } catch (Exception e) {
//                result.setSecure(0);
//            }
//
//            String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
//            DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
//            DateTime referencedate = dtf.parseDateTime(Util.NullToString(obj.get("referenceDate")));
//            try {
//                DateTime expirationDate = dtf.parseDateTime(Util.NullToString(obj.get("expirationDate")));
//                result.setExpirationDate(Util.DateToNull(expirationDate.toDate()));
//            } catch (Exception e) {
//
//                result.setExpirationDate(null);
//            }
//
//            try {
//                Date reference=Util.parse(Util.NullToString(obj.get("referenceDate")));
//                result.setReferenceDate(Util.DateToNull(reference));
//            } catch (Exception e) {
//                result.setReferenceDate(null);
//            }
//
//            return result;
//        } catch (Exception e) {
//            
//            return null;
//        }
//
//    }
//
//    public static String JsonFromAlgorithmAsymmetric(AlgorithmAsymmetric item) {
//        try {
//
//            JSONObject obj = new JSONObject();
//            obj.put("algorithmAsymmetricId", item.getAlgorithmAsymmetricId());
//            obj.put("algorithmName", item.getAlgorithmName());
//            obj.put("isActive", item.getIsActive());
//            obj.put("binOid", item.getBinOid());
//            obj.put("deltaBits", item.getDeltaBits());
//            obj.put("maxBits", item.getMaxBits());
//            obj.put("minBits", item.getMinBits());
//            obj.put("minSecureBits", item.getMinSecureBits());
//            obj.put("oid", item.getOid());
//            obj.put("referenceDate", item.getReferenceDate());
//            obj.put("secure", item.getSecure());
//            obj.put("size", item.getSize());
//
//            return obj.toString();
//        } catch (Exception e) {
//
//            
//            return null;
//        }
//
//    }
//
//    public static AlgorithmHash AlgorithmHashFromJson(String JSONEntity) {
//
//        try {
//
//            AlgorithmHash result = new AlgorithmHash();
//            String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
//            DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
//            
//            JSONObject obj = new JSONObject(JSONEntity);
//            
//            
//            try {
//                result.setAlgorithmHashId(Util.nullToZero(obj.get("algorithmHashId")));
//
//            } catch (Exception e) {
//                result.setAlgorithmHashId(null);
//
//            }
//            try {
//                result.setAlgorithmName(Util.NullToString(obj.get("algorithmName")));
//
//            } catch (Exception e) {
//                result.setAlgorithmName(null);
//
//            }
//            try {
//                result.setBinOid(Util.NullToString(obj.get("binOid")));
//
//            } catch (Exception e) {
//                result.setBinOid(null);
//
//            }
//            try {
//                result.setBits(Util.nullToZero(obj.get("bits")));
//
//            } catch (Exception e) {
//                result.setBits(null);
//
//            }
//            try {
//                result.setBytes(Util.nullToZero(obj.get("bytes")));
//            } catch (Exception e) {
//                result.setBytes(null);
//            }
//            try {
//                result.setIsActive(Util.NUllToBoolean(obj.get("isActive")));
//            } catch (Exception e) {
//                result.setIsActive(false);
//            }
//            try {
//                result.setOid(Util.NullToString(obj.get("oid")));
//
//            } catch (Exception e) {
//                result.setOid(null);
//
//            }
//            try {
//           
//                Date reference=Util.parse(Util.NullToString(obj.get("referenceDate")));
//                result.setReferenceDate(Util.DateToNull(reference));
//
//            } catch (Exception e) {
//                
//                
//                result.setReferenceDate(null);
//
//            }
//            try {
//                result.setSecure(Util.nullToZero(obj.get("secure")));
//
//            } catch (Exception e) {
//                result.setSecure(null);
//
//            }
//
//            return result;
//        } catch (Exception e) {
//
//            
//        }
//        return null;
//    }
//
//    public static String JsonFromAlgorithmHash(AlgorithmHash item) {
//        try {
//
//            JSONObject obj = new JSONObject();
//            obj.put("algorithmHashId", item.getAlgorithmHashId());
//            obj.put("algorithmName", item.getAlgorithmName());
//            obj.put("binOid", item.getBinOid());
//            obj.put("bits", item.getBits());
//            obj.put("bytes", item.getBytes());
//            obj.put("isActive", item.getIsActive());
//            obj.put("oid", item.getOid());
//            obj.put("referenceDate", item.getReferenceDate());
//            obj.put("secure", item.getSecure());
//            return obj.toString();
//        } catch (Exception e) {
//            
//            return null;
//        }
//
//    }
//
//    public static AlgorithmHistory AlgorithmHistoryFromJson(String JSONEntity) {
//
//        try {
//
//            String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
//            DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
//            AlgorithmHistory result = new AlgorithmHistory();
//            JSONObject obj = new JSONObject(JSONEntity);
//            
//            try {
//                result.setAction(Util.NullToString(obj.get("action")));
//
//            } catch (Exception e) {
//                result.setAction(null);
//
//            }
//            try {
//                result.setDetails(Util.NullToString(obj.get("details")));
//
//            } catch (Exception e) {
//                result.setDetails(null);
//
//            }
//            try {
//                result.setEventId(Util.nullToZero(obj.get("eventId")));
//
//            } catch (Exception e) {
//                result.setEventId(0);
//
//            }
//            try {
//                result.setHostName(Util.NullToString(obj.get("hostName")));
//
//            } catch (Exception e) {
//                result.setHostName(null);
//
//            }
//            try {
//                result.setUserId(Util.nullToZero(obj.get("userId")));
//            } catch (Exception e) {
//                result.setUserId(null);
//            }
//            try {
//                DateTime referencedate = dtf.parseDateTime(Util.NullToString(obj.get("referenceDate")));
//                result.setReferenceDate(Util.DateToNull(referencedate.toDate()));
//            } catch (Exception e) {
//                DateTime referencedate = dtf.parseDateTime(null);
//                result.setReferenceDate(null);
//            }
//
//            return result;
//        } catch (Exception e) {
//            
//            return null;
//        }
//    }
//
//    public static String JsonFromAlgorithmHistory(AlgorithmHistory item) {
//        try {
//
//            JSONObject obj = new JSONObject();
//            obj.put("action", item.getAction());
//            obj.put("details", item.getDetails());
//            obj.put("eventId", item.getEventId());
//            obj.put("hostName", item.getHostName());
//            obj.put("referenceDate", item.getReferenceDate());
//            obj.put("userId", item.getUserId());
//
//            return obj.toString();
//        } catch (Exception e) {
//
//            
//
//            return null;
//        }
//    }
//
//    public static AlgorithmSignSymm AlgorithmSignSymmFromJson(String JSONEntity) {
//        try {
//
//            AlgorithmSignSymm result = new AlgorithmSignSymm();
//            JSONObject obj = new JSONObject(JSONEntity);
//            
//            try {
//                result.setAlgorithmAsymmetricId(Util.nullToZero(obj.get("algorithmAsymmetricId")));
//
//            } catch (Exception e) {
//                result.setAlgorithmAsymmetricId(0);
//
//            }
//            try {
//                result.setAlgorithmHashId(Util.nullToZero(obj.get("algorithmHashId")));
//
//            } catch (Exception e) {
//                result.setAlgorithmHashId(0);
//
//            }
//            try {
//                result.setAlgorithmName(Util.NullToString(obj.get("algorithmName")));
//
//            } catch (Exception e) {
//                result.setAlgorithmName(null);
//
//            }
//            try {
//                result.setAlgorithmSignId(Util.nullToZero(obj.get("algorithmSignId")));
//
//            } catch (Exception e) {
//                result.setAlgorithmSignId(0);
//
//            }
//            try {
//                result.setAsymmBin(Util.NullToString(obj.get("asymmBin")));
//            } catch (Exception e) {
//                result.setAsymmBin(null);
//            }
//            try {
//                result.setBinOid(Util.NullToString(obj.get("binOid")));
//
//            } catch (Exception e) {
//                result.setBinOid(null);
//
//            }
//            try {
//                result.setHashBin(Util.NullToString(obj.get("hashBin")));
//            } catch (Exception e) {
//                result.setHashBin(null);
//            }
//            try {
//                result.setIsActive(Util.NUllToBoolean(obj.get("isActive")));
//            } catch (Exception e) {
//                result.setIsActive(false);
//            }
//            try {
//                result.setOid(Util.NullToString(obj.get("oid")));
//            } catch (Exception e) {
//                result.setOid(null);
//            }
//            
//            try {
//                Date reference=Util.parse(Util.NullToString(obj.get("referenceDate")));
//                result.setReferenceDate(Util.DateToNull(reference));
//            } catch (Exception e) {
//                result.setReferenceDate(null);
//            }
//            //result.setReferenceDate("");
//            return result;
//        } catch (Exception e) {
//
//            
//            return null;
//        }
//
//    }
//
//    public static String JsonFromAlgorithSymm(AlgorithmSignSymm item) {
//        try {
//
//            JSONObject obj = new JSONObject();
//            obj.put("algorithmAsymmetricId", item.getAlgorithmAsymmetricId());
//            obj.put("algorithmHashId", item.getAlgorithmHashId());
//            obj.put("algorithmName", item.getAlgorithmName());
//            obj.put("algorithmSignId", item.getAlgorithmSignId());
//            obj.put("asymmBin", item.getAsymmBin());
//            obj.put("binOid", item.getBinOid());
//            obj.put("hashBin", item.getHashBin());
//            obj.put("isActive", item.getIsActive());
//            obj.put("oid", item.getOid());
//            obj.put("referenceDate", item.getReferenceDate());
//            
//            return obj.toString();
//        } catch (Exception e) {
//            
//            return null;
//
//        }
//
//    }
//
//    public static String JsonFromUsers(Users item) {
//        try {
//
//            JSONObject obj = new JSONObject();
//            obj.put("authenticationMode", item.getAuthenticationMode());
//            obj.put("calle", item.getCalle());
//            obj.put("certVencimiento", item.getCertVencimiento());
//            obj.put("codigoPostal", item.getCodigoPostal());
//            obj.put("colonia", item.getColonia());
//            obj.put("countryCode", item.getCountryCode());
//            obj.put("curp", item.getCurp());
//            obj.put("email", item.getEmail());
//            obj.put("estado", item.getEstado());
//            obj.put("firstName", item.getFirstName());
//            obj.put("IPAddress", item.getIPAddress());
//            obj.put("identificador", item.getIdentificador());
//            obj.put("isActive", item.getIsActive());
//            obj.put("isLockedOut", item.getIsLockedOut());
//            obj.put("lastActivityDate", item.getLastActivityDate());
//            obj.put("lastName", item.getLastName());
//            obj.put("lastSigninDate", item.getLastSigninDate());
//            obj.put("lastUserLogRecordId", item.getLastUserLogRecordId());
//            obj.put("middleName", item.getMiddleName());
//            obj.put("municipio", item.getMunicipio());
//            obj.put("numero", item.getNumero());
//            obj.put("onlyReader", item.getOnlyReader());
//            obj.put("password", item.getPassword());
//            obj.put("puesto", item.getPuesto());
//            obj.put("referenceDate", item.getReferenceDate());
//            obj.put("rfc", item.getRfc());
//            obj.put("sessionId", item.getSessionId());
//            obj.put("statusId", item.getStatusId());
//            obj.put("telNumber", item.getTelNumber());
//            obj.put("userId", item.getUserId());
//            obj.put("userType", item.getUserType());
//            obj.put("username", item.getUsername());
//     
//
//            return obj.toString();
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public static Users UsersFromJson(String JSONEntity) {
//        try {
//
//            Users result = new Users();
//            JSONObject obj = new JSONObject(JSONEntity);
//            String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
//            DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
//            try {
//                DateTime certVencimiento = dtf.parseDateTime(Util.NullToString(obj.get("certVencimiento")));
//                result.setCertVencimiento(certVencimiento.toDate());
//            } catch (Exception e) {
//              
//                result.setCertVencimiento(null);
//            }
//
//            try {
//                result.setAuthenticationMode((short) Util.nullToZero(obj.get("authenticationMode")));
//            } catch (Exception e) {
//                result.setAuthenticationMode((short) 0);
//            }
//
//            try {
//                result.setCalle(Util.NullToString(obj.get("calle")));
//            } catch (Exception e) {
//                result.setCalle(null);
//            }
//
//            try {
//                result.setCodigoPostal(Util.NullToString(obj.get("codigoPostal")));
//            } catch (Exception e) {
//                result.setCodigoPostal(null);
//            }
//
//            try {
//                result.setCountryCode(Util.NullToString(obj.get("countryCode")));
//            } catch (Exception e) {
//
//                result.setCountryCode(null);
//            }
//
//            try {
//                result.setColonia(Util.NullToString(obj.get("colonia")));
//            } catch (Exception e) {
//                result.setColonia(null);
//            }
//
//            try {
//                result.setCurp(Util.NullToString(obj.get("curp")));
//            } catch (Exception e) {
//                result.setCurp(null);
//            }
//
//            try {
//                result.setEmail(Util.NullToString(obj.get("email")));
//            } catch (Exception e) {
//                result.setEmail(null);
//            }
//
//            try {
//                result.setEstado(Util.NullToString(obj.get("estado")));
//            } catch (Exception e) {
//                result.setEstado(null);
//            }
//
//            try {
//                result.setFirstName(Util.NullToString(obj.get("firstName")));
//            } catch (Exception e) {
//                result.setFirstName(null);
//            }
//
//            try {
//                result.setIdentificador(Util.NullToString(obj.get("identificador")));
//            } catch (Exception e) {
//                result.setIdentificador(null);
//            }
//            try {
//                result.setIsActive(Util.NUllToBoolean(obj.get("isActive")));
//
//            } catch (Exception e) {
//                result.setIsActive(false);
//
//            }
//
//            try {
//                result.setIsLockedOut(Util.NUllToBoolean(obj.get("isLockedOut")));
//            } catch (Exception e) {
//                result.setIsLockedOut(false);
//            }
//
//            try {
//                result.setIPAddress(Util.NullToString(obj.get("IPAddress")));
//            } catch (Exception e) {
//                result.setIPAddress(null);
//            }
//            try {
//                DateTime lastActivityDate = dtf.parseDateTime(obj.getString("lastActivityDate"));
//                result.setLastActivityDate(Util.DateToNull(lastActivityDate.toDate()));
//            } catch (Exception e) {
//                result.setLastActivityDate(null);
//            }
//            try {
//                result.setLastName(Util.NullToString(obj.get("lastName")));
//            } catch (Exception e) {
//                result.setLastName(null);
//            }
//            try {
//                DateTime lastSigninDate = dtf.parseDateTime(obj.getString("lastSigninDate"));
//                result.setLastSigninDate(Util.DateToNull(lastSigninDate.toDate()));
//
//            } catch (Exception e) {
//                result.setLastSigninDate(null);
//
//            }
//            try {
//                result.setLastUserLogRecordId(Util.nullToZero(obj.get("lastUserLogRecordId")));
//            } catch (Exception e) {
//                result.setLastUserLogRecordId(null);
//            }
//            try {
//                result.setMiddleName(Util.NullToString(obj.get("middleName")));
//            } catch (Exception e) {
//                result.setMiddleName(null);
//            }
//
//            try {
//                result.setMunicipio(Util.NullToString(obj.get("municipio")));
//            } catch (Exception e) {
//                result.setMunicipio(null);
//            }
//
//            try {
//                result.setNumero(Util.NullToString(obj.get("numero")));
//            } catch (Exception e) {
//                result.setNumero(null);
//            }
//
//            try {
//                result.setOnlyReader(Util.NUllToBoolean(obj.get("onlyReader")));
//            } catch (Exception e) {
//                result.setOnlyReader(false);
//            }
//
//            try {
//                result.setPassword(obj.getString("password"));
//            } catch (Exception e) {
//                result.setPassword(null);
//            }
//
//            try {
//                result.setPuesto(Util.NullToString(obj.get("puesto")));
//            } catch (Exception e) {
//                result.setPuesto(null);
//            }
//
//            try {
//                DateTime referenceDate = dtf.parseDateTime(obj.getString("referenceDate"));
//
//                result.setReferenceDate(Util.DateToNull(referenceDate.toDate()));
//            } catch (Exception e) {
//                result.setReferenceDate(null);
//
//            }
//            try {
//                result.setRfc(Util.NullToString(obj.get("rfc")));
//            } catch (Exception e) {
//                result.setRfc(null);
//            }
//
//            try {
//                result.setSessionId(Util.NullToString(obj.get("sessionId")));
//            } catch (Exception e) {
//                result.setSessionId(null);
//            }
//
//            try {
//                result.setStatusId((short) Util.nullToZero(obj.get("statusId")));
//            } catch (Exception e) {
//                result.setStatusId((short) 0);
//            }
//
//            try {
//                result.setTelNumber(Util.NullToString(obj.get("telNumber")));
//            } catch (Exception e) {
//                result.setTelNumber(null);
//            }
//
//            try {
//                result.setUserId(Util.nullToZero(obj.get("userId")));
//            } catch (Exception e) {
//                result.setUserId(null);
//            }
//
//            try {
//                result.setUsername(Util.NullToString(obj.get("username")));
//            } catch (Exception e) {
//                result.setUsername(null);
//            }
//
//           
//            return result;
//
//        } catch (Exception e) {
//
//            
//            return null;
//        }
//    }
//
//    public static DocumentReport DocumentReportFromJson(String JSONEntity) {
//        JSONObject obj = new JSONObject(JSONEntity);
//        DocumentReport result = new DocumentReport();
//        result.setDocumentId(Util.nullToZero(obj.get("documentId")));
//        result.setFullName(Util.NullToString("fullName"));
//        result.setDocumentName(Util.NullToString(obj.get("documentName")));
//        result.setTipoFirma(Util.NullToString(obj.get("tipoFirma")));
//        result.setFirmas((short) Util.nullToZero(obj.get("firmas")));
//        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
//        DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
//        DateTime dateCreated = dtf.parseDateTime(Util.NullToString(obj.get("dateCreated")));
//        DateTime expirationDated = dtf.parseDateTime(Util.NullToString(obj.get("expirationDated")));
//        result.setDateCreated(Util.DateToNull(dateCreated.toDate()));
//        result.setExpirationDated(Util.DateToNull(expirationDated.toDate()));
//        return result;
//    }
//
//    public static String JsonFromDocumentReport(DocumentReport item) {
//        JSONObject obj = new JSONObject();
//        obj.put("documentId", item.getDocumentId());
//        obj.put("fullName", item.getFullName());
//        obj.put("documentName", item.getDocumentName());
//        obj.put("tipoFirma", item.getTipoFirma());
//        obj.put("firmas", item.getFirmas());
//        obj.put("dateCreated", item.getDateCreated());
//        obj.put("expirationDated", item.getExpirationDated());
//
//        return obj.toString();
//    }
//
//    public static UserLogReport UserLogReportFromJson(String JSONEntity) {
//
//        return null;
//    }
//
//    public static String JsonFromUserLogReport(UserLogReport item) {
//
//        return "";
//    }
//
//    public static AuditTrailReport AuditTrailReportFromJson(String JSONEntity) {
//
//        return null;
//    }
//
//    public static String JsonFromAuditTrailReport(AuditTrailReport item) {
//
//        return "";
//    }
//
//    public static Area AreaFromJson(String JSONEntity) {
//        //{"areaId":3,"areaName":"Doncencia","isActive":true,"referenceDate":1470689050400}
//        Area result = new Area();
//        JSONObject obj = new JSONObject(JSONEntity);
//        try {
//            result.setAreaId((short) Util.nullToZero(obj.get("areaId")));
//
//        } catch (Exception e) {
//            result.setAreaId((short) 0);
//
//        }
//        try {
//            result.setAreaName(Util.NullToString(obj.get("areaName")));
//
//        } catch (Exception e) {
//            result.setAreaName(null);
//
//        }
//        try {
//            result.setIsActive(Util.NUllToBoolean(obj.get("isActive")));
//
//        } catch (Exception e) {
//            result.setIsActive(true);
//
//        }
//        try {
//            String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
//            DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
//            DateTime referencedate = dtf.parseDateTime(Util.NullToString(obj.get("referenceDate")));
//        } catch (Exception e) {
//
//            result.setReferenceDate(null);
//
//        }
//
//        return result;
//    }
//
//    public static String JsonFromArea(Area item) {
//        JSONObject obj = new JSONObject();
//        obj.put("areaId", item.getAreaId());
//        obj.put("areaName", item.getAreaName());
//        obj.put("isActive", item.getIsActive());
//        obj.put("referenceDate", item.getReferenceDate());
//        return obj.toString();
//    }
//
//    public static String JsonFromProfileProcess(ProfileProcess item) {
//
//        JSONObject obj = new JSONObject();
//
//        obj.put("conversionType", item.getConversionType());
//        obj.put("deactivatedByUserId", item.getDeactivatedByUserId());
//        obj.put("deactivatedDate", item.getDeactivatedDate());
//        obj.put("groupId", item.getGroupId());
//        obj.put("hasDocuments", item.getHasDocuments());
//        obj.put("isActive", item.getIsActive());
//        obj.put("lastUpdated", item.getLastUpdated());
//        obj.put("preconfigured", item.getPreconfigured());
//        obj.put("previousSignatureType", item.getPreviousSignatureType());
//        obj.put("profileDescription", item.getProfileDescription());
//        obj.put("profileName", item.getProfileName());
//        obj.put("profileProcessId", item.getProfileProcessId());
//        obj.put("referenceDate", item.getReferenceDate());
//        obj.put("signatureType", item.getSignatureType());
//        obj.put("storageTime", item.getStorageTime());
//        obj.put("userId", item.getUserId());
//        return obj.toString();
//    }
//
//    public static ProfileProcess ProfileProcessFromJson(String JSONEntity) {
//
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
//
//    public static Task TaskFromJson(String JSONEntity) {
//
//        Task result = new Task();
//        JSONObject obj = new JSONObject(JSONEntity);
//
//        try {
//            result.setMenu(Util.NullToString(obj.get("menu")));
//
//        } catch (Exception e) {
//            result.setMenu(null);
//
//        }
//        try {
//            result.setPortalAdmin(Util.NUllToBoolean(obj.get("portalAdmin")));
//
//        } catch (Exception e) {
//            result.setPortalAdmin(null);
//
//        }
//        try {
//            result.setPortalUser(Util.NUllToBoolean(obj.get("portalUser")));
//
//        } catch (Exception e) {
//            result.setPortalUser(null);
//
//        }
//        try {
//            result.setTaskCode(Util.NullToString(obj.get("taskCode")));
//
//        } catch (Exception e) {
//            result.setTaskCode(null);
//
//        }
//        try {
//            result.setTaskId((short) Util.nullToZero(obj.get("taskId")));
//
//        } catch (Exception e) {
//            result.setTaskId((short) 0);
//
//        }
//        try {
//            result.setTaskName(Util.NullToString(obj.get("taskName")));
//
//        } catch (Exception e) {
//            result.setTaskName(null);
//
//        }
//
//        return result;
//
//    }
//
//    public static String TaskFromJson(Task item) {
//
//        JSONObject obj = new JSONObject();
//
//        obj.put("menu", item.getMenu());
//        obj.put("portalAdmin", item.getPortalAdmin());
//        obj.put("portalUser", item.getPortalUser());
//        obj.put("taskCode", item.getTaskCode());
//        obj.put("taskId", item.getTaskId());
//        obj.put("taskName", item.getTaskName());
//        
//        return obj.toString();
//
//    }
//    
//   
//
//}
