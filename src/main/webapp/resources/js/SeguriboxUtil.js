/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Validar spinner numerico de semanas cuando cambia y lo foza a 1
function ValidaSemanas() {
    var numero =  $("#input_forminput\\:semanas").val();
    var numeroString = new String(numero);
    if(numeroString.charAt(0) === "0") {
        $("#input_forminput\\:semanas").val("1");
    }
    if (numero < 1 || numero >100) {
        $("#input_forminput\\:semanas").val("1");
    }
    if (numero >100) {
        $("#input_forminput\\:semanas").val("99");
    }
    return true;
}

