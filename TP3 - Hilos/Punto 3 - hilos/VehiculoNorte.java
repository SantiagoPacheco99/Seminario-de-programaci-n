class VehiculoNorte extends Thread {

    private GestorPuente puente;

    public VehiculoNorte(GestorPuente puente, int id) {
        this.puente = puente;
        setName("Vehiculo Norte " + id);
    }

    @Override
    public void run() {

        puente.entrarNorte();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        puente.salirPuente();
    }
}