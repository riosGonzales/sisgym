/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import security.AES;

/**
 *
 * @author Usuario
 */
public class ValidacionService {
    
    
    
    public boolean ValidarToken(String usuFecha, String token) {

        return usuFecha.equals(AES.decrypt(token, "lafedecuto"));
    }
    
    public static void main(String[] args) {
        ValidacionService vs = new ValidacionService();
      System.out.println(vs.ValidarToken("tUki2023-11-04 12:04:48","CTFMG5g9N82D57ZifSr1Aq5ZT1yAKSL8y4hiQCCDU6E=" )) ;
    }
}
