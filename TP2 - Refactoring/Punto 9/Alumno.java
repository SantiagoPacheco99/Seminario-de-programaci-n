package Punto4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Alumno {

    Connection connection = null;

    public Alumno() throws SQLException {
        try {
            conectar();
            inscribir();
        } finally {
            cerrar();
        }
    }

    public void conectar() throws SQLException {
        String jdbc = "jdbc:mysql://localhost:5000/alumno";
        connection = DriverManager.getConnection(jdbc, "root", "");
        System.out.println("Conexion OK");
    }

    public void cerrar() throws SQLException {
        if (connection != null)
            connection.close();
    }

    // Metodo que inscribe a alumnos
    public void inscribir() throws SQLException {
        PreparedStatement stmt1 = null, stmt2 = null;
        connection.setAutoCommit(false);
        try {
            // Se preparan las sentencias SQL
            stmt1 = connection.prepareStatement("INSERT INTO alumnos (id,nombre,gmail) VALUES( ?, ?, ? );");
            stmt2 = connection.prepareStatement("INSERT INTO inscripciones (id_inscripcion,id_alumno) VALUES(?, ?);");
            System.out.println("Primer INSERT tabla [alumnos] ");
            stmt1.setInt(1, 1);
            stmt1.setString(2, "Juan Perez");
            stmt1.setString(3, "jperez@mail.com");
            stmt1.executeUpdate();

            System.out.println("Segundo INSERT tabla [alumnos] ");
            stmt1.setInt(1, 2);
            stmt1.setString(2, "Pablo Gomez");
            stmt1.setString(3, "pgomez@mail.com");
            stmt1.executeUpdate();

            System.out.println("Tercer INSERT tabla [alumnos] ");
            stmt1.setInt(1, 3);
            stmt1.setString(2, "Juana Perez");
            stmt1.setString(3, "juanaperez@mail.com");
            stmt1.executeUpdate();
            System.out.println("Primer INSERT tabla [inscripciones]");
            stmt2.setInt(1, 1);
            // stmt2.setInt(2, 3); // Tipo de dato CORRECTO INT
            // stmt2.setInt(2, "Dato erroneo"); // Tipo de dato INCORRECTO
            connection.commit();
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        } finally {
            if (stmt1 != null)
                stmt1.close();
            if (stmt2 != null)
                stmt2.close();
        }
    }

    public static void main(String[] args) {
        try {
            new Alumno();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
