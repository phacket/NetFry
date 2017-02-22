/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//Se Inicializan los controles en el formulario
$(document).ready(function () {
    $("#form\\:btnReq").css("display", "none"); //Descarga del certificado
    $("#form\\:req").css("display", "none"); //requerimiento
    $("#input_form\\:telNumber").mask("9999999999");
    $("#input_form\\:cpostal").mask("99999");
    $("#input_form\\:rfc").mask("AAAAAAAAAAAAA");
    $("#input_form\\:curp").mask("AAAAAAAAAAAAAAAAAA");
    shortcut.add("F5", function () {
        AvoidReload();
    });
    shortcut.add("Ctrl+F5", function () {
        AvoidReload();
    });
    shortcut.add("Ctrl+R", function () {
        AvoidReload();
    });
    shortcut.add("Shift+F5", function () {
        AvoidReload();
    });
});

function AvoidReload() {
    PF('growlWV').renderMessage({
        "summary": "Información",
        "detail": "Por seguridad no se permite la recarga de esta página ",
        "severity": "info"
    });
}

//Variables que se enviaran en la ejecucion de los servicios
var nombre = "";
var apaterno = "";
var amaterno = "";
var fullName = "";
var telefono = "";
var calle = "";
var numeroExt = "";
var cpostal = "";
var municipio = "";
var colonia = "";
var estado = "";
var area = "";
var rfc = "";
var curp = "";
var country = "";
var email = "";
var keysize = "";
var passwordanul = "";
var passwordanul2 = "";
var password = "";
var password2 = "";
var fechaven = new Date();
var privatekey = "";
var publickey = "";
var certificateus = "";
var certificateac = "";
var pfx = "";
var sessionId = "";
var serialNumber = "";
var url = "";
var urlinsert = "";
var fechaven2 = new Date();
var numrequests = 0;
var reqexitoso = false;
var saveexitoso = false;
var certGenerado=false;

