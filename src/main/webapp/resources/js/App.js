/* global getElementById */


$(document).ready(function () {



    $("#btn-typeGroup").click(function () {
        if ($("#typeUser").val() === "admin") {
            location.href = "AdminGroupPermissions.html";
        } else {
            location.href = "AdminGroupHSMKey.html";
        }
    });

    $("#return-r").click(function () {
        window.location = "../index.jsf";
    });

    $("#return-s").click(function () {
        window.location = "../index.jsf";
    });

    $("#return-m").click(function () {
        window.location = "../index.jsf";
    });

    $("#return-p").click(function () {
        window.location = "../index.jsf";
    });

    $("#return-home").click(function () {
        window.location = "../index.jsf";
    });

    $("#return-areas").click(function () {
        window.location = "adminareas.jsf";
    });


    $("#btn-date1").click(function () {
        var monthComplete;
        var today = new Date();
        var day = today.getUTCDate();
        var month = today.getUTCMonth();
        month += 1;
        if (month < 10) {
            monthComplete = "0" + month;
        } else {
            monthComplete = month;
        }
        var year = today.getFullYear();
        document.getElementById("datepicker").value = year + "-" + monthComplete + "-" + day;
    });

    $("#btn-date2").click(function () {
        var monthComplete;
        var today = new Date();
        var day = today.getUTCDate();
        var month = today.getUTCMonth();
        month += 1;
        if (month < 10) {
            monthComplete = "0" + month;
        } else {
            monthComplete = month;
        }
        var year = today.getFullYear();
        document.getElementById("datepicker2").value = year + "-" + monthComplete + "-" + day;
    });



    if ($("body").height() < $(window).height()) {
        $("footer").css({"position": "absolute", "bottom": "0px"});
    }


    // ------- DIRECCIONAMIENTOS DE LAS PAGINAS  --------
    // Direccionamientos Return
    $("#return-r").click(function () {
        window.location = "../index.jsf";
    });

    $("#return-s").click(function () {
        window.location = "../index.jsf";
    });

    $("#return-m").click(function () {
        window.location = "../index.jsf";
    });

    $("#return-home").click(function () {
        window.location = "../index.jsf";
    });

    // Direccionamientos Menu Principal
    $("#admindocuments").click(function () {
        window.location = "admindocuments.jsf";
    });

    $("#adminprocess").click(function () {
        window.location = "menuadminprocess.jsf";
    });

    $("#reports").click(function () {
        window.location = "menureports.jsf";
    });

    $("#monitoring").click(function () {
        window.location = "menumonitoring.jsf";
    });

    $("#policies").click(function () {
        window.location = "menupolicies.jsf";
    });

    $("#configuration").click(function () {
        window.location = "systemparameters.jsf";
    });

    // Direccionamientos Menu Reportes
    $("#r-user").click(function () {
        window.location = "usersinplatform.jsf";
    });

    $("#r-admin").click(function () {
        window.location = "admininplatform.jsf";

    });

    $("#r-doc1").click(function () {
        window.location = "docsinplatform.jsf";
    });

    $("#r-doc2").click(function () {
        window.location = "docsinstandard.jsf";
    });

    $("#r-doc3").click(function () {
        window.location = "signaturesbydoc.jsf";
    });

    $("#r-doc4").click(function () {
        window.location = "docstoexpire.jsf";
    });

    $("#r-cer").click(function () {
        window.location = "certtoexpire.jsf";
    });

    // Direccionamientos Menu Seguridad
    $("#s-are").click(function () {
        window.location = "adminareas.jsf";
    });

    $("#s-gru").click(function () {
        window.location = "admingroups.jsf";
    });

    $("#s-use").click(function () {
        window.location = "adminusers.jsf";
    });

    $("#s-per").click(function () {
        window.location = "adminprofileprocesses.jsf";
    });

    $("#s-lla").click(function () {
        window.location = "adminhsmkeys.jsf";
    });

    // Direccionamientos Menu Monitoreo
    $("#m-use").click(function () {
        window.location = "userlog.jsf";
    });

    $("#m-aud").click(function () {
        window.location = "audittrial.jsf";
    });

    // Direccionamientos Menu Politicas
    $("#p-asy").click(function () {
        window.location = "adminasimetricalgorithms.jsf";
    });

    $("#p-sig").click(function () {
        window.location = "adminalgorithms.jsf";
    });

    $("#p-has").click(function () {
        window.location = "adminhashalgorithms.jsf";
    });

    $("#p-lib").click(function () {
        window.location = "adminlibraryalgorithms.jsf";
    });

//    $("#p-his").click(function () {
//        window.location = "algorithmhistory.jsf";
//    });

    /*    DIRECCIONAMIENTO APARTADO AREAS   */
    $("#add-area").click(function () {
        window.location = "adminarea.jsf";
    });

    $("#cancel-area").click(function () {
        window.location = "adminareas.jsf";
    });

    $("#end-area").click(function () {
        window.location = "adminareas.jsf";
    });


    /*    DIRECCIONAMIENTO APARTADO GRUPOS   */
    $("#add-group").click(function () {
        window.location = "admingroup.jsf";
    });

    $("#cancel-group").click(function () {
        window.location = "admingroups.jsf";
    });

    $("#end-group").click(function () {
        window.location = "admingroups.jsf";
    });

    /*    DIRECCIONAMIENTO APARTADO HSM KEYS   */
    $("#add-hsmkey").click(function () {
        window.location = "adminhsmkey.jsf";
    });

    $("#cancel-hsm").click(function () {
        window.location = "adminhsmkeys.jsf";
    });

    $("#end-hsm").click(function () {
        window.location = "adminhsmkeys.jsf";
    });

    /*    DIRECCIONAMIENTO APARTADO PERFILES DE PROCESO   */
    $("#add-profile").click(function () {
        window.location = "adminprofileprocess.jsf";
    });

    $("#cancel-profile").click(function () {
        window.location = "adminprofileprocesses.jsf";
    });

    $("#next-profile").click(function () {
        window.location = "adminprofileprocesshsmkeys.jsf";
    });

    $("#end-profile").click(function () {
        window.location = "adminprofileprocesses.jsf";
    });

    /*    DIRECCIONAMIENTO APARTADO USUARIOS   */
    $("#add-user").click(function () {
        window.location = "menuusers.jsf";
    });

    $("#cancel-user").click(function () {
        window.location = "adminusers.jsf";
    });

    $("#next-user1").click(function () {
        window.location = "adminuseraccess.jsf";
    });

    $("#next-user2").click(function () {
        window.location = "admingroupusers.jsf";
    });

    $("#end-user").click(function () {
        window.location = "adminusers.jsf";
    });

    $("#end-user").click(function () {
        window.location = "adminusers.jsf";
    });
    $("#end-user").click(function () {
        window.location = "adminusers.jsf";
    });

    /*    DIRECCIONAMIENTO APARTADO MONITOREO  */
    $("#m-audadmin").click(function () {
        window.location = "audittrailadmin.jsf";
    });
    $("#m-usead").click(function () {
        window.location = "userlogadmin.jsf";
    });
    $("#m-useuser").click(function () {
        window.location = "userloguser.jsf";
    });
    $("#m-auduser").click(function () {
        window.location = "audittrailuser.jsf";
    });

    /*    DIRECCIONAMIENTO APARTADO POLITICAS  */

    $("#auth-method").change(function () {
        var method = $("select[name='auth-methodInner'] option:selected").val();
        if (method === "user") {
            $("#for-cert").hide();
            $("#for-cert2").hide();
        } else {
            $("#for-cert").show();
            $("#for-cert2").show();
        }
    });

    $("#btn-users").click(function () {
        $("#tabs-user").show();
        $("#tabs-admins").hide();
    });

    $("#btn-admins").click(function () {
        $("#tabs-user").hide();
        $("#tabs-admins").show();
    });


    $("#s-usead").click(function () {
        window.location = "adminuseradmin.jsf";
    });

    $("#s-usecert").click(function () {
        window.location = "adminusercertificate.jsf";
    });

    $("#s-usepass").click(function () {
        window.location = "adminuserpassword.jsf";
    });



    $.datepicker.regional['es'] = {
        closeText: 'Cerrar',
        prevText: '<Ant',
        nextText: 'Sig>',
        currentText: 'Hoy',
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
        dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mié', 'Juv', 'Vie', 'Sáb'],
        dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sá'],
        weekHeader: 'Sm',
        dateFormat: 'dd/mm/yy',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: ''
    };
    $.datepicker.setDefaults($.datepicker.regional['es']);
    $(function () {
        $("#fecha").datepicker();

    });






});




