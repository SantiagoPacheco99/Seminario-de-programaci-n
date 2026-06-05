public class MainPuente {
    public static void main(String[] args) {

        GestorPuente puente = new GestorPuente();

        for (int i = 1; i <= 3; i++) {
            new VehiculoNorte(puente, i).start();
        }

        for (int i = 1; i <= 5; i++) {
            new VehiculoSur(puente, i).start();
        }

        new Ambulancia(puente).start();
    }
}
