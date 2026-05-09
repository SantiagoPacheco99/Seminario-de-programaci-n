public class QuickSort {

    public static void quicKSort(int[] vec) {
        quickSort(vec, 0, vec.length - 1);
    }

    private static void quickSort(int[] vec, int first, int last) {
        int center = (first + last) / 2; // El pivote será el centro
        if (first < last) {
            center = division(vec, first, last, center);
            quickSort(vec, first, center);
            if (center == first)
                quickSort(vec, center + 1, last);
            else
                quickSort(vec, center, last);
        }
    }

    private static int division(int[] vec, int first, int last, int center) {
        int left, right, pivote;
        pivote = vec[center];
        left = first;
        right = last;
        do {
            while (left < right && vec[left] < pivote)
                left++;
            while (left < right && vec[right] > pivote)
                right--;
            if (left < right) {
                interChange(vec, left, right);
                left++;
                right--;
            }
        } while (left < right);
        if (left < right) {
            int pos = right;
            right = left;
            left = pos;
        }
        return left;
    }

    private static void interChange(int[] vec, int left, int right) {
        int temp = vec[left];
        vec[left] = vec[right];
        vec[right] = temp;
    }

    public static void main(String[] args) {

        // Parcial 2023
        int[] vec = new int[] { 40, 5, 20, 45, 30, 15, 25, 10, 35, 0 };

        System.out.println("Vector sin ordenar: ");
        for (int i = 0; i < vec.length; i++) {
            System.out.print(vec[i] + " - ");
        }

        System.out.println("");

        QuickSort.quicKSort(vec); // Ordenamos el vector con Quicksort
        System.out.println("");
        System.out.println("Vector ordenado por Quicksort: ");
        for (int i = 0; i < vec.length; i++) {
            System.out.print(vec[i] + " - ");
        }
    }
}