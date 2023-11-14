/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import io.jsonwebtoken.Claims;
import security.AES;
import security.JwtHelper;

/**
 *
 * @author Usuario
 */
public class ValidacionService {

    public boolean ValidarToken(String usuFecha, String tokennnnn) {

        return usuFecha.equals(AES.decrypt(tokennnnn, "lafedecuto"));

    }

    public boolean ValidarToken(String token) {

 
        try {
             Claims claims = JwtHelper.decodeToken(token);
             System.out.println("Acceso autorizado: " + claims.getSubject());
             return true;
             
        } catch (Exception e) {
            return false;
        }
        
    }

    public static void main(String[] args) {
        ValidacionService vs = new ValidacionService();
        System.out.println(vs.ValidarToken("tUki2023-11-04 12:04:48", "CTFMG5g9N82D57ZifSr1Aq5ZT1yAKSL8y4hiQCCDU6E="));
    }
}
