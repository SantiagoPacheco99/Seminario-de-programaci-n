
import java.sql.*;

public class BancoRefactorizado {

    // Extract Constant
    private static final String DATABASE_URL =
            "jdbc:mysql://localhost:5000/banco";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static final String INSERT_MOVEMENT_QUERY =
            "INSERT INTO movimientos(cuenta,mov,importe) VALUES (?, ?, ?)";

    private static final String DEPOSIT_QUERY =
            "UPDATE cuentas SET saldo = saldo + ? WHERE cuenta = ?";

    private static final String WITHDRAW_QUERY =
            "UPDATE cuentas SET saldo = saldo - ? WHERE cuenta = ?";

    private Connection connection = null;

    public BancoRefactorizado() throws SQLException {
        conectar();
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
    private boolean clienteExiste(int accountNumber)
            throws SQLException {

        String query =
                "SELECT * FROM cuentas WHERE cuenta = ?";

        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(query);

            statement.setInt(1, accountNumber);

            ResultSet resultSet =
                    statement.executeQuery();

            return resultSet.next();

        } finally {

            if (statement != null) {
                statement.close();
            }
        }
    }

    // Extract Method
    private boolean saldoSuficiente(
            int accountNumber,
            double amount) throws SQLException {

        String query =
                "SELECT saldo FROM cuentas WHERE cuenta = ?";

        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(query);

            statement.setInt(1, accountNumber);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                double balance =
                        resultSet.getDouble("saldo");

                return balance >= amount;
            }

        } finally {

            if (statement != null) {
                statement.close();
            }
        }

        return false;
    }

    // Remove Duplicate Code
    // Extract Method
    private void ejecutarOperacion(
            int accountNumber,
            double amount,
            String movementType,
            String updateQuery) throws SQLException {

        PreparedStatement movementStatement = null;
        PreparedStatement balanceStatement = null;

        connection.setAutoCommit(false);

        try {

            registrarMovimiento(
                    accountNumber,
                    amount,
                    movementType);

            balanceStatement =
                    connection.prepareStatement(updateQuery);

            balanceStatement.setDouble(1, amount);
            balanceStatement.setInt(2, accountNumber);

            balanceStatement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {

            connection.rollback();

            e.printStackTrace();

        } finally {

            if (movementStatement != null) {
                movementStatement.close();
            }

            if (balanceStatement != null) {
                balanceStatement.close();
            }

            connection.setAutoCommit(true);
        }
    }

    // Extract Method
    private void registrarMovimiento(
            int accountNumber,
            double amount,
            String movementType) throws SQLException {

        PreparedStatement statement =
                connection.prepareStatement(
                        INSERT_MOVEMENT_QUERY);

        statement.setInt(1, accountNumber);
        statement.setString(2, movementType);
        statement.setDouble(3, amount);

        statement.executeUpdate();

        statement.close();
    }

    // Extract Method
    public void depositar(
            int accountNumber,
            double amount) throws SQLException {

        if (clienteExiste(accountNumber)) {

            ejecutarOperacion(
                    accountNumber,
                    amount,
                    "D",
                    DEPOSIT_QUERY);

            System.out.println("Deposito realizado");
        }
    }

    // Extract Method
    public void extraer(
            int accountNumber,
            double amount) throws SQLException {

        if (clienteExiste(accountNumber)
                && saldoSuficiente(accountNumber, amount)) {

            ejecutarOperacion(
                    accountNumber,
                    amount,
                    "E",
                    WITHDRAW_QUERY);

            System.out.println("Extraccion realizada");

        } else {

            System.out.println(
                    "Saldo insuficiente o cuenta inexistente");
        }
    }

    // Rename
    public void mostrarMovimientos(
            int accountNumber) throws SQLException {

        String query =
                "SELECT * FROM movimientos WHERE cuenta = ?";

        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(query);

            statement.setInt(1, accountNumber);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                System.out.println(
                        "Cuenta: "
                        + resultSet.getInt("cuenta")
                        + ", Movimiento: "
                        + resultSet.getString("mov")
                        + ", Importe: "
                        + resultSet.getDouble("importe"));
            }

        } finally {

            if (statement != null) {
                statement.close();
            }
        }
    }

    public static void main(String[] args) {

        try {

            BancoRefactorizado bankSystem =
                    new BancoRefactorizado();

            bankSystem.depositar(1001, 5000);
            bankSystem.extraer(1001, 1000);
            bankSystem.mostrarMovimientos(1001);
            bankSystem.cerrar();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}