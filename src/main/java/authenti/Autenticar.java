package authenti;

import Entities.Usuario;
import java.util.ArrayList;
import java.util.List;

public class Autenticar {

    static List<String> direcciones = new ArrayList<>();

    public Autenticar() {
    }

    public static String asociarLlave(Usuario usu) {
        direcciones.add("E:\\Downloads\\Gym_SegundoMOD\\Casi_Final\\Sis_Gym\\Sis_Gym\\src\\main\\webapp");
        direcciones.add("E:\\User\\Documentos\\UNI\\VI CICLO\\Criptografia II\\Sistema pyGymfit\\sisGYM ACTUAL\\Sis_Gym\\Sis_Gym\\src\\main\\webapp");
        direcciones.add("C:\\Users\\wtke9\\Downloads\\Sis_Gym\\Sis_Gym\\src\\main\\webapp");
        
        String anshipath = direcciones.get(2) + "\\img\\qr" + usu.getLogiUsua() + ".png";
        /*    String Requenapath = "E:\\Downloads\\daniel\\Sis_Gym\\src\\main\\webapp\\img\\qr" + usu.getLogiUsua() + ".png";

        String Janopath = ""C:\\Users\\jano_\\Downloads\\Sis_Gym\\src\\qr\\qr" + usu.getLogiUsua() + ".png";
        String Marinpath = "C:\\Users\\wtke9\\Downloads\\Sis_Gym\\src\\qr\\qr" + usu.getLogiUsua() + ".png";
        String Angiepath = "C:\\Users\\wtke9\\Downloads\\Sis_Gym\\src\\qr\\qr" + usu.getLogiUsua() + ".png";        */
        String companyName = "SisGym";
        String email = usu.getLogiUsua();
        String secretKey = f2.generateSecretKey();
        String barCodeUrl = f2.getGoogleAuthenticatorBarCode(secretKey, email, companyName);
        try {
            f2.createQRCode(barCodeUrl, anshipath, 500, 500);
            return secretKey;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static boolean autenticarCodigo(Usuario usu, String codigo) {
        String llaveUsu = usu.getToknUsua();
        String hexKeyUsu = f2.getTOTPCode(llaveUsu);
        return codigo.equals(hexKeyUsu);
    }

}
