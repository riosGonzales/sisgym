package Service;

import Entities.Rol;
import com.google.gson.Gson;
import java.util.List;
import jpa.RolJpaController;

public class RolService {
    
    RolJpaController objPrueba = new RolJpaController();
    
    public String getJSon() {
        List<Rol> lista = objPrueba.findRolEntities();
        Gson g = new Gson();
        String resultado = g.toJson(lista);
        return "{\"data\":" + resultado + " }";
    }
    
}
