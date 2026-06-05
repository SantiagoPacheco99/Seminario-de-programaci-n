class VehiculoSur extends Thread {

    private GestorPuente puente;

    public VehiculoSur(GestorPuente puente, int id) {
        this.puente = puente;
        setName("Vehiculo Sur " + id);
    }

    @Override
    public void run() {

        puente.entrarSur();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        puente.salirPuente();
    }
}