//Valida si se debe activa el boton de generacion de requerimiento
function ActivarRequerimiento(control) {
    
    var activar = true;
    var validControl = true;
    nombre = $("#input_form\\:nombre").val();
    apaterno = $("#input_form\\:apaterno").val();
    amaterno = $("#input_form\\:amaterno").val();
    fullName = $("#input_form\\:commonName").val();
    telefono = $("#input_form\\:telNumber").val();
    calle = $("#input_form\\:calle").val();
    numeroExt = $("#input_form\\:numeroExt").val();
    cpostal = $("#input_form\\:cpostal").val();
    municipio = $("#input_form\\:municipio").val();
    colonia = $("#input_form\\:colonia").val();
    estado = $("#input_form\\:estado").val();
    area = $("#form\\:area").val();
    rfc = $("#input_form\\:rfc").val();
    curp = $("#input_form\\:curp").val();
    country = $("#form\\:countryInner").val();
    email = $("#input_form\\:email").val();
    keysize = $("#form\\:keySizeInner").val();
    passwordanul = $("#input_form\\:password2").val();
    passwordanul2 = $("#input_form\\:confirm2").val();
    password = $("#input_form\\:password").val();
    password2 = $("#input_form\\:confirm").val();
    fechaven = $("#form\\:cdate1_input").val();
    sessionId = $("#form\\:usersessionid").val();

    if (nombre === "" || apaterno === "" || amaterno === "" || telefono === "" || calle === "" || numeroExt === "" || cpostal === "" || municipio === "")
        activar = false;
    if (colonia === "" || estado === "" || area === "" || rfc === "" || curp === "" || country === "" || email === "" || keysize === "")
        activar = false;
    if (passwordanul === "" || password === "" || fechaven === "" || passwordanul2 === "" || password2 === "")
        activar = false;

    if (control == 'telefono') {
        validControl = validarTelefono();
    }
    if (control == 'rfc') {
        validControl = validRfc();
    }
    if (control == 'email') {
        validControl = validEmail();
    }
    if (control == 'curp') {
        validControl = validCurp();
    }
    if (control == 'confirm') {
        validControl = validPassword();
    }
    if (control == 'confirmanula') {
        validControl = validAnularPassword();
    }
    if (certGenerado==true) { //No se puede activar el boton nuevamente por el certificado ya fue generado
        activar = false;
    }
    if (activar && validControl)
        $("#form\\:req").css("display", "inline"); //Ocultar el boton de requerimiento
    else {
        $("#form\\:btnReq").css("display", "none");
        $("#form\\:req").css("display", "none");
    }

}
//Validacion de los campos obligatorio
function ValidateForm() {
    var str = ""; //Variable de retorno
    //Asignar los controles a las variables para su validacion
    $("#form\\:publickey").val("");
    $("#form\\:privatekey").val("");

    fechaven2 = DateLong($("#form\\:cdate1_input").val());
    if (certGenerado==true) {
        str = str + "* El certificado ya fue generado     *\n";
    }

    if (nombre === "") {
        str = str + "* El nombre es mandatorio            *\n";
    }
    if (apaterno === "") {
        str = str + "* El apellido paterno es mandatorio  *\n";
    }
    if (amaterno === "") {
        str = str + "* El apellido materno es mandatorio  *\n";
    }
    if (fullName === "") {
        str = str + "* El nombre completo es mandatorio   *\n";
    }
    if (telefono === "") {
        str = str + "* El numero telefónico es mandatorio *\n";
    }
    if (calle === "") {
        str = str + "* La calle es mandatoria              *\n";
    }
    if (numeroExt === "") {
        str = str + "* El número exterior es mandatorio    *\n";
    }
    if (cpostal === "") {
        str = str + "* El codigo postal es mandatorio      *\n";
    }
    if (municipio === "") {
        str = str + "* El municipio es mandatorio          *\n";
    }
    if (colonia === "") {
        str = str + "* La colonia es mandatoria            *\n";
    }
    if (estado === "") {
        str = str + "* El estado es mandatorio             *\n";
    }
    if (area === "") {
        str = str + "* El área es mandatoria               *\n";
    }
    if (rfc === "") {
        str = str + "* El RFC es mandatorio                *\n";
    }
    if (curp === "") {
        str = str + "* El CURP es mandatorio               *\n";
    }
    if (country === "") {
        str = str + "* El país es mandatorio               *\n";
    }
    if (email === "") {
        str = str + "* El correo electrónico es mandatorio *\n";
    }
    if (keysize === "") {
        str = str + "* El tamaño de la llave es mandatoria *\n";
    }
    if (passwordanul === "") {
        str = str + "* La clave de anulación es mandatoria *\n";
    }
    if (password === "") {
        str = str + "* La contraseña es mandatoria         *\n";
    }
    if (fechaven === "") {
        str = str + "* La fecha es mandatoria              *\n";
    }
    if (passwordanul !== passwordanul2) {
        str = str + "* Las claves de anulación deben ser las mismas *\n";
    }
    if (password !== password2) {
        str = str + "* Las contraseñas deben ser las mismas *\n";
    }
    if (!validCurp()) {
        str = str + "* El CURP es invalido                  *\n";
    }
    if (!validEmail()) {
        str = str + "* El correo electrónico es invalido    *\n";
    }
    if (!validRfc()) {

        str = str + "* El RFC es invalido                   *\n";
    }
    return str;
}

//Generar requerimiento
$("#form\\:req").on("click", function () {
    //Valida que todos los campo cunmplan con el formato y contenido
    var validFormMsg = "";
    validFormMsg = ValidateForm();
    if (validFormMsg !== "") {
        //La captura de campos es incorrecta y faltante
        PF('growlWV').renderMessage({
            "summary": "Información!",
            "detail": validFormMsg,
            "severity": "warm"
        });
        return;
    }
    //La captura de datos esta completa se procede a generar el requerimiento
     PF('growlWV').renderMessage({
        "summary": "Información!",
        "detail": "Generando llaves",
        "severity": "info"
    });
    //Generacion del llas llaves del requerimiento
    genKeys(keysize, password, function (error, result) {
        if (error) {
            PF('growlWV').renderMessage({
                "summary": "Información!",
                "detail": "Se ha generado un error al generar el requerimiento",
                "severity": "warm"
            });
            return;
        }
        $("#form\\:publickey").val(result.publicKey);
        $("#form\\:privatekey").val(result.privateKey);
        privatekey = $("#form\\:privatekey").val();
        publickey = $("#form\\:publickey").val();
        //Configuracion que viene desde el servidor
        url = $("#form\\:urlrequest").val();
        urlinsert = $("#form\\:urluserinsert").val() + sessionId;
         PF('growlWV').renderMessage({
        "summary": "Información!",
        "detail": "Llaves generadas correctamente",
        "severity": "info"
    });
        // realizar la firma del requerimiento
        RequestCertificate();

    });

});

