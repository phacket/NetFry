/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//funciones de validacion de Mail, CURP y RFC
function validPassword() {

    var password = document.getElementById("input_form:password").value;
    var confirm = document.getElementById("input_form:confirm").value;
    if (confirm == "") {
        return false;
    }
    if (password === confirm) {
        $("#confirmmsg").css('display', 'none');
        return true;
    } else {
        $("#confirmmsg").css('display', 'inline');
        $("#confirmmsg").html("Contraseñas no coinciden");
        $("#confirmmsg").css('color', 'red');
        return false;
    }
}

function validAnularPassword() {
    var password = document.getElementById("input_form:password2").value;
    var confirm = document.getElementById("input_form:confirm2").value;
    if (confirm=="") {
        return false;
    }
    if (password === confirm) {
        $("#confirmanumsg").css('display', 'none');
        return true;
    } else {
        $("#confirmanumsg").css('display', 'inline');
        $("#confirmanumsg").html("Contraseñas no coinciden");
        $("#confirmanumsg").css('color', 'red');
        return false;
    }
}
function validEmail() {
    var email = document.getElementById("input_form:email").value;
    if (email=="") {
        return false;
    }
    var pattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    if (pattern.test(email)) {
        $("#emailmsg").css('display', 'none');
        return true;
    } else {
        $("#emailmsg").css('display', 'inline');
        $("#emailmsg").html("Formato de email incorrecto");
        $("#emailmsg").css('color', 'red');
        return false;
    }
}
function validCurp() {
    var curp = document.getElementById("input_form:curp").value;
    if (curp=="") {
        return false;
    }
    var pattern = /^[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[HM]{1}(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)[B-DF-HJ-NP-TV-Z]{3}[0-9A-Z]{1}[0-9]{1}$/;
    if (pattern.test(curp)) {
        $("#curp").css('display', 'none');
        return true;
    } else {
        $("#curp").css('display', 'inline');
        $("#curp").html("Formato de CURP incorrecto");
        $("#curp").css('color', 'red');
        return false;
    }
}
function validRfc() {
    var rfc = document.getElementById("input_form:rfc").value;
    if (rfc=="") {
        return false;
    }
    var pattern = /^([A-ZÑ\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[A-Z|\d]{3})$/;
    if (pattern.test(rfc)) {
        $("#rfcmsg").css('display', 'none');
        return true;
    } else {
        $("#rfcmsg").css('display', 'inline');
        $("#rfcmsg").html("Formato de RFC incorrecto");
        $("#rfcmsg").css('color', 'red');
        return false;
    }
}

function validarTelefono() {
    var telnumber = document.getElementById("input_form:telNumber").value;
    if (telnumber.length == 0) {
        return false;
    }
    if (telnumber.length == 10) {
        $("#telmsg").css('display', 'none');
        return true;
    } else {
        $("#telmsg").css('display', 'inline');
        $("#telmsg").html("El número telefonico es incorrecto");
        $("#telmsg").css('color', 'red');
        return false;
    }

}


function DateLong(fecha) {
// Opera 8.0+
    var isOpera = (!!window.opr && !!opr.addons) || !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
    // Firefox 1.0+
    var isFirefox = typeof InstallTrigger !== 'undefined';
    // Safari 3.0+ "[object HTMLElementConstructor]" 
    var isSafari = /constructor/i.test(window.HTMLElement) || (function (p) {
        return p.toString() === "[object SafariRemoteNotification]";
    })(!window['safari'] || safari.pushNotification);
    // Internet Explorer 6-11
    var isIE = /*@cc_on!@*/false || !!document.documentMode;
    // Edge 20+
    var isEdge = !isIE && !!window.StyleMedia;
    // Chrome 1+
    var isChrome = !!window.chrome && !!window.chrome.webstore;
    // Blink engine detection
    var isBlink = (isChrome || isOpera) && !!window.CSS;

    // Simpre convierte la fecha de español a ingles para el calculo
    var fecha2 = fecha.toLowerCase();
    fecha2 = fecha2.replace("enero", "january");
    fecha2 = fecha2.replace("febrero", "february");
    fecha2 = fecha2.replace("marzo", "march");
    fecha2 = fecha2.replace("abril", "april");
    fecha2 = fecha2.replace("mayo", "may");
    fecha2 = fecha2.replace("junio", "june");
    fecha2 = fecha2.replace("julio", "july");
    fecha2 = fecha2.replace("agosto", "august");
    fecha2 = fecha2.replace("septiembre", "september");
    fecha2 = fecha2.replace("octubre", "october");
    fecha2 = fecha2.replace("noviembre", "november");
    fecha2 = fecha2.replace("diciembre", "december");

    var d = new Date();
    var year = d.getFullYear();
    var month = d.getMonth();
    var day = d.getDate();
    var c = new Date(year + 1, month, day)
    var longresult = Date.parse(c); // por defecto un año si hay algun error de explorador

    if (isIE || isEdge) // es internet explorer o edge
    {
        //Para IE convierte el mes en numero para que lo pueda parsear
        var fecha3 = fecha2;
        fecha3 = fecha3.replace("january", "01");
        fecha3 = fecha3.replace("february", "02");
        fecha3 = fecha3.replace("march", "03");
        fecha3 = fecha3.replace("april", "04");
        fecha3 = fecha3.replace("may", "05");
        fecha3 = fecha3.replace("june", "06");
        fecha3 = fecha3.replace("july", "07");
        fecha3 = fecha3.replace("august", "08");
        fecha3 = fecha3.replace("september", "09");
        fecha3 = fecha3.replace("october", "10");
        fecha3 = fecha3.replace("november", "11");
        fecha3 = fecha3.replace("december", "12");
        fecha3 = fecha3.substring(3, 5) + "/" + fecha3.substring(0, 2) + "/" + fecha3.substring(6, 10);
        longresult = Date.parse(fecha3);
    } else // If another browser, return 0
    {
        longresult = Date.parse(fecha2);
    }
    if (isNaN(longresult))
        return Date.parse(c); // por defecto un año si hay algun erro de explorador
    else
        return longresult;
}