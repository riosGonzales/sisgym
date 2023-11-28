/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Session;

import javax.servlet.http.HttpSession;

/**
 *
 * @author jano_
 */
public class Session {
    

    public static void crearsesion(HttpSession session, int codi, String logi, String tipoUsuario) {
        session.setAttribute("logueado", "1");
        session.setAttribute("codiUsua", codi);
        session.setAttribute("logiUsua", logi);
        session.setAttribute("TipoUsuario", tipoUsuario);

    }

    public static boolean sesionvalida(HttpSession session) {
        String logueado = (String) session.getAttribute("logueado");
        if (logueado.equals("1")) {
            return true;
        }
        return false;
    }

    public static void cerrarsesion(HttpSession session) {
        session.removeAttribute("logueado");
        session.removeAttribute("codiUsua");
        session.removeAttribute("logiUsua");
        session.removeAttribute("TipoUsuario");
        session.invalidate();
    }

    public static int getCodi(HttpSession session) {
        Object ocodigo = session.getAttribute("codiUsua");
        return Integer.parseInt(ocodigo.toString());
    }

    public static String getLogi(HttpSession session) {
        Object ologi = session.getAttribute("logiUsua");
        return ologi.toString();
    }

}
