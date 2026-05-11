package Punto5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Banco {

    private Connection conexion = null;

    public Banco() throws SQLException {
        try {
            conectar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void conectar() throws SQLException {
        String jdbc = "jdbc:mysql://localhost:5000/banco";
        conexion = DriverManager.getConnection(jdbc, "root", "");
        System.out.println("Conexion OK");
    }

    public void cerrar() throws SQLException {
        if (conexion != null)
            conexion.close();
    }

    private boolean clienteExiste(int cuenta) throws SQLException {
        String CONSULTA = "select * from cuentas\n" +
                "where cuenta = ?;";
        PreparedStatement consulta = null;
        try {
            consulta = conexion.prepareStatement(CONSULTA);
            consulta.setInt(1, cuenta);
            ResultSet set = consulta.executeQuery();
            if (set.next()) {
                cuenta = set.getInt("cuenta");
                return true;
            }
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (consulta != null) {
                consulta.close();
            }
        }
        return false;
    }

    // Metodo que verifica si existe un cliente en la BD
    public boolean existe_cliente(int cuenta) throws SQLException {
        try {
            return clienteExiste(cuenta);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Metodo que verifica si el saldo de una X cuenta es suficiente para realizar X
    public boolean saldoSoI(int cuenta, double monto) throws SQLException {
        String CONSULTA = "select saldo from cuentas\n" +
                "where cuenta = ?;";
        PreparedStatement consulta = null;
        try {
            consulta = conexion.prepareStatement(CONSULTA);
            consulta.setInt(1, cuenta);
            ResultSet set = consulta.executeQuery();
            if (set.next()) {
                double saldo = set.getDouble("saldo");
                return saldo >= monto; // Retorna verdadero si el monto a extraer es menor o igual al saldo
            }
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (consulta != null) {
                consulta.close();
            }
        }

        return false;
    }

    // Punto a - Para extraer o depositar al cliente X, este debe existir en la
    // tabla Clientes. Manejar situacion con transacciones

    // Metodo que deposita en una cuenta
    private void depositar(int cuenta, double monto) throws SQLException {
        conexion.setAutoCommit(false);
        String MOVI = "INSERT INTO movimientos(cuenta,mov,importe) VALUES (?, ?, ?)";
        String CUENTA = "UPDATE cuentas set saldo = saldo + ? where cuenta = ?";
        PreparedStatement movi = null, cuenta_cliente = null;
        try {
            movi = conexion.prepareStatement(MOVI);
            movi.setInt(1, cuenta);
            movi.setString(2, "D");
            movi.setDouble(3, monto);
            movi.executeUpdate();
            cuenta_cliente = conexion.prepareStatement(CUENTA);
            cuenta_cliente.setDouble(1, monto);
            cuenta_cliente.setInt(2, cuenta);
            cuenta_cliente.executeUpdate();
            conexion.commit();
            conexion.setAutoCommit(true);
        } catch (SQLException e) {
            conexion.rollback();
            e.printStackTrace();
        } finally {
            if (movi != null)
                movi.close();
            if (cuenta_cliente != null)
                cuenta_cliente.close();
        }
        cerrar();
    }

    public void realizar_deposito(int cuenta, double monto) throws SQLException {
        try {
            if (existe_cliente(cuenta)) {
                depositar(cuenta, monto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metodo que extrae de la tabla cuentas

    public void extraer(int cuenta, double monto) throws SQLException {
        conexion.setAutoCommit(false);
        String MOVI = "INSERT INTO movimientos(cuenta,mov,importe) VALUES (?, ?, ?)";
        String CUENTA = "UPDATE cuentas set saldo = saldo - ? where cuenta = ?";
        PreparedStatement movi = null, cuenta_cliente = null;
        try {
            movi = conexion.prepareStatement(MOVI);
            movi.setInt(1, cuenta);
            movi.setString(2, "E");
            movi.setDouble(3, monto);
            movi.executeUpdate();
            cuenta_cliente = conexion.prepareStatement(CUENTA);
            cuenta_cliente.setDouble(1, monto);
            cuenta_cliente.setInt(2, cuenta);
            cuenta_cliente.executeUpdate();
            conexion.commit();
            conexion.setAutoCommit(true);
        } catch (SQLException e) {
            conexion.rollback();
            e.printStackTrace();
        } finally {
            if (movi != null)
                movi.close();
            if (cuenta_cliente != null)
                cuenta_cliente.close();
        }
        cerrar();
    }

    // Punto c - Mostrar todos los movimientos de una cuenta de la tabla
    // movimientos.
    public void mostrar_movimientos(int cuenta) throws SQLException {
        String QUERY = "select * from movimientos\n" +
                "where cuenta = ?";
        PreparedStatement consulta = null;
        try {
            consulta = conexion.prepareStatement(QUERY);
            consulta.setInt(1, cuenta);
            ResultSet set = consulta.executeQuery();
            while (set.next()) {
                int cuenta_cliente = set.getInt("cuenta");
                String mov = set.getString("mov");
                double importe = set.getDouble("importe");
                System.out.println("Cuenta: " + cuenta_cliente + ", Movimiento: " + mov + ", Importe: " + importe);
            }
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (consulta != null)
                consulta.close();
        }

        cerrar();
    }

    // Punto d - Mostrar solo los depósitos de una cuenta.
    public void mostrar_depositos(int cuenta) throws SQLException {
        if (existe_cliente(cuenta)) {
            String QUERY = "select * from movimientos where cuenta like ? and mov like ?";
            PreparedStatement consulta = null;
            try {
                consulta = conexion.prepareStatement(QUERY);
                consulta.setInt(1, cuenta);
                consulta.setString(2, "D");
                ResultSet set = consulta.executeQuery();
                while (set.next()) {
                    cuenta = set.getInt("cuenta");
                    String mov = set.getString("mov");
                    double importe = set.getDouble("importe");
                    System.out.println("Cuenta: " + cuenta + ", Movimiento: " + mov + ", Importe: " + importe);
                }
                set.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (consulta != null)
                    consulta.close();
            }
        }
        cerrar();
    }

    // Punto e - Mostrar todos los movimientos de un cliente (se ingresará el nombre
    // del cliente).

    public void mostrar_mov_Cli(String nom) throws SQLException {
        String QUERY = "SELECT * FROM movimientos m " +
                "INNER JOIN cuentas c ON m.cuenta = c.cuenta " +
                "WHERE cliente LIKE ?";
        PreparedStatement consulta = null;
        try {
            consulta = conexion.prepareStatement(QUERY);
            consulta.setString(1, "%" + nom + "%");
            ResultSet set = consulta.executeQuery();
            while (set.next()) {
                int cuenta = set.getInt("cuenta");
                String mov = set.getString("mov");
                double importe = set.getDouble("importe");
                System.out.println("Cuenta: " + cuenta + ", Movimiento: " + mov + ", Importe: " + importe);
            }
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (consulta != null)
                consulta.close();
        }
        cerrar();
    }

    public static void main(String[] args) {
        try {
            Banco b = new Banco();
            // Punto a
            // int cuenta = Console.readInt("Ingrese cuenta a la cual se depositara: ");
            // double monto = Console.readDouble("Ingrese el monto a depositar");
            // b.realizar_deposito(cuenta, monto);

            // Punto b
            int cuenta = Console.readInt("Ingrese cuenta a la cual se realizara una extraccion: ");
            double monto = Console.readDouble("Ingrese el monto a extraer");
            if (b.existe_cliente(cuenta) && b.saldoSoI(cuenta, monto)) { // Preguntara si la cuenta existe, ademas de que si esa cuenta tiene saldo suficiente para la extraccion
                b.extraer(cuenta, monto);
                System.out.println("Extraccion exitosa");
            } else{
                System.out.println("El cliente NO existe o no hay saldo suficiente para realizar una extraccion");
            }
            // Punto c
            // b.mostrar_movimientos(1002);
            // Punto d
            // b.mostrar_depositos(1003);
            // Punto e
            // b.mostrar_mov_Cli("Maria Lopez");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
