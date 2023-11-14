/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Usuario
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(dao.service.AsistenciaFacadeREST.class);
        resources.add(dao.service.ClasesFacadeREST.class);
        resources.add(dao.service.ClienteFacadeREST.class);
        resources.add(dao.service.FacturaFacadeREST.class);
        resources.add(dao.service.MatriculaFacadeREST.class);
        resources.add(dao.service.MembresiaFacadeREST.class);
        resources.add(dao.service.PagoFacadeREST.class);
        resources.add(dao.service.UsuarioFacadeREST.class);
    }
    
}
