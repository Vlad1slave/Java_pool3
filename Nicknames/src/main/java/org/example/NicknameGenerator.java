package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class NicknameGenerator {

    private static final int TOTAL_WORDS = 100_000;
    private static final String LETTERS = "abc";
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 5;

    private static final AtomicInteger beautifulWordsLength3 = new AtomicInteger(0);
    private static final AtomicInteger beautifulWordsLength4 = new AtomicInteger(0);
    private static final AtomicInteger beautifulWordsLength5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        String[] texts = generateTexts();

        Thread palindromeThread = new Thread(() -> checkPalindromes(texts));
        Thread sameLetterThread = new Thread(() -> checkSameLetters(texts));
        Thread ascendingOrderThread = new Thread(() -> checkAscendingOrder(texts));

        palindromeThread.start();
        sameLetterThread.start();
        ascendingOrderThread.start();

        palindromeThread.join();
        sameLetterThread.join();
        ascendingOrderThread.join();

        System.out.println("Красивых слов с длиной 3: " + beautifulWordsLength3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + beautifulWordsLength4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + beautifulWordsLength5.get() + " шт");
    }

    private static String[] generateTexts() {
        Random random = new Random();
        String[] texts = new String[TOTAL_WORDS];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText(LETTERS, MIN_LENGTH + random.nextInt(MAX_LENGTH - MIN_LENGTH + 1));
        }
        return texts;
    }

    private static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    private static void checkPalindromes(String[] texts) {
        for (String text : texts) {
            if (isPalindrome(text)) {
                incrementCounter(text.length());
            }
        }
    }

    private static void checkSameLetters(String[] texts) {
        for (String text : texts) {
            if (isSameLetter(text)) {
                incrementCounter(text.length());
            }
        }
    }

    private static void checkAscendingOrder(String[] texts) {
        for (String text : texts) {
            if (isAscendingOrder(text)) {
                incrementCounter(text.length());
            }
        }
    }

    private static boolean isPalindrome(String text) {
        int length = text.length();
        for (int i = 0; i < length / 2; i++) {
            if (text.charAt(i) != text.charAt(length - i - 1)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSameLetter(String text) {
        char firstChar = text.charAt(0);
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != firstChar) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAscendingOrder(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    private static void incrementCounter(int length) {
        switch (length) {
            case 3:
                beautifulWordsLength3.incrementAndGet();
                break;
            case 4:
                beautifulWordsLength4.incrementAndGet();
                break;
            case 5:
                beautifulWordsLength5.incrementAndGet();
                break;
        }
    }
}