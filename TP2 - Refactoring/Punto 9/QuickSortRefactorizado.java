public class QuickSortRefactorizado {

    public static void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private static void quickSort(int[] array, int first, int last) {

        if (first < last) {

            int pivotIndex = (first + last) / 2;

            pivotIndex = partition(array, first, last, pivotIndex);

            quickSort(array, first, pivotIndex);

            if (pivotIndex == first)
                quickSort(array, pivotIndex + 1, last);
            else
                quickSort(array, pivotIndex, last);
        }
    }

    // Rename + Extract Method
    private static int partition(int[] array, int first, int last, int pivotIndex) {

        int pivot = array[pivotIndex];
        int left = first;
        int right = last;

        do {

            while (left < right && array[left] < pivot)
                left++;

            while (left < right && array[right] > pivot)
                right--;

            if (left < right) {
                swap(array, left, right);
                left++;
                right--;
            }

        } while (left < right);

        if (left < right) {
            int temp = right;
            right = left;
            left = temp;
        }

        return left;
    }

    // Rename
    private static void swap(int[] array, int left, int right) {

        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    // Extract Method
    private static void printArray(int[] array) {

        for (int value : array) {
            System.out.print(value + " - ");
        }

        System.out.println();
    }

    public static void main(String[] args) {

        int[] array = { 40, 5, 20, 45, 30, 15, 25, 10, 35, 0 };

        System.out.println("Vector sin ordenar:");
        printArray(array);

        quickSort(array);

        System.out.println("\nVector ordenado por QuickSort:");
        printArray(array);
    }
}