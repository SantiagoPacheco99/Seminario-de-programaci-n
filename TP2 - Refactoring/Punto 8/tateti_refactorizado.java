import java.util.Scanner;

class tateti_refactorizado {

    static final int SIZE = 3; // ✔ Replace Magic Number

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in); 

        int ch2 = 1;

        char matrix[][];
        matrix = new char[SIZE][SIZE];

        while (ch2 == 1) {

            inicializarTablero(matrix); // ✔ Extract Method

            int sum = 0;

            while (sum < 9) {

                imprimirTablero(matrix); // ✔ Extract Method

                jugar(matrix, 'X', sc); // ✔ Extract Method
                sum++;

                if (verificarGanador(matrix, 'X')) { // ✔ Extract Method
                    System.out.println("Jugador 1 gana");
                    break;
                }

                if (sum == 9) {
                    System.out.println("Empate");
                    break;
                }

                jugar(matrix, 'O', sc);
                sum++;

                if (verificarGanador(matrix, 'O')) {
                    System.out.println("Jugador 2 gana");
                    break;
                }
            }

            System.out.println("Desea jugar otra vez??? 1=si 2=no");
            ch2 = sc.nextInt(); // ✔ Replace Dependency
        }

        sc.close();
    }

    // ✔ Extract Method
    static void inicializarTablero(char[][] matrix) {
        for (int m = 0; m < SIZE; m++)
            for (int n = 0; n < SIZE; n++)
                matrix[m][n] = ' ';
    }

    // ✔ Extract Method
    static void imprimirTablero(char[][] matrix) {
        System.out.println("   1   2   3");
        for (int m = 0; m < SIZE; m++) {
            System.out.print((m + 1) + " ");
            for (int n = 0; n < SIZE; n++) {
                System.out.print(" " + matrix[m][n] + " ");
                if (n < SIZE - 1) System.out.print("|");
            }
            System.out.println();
            if (m < SIZE - 1) System.out.println("  ---|---|---");
        }
    }

    // ✔ Extract Method + Introduce Parameter
    static void jugar(char[][] matrix, char jugador, Scanner sc) {

        int i, j;

        System.out.println("Jugador " + jugador + ": ingrese renglon y columna");

        do {
            System.out.print("Renglon: ");
            i = sc.nextInt();

            System.out.print("Columna: ");
            j = sc.nextInt();

        } while (!esValido(matrix, i, j));

        matrix[i - 1][j - 1] = jugador;
    }

    // ✔ Extract Method
    static boolean esValido(char[][] matrix, int i, int j) {
        return i >= 1 && i <= SIZE &&
               j >= 1 && j <= SIZE &&
               matrix[i - 1][j - 1] == ' ';
    }

    // ✔ Extract Method (simplifica condicionales)
    static boolean verificarGanador(char[][] matrix, char jugador) {

        for (int m = 0; m < SIZE; m++) {
            if (matrix[m][0] == jugador && matrix[m][1] == jugador && matrix[m][2] == jugador) return true;
            if (matrix[0][m] == jugador && matrix[1][m] == jugador && matrix[2][m] == jugador) return true;
        }

        if (matrix[0][0] == jugador && matrix[1][1] == jugador && matrix[2][2] == jugador) return true;
        if (matrix[0][2] == jugador && matrix[1][1] == jugador && matrix[2][0] == jugador) return true;

        return false;
    }
}