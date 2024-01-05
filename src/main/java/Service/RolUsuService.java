package Service;

import Entities.Cliente;
import Entities.Rolusuario;
import PDF.GeneradorPDF;
import PDF.GenerarPDF;
import com.google.gson.Gson;
import java.util.List;
import jpa.RolusuarioJpaController;

/**
 *
 * @author wtke9
 */
public class RolUsuService {

    RolusuarioJpaController objPrueba = new RolusuarioJpaController();

    public List<Rolusuario> ListaUsu() {
        return objPrueba.findClientesByEstado(true);
    }

    public void imprimirReporte() {
        GenerarPDF gpdf = new GenerarPDF();
        gpdf.imprimirPDF();
    }
}
