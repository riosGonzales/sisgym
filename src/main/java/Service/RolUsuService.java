package Service;
import Entities.Rolusuario;
import PDF.GenerarPDF;
import java.util.List;
import jpa.RolusuarioJpaController;

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