//Genera el certificado del usuario
function RequestCertificate() {
    numrequests += 1;
    var resultado = false;
    var jsonRequestEntity = JSON.stringify({
        fullName: fullName,
        country: country,
        email: email,
        colonia: colonia,
        estado: estado,
        publickey: publickey,
        certVencimiento: fechaven2,
        area: area,
        sessionid: sessionId
    });
     PF('growlWV').renderMessage({
        "summary": "Información!",
        "detail": "Generando certificado",
        "severity": "info"
    });
    var requestReqResult = $.ajax({
        url: url,
        type: 'POST',
        headers: {'Access-Control-Allow-Origin': '*'},
        crossDomain: true,
        data: jsonRequestEntity,
        contentType: 'application/json;charset=utf-8',
        dataType: 'json'
    });
    requestReqResult.done(function (data) {
         PF('growlWV').renderMessage({
        "summary": "Información!",
        "detail": "Certificado generado correctamente",
        "severity": "info"
    });
        certificateac = data.certAut;
        certificateus = data.certUser;
         PF('growlWV').renderMessage({
        "summary": "Información!",
        "detail": "Generando P12",
        "severity": "info"
    });
        SgData_CodePKCS12(["-----BEGIN CERTIFICATE-----" + certificateus + "-----END CERTIFICATE-----", "-----BEGIN CERTIFICATE-----" + certificateac + "-----END CERTIFICATE-----"], privatekey, password, function (error, result) {
            if (error) {
                PF('growlWV').renderMessage({
                    "summary": "Información!",
                    "detail": error,
                    "severity": "warm"
                });
            } else {
                pfx = result;
                $("#form\\:pfx").val(result);
            }
        });
        SaveUserCertificate();
        reqexitoso = true;
        certGenerado=true;
    });
    requestReqResult.fail(function (jqXHR, textStatus) {
        var ajaxresult=jqXHR.responseText;
        PF('growlWV').renderMessage({
            "summary": "Información",
            "detail": "Error al generar los certificados: " +ajaxresult,
            "severity": "warm"
        });
        $("#form\\:req").css("display", "inline");
    });
    return resultado;
}
//Guardar el usuario en base de datos
function SaveUserCertificate() {
    //Crear la entidad usuario que se va a guardar en base de datos
    var jsonUserEntity = JSON.stringify({
        userId: 0,
        userType: 1, //Administrador
        areaId: area,
        onlyReader: false,
        username: nombre,
        password: password,
        firstName: nombre,
        middleName: apaterno,
        lastName: amaterno,
        organization: "",
        keySizeBits: null,
        challengePrase: null,
        privateKey: null,
        rfc: rfc,
        curp: curp,
        puesto: null,
        identificador: null,
        calle: calle,
        numero: numeroExt,
        codigoPostal: cpostal,
        municipio: municipio,
        colonia: colonia,
        estado: estado,
        authenticationMode: 2,
        certificate: null,
        p12: pfx,
        noSerie: serialNumber,
        certVencimiento: fechaven2,
        hash256Certificate: null,
        isActive: true,
        email: email,
        telNumber: telefono,
        countryCode: country,
        isLockedOut: false,
        lastSigninDate: null,
        referenceDate: null,
        lastUpdated: null,
        iPAddress: null,
        statusId: null,
        lastUserLogRecordId: null,
        lastActivityDate: null,
        sessionId: null,
        areaName: "",
        certb64: certificateus,
        certB64: certificateus,
        ipaddress: null
    });
     PF('growlWV').renderMessage({
        "summary": "Información!",
        "detail": "Guardando usuario",
        "severity": "info"
    });
    //Metodo que invoca el servicio de creacion de usuario
    var requestSaveResult = $.ajax({
        url: urlinsert,
        type: 'POST',
        headers: {'Access-Control-Allow-Origin': '*'},
        data: jsonUserEntity,
        contentType: 'application/json;charset=utf-8',
        crossDomain: true,
        dataType: 'json'
    });

    requestSaveResult.done(function (msg) {
        PF('growlWV').renderMessage({
            "summary": "Información!",
            "detail": "El usuario ha sido creado satisfactoriamente",
            "severity": "info"
        });
        $("#form\\:btnReq").css("display", "inline");
        $("#form\\:req").css("display", "none");
        saveexitoso = true;
    });
    requestSaveResult.fail(function (jqXHR, textStatus) {
        var ajaxresult=jqXHR.responseText;
        PF('growlWV').renderMessage({
            "summary": "Información!",
            "detail": "Error al guardar el usuario con certificado: " + ajaxresult,
            "severity": "warm"
        });
        $("#form\\:btnReq").css("display", "none");
        $("#form\\:req").css("display", "inline");
    });
}
