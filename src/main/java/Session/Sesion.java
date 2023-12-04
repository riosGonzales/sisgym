package Session;

import javax.servlet.http.HttpSession;

public class Sesion {

    public static void crearSesion(HttpSession sesion,String tipoUsua) {
        sesion.setAttribute("valido", "ok");
         sesion.setAttribute("tipoUsua", tipoUsua);
    }

    public static boolean validoOK(HttpSession sesion) {
        try {
            String valido = sesion.getAttribute("valido").toString();
            return valido.equals("ok");
        } catch (Exception e) {
            return false;
        }

    }

    public static void eliminarSesion(HttpSession sesion) {
        sesion.removeAttribute("valido");
        sesion.invalidate();
    }
}
