var webSocket;
var messages = document.getElementById("messages");

function openSocket() {
    if (webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED) {
        writeResponse("WebSocket ya está abierto");
        return;
    }
    let ruta = "ws://localhost:8080/proceso";
//webSocket = new WebSocket("ws://" + document.location.hostname + ":9999" + document.location.pathname + "proceso");
    webSocket = new WebSocket(ruta);

    webSocket.onopen = function (event) {
        if (event.data === undefined)
            return;
        writeResponse(event.data);
    };
    webSocket.onmessage = function (event) {
        writeResponseJSON(event.data);
    };
    webSocket.onclose = function (event) {
        writeResponse("Conexión cerrada");
    };
}




function send() {
    try {
        alert(clave);
        var usuario = document.getElementById("usuarioinput").value;
        var text = document.getElementById("messageinput").value;
        var textCifrado = cifradoAES(text, clave);
        var fecha = new Date();

        let msj = {user: usuario, text: textCifrado, fecha: fecha};
        let smsj = JSON.stringify(msj);
        webSocket.send(smsj);
        document.getElementById("messageinput").value = "";
    } catch (err) {
        // Manejar errores aquí
    }
}

function closeSocket() {
    try {
        webSocket.close();
    } catch (err) {
        // Manejar errores aquí
    }
}

function writeResponse(text) {
    messages.innerHTML += "<br/>" + text;
}

function writeResponseJSON(text) {

    try {
        const msj = JSON.parse(text);

        messages.innerHTML += "<br/>" + msj.user + " dice: " + descifradoAES(msj.text, clave) + ", " + msj.fecha;
    } catch (e) {
        console.log("Mensaje de Error -->" + e);
    }


}


function cifradoAES(mensaje, clave) {
    var ciphertext = CryptoJS.AES.encrypt(mensaje, clave).toString();
    return ciphertext;
}

function descifradoAES(ciphertext, clave) {
    var bytes = CryptoJS.AES.decrypt(ciphertext, clave);
    var originalText = bytes.toString(CryptoJS.enc.Utf8);
    return originalText;
}