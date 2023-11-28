
package Session;

import javax.servlet.http.HttpSession;

public class Sesion {
    public static void crearSesion(HttpSession sesion){
        sesion.setAttribute("valido", "ok");
    }
    public static boolean validoOK(HttpSession sesion){
        try {
            String valido = sesion.getAttribute("valido").toString();
        if(valido.equals("ok")){
            return true;
        }
        return false;
        } catch (Exception e) {
             return false;
        }
       
        
    }
    public static void eliminarSesion(HttpSession sesion){
        sesion.removeAttribute("valido");
        sesion.invalidate();
    }
}
