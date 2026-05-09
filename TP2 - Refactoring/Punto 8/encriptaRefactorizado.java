import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class encriptaRefactorizado {

    public static void main(String[] args) {

        String textoOriginal = leerEntrada();

        String textoEncriptado = procesarTexto(textoOriginal, true);
        System.out.println("Encriptado: " + textoEncriptado);

        String textoDesencriptado = procesarTexto(textoEncriptado, false);
        System.out.println("Desencriptado: " + textoDesencriptado);
    }

    // ✔ Extract Method
    static String leerEntrada() {
        System.out.println("Introduzca los datos a encriptar:");

        try {
            BufferedReader texto = new BufferedReader(new InputStreamReader(System.in));
            return texto.readLine();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return "";
    }

    // ✔ Extract Method + Introduce Parameter
    static String procesarTexto(String entrada, boolean encriptar) {

        StringBuilder salida = new StringBuilder();

        for (int i = 0; i < entrada.length(); i++) {

            char caracter = entrada.charAt(i);

            for (int j = 0; j <= salida.length(); j++) {
                if (encriptar) {
                    caracter++;
                } else {
                    caracter--;
                }
            }

            salida.append(caracter);
        }

        return salida.toString();
    }
}