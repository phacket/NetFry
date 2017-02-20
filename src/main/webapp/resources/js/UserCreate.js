/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//Se Inicializan los controles en el formulario
$(document).ready(function () {
    $("#form\\:BtnSave").css("display", "none");//Descarga del certificado
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
        "detail": "Por serguridad no se permite la recarga de esta página ",
        "severity": "info"
    });
}

//Valida si se debe activa el boton de generacion de requerimiento
function ActivarRequerimiento(control)
{
    var activar = true;
    var validControl = true;
    nombre = $("#input_form\\:nombre").val();
    apaterno = $("#input_form\\:apaterno").val();
    amaterno = $("#input_form\\:amaterno").val();
    telefono = $("#input_form\\:telNumber").val();
    calle = $("#input_form\\:calle").val();
    numeroExt = $("#input_form\\:numeroExt").val();
    cpostal = $("#input_form\\:cpostal").val();
    municipio = $("#input_form\\:municipio").val();
    colonia = $("#input_form\\:colonia").val();
    estado = $("#input_form\\:estado").val();
    area = $("#form\\:areaInner").val();
    rfc = $("#input_form\\:rfc").val();
    curp = $("#input_form\\:curp").val();
    country = $("#form\\:countryInner").val();
    email = $("#input_form\\:email").val();
    password = $("#input_form\\:password").val();
    password2 = $("#input_form\\:confirm").val();


    if (nombre === "" || apaterno === "" || amaterno === "" || telefono === "" || calle === "" || numeroExt === "" || cpostal === "" || municipio === "")
        activar = false;
    if (colonia === "" || estado === "" || area === "" || rfc === "" || curp === "" || country === "" || email === "")
        activar = false;
    if (password === "" || password2 === "")
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
    if (activar && validControl)
        $("#form\\:BtnSave").css("display", "inline");//Ocultar el boton de requerimiento
    else
    {
        $("#form\\:BtnSave").css("display", "none");
    }
}