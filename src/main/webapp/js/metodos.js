
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