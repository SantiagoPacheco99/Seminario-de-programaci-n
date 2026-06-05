public class GestorPuente {

    private boolean puenteOcupado = false;

    private int esperandoNorte = 0;
    private int esperandoSur = 0;

    private boolean ambulanciaEsperando = false;

    // Vehículo que llega desde el norte
    public synchronized void entrarNorte() {

        esperandoNorte++;

        try {

            while (puenteOcupado ||
                  ambulanciaEsperando ||
                  (esperandoSur > esperandoNorte)) {

                wait();
            }

            esperandoNorte--;
            puenteOcupado = true;

            System.out.println(
                    Thread.currentThread().getName()
                    + " entra desde el Norte");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Vehículo que llega desde el sur
    public synchronized void entrarSur() {

        esperandoSur++;

        try {

            while (puenteOcupado ||
                  ambulanciaEsperando ||
                  (esperandoNorte > esperandoSur)) {

                wait();
            }

            esperandoSur--;
            puenteOcupado = true;

            System.out.println(
                    Thread.currentThread().getName()
                    + " entra desde el Sur");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Ambulancia
    public synchronized void entrarAmbulancia() {

        ambulanciaEsperando = true;

        try {

            while (puenteOcupado) {
                wait();
            }

            ambulanciaEsperando = false;
            puenteOcupado = true;

            System.out.println(
                    Thread.currentThread().getName()
                    + " (AMBULANCIA) entra al puente");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Salida del puente
    public synchronized void salirPuente() {

        puenteOcupado = false;

        System.out.println(
                Thread.currentThread().getName()
                + " sale del puente");

        notifyAll();
    }
}