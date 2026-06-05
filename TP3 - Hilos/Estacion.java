class Tren {

    private final int CAPACIDAD = 50;
    private int pasajeros = 0;
    private boolean listoParaSalir = false;

    // Método invocado por cada pasajero
    public synchronized void abordar() {

        pasajeros++;

        System.out.println(
                Thread.currentThread().getName()
                + " abordó el tren. Pasajeros: "
                + pasajeros);

        // Cuando llega el pasajero 50
        if (pasajeros == CAPACIDAD) {

            listoParaSalir = true;

            System.out.println(
                    "\nCapacidad completa. El tren puede partir.\n");

            notifyAll(); // Despierta al hilo del tren para que pueda partir
        }
    }

    // El tren queda esperando hasta que se aborden los 50 pasajeros
    public synchronized void esperarPasajeros() {

        while (!listoParaSalir) {

            try {

                System.out.println(
                        "El tren espera a los pasajeros...");

                wait();

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }

        System.out.println("El tren parte con "
                + pasajeros
                + " pasajeros.");
    }
}

// Hilo de los pasajeros
class Pasajero extends Thread {

    private Tren tren;

    public Pasajero(Tren tren, int numero) {

        this.tren = tren;
        setName("El pasajero " + numero);
    }

    @Override
    public void run() {
        tren.abordar();
    }
}

// Hilo tren
class HiloTren extends Thread {

    private Tren tren;

    public HiloTren(Tren tren) {

        this.tren = tren;
    }

    @Override
    public void run() {

        tren.esperarPasajeros();
    }
}

public class Estacion {

    public static void main(String[] args) {

        Tren tren = new Tren();

        HiloTren hiloTren =
                new HiloTren(tren);

        hiloTren.start();

        // Se crean los 50 pasajeros
        for (int i = 1; i <= 50; i++) {

            Pasajero pasajero =
                    new Pasajero(tren, i);

            pasajero.start();
        }
    }
}