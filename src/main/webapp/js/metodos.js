
function cifrar(message) {
    var pass = CryptoJS.SHA256(message);
    var hashHex = pass.toString(CryptoJS.enc.Hex);
    console.log("pass: " + pass);
    //console.log("hex: " + hashHex);
    return hashHex;
}

function limpiar() {
    const inputElements = document.querySelectorAll('input');
    for (const input of inputElements) {
        input.value = '';
    }
}

function establecerCookie(nombre, valor, diasParaExpirar) {
    var fechaExpiracion = new Date();
    fechaExpiracion.setTime(fechaExpiracion.getTime() + (diasParaExpirar * 24 * 60 * 60 * 1000));
    var expires = "expires=" + fechaExpiracion.toUTCString();
    document.cookie = nombre + "=" + valor + ";" + expires + ";path=/";
}

// Función para obtener el valor de una cookie
function obtenerCookie(nombre) {
    var nombreCookie = nombre + "=";
    var cookies = decodeURIComponent(document.cookie).split(';');
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].trim();
        if (cookie.indexOf(nombreCookie) === 0) {
            return cookie.substring(nombreCookie.length);
        }
    }
    return null; // Devuelve null si no se encuentra la cookie
}
