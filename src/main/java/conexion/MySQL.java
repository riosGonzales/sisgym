package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQL {

    static MySQL instance = null;
    Connection connection = null;

    public MySQL() throws Exception {        
        String url = "jdbc:mysql://localhost:3306/sisgym2?noAccessToProcedureBodies=true";
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, "root", "");
    }

    public static MySQL getInstance() throws Exception {
        if (instance == null) {
            instance = new MySQL();
        }
        return instance;
    }

    public Connection getConnection() {
        System.out.println("CONECTA A LA BD PARA REPORTE");
        return connection;
    }

    @Override
    protected void finalize() throws Throwable {
        connection.close();
        connection=null;
        super.finalize();
    }
    
    public static void main(String[] args) throws Exception {
        try {
            MySQL.getInstance().getConnection();
            System.out.println("Conectado");
        } catch (Exception e) {
            System.out.println("No");
        }
    }
}
