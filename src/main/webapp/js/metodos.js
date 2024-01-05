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

/*La función calculateSharedKey realiza los siguientes pasos:
 
 Genera una clave privada para Alice utilizando el algoritmo de intercambio de claves de Diffie-Hellman elíptico (ECDH) con una curva elíptica de 256 bits.
 Importa la clave pública de Bob, que previamente ha sido codificada en base64 y luego convertida al formato necesario para ser importada
 como una clave pública. Utiliza las claves de Alice y Bob para derivar una clave compartida utilizando el algoritmo ECDH,
 y esta clave compartida se define como una clave para el algoritmo de cifrado simétrico AES-GCM de 256 bits.
 Exporta la clave compartida en formato raw y la convierte a base64 para ser retornada como resultado.*/

async function calculateSharedKey(bobPublicKeyBase64) {
    // Generar claves de Alice
    const alice = await crypto.subtle.generateKey(
            {
                name: "ECDH",
                namedCurve: "P-256" // Curva elíptica utilizada
            },
            true, // extracción de la clave privada (false si no se necesita)
            ["deriveKey"] // uso de la clave
            );

    // Importar la clave pública de Bob
    const bobPublicKeyBuffer = new Uint8Array(atob(bobPublicKeyBase64).split('').map(c => c.charCodeAt(0)));
    const bobPublicKey = await crypto.subtle.importKey(
            "spki",
            bobPublicKeyBuffer,
            {
                name: "ECDH",
                namedCurve: "P-256"
            },
            true,
            []
            );

    // Calcular la clave compartida de Alice
    const aliceSharedKey = await crypto.subtle.deriveKey(
            {
                name: "ECDH",
                public: bobPublicKey
            },
            alice.privateKey,
            {
                name: "AES-GCM",
                length: 256
            },
            true,
            ["encrypt", "decrypt"]
            );

    // Exportar la clave compartida a base64
    const exportedKey = await crypto.subtle.exportKey("raw", aliceSharedKey);
    const sharedKeyBase64 = btoa(String.fromCharCode(...new Uint8Array(exportedKey)));

    return sharedKeyBase64;
}

async function runDH(claveBOB) {
    // Clave pública de Bob en Base64 (para ejemplo)
    //const bobPublicKeyBase64 = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE0Q+PJa/E7r2/Hrq3bUkwIaE7z/q/mAKZcefOx2xkppUOJZBOfJIur714oF98/ytY7Q8JNGVdr1E7mB82QcPkTA==";
    const bobPublicKeyBase64 = claveBOB;
    // Calcular la clave compartida
    const aliceSharedKey = await calculateSharedKey(bobPublicKeyBase64);

    return aliceSharedKey;
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