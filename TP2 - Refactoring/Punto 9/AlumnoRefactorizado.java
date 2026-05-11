import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AlumnoRefactorizado {

    // Extract Constant
    private static final String DATABASE_URL =
            "jdbc:mysql://localhost:5000/alumno";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static final String INSERT_ALUMNO =
            "INSERT INTO alumnos (id,nombre,gmail) VALUES( ?, ?, ? );";

    private static final String INSERT_INSCRIPCION =
            "INSERT INTO inscripciones (id_inscripcion,id_alumno) VALUES(?, ?);";

    private Connection connection = null;

    public AlumnoRefactorizado() throws SQLException {

        try {
            conectar();
            inscribir();
        } finally {

            cerrar();
        }
    }

    // Rename
    public void conectar() throws SQLException {

        connection = DriverManager.getConnection(
                DATABASE_URL,
                USER,
                PASSWORD);

        System.out.println("Conexion OK");
    }

    public void cerrar() throws SQLException {

        if (connection != null) {
            connection.close();
        }
    }

    // Extract Method
    public void inscribir() throws SQLException {

        PreparedStatement studentStatement = null;
        PreparedStatement enrollmentStatement = null;

        connection.setAutoCommit(false);

        try {

            studentStatement =
                    connection.prepareStatement(INSERT_ALUMNO);

            enrollmentStatement =
                    connection.prepareStatement(INSERT_INSCRIPCION);

            insertarAlumno(
                    studentStatement,
                    1,
                    "Juan Perez",
                    "jperez@mail.com");

            insertarAlumno(
                    studentStatement,
                    2,
                    "Pablo Gomez",
                    "pgomez@mail.com");

            insertarAlumno(
                    studentStatement,
                    3,
                    "Juana Perez",
                    "juanaperez@mail.com");

            insertarInscripcion(
                    enrollmentStatement,
                    1,
                    3);

            connection.commit();

        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            connection.rollback();
        } finally {
            
            if (studentStatement != null) {
                studentStatement.close();
            }

            if (enrollmentStatement != null) {
                enrollmentStatement.close();
            }
        }
    }

    // Extract Method
    // Remove Duplicate Code
    private void insertarAlumno(
            PreparedStatement studentStatement,
            int id,
            String nombre,
            String gmail) throws SQLException {

        studentStatement.setInt(1, id);
        studentStatement.setString(2, nombre);
        studentStatement.setString(3, gmail);

        studentStatement.executeUpdate();

        System.out.println(
                "Alumno insertado: " + nombre);
    }

    // Extract Method
    private void insertarInscripcion(
            PreparedStatement enrollmentStatement,
            int idInscripcion,
            int idAlumno) throws SQLException {

        enrollmentStatement.setInt(1, idInscripcion);
        enrollmentStatement.setInt(2, idAlumno);

        enrollmentStatement.executeUpdate();

        System.out.println(
                "Inscripcion registrada");
    }

    public static void main(String[] args) {

        try {

            new AlumnoRefactorizado();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}