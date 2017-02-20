package com.seguriboxltvweb.jsf;

import com.google.gson.Gson;
import com.seguriboxltvweb.domain.Area;
import com.seguriboxltvweb.domain.CertificateName;
import com.seguriboxltvweb.domain.Dopksc;
import com.seguriboxltvweb.domain.HSMKey;
import com.seguriboxltvweb.domain.Users;
import com.seguriboxltvweb.jsf.util.CountryCode;
import com.seguriboxltvweb.jsf.util.JsfUtil;
import com.seguriboxltvweb.jsf.util.JsonUtil;
import com.seguriboxltvweb.jsf.util.SeguriboxGetProperties;
import com.seguriboxltvweb.jsf.util.SessionUtils;
import com.seguriboxltvweb.jsf.util.Util;
import com.seguriboxltvweb.jsf.util.wsUtil;
import com.seguridata.certificate.SDCertificates;
import com.sserver.client.ws.AuthorityKeyIdentifier;
import com.sserver.client.ws.BasicConstraints;
import com.sserver.client.ws.CertifyRequest;
import com.sserver.client.ws.DistinguishedNames;
import com.sserver.client.ws.EnhancedKeyUsage;
import com.sserver.client.ws.Extensions;
import com.sserver.client.ws.KeyUsage;
import com.sserver.client.ws.Netscape;
import com.sserver.client.ws.NetscapeCertificateType;
import com.sserver.client.ws.PublicKeyType;
import com.sserver.client.ws.SeguriServerService;
import com.sserver.client.ws.SeguriServerService_Service;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import javax.security.cert.X509Certificate;
import javax.xml.ws.BindingProvider;
import java.io.Serializable;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import static javax.ws.rs.client.Entity.entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.primefaces.context.RequestContext;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Germán Hernández López.
 * @version 1.0.
 */
@Named("usersController")
@SessionScoped
public class UsersController implements Serializable {

    private List<Locale> availableLocale;
    private String pfx;
    private String certUser;
    private String cerAut;
    private final WebTarget webTarget;
    private final WebTarget webTargetArea;
    private final WebTarget webTargetHsmkey;
    private final Client client;
    //TODO: Cambia esta configuracion a un archivo configuracion 
    private final String BASE_URI;
    private final String BASE_AJAX;
    private boolean isEdit;
    private String sessionId;
    private List<Users> items = null;
    private Users selected = new Users();
    private DualListModel<Users> users;
    List<Users> citiesSource = new ArrayList<>();
    List<Users> citiesTarget = new ArrayList<>();
    private String commonName = "";
    UIInput passtext;
    UIInput keysize;
    private String privateKey;
    private String publicKey;
    private String urlrequest;
    private String urluserinsert;
    private String fullName;
    private String passwordValidate;
    private UIComponent valorSbmit;
    private final String userId;
    private String serialNumberUser;
    private String serialNumber;
    private String challengePrasePass;
    private boolean btnCer = false;
    private boolean btnDoPKCS12 = false;
    private boolean btnReq = false;
    private final String sourceUser;
    private final String sourceArea;

    /**
     * Constructor de la clase UsersController.
     *
     * @throws IOException
     */
    public UsersController() throws IOException {
        sessionId = SessionUtils.getSessionId();
        userId = SessionUtils.getUserId();
        client = ClientBuilder.newClient();
        BASE_URI = new SeguriboxGetProperties().getPropValues("UrlBase");
        BASE_AJAX = new SeguriboxGetProperties().getPropValues("UrlAjax");
        webTarget = client.target(BASE_URI).path("users");
        webTargetArea = client.target(BASE_URI).path("area");
        webTargetHsmkey = client.target(BASE_URI).path("hsmkey");
        urlrequest=BASE_AJAX+"/users/getrequestcertificate";
        urluserinsert=BASE_AJAX+"/users/createusercertificate/";
        sourceUser = "users";
        sourceArea = "area";
    }

    public String getUrlrequest() {
        return urlrequest;
    }

    public void setUrlrequest(String urlrequest) {
        this.urlrequest = urlrequest;
    }

    public String getUrluserinsert() {
        return urluserinsert;
    }

