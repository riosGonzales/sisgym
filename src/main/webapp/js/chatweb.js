var webSocket;
var messages = document.getElementById("messages");
var logi = sessionStorage.getItem("logi");
var claveObt = sessionStorage.getItem("clave");
console.log(logi);
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
        alert(claveObt);
        var usuario = sessionStorage.getItem("logi");
        var text = document.getElementById("messageinput").value;
        var textCifrado = cifradoAES(text, claveObt);
        var fecha = new Date();

        let msj = {user: usuario, text: textCifrado, fecha: fecha};
        let smsj = JSON.stringify(msj);
        webSocket.send(smsj);
        document.getElementById("messageinput").value = "";
    } catch (err) {
        console.log(err);
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
        const fecha = new Date(msj.fecha);

        const ahora = new Date();
        const ayer = new Date(ahora);
        ayer.setDate(ayer.getDate() - 1);

        let fechaFormateada;
        if (
                fecha.getDate() === ahora.getDate() &&
                fecha.getMonth() === ahora.getMonth() &&
                fecha.getFullYear() === ahora.getFullYear()
                ) {
            fechaFormateada = "hoy a las " + fecha.toLocaleString('es-ES', {hour: '2-digit', minute: '2-digit'});
        } else if (
                fecha.getDate() === ayer.getDate() &&
                fecha.getMonth() === ayer.getMonth() &&
                fecha.getFullYear() === ayer.getFullYear()
                ) {
            fechaFormateada = "ayer a las " + fecha.toLocaleString('es-ES', {hour: '2-digit', minute: '2-digit'});
        } else {
            fechaFormateada = fecha.toLocaleString('es-ES', {
                year: 'numeric',
                month: 'numeric',
                day: 'numeric',
                hour: '2-digit',
                minute: '2-digit',
                second: '2-digit'
            });
        }

        const usuarioIniciales = msj.user.slice(0, 3).toUpperCase();
        messages.innerHTML +=
                "<li class='chat incoming'>" +
                "<span class='user-initials' style='font-size: 10px;'>" + "<small>" + usuarioIniciales + "</small>" + "</span>" +
                "<p>" + "<small>" + msj.user + "</small>" + ":<br/>" +
                descifradoAES(msj.text, claveObt) + "<br/>" +
                "<small class='small-date'>" + fechaFormateada + "</small></p>" +
                "</li>";

        // Desplaza el scroll hacia abajo hasta el final del contenedor de mensajes
        chatbox.scrollTop = chatbox.scrollHeight;

    } catch (err) {
        console.error(err);
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