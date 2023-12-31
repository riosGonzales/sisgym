package chat;

import java.io.IOException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Daniscarft
 */
public class node extends AESSecurityCap {

    public static void main(String[] args) throws IOException, NoSuchPaddingException, IllegalBlockSizeException {
        node server = new node();
        node client = new node();
        node server2 = new node();
        node client2 = new node();
        server.setClavePublicaReceptor(client.getClavePublica());

        client.setClavePublicaReceptor(server.getClavePublica());

        String data = "Hola";

        String enc = server.encriptar(data);

        System.out.println("Hola se convierte en  " + enc);

        System.out.println(enc + " es convertido a " + client.desencriptar(enc));
        System.out.println("Clave de Bob: " + obtenerClaveBob());

    }

    public static String obtenerClaveBob() {
        node bob = new node();
        return bob.getClavePublicaString();
    }
}
