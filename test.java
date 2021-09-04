import java.util.*;

public class test {
    public static void main(String[] args) {
        char[] arr = new char[4];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = '.';
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        System.out.println(arr[4]);
    }
}