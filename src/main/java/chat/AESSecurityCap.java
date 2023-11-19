/*
 * Clase AESSecurityCap: Proporciona funcionalidades de encriptación y desencriptación utilizando AES
 * con intercambio de claves basado en el protocolo de acuerdo de claves ECDH.
 */
package chat;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AESSecurityCap {

    private PublicKey clavePublica;
    public String claveparaJS;

    // Objeto para el acuerdo de claves ECDH
    KeyAgreement acuerdoClaves;

    // Clave secreta compartida derivada del acuerdo de claves
    byte[] secretoCompartido;

    // Algoritmo de cifrado AES
    String ALGO = "AES";

    // Constructor
    AESSecurityCap() {
        generarParametrosIntercambioClaves(); // Inicializa los parámetros del intercambio de claves
        System.out.println("Clave pública generada: " + Base64.getEncoder().encodeToString(clavePublica.getEncoded()));
    }

    public String getClavePublicaString() {
        return Base64.getEncoder().encodeToString(clavePublica.getEncoded());
    }

    // Inicializa los parámetros del intercambio de claves ECDH
    private void generarParametrosIntercambioClaves() {
        try {
            // Genera un par de claves para el intercambio de claves ECDH
            KeyPairGenerator generadorParClaves = KeyPairGenerator.getInstance("EC");
            generadorParClaves.initialize(256);
            KeyPair parClaves = generadorParClaves.generateKeyPair();

            // Obtiene la clave pública del par de claves
            clavePublica = parClaves.getPublic();
            System.out.println("Clave privada generada: " + Base64.getEncoder().encodeToString(parClaves.getPrivate().getEncoded()));

            // Inicializa el objeto KeyAgreement con la clave privada del par de claves
            acuerdoClaves = KeyAgreement.getInstance("ECDH");
            acuerdoClaves.init(parClaves.getPrivate());

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    // Establece la clave pública del receptor para completar el acuerdo de claves
    public void setClavePublicaReceptor(PublicKey clavePublicaReceptor) {
        try {
            acuerdoClaves.doPhase(clavePublicaReceptor, true);
            secretoCompartido = acuerdoClaves.generateSecret();
            claveparaJS = Base64.getEncoder().encodeToString(secretoCompartido);
            System.out.println("Clave secreta compartida generada: " + claveparaJS);

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    // Encripta un mensaje utilizando AES
    public String encriptar(String mensaje) throws NoSuchPaddingException, IllegalBlockSizeException {
        try {
            // Genera la clave para AES
            Key clave = generarClave();

            // Inicializa el cifrado para la encriptación
            Cipher cifrado = Cipher.getInstance(ALGO);
            cifrado.init(Cipher.ENCRYPT_MODE, clave);

            // Encripta el mensaje y devuelve la representación en Base64
            byte[] valorEncriptado = cifrado.doFinal(mensaje.getBytes());
            return Base64.getEncoder().encodeToString(valorEncriptado);

        } catch (BadPaddingException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return mensaje;
    }

    // Desencripta un mensaje utilizando AES
    public String desencriptar(String datosEncriptados) {
        try {
            // Genera la clave para AES
            Key clave = generarClave();

            // Inicializa el cifrado para la desencriptación
            Cipher cifrado = Cipher.getInstance(ALGO);
            cifrado.init(Cipher.DECRYPT_MODE, clave);

            // Decodifica los datos encriptados en Base64
            byte[] valorDecodificado = Base64.getDecoder().decode(datosEncriptados);

            // Desencripta los datos y los convierte a una cadena
            byte[] valorDesencriptado = cifrado.doFinal(valorDecodificado);
            return new String(valorDesencriptado);

        } catch (BadPaddingException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return datosEncriptados;
    }

    // Devuelve la clave pública
    public PublicKey getClavePublica() {
        return clavePublica;
    }

    // Genera una clave secreta utilizando la clave compartida y el algoritmo AES
    protected Key generarClave() {
        return new SecretKeySpec(secretoCompartido, ALGO);
    }

}
