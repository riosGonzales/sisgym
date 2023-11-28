package Service;

import Entities.Prueba;
import com.google.gson.Gson;
import java.util.List;
import jpa.PruebaJpaController;

public class PruebaService {
    
    PruebaJpaController objPrueba = new PruebaJpaController();
    
    public String getJSon() {
        List<Prueba> lista = objPrueba.findPruebaEntities();
        Gson g = new Gson();
        String resultado = g.toJson(lista);
        return "{\"data\":" + resultado + " }";
    }
    
}
