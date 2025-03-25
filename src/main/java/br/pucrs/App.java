package br.pucrs;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class App 
{
    private static long iteracoes;
    
    public static void mergeSort(long[] arr, int left, int right) {
        if (left < right) {
            iteracoes++;
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(long[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        long[] L = new long[n1];
        long[] R = new long[n2];
        
        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);
        
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            iteracoes++;
            arr[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
        }
        while (i < n1) { arr[k++] = L[i++]; iteracoes++; }
        while (j < n2) { arr[k++] = R[j++]; iteracoes++; }
    }

    public static long maxVal1(long[] arr) {
        long max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            iteracoes++;
            if (arr[i] > max) max = arr[i];
        }
        return max;
    }

    public static long maxVal2(long[] arr, int init, int end) {
        iteracoes++;
        if (init == end) return arr[init];
        int mid = (init + end) / 2;
        return Math.max(maxVal2(arr, init, mid), maxVal2(arr, mid + 1, end));
    }

    public static long multiply(long x, long y, int n) {
        iteracoes++;
        if (n == 1) return x * y;
        int m = (int) Math.ceil(n / 2.0);
        long a = x >> m, b = x & ((1L << m) - 1);
        long c = y >> m, d = y & ((1L << m) - 1);
        long e = multiply(a, c, m);
        long f = multiply(b, d, m);
        long g = multiply(b, c, m);
        long h = multiply(a, d, m);
        return (e << (2 * m)) + ((g + h) << m) + f;
    }

    public static long[] generateArray(int size) {
        Random rand = new Random();
        long[] arr = new long[size];
        for (int i = 0; i < size; i++) arr[i] = rand.nextInt(10000);
        return arr;
    }

    public static void main(String[] args) {
        int[] sizes = {32, 2048, 1048576};
        for (int size : sizes) {
            System.out.println("\nTamanho: " + size);
            long[] arr = generateArray(size);
            iteracoes = 0;
            long start = System.nanoTime();
            mergeSort(arr.clone(), 0, size - 1);
            long end = System.nanoTime();
            System.out.println("MergeSort - Tempo: " + (end - start) + " ns, Iterações: " + iteracoes);
            
            iteracoes = 0;
            start = System.nanoTime();
            maxVal1(arr.clone());
            end = System.nanoTime();
            System.out.println("maxVal1 - Tempo: " + (end - start) + " ns, Iterações: " + iteracoes);
            
            iteracoes = 0;
            start = System.nanoTime();
            maxVal2(arr.clone(), 0, size - 1);
            end = System.nanoTime();
            System.out.println("maxVal2 - Tempo: " + (end - start) + " ns, Iterações: " + iteracoes);
            
            iteracoes = 0;
            start = System.nanoTime();
            multiply(123456789, 987654321, 64);
            end = System.nanoTime();
            System.out.println("Multiplicação - Tempo: " + (end - start) + " ns, Iterações: " + iteracoes);
        }
    }
}