    public void setUrluserinsert(String urluserinsert) {
        this.urluserinsert = urluserinsert;
    }

    
    public void setPfx(String pfx) {
        this.pfx = pfx;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setCertUser(String certUser) {
        this.certUser = certUser;
    }

    public void setCerAut(String cerAut) {
        this.cerAut = cerAut;
    }

    /**
     *
     * @param passwordValidate
     */
    public void setPasswordValidate(String passwordValidate) {
        this.passwordValidate = passwordValidate;
    }

    /**
     *
     * @return
     */
    public boolean isBtnReq() {
        return btnReq;
    }

    /**
     *
     * @return
     */
    public boolean isBtnDoPKCS12() {
        return btnDoPKCS12;
    }

    /**
     *
     * @return
     */
    public boolean isBtnCer() {
        return btnCer;
    }

    /**
     * Método que construye el fullName uniendo nombre, apellido paterno y
     * apellido materno.
     *
     * @return nombre del usuario completo.
     */
    public String getFullName() {
        String str = "";
        if (selected.getFirstName() == null) {

            selected.setFirstName("");
        }

        if (selected.getMiddleName() == null) {

            selected.setMiddleName("");
        }

        if (selected.getLastName() == null) {

            selected.setLastName("");
        }

        str = selected.getFirstName() + " " + selected.getMiddleName() + " " + selected.getLastName();

        return str;
    }

    /**
     *
     * @return
     */
    public String getPfx() {
        return pfx;
    }

    /**
     *
     * @return
     */
    public String getCertUser() {
        return certUser;
    }

    /**
     *
     * @return
     */
    public String getCerAut() {
        return cerAut;
    }

    /**
     *
     * @return
     */
    public String getPasswordValidate() {
        return passwordValidate;
    }

    /**
     *
     * @return
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     *
     * @return
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     *
     * @return @throws IOException
     */
    public List<CountryCode> getAvailableLocale() throws IOException {
        CountryCode cc = new CountryCode();
        List<CountryCode> mylista = cc.GetCountry();
//        for (CountryCode item : mylista) {
//            System.out.println("Codigo de Pais = " + item.getCodigoPais()
//                    + ", Nombre de Pais = " + item.getNombrePais());
//        }

        return mylista;
    }

    /**
     * Método que asigna una lista de usuarios a la variable items.
     */
    public void fillTable() {

        items = findAll();
    }

    /**
     * Método que redirige a la sección de usuarios y carga la tabla de
     * usuarios.
     *
     * @return outcome a que redirige a la página adminusers.jsf.
     */
    public String goToUsers() {
        try {
            items = findAll();
            return "adminusers?faces-redirect=true";
        } catch (Exception e) {

            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }

    }

    /**
     * Método que cambia la contraseña del usuario.
     *
     * @return outcome que redirige a la página adminusers.jsf
     * @throws Exception
     */
    public String resetPassword() throws Exception {
        try {
            String usern = selected.getUsername();
            String newPass = selected.getPassword();

            String path = MessageFormat.format(sourceUser + "/resetpassword/{0}/{1}/{2}",
                    new Object[]{sessionId, usern, newPass});
            wsUtil.ExecutePutRestService(true, BASE_URI, path, "");

            setSelected(new Users());
            JsfUtil.addSuccessMessage("Contraseña actualizada correctamente");
            items = findAll();
            return "adminusers?faces-redirect=true";

        } catch (Exception e) {

            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }
    }

    /**
     * Método que edita la información de un usuario.
     *
     * @param item usuario a editar
     * @return outcome que redirige a la página adminuserpasswordedit.jsf.
     */
    public String goToEdit(Users item) {
        item.setPassword("");
        setSelected(item);

        return "adminuserpasswordedit?faces-redirect=true";
    }

    /**
     * Método que limpia la variable selected del tipo User
     *
     * @throws IOException
     */
    public void fillControls() throws IOException {

        pfx = "";
        cerAut = "";
        certUser = "";
        publicKey = "";
        privateKey = "";
        serialNumber = "";
        serialNumberUser = "";
        btnCer = false;
        btnDoPKCS12 = false;
        challengePrasePass = "";
        passwordValidate = "";

        setSelected(new Users());
        FacesContext.getCurrentInstance().getExternalContext().redirect("adminuserpassword.jsf");

    }

    /**
     * Método que limpia las variables antes de ir a la página de creación de
     * usuario por certificado.
     *
     * @throws IOException
     */
    public void fillControlsCert() throws IOException {

        pfx = "";
        cerAut = "";
        certUser = "";
        publicKey = "";
        privateKey = "";
        serialNumber = "";
        serialNumberUser = "";
        btnCer = false;
        btnDoPKCS12 = false;
        challengePrasePass = "";
        passwordValidate = "";

        setSelected(new Users());
        FacesContext.getCurrentInstance().getExternalContext().redirect("adminusercertificate.jsf");

    }

    /**
     * Método que limpia las variables antes de ir a la página de creación de
     * usuario administrador.
     *
     * @throws IOException
     */
    public void fillControlsCertAdmin() throws IOException {

        pfx = "";
        cerAut = "";
        certUser = "";
        publicKey = "";
        privateKey = "";
        serialNumber = "";
        serialNumberUser = "";
        btnCer = false;
        btnDoPKCS12 = false;
        challengePrasePass = "";
        passwordValidate = "";

        setSelected(new Users());
        FacesContext.getCurrentInstance().getExternalContext().redirect("adminuseradmin.jsf");

    }

    /**
     * Método que le quita a la llave privada los enunciados:BEGIN CERTIFICATE
     * REQUEST y END CERTIFICATE REQUEST-.
     *
     * @return llave privada sin enunciados en la cabecera o pie.
     * @throws Exception
     */
    public String GetStringPrivateKey() throws Exception {
        if (publicKey.equals("")) {

            throw new Exception("El requerimiento esta vacio");
        }
        String str = publicKey;
        str = str.replace("-----BEGIN CERTIFICATE REQUEST-----", " ");
        str = str.replace("-----END CERTIFICATE REQUEST-----", " ");

        return str;
    }

    /**
     *
     * Método que obtiene un registro de área almacenado en la base de datos en
     * base a su identificado.
     *
     * @param id del área a buscar.
     * @return objeto del tipo área encontrado
     * @throws Exception
     */
    public Area getArea(java.lang.Short id) throws Exception {

        try {

            String path = MessageFormat.format(sourceArea + "/{0}/{1}", new Object[]{sessionId, id.toString()});

            String item = wsUtil.ExecuteGetRestService(true, BASE_URI, path);
            return JsonUtil.AreaFromJson(item);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Método que devuelve la llave del agente almacenada en la base de datos.
     *
     * @return llave HSM del agente
     * @throws Exception
     */
    public HSMKey getHSMKey() throws Exception {

        String id = Util.GetsystemParameter("KEYAGENT", "parameterValue");

        try {
            WebTarget resource = webTargetHsmkey;
            String item = resource.path(MessageFormat.format("{0}/{1}", new Object[]{sessionId, id}))
                    .request(MediaType.APPLICATION_JSON + ";charset=utf-8").get(String.class);
            return JsonUtil.HSMKeyFromJson(item);
        } catch (Exception e) {

            throw e;
        }
    }

    /**
     * Método que descarga el certificado del usuario almacenado en la base de
     * datos en base a su id
     *
     * @param user para obtener su id.
     */
    public void donwloadCert(Users user) {

        String id = user.getUserId().toString();
        try {

            String item = null;
            Response response = webTarget.path(MessageFormat.format("getcertificateuserbyid/{0}/{1}",
                    new Object[]{sessionId, id})).request(MediaType.APPLICATION_JSON + ";charset=utf-8")
                    .get(Response.class);
            item = response.readEntity(String.class);

            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                throw new Exception(item);
            } else {

                CertificateName cert = new Gson().fromJson(item, CertificateName.class);
                donwload(cert);
            }

        } catch (Exception e) {

            JsfUtil.addErrorMessage(e.getMessage());

        }
    }

    /**
     * Método que decodifica el certificado obtenido desde la base de datos y lo
     * descarga al usuario, este método se utiliza dentro del método
     * donwloadCert.
     *
     * @param cer certificado obtenido.
     */
    public void donwload(CertificateName cer) {

        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] array = decoder.decodeBuffer(cer.getCertjson());

            HttpServletResponse response
                    = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();

            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + cer.getCertificateName());
            response.getOutputStream().write(array);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(e.getMessage());
        }

    }
    
    public String donwloadcert() {

        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] array = decoder.decodeBuffer(pfx);

            HttpServletResponse response
                    = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();

            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + selected.getFirstName()+".p12");
            response.getOutputStream().write(array);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();
            return "adminusers?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(e.getMessage());
            return "adminusers?faces-redirect=true";
        }

    }



    /**
     * Método que obtiene el certificado consumiendo un web service SOAP
     * proporcionado por seguridata
     */
    public void requestCert() {

        try {
            if (publicKey.equals("")) {
                btnCer = false;
                btnReq = false;
                throw new Exception("La llave pública esta vacía");
            }

            SDCertificates cert = new SDCertificates();

            HSMKey hsmke = getHSMKey();
            com.seguridata.certificate.bean.Certificate result = cert.decodeCertificate(hsmke.getCertificate());
            String WS_URL = Util.GetsystemParameter("SEGURISERVERURL", "parameterValue");
            //String WS_URL = "http://201.122.192.62:81/WS_SServer1.6.0.1460/WSSeguriServer?wsdl";

            //String WS_URL = "https://200.66.66.210:91/WS_SServer1.6.0.1460/WSSeguriServer?wsdl";
            String AGENT_LABE = hsmke.getKeyTag();
            String AGENT_SERIAL = result.getSerialNumber();
            String hashAlgorihm = "SHA-256";
            //String AGENT_LABE = "Agente Certificador";
            //String AGENT_SERIAL = "3FE";

//            System.out.println("fullName: " + getFullName() + "----Area" + Util.GetsystemParameter("ORGANIZATION", "parameterValue")
//                    + "------CountryCose" + selected.getCountryCode() + "-----email" + selected.getEmail() + "------Colonia" + selected.getColonia()
//                    + "--------Estado" + selected.getEstado() + "------requerimiento" + GetStringPrivateKey());
            //Requerimiento generado vía Web en el Portal de Usuario
            String pkcs10b64 = GetStringPrivateKey();

            //definimos el objeto distinguished names
            DistinguishedNames dnames = new DistinguishedNames();

            String organization = Util.GetsystemParameter("ORGANIZATION", "parameterValue");

            dnames.setDnCn(getFullName());
            dnames.setDnO(organization);
            dnames.setDnC(selected.getCountryCode());
            dnames.setDnE(selected.getEmail());
            dnames.setDnL(selected.getColonia());
            dnames.setDnS(selected.getEstado());

            dnames.setDnOu(getArea((short) selected.getAreaId()).getAreaName());

            //definimos el objeto CertifyRequest
            CertifyRequest request = new CertifyRequest();

            //asignamos el objeto distinguished names
            request.setDistinguishedNames(dnames);

            //asignamos el periodo de validez
            request.setValidFrom(Util.GetTodayUTC());
            request.setValidTo(Util.dateTooUtc(selected.getCertVencimiento()));
            //asignamos las extensiones
            request.setExtensions(getExtension());
            //asignamos el requerimiento
            request.setPublicKey(pkcs10b64);
            //asignamos el tipo de requerimiento.
            request.setPublicKeyType(PublicKeyType.PKCS_10);

            //conexion con el Web Service.
            SeguriServerService_Service service = new SeguriServerService_Service(new URL(WS_URL));
            SeguriServerService port = service.getSeguriServerServicePort();
            ((BindingProvider) port).getRequestContext().put(BindingProvider.SESSION_MAINTAIN_PROPERTY, true);

            if (new Date().compareTo(result.getNotAfter()) > 0) {

                throw new Exception("El agente certificador ha expirado");

            }
            //obteniendo el hash de la transaccion
            String certifyTransaction = port.getCertifyTransanction(request, AGENT_SERIAL);

            //imprimiendo el hash de la transaccion.
            //Firmando el hash [certifyTransaction es el hash en base 64]
            //Metodo que firma con las llaves del agente AGENT_LABEL
            /**
             * ************************ IMPORTANTE *************************
             */
            //En este punto se invoca el WS PKCS#11 para firmar el hash certifyTransaction
            //con el agente indicado en 
            String signature = doPKCS1(certifyTransaction, hashAlgorihm, AGENT_LABE);
            Dopksc dopks = new Gson().fromJson(signature, Dopksc.class);
            String signatur = dopks.getSignature();

            /**
             * ************************ IMPORTANTE *************************
             */
            //obteniendo la bolsa de certificados
            String certB64 = port.certifyUser(signatur);

            //imprimiendo la bolsa de certificados
            //obteniendo los certificados de la AC y usuario
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream stream = new ByteArrayInputStream((new BASE64Decoder()).decodeBuffer(certB64));
            Collection certificates = cf.generateCertificates(stream);

            if (certificates != null && certificates.size() == 2) {
                Iterator i = certificates.iterator();

                int h = 0;
                X509Certificate x509Certificate = null;

                while (i.hasNext()) {
                    h++;
                    //Certificate cer=(Certificate) i.next();
                    x509Certificate = X509Certificate.getInstance(((Certificate) i.next()).getEncoded());
                    //imprimiendo los nombres distinguidos del certificado
                    //x509Certificate.

                    //
                    //
                    com.seguridata.certificate.bean.Certificate c = cert.decodeCertificate(x509Certificate.getEncoded());

                    byte[] encoded = Base64.getEncoder().encode(x509Certificate.getEncoded());
                    String encodedSerial = "" + x509Certificate.getSerialNumber();
//  
//                    
//                    

                    if (h == 1) {
                        serialNumber = encodedSerial;
                        cerAut = new String(encoded);

                    } else {
                        serialNumberUser = encodedSerial;
                        certUser = new String(encoded);

                    }

//                    
                }
//                
                btnCer = true;
                btnReq = true;

            }

        } catch (Exception e) {
            if (e.getMessage().equals("La llave pública esta vacía")) {
                btnCer = false;
                btnReq = false;
                JsfUtil.addErrorMessage(e.getMessage());
            } else {
                btnCer = false;
                btnReq = true;

                e.printStackTrace();
                JsfUtil.addErrorMessage(e.getMessage());
            }

        }

    }

    /**
     *
     * @return @throws Exception
     */
    public static Extensions getExtension() throws Exception {
        Extensions ext = new Extensions();

        AuthorityKeyIdentifier auth = new AuthorityKeyIdentifier();
        BasicConstraints basic = new BasicConstraints();
        EnhancedKeyUsage enhanced = new EnhancedKeyUsage();
        KeyUsage keyusage = new KeyUsage();
        Netscape netscape = new Netscape();
        NetscapeCertificateType netsCertType = new NetscapeCertificateType();

        //keyusage
        keyusage.setDigSignature(true);
        keyusage.setNonRepudiation(true);
        keyusage.setDataEncipherment(true);
        keyusage.setKeyAgreement(true);
        keyusage.setCritical(true);
        keyusage.setKeyEncipherment(true);

        //BasicConstraints
        basic.setCritical(true);
        basic.setFinalUser(true);

        //AuthorityKeyIdentifier
        auth.setKeyId(true);

        //netscape
        netsCertType.setSslClient(true);
        netsCertType.setSMime(true);

        //enhacend key usage
        enhanced.setTlsWebClient(true);
        enhanced.setSecureMail(true);

        //opciones
        ext.setAuthInfoAccess(true);
        ext.setCrlDistPoint(true);
        ext.setEdifact(false);
        ext.setSubjectKeyIdentifier(1);

        //objetos.
        ext.setAuthorityKeyIdentifier(auth);
        ext.setBasicConstraints(basic);
        ext.setEnhancedKeyUsage(enhanced);
        ext.setKeyUsage(keyusage);
        ext.setNetscape(netscape);
        ext.setNetscapeCertificateType(netsCertType);

        return ext;
    }

    /**
     * Método que se utiliza para revocar un certificado en la base de datos en
     * base al identificador del usuario.
     *
     * @param user usuario para extraer su id
     */
    public void revokeCertificate(Users user) {

        String id = user.getUserId() + "";
        try {

            Response response = webTarget.path(MessageFormat.format("certificaterevoke/{0}/{1}",
                    new Object[]{sessionId, id})).request(MediaType.APPLICATION_JSON + ";charset=utf-8")
                    .delete(Response.class
                    );
            String item = response.readEntity(String.class
            );

            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                throw new Exception(item);
            } else {
                items = findAll();
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Users").getString("revoke"));
            }

        } catch (Exception e) {
            items = findAll();
            e.printStackTrace();
            JsfUtil.addErrorMessage(e.getMessage());

        }
    }

    /**
     * Método que lo ejecuta el método requestCert y se encarga de obtener el
     * PKCS1 consumiendo un servicio rest de suguridata
     *
     * @param hashB64 hash en base 64 que se obtiene en el método requestCert.
     * @param hashAlgorithm tipo de algoritmo a utilizar por defecto de le pasa
     * SHA-256
     * @param label etiqueta de la llave del agente certificador.
     * @return PKCS1.
     * @throws Exception
     */
    public String doPKCS1(String hashB64, String hashAlgorithm, String label) throws Exception {

        String dopkcs1str = null;
        try {
            if (hashB64.isEmpty()) {

                throw new Exception("El hash de transaccion esta vacio");
            }
            String entity = "{\n"
                    + "\"hashB64\":\"" + hashB64 + "\",\n"
                    + "\"hashAlgorithm\":\"" + hashAlgorithm + "\",\n"
                    + "\"label\":\"" + label + "\"\n"
                    + "}";

            String userName = Util.GetsystemParameter("DOPKCS1USERNAME", "parameterValue");
            String password = Util.GetsystemParameter("DOPKCS1PASSWORD", "parameterValue");

            String urldopk = Util.GetsystemParameter("DOPKCS1URL", "parameterValue");

            HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder()
                    .credentials(userName, password)
                    .build();

            Client clientdopk = ClientBuilder.newClient();
            clientdopk.register(feature);
            // WebTarget webTargetdopk = clientdopk.target("http://201.122.192.62:81/RestWS/tagService/doPKCS1/");
            WebTarget webTargetdopk = clientdopk.target(urldopk);

            Response response = webTargetdopk.request().post(entity(entity, MediaType.APPLICATION_JSON + ";charset=utf-8"), Response.class
            );
//            

            if (response.getStatus() == 200) {

                dopkcs1str = response.readEntity(String.class
                );

            } else {

                throw new Exception(response.readEntity(String.class));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return dopkcs1str;
    }

    /**
     *
     * @return
     */
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        //sessionId = SessionUtils.getSessionId();
        //this.sessionId = SessionUtils.getSessionId();
    }

    /**
     *
     * @return
     */
    public Users getSelected() {
        return selected;
    }

    /**
     *
     * @param selected
     */
    public void setSelected(Users selected) {
        this.selected = selected;
    }

    /**
     * Método que genera una lista de países con la librería Locale que se
     * utiliza al en la creación de usuarios.
     *
     * @return lista de paises
     * @throws IOException
     */
    public List<Locale> getCountry() throws IOException {
        List<Locale> list = new ArrayList<>();
        String[] locales = Locale.getISOCountries();
        //Util.GetsystemParameter("ARCHIVEURL", "parameterValue");
        Arrays.sort(locales);
        for (String countryCode : locales) {

            Locale obj = new Locale("", countryCode);
            list.add(obj);
        }
        return list;
    }

    /**
     *
     * @return
     */
    public List<Users> getItems() {
        if (items == null) {
            items = findAll();
        }
        return items;
    }

    /**
     * Método que obtiene una lista de todos los registros de usuarios
     * almacenados en la base de datos.
     *
     * @return lista de todos los usuarios.
     */
    public List<Users> findAll() {
        try {

            String path = MessageFormat.format(sourceUser + "/{0}", new Object[]{getSessionId()});
            String jsonListString = wsUtil.ExecuteGetRestService(true, BASE_URI, path);
            return convertToList(jsonListString);

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return new ArrayList();
        }
    }

    /**
     * Método que convierte la lista de usuarios en formato JSON en un lista de
     * objetos del tipo Users.
     *
     * @param jsonListString lista en formato JSON de usuarios.
     * @return lista de todos los usuarios.
     */
    private List<Users> convertToList(String jsonListString) {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<Users> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                Users item = JsonUtil.UsersFromJson(jsonList.get(i).toString());
                list.add(item);
            }
            return list;
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

    /**
     * Método que elimina un usuario de la base de datos.
     *
     * @param selected usuario a eliminar.
     */
    public void remove(Users selected) {
        try {

            String usern = selected.getUserId() + "";

            String path = MessageFormat.format(sourceUser + "/{0}/{1}", new Object[]{sessionId, usern});
            wsUtil.ExecuteDeleteRestService(true, BASE_URI, path);

            items = findAll();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Users").getString("UsersDeleted"));

        } catch (Exception e) {
            if (e.getMessage().contains("REFERENCE constraint")) {
                items = findAll();
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Users").getString("consrainDelete"));
            } else {
                items = findAll();
                JsfUtil.addErrorMessage(e.getMessage());
            }
        }

    }

    /**
     * Método que edita la información de un usuario en la base de datos y
     * actualiza la tabla de la interface de usuarios.
     *
     * @param selected
     * @return outcome a la página adminasimetricalgorithms.jsf.
     * @throws Exception
     */
    public String
            edit(Users selected) throws Exception {
        Response response = webTarget.path(MessageFormat.format("{0}", new Object[]{sessionId})).request(MediaType.APPLICATION_JSON + ";charset=utf-8")
                .put(entity(selected, MediaType.APPLICATION_JSON + ";charset=utf-8"), Response.class
                );
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new Exception("Imposible actualizar el área, Codigo de retorno: " + response.getEntity());
        } else {
            return "adminasimetricalgorithms?faces-redirect=true";

        }
    }

    /**
     * Método que inserta un usuario para logearse con username y password.
     *
     * @param selected usuario a insertar
     * @return outcome a la página adminusers.jsf.
     * @throws Exception
     */
    public String insert(Users selected) throws Exception {
        try {
            selected.setUserType((short) 2);
            selected.setAuthenticationMode((short) 1);
            selected.setUsername(selected.FullName());
            selected.setP12("");
            selected.setCertVencimiento(new Date());
            String res = JsonUtil.JsonFromUsers(selected);

            String path = MessageFormat.format(sourceUser + "/{0}", new Object[]{sessionId});
            wsUtil.ExecutePostRestService(true, BASE_URI, path, res);

            items = findAll();
            setSelected(new Users());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Users").getString("UsersCreated"));
            return "adminusers?faces-redirect=true";

        } catch (Exception e) {

            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }
    }

    /**
     * Método que inserta un usuario para logearse con certificado.
     *
     * @return outcome a la página adminusers.jsf.
     *
     */
    public String insertCert() {
        String str = "";
        try {
            if (pfx.equals("") && !"".equals(privateKey) && !"".equals(cerAut) && !"".equals(certUser)) {
                btnDoPKCS12 = false;
                btnCer = true;
                btnReq = true;
                throw new Exception("El certificado personal (P12) esta vacío");
            } else if (privateKey.equals("")) {
                btnReq = false;
                btnCer = false;
                btnDoPKCS12 = false;
                privateKey = "";
                throw new Exception("La llave privada esta vacía");
            } else if (cerAut.equals("") || certUser.equals("")) {
                btnCer = false;
                btnReq = true;
                btnDoPKCS12 = false;
                throw new Exception("No se ha generado el certificado");
            }
            selected.setUserType((short) 2);
            selected.setAuthenticationMode((short) 2);
            selected.setUsername(selected.FullName());
            selected.setCertificate(pfx.getBytes());
            selected.setCertB64(certUser);
            selected.setNoSerie(serialNumberUser);
            selected.setP12(pfx);

            String res = JsonUtil.JsonFromUsers(selected);

            String path = MessageFormat.format(sourceUser + "/createusercertificate/{0}", new Object[]{sessionId});
            wsUtil.ExecutePostRestService(true, BASE_URI, path, res);

            items = findAll();
            setSelected(new Users());
            btnReq = false;
            btnDoPKCS12 = false;
            btnCer = false;
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Users").getString("UsersCreated"));

            str = "adminusers?faces-redirect=true";

        } catch (Exception e) {
            switch (e.getMessage()) {
                case "La llave privada esta vacía":
                    JsfUtil.addErrorMessage(e.getMessage());
                    str = null;
                    break;
                case "No se ha generado el certificado":
                    JsfUtil.addErrorMessage(e.getMessage());
                    str = null;
                    break;
                case "El certificado personal (P12) esta vacío":
                    JsfUtil.addErrorMessage(e.getMessage());
                    str = null;
                    break;
                default:
                    btnDoPKCS12 = true;
                    btnCer = true;
                    btnReq = true;
                    JsfUtil.addErrorMessage(e.getMessage());
                    str = null;
                    break;
            }
        }
        return str;
    }

    /**
     * Método que inserta un usuario administrador para logearse con certificado
     * o username y password.
     *
     * @return outcome a la página adminusers.jsf.
     *
     */
    public String insertaAdmin() {
        String str = "";
        try {
            if (pfx.equals("") && !"".equals(privateKey) && !"".equals(cerAut) && !"".equals(certUser)) {
                btnDoPKCS12 = false;
                btnCer = true;
                btnReq = true;
                throw new Exception("El certificado personal (P12) esta vacío");
            } else if (privateKey.equals("")) {
                btnReq = false;
                btnCer = false;
                btnDoPKCS12 = false;
                privateKey = "";
                throw new Exception("La llave privada esta vacía");
            } else if (cerAut.equals("") || certUser.equals("")) {
                btnCer = false;
                btnReq = true;
                btnDoPKCS12 = false;
                throw new Exception("No se ha generado el certificado");
            }
            selected.setUserType((short) 1);
            selected.setAuthenticationMode((short) 2);
            selected.setUsername(selected.FullName());
            selected.setCertificate(pfx.getBytes());
            selected.setCertB64(certUser);
            selected.setNoSerie(serialNumberUser);
            selected.setP12(pfx);

            String res = JsonUtil.JsonFromUsers(selected);
//            File file = new File("C:/filename.txt");
//
//            // if file doesnt exists, then create it
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//
//            FileWriter fw = new FileWriter(file.getAbsoluteFile());
//            try (BufferedWriter bw = new BufferedWriter(fw)) {
//                bw.write(res);
//            }

            String path = MessageFormat.format(sourceUser + "/createusercertificate/{0}", new Object[]{sessionId});
            wsUtil.ExecutePostRestService(true, BASE_URI, path, res);
            items = findAll();
            setSelected(new Users());
            btnReq = false;
            btnDoPKCS12 = false;
            btnCer = false;
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Users").getString("UsersCreated"));

            str = "adminusers?faces-redirect=true";

        } catch (Exception e) {

            switch (e.getMessage()) {
                case "La llave privada esta vacía":
                    JsfUtil.addErrorMessage(e.getMessage());
                    str = null;
                    break;
                case "No se ha generado el certificado":
                    JsfUtil.addErrorMessage(e.getMessage());
                    str = null;
                    break;
                case "El certificado personal (P12) esta vacío":
                    JsfUtil.addErrorMessage(e.getMessage());
                    str = null;
                    break;
                default:
                    btnDoPKCS12 = true;
                    btnCer = true;
                    btnReq = true;
                    JsfUtil.addErrorMessage(e.getMessage());
                    str = null;
                    break;
            }
        }
        return str;
    }

    /**
     * Método que obtiene un registro de usuario de la base de datos en base a
     * su identificador.
     *
     * @param id del usuario para encontrar el registro.
     * @return objeto de tipo Users.
     */
    public Users getUsers(java.lang.Integer id) {
        try {
            WebTarget resource = webTarget;
            String item = resource.path(MessageFormat.format("{0}", new Object[]{id}))
                    .request(MediaType.APPLICATION_JSON + ";charset=utf-8").get(String.class
            );
            return JsonUtil.UsersFromJson(item);
        } catch (Exception e) {

            JsfUtil.addErrorMessage("No fue posible encontrar el parámetro" + id);
            return null;
        }
    }

    
    /**
     *
     * @param event
     */
    public void onTransfer(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        for (Object item : event.getItems()) {
            builder.append(((Users) item).getUsername()).append("</br>");
        }
    }


   
}
