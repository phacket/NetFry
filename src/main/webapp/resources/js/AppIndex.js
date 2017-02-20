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
        window.location = "index.jsf";
    });

    $("#return-s").click(function () {
        window.location = "index.jsf";
    });

    $("#return-m").click(function () {
        window.location = "index.jsf";
    });

    $("#return-p").click(function () {
        window.location = "index.jsf";
    });

    $("#return-home").click(function () {
        window.location = "index.jsf";
    });

    $("#return-areas").click(function () {
        window.location = "admin/adminareas.jsf";
    });

    $("#datepicker").datepicker({
        dateFormat: "yy-mm-dd"
    });

    $("#datepicker2").datepicker({
        dateFormat: "yy-mm-dd"
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

   // $(window).load(function () {
   //     $('.blueberry').blueberry();
   // });

    if ($("body").height() < $(window).height()) {
        $("footer").css({"position": "absolute", "bottom": "0px"});
    }


    // ------- DIRECCIONAMIENTOS DE LAS PAGINAS  --------
    // Direccionamientos Return
    $("#return-r").click(function () {
        window.location = "index.jsf";
    });

    $("#return-s").click(function () {
        window.location = "index.jsf";
    });

    $("#return-m").click(function () {
        window.location = "index.jsf";
    });

    $("#return-home").click(function () {
        window.location = "index.jsf";
    });

    // Direccionamientos Menu Principal
    $("#admindocuments").click(function () {
        window.location = "admin/admindocuments.jsf";
    });

    $("#adminprocess").click(function () {
        window.location = "admin/menuadminprocess.jsf";
    });

    $("#reports").click(function () {
        window.location = "reports/menureports.jsf";
    });

    $("#monitoring").click(function () {
        window.location = "monitoring/menumonitoring.jsf";
    });

    $("#policies").click(function () {
        window.location = "admin/menupolicies.jsf";
    });

    $("#configuration").click(function () {
        window.location = "admin/systemparameters.jsf";
    });

    // Direccionamientos Menu Reportes
    $("#r-user").click(function () {
        window.location = "reports/usersinplatform.jsf";
    });

    $("#r-admin").click(function () {
        window.location = "reports/admininplatform.jsf";

    });

    $("#r-doc1").click(function () {
        window.location = "reports/docsinplatform.jsf";
    });

    $("#r-doc2").click(function () {
        window.location = "reports/docsinstandard.jsf";
    });

    $("#r-doc3").click(function () {
        window.location = "reports/signaturesbydoc.jsf";
    });

    $("#r-doc4").click(function () {
        window.location = "reports/docstoexpire.jsf";
    });

    $("#r-cer").click(function () {
        window.location = "reports/certtoexpire.jsf";
    });

    // Direccionamientos Menu Seguridad
    $("#s-are").click(function () {
        window.location = "admin/adminareas.jsf";
    });

    $("#s-gru").click(function () {
        window.location = "admin/admingroups.jsf";
    });

    $("#s-use").click(function () {
        window.location = "admin/adminusers.jsf";
    });

    $("#s-per").click(function () {
        window.location = "admin/adminprofileprocesses.jsf";
    });

    $("#s-lla").click(function () {
        window.location = "admin/adminhsmkeys.jsf";
    });

    // Direccionamientos Menu Monitoreo
    $("#m-use").click(function () {
        window.location = "monitoring/userlog.jsf";
    });

    $("#m-aud").click(function () {
        window.location = "monitoring/audittrial.jsf";
    });

    // Direccionamientos Menu Politicas
    $("#p-asy").click(function () {
        window.location = "admin/adminasimetricalgorithms.jsf";
    });

    $("#p-sig").click(function () {
        window.location = "admin/adminalgorithms.jsf";
    });

    $("#p-has").click(function () {
        window.location = "admin/adminhashalgorithms.jsf";
    });

    $("#p-lib").click(function () {
        window.location = "admin/adminlibraryalgorithms.jsf";
    });

    $("#p-his").click(function () {
        window.location = "admin/algorithmhistory.jsf";
    });

    /*    DIRECCIONAMIENTO APARTADO AREAS   */
    $("#add-area").click(function () {
        window.location = "admin/adminarea.jsf";
    });

    $("#cancel-area").click(function () {
        window.location = "admin/adminareas.jsf";
    });

    $("#end-area").click(function () {
        window.location = "admin/adminareas.jsf";
    });


    /*    DIRECCIONAMIENTO APARTADO GRUPOS   */
    $("#add-group").click(function () {
        window.location = "admin/admingroup.jsf";
    });

    $("#cancel-group").click(function () {
        window.location = "admin/admingroups.jsf";
    });

    $("#end-group").click(function () {
        window.location = "admin/admingroups.jsf";
    });

    /*    DIRECCIONAMIENTO APARTADO HSM KEYS   */
    $("#add-hsmkey").click(function () {
        window.location = "admin/adminhsmkey.jsf";
    });

    $("#cancel-hsm").click(function () {
        window.location = "admin/adminhsmkeys.jsf";
    });

    $("#end-hsm").click(function () {
        window.location = "admin/adminhsmkeys.jsf";
    });

    /*    DIRECCIONAMIENTO APARTADO PERFILES DE PROCESO   */
    $("#add-profile").click(function () {
        window.location = "admin/adminprofileprocess.jsf";
    });

    $("#cancel-profile").click(function () {
        window.location = "admin/adminprofileprocesses.jsf";
    });

    $("#next-profile").click(function () {
        window.location = "admin/adminprofileprocesshsmkeys.jsf";
    });

    $("#end-profile").click(function () {
        window.location = "admin/adminprofileprocesses.jsf";
    });

    /*    DIRECCIONAMIENTO APARTADO USUARIOS   */
    $("#add-user").click(function () {
        window.location = "admin/menuusers.jsf";
    });

    $("#cancel-user").click(function () {
        window.location = "admin/adminusers.jsf";
    });

    $("#next-user1").click(function () {
        window.location = "admin/adminuseraccess.jsf";
    });

    $("#next-user2").click(function () {
        window.location = "admin/admingroupusers.jsf";
    });

    $("#end-user").click(function () {
        window.location = "admin/adminusers.jsf";
    });

    $("#end-user").click(function () {
        window.location = "admin/adminusers.jsf";
    });
    $("#end-user").click(function () {
        window.location = "admin/adminusers.jsf";
    });

    /*    DIRECCIONAMIENTO APARTADO MONITOREO  */
    $("#m-audadmin").click(function () {
        window.location = "monitoring/audittrailadmin.jsf";
    });
    $("#m-usead").click(function () {
        window.location = "monitoring/userlogadmin.jsf";
    });
    $("#m-useuser").click(function () {
        window.location = "monitoring/userloguser.jsf";
    });
    $("#m-auduser").click(function () {
        window.location = "monitoring/audittrailuser.jsf";
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
        window.location = "admin/adminuseradmin.jsf";
    });

    $("#s-usecert").click(function () {
        window.location = "admin/adminusercertificate.jsf";
    });

    $("#s-usepass").click(function () {
        window.location = "admin/adminuserpassword.jsf";
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

