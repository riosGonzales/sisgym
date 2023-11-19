
// Alice's code
const crypto = require('crypto');

// Generar par de claves para Alice
const alice = crypto.createDiffieHellman(2048);
const aliceKeys = alice.generateKeys();

// Enviar la clave pública de Alice a Bob (normalmente a través de una red)
const alicePublicKey = alice.getPublicKey('hex');

// La clave pública de Bob (recibida por Alice)
const bobPublicKey = '<clave_publica_de_bob>'; // Deberías obtener esto de alguna manera en el mundo real

// Calcular la clave compartida de Alice
const aliceSharedKey = alice.computeSecret(bobPublicKey, 'hex', 'hex');

console.log('Alice Shared Key:', aliceSharedKey);


