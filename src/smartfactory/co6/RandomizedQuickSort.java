package smartfactory.co6;

import java.util.Random;

public class RandomizedQuickSort {
    public static class ProductionOrder {
        private String id;
        private int priority;
        private String description;

        public ProductionOrder(String id, int priority, String description) {
            this.id = id;
            this.priority = priority;
            this.description = description;
        }

        public String getId() { return id; }
        public int getPriority() { return priority; }
        public String getDescription() { return description; }
    }

    public static void sort(ProductionOrder[] arr) {
        if (arr == null || arr.length <= 1) return;
        Random rand = new Random();
        sort(arr, 0, arr.length - 1, rand);
    }

    public static void sort(ProductionOrder[] arr, int low, int high, Random rand) {
        if (low < high) {
            int pIndex = low + rand.nextInt(high - low + 1);
            ProductionOrder temp = arr[pIndex];
            arr[pIndex] = arr[high];
            arr[high] = temp;

            int pi = partition(arr, low, high);
            sort(arr, low, pi - 1, rand);
            sort(arr, pi + 1, high, rand);
        }
    }

    private static int partition(ProductionOrder[] arr, int low, int high) {
        int pivot = arr[high].getPriority();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j].getPriority() <= pivot) {
                i++;
                ProductionOrder temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        ProductionOrder temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    public static boolean isSorted(ProductionOrder[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i].getPriority() > arr[i + 1].getPriority()) return false;
        }
        return true;
    }
}
