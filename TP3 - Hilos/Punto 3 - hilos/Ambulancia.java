class Ambulancia extends Thread {

    private GestorPuente puente;

    public Ambulancia(GestorPuente puente) {
        this.puente = puente;
        setName("Ambulancia");
    }

    @Override
    public void run() {

        puente.entrarAmbulancia();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        puente.salirPuente();
    }
}