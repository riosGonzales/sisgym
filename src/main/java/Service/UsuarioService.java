package Service;

import Entities.Empleado;
import Entities.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import jpa.UsuarioJpaController;

public class UsuarioService {

    UsuarioJpaController objUsuario = new UsuarioJpaController();
   
    public String buscarUsuario(Integer id) {
        Usuario usuario = objUsuario.findUsuario(id);
        JsonObject jsonUsuario = new JsonObject();

        if (usuario.getEmpleadoidEmpleado() != null) {
            Empleado empleado = usuario.getEmpleadoidEmpleado();
            String nombreUsuario = usuario.getLogiUsua();
            String tipoUsuario = usuario.getTipoUsuario();
            Date fechaRegistro = usuario.getFechaRegistro();
            String estadoUsuario = usuario.getEstadoUsuario();

            String nombreCompleto = empleado.getNombreEmpleado() + " " + empleado.getApellidoEmpleado();

            // Formatear la fecha como "yyyy-MM-dd"
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechaRegistroFormatted = dateFormat.format(fechaRegistro);

            jsonUsuario.addProperty("nombreUsuario", nombreUsuario);
            jsonUsuario.addProperty("tipoUsuario", tipoUsuario);
            jsonUsuario.addProperty("fechaRegistro", fechaRegistroFormatted);
            jsonUsuario.addProperty("estadoUsuario", estadoUsuario);
            jsonUsuario.addProperty("nombreCompleto", nombreCompleto);
        }

        // Convertir el JsonObject a una cadena JSON
        Gson gson = new GsonBuilder().create();
        String resultado = gson.toJson(jsonUsuario);

        return resultado;
    }
    
     public static void main(String[] args) {
        UsuarioService objUsuario = new UsuarioService();
        String resultado = objUsuario.buscarUsuario(1);
        System.out.println(resultado);
    }
    
    
}




