package samsungtdd;

public class FizzBuzz {
    static String fizzBuzz(int number) {
        if (number <= 0) return "";
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= number; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                sb.append("FizzBuzz ");
            } else if (i % 3 == 0) {
                sb.append("Fizz ");
            } else if (i % 5 == 0) {
                sb.append("Buzz ");
            } else {
                sb.append(i).append(" ");
            }
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}
