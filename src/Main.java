import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static AtomicInteger counterThreeLetters = new AtomicInteger();
    private static AtomicInteger counterFourLetters = new AtomicInteger();
    private static AtomicInteger counterFiveLetters = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindromeThread = new Thread(() -> {
            for (String str: texts) {
                if (isPalindrome(str)) {
                    incrementCounter(str.length());
                }
            }
        });

        Thread sameLettersThread = new Thread(() -> {
            for (String str : texts) {
                if (isSameLetters(str)) {
                    incrementCounter(str.length());
                }
            }
        });

        Thread increasingLettersThread = new Thread(() -> {
            for (String str : texts) {
                if (isIncreasingLetters(str)) {
                    incrementCounter(str.length());
                }
            }
        });

        // Запуск всех потоков
        palindromeThread.start();
        sameLettersThread.start();
        increasingLettersThread.start();

        // Ожидание завершения всех потоков
        palindromeThread.join();
        sameLettersThread.join();
        increasingLettersThread.join();

        // Вывод результатов
        System.out.println("Красивых слов с длиной 3: " + counterThreeLetters.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + counterFourLetters.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + counterFiveLetters.get() + " шт");
    }

    public static void incrementCounter(int length) {
        switch (length) {
            case 3: counterThreeLetters.getAndIncrement(); break;
            case 4: counterFourLetters.getAndIncrement(); break;
            case 5: counterFiveLetters.getAndIncrement(); break;
        }
    }


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String str) {
        int start = 0;
        int end = str.length() - 1;

        while (start < end) {
            char startChar = str.charAt(start);
            char endChar = str.charAt(end);

            if (startChar != endChar) {
                return false;
            }

            start++;
            end--;
        }

        return true;
    }

    public static boolean isSameLetters(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) != text.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }


    public static boolean isIncreasingLetters(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) > text.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }
}
