class CuentaBancaria {

    private double saldo;

    public CuentaBancaria(double saldoInicial) {
        saldo = saldoInicial;
    }

    public synchronized void ingresar(double monto) {

        saldo += monto;

        System.out.println(
                Thread.currentThread().getName()
                + " ingresó $" + monto
                + " | Saldo actual: $" + saldo);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void extraer(double monto) {

        if (saldo >= monto) {

            saldo -= monto;

            System.out.println(
                    Thread.currentThread().getName()
                    + " extrajo $" + monto
                    + " | Saldo actual: $" + saldo);

        } else {

            System.out.println(
                    Thread.currentThread().getName()
                    + " NO pudo extraer $" + monto
                    + " | Saldo insuficiente");
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class HiloIngreso extends Thread {

    private CuentaBancaria cuenta;

    public HiloIngreso(CuentaBancaria cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    public void run() {

        cuenta.ingresar(1000);
        cuenta.ingresar(500);
        cuenta.ingresar(700);
        cuenta.ingresar(300);
        cuenta.ingresar(1200);
    }
}

class HiloExtraccion extends Thread {

    private CuentaBancaria cuenta;

    public HiloExtraccion(CuentaBancaria cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    public void run() {

        cuenta.extraer(800);
        cuenta.extraer(1500);
        cuenta.extraer(600);
    }
}

public class Banco {

    public static void main(String[] args) {

        CuentaBancaria cuenta = new CuentaBancaria(2000);

        HiloIngreso ingreso = new HiloIngreso(cuenta);
        HiloExtraccion extraccion = new HiloExtraccion(cuenta);

        ingreso.setName("Hilo Ingreso");
        extraccion.setName("Hilo Extracción");

        ingreso.start();
        extraccion.start();
    }
}

