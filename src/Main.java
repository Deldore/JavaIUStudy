import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String path = "src/input.txt";
        int[] numbers = new int[2];

        int[] intArray = new int[2]; // Массив для хранения целых чисел
        int index = 0; // Индекс для заполнения массива

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    int number = Integer.parseInt(line);
                    if (index < intArray.length) {
                        intArray[index] = number;
                        index++;
                    } else {
                        System.out.println("Array is full. Number " + number + " was ignored");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: '" + line + "' isn't a number.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (intArray.length == 2) {
            System.out.println(intArray[0] / intArray[1]);
        } else {
            System.out.println("Arrays length is less than 2");
        }
    }
}