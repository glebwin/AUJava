package ru.spbau.glebwin.streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SecondPartTasks {
    private SecondPartTasks() {
    }

    // Найти строки из переданных файлов, в которых встречается указанная подстрока.
    public static List<String> findQuotes(List<String> paths, CharSequence sequence) {
        return paths.stream()
                .flatMap(p -> {
                    try {
                        return Files.lines(Paths.get(p));
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(l -> l.contains(sequence))
                .collect(Collectors.toList());
    }

    // В квадрат с длиной стороны 1 вписана мишень.
    // Стрелок атакует мишень и каждый раз попадает в произвольную точку квадрата.
    // Надо промоделировать этот процесс с помощью класса java.util.Random и посчитать, какова вероятность попасть в мишень.
    public static double piDividedBy4() {
        class Pair {
            double x;
            double y;

            Pair(double x, double y) {
                this.x = x;
                this.y = y;
            }

            double getX() {
                return x;
            }

            double getY() {
                return y;
            }
        }
        final int ITERATIONS = 1_000_000;
        final int SQUARE_SIDE = 1;
        final double HALF_SIDE = SQUARE_SIDE / 2.0;
        Random random = new Random();
        return Stream.generate(() -> new Pair(random.nextDouble(), random.nextDouble()))
                .limit(ITERATIONS)
                .filter(p -> Math.sqrt(Math.pow(p.getX() - HALF_SIDE, 2) + Math.pow(p.getY() - HALF_SIDE, 2)) <= HALF_SIDE)
                .count() / (double) ITERATIONS;
    }

    // Дано отображение из имени автора в список с содержанием его произведений.
    // Надо вычислить, чья общая длина произведений наибольшая.
    public static String findPrinter(Map<String, List<String>> compositions) {
        return compositions.entrySet()
                .stream()
                .max(Comparator.comparing(e -> e.getValue().stream().mapToInt(String::length).sum()))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    // Вы крупный поставщик продуктов. Каждая торговая сеть делает вам заказ в виде Map<Товар, Количество>.
    // Необходимо вычислить, какой товар и в каком количестве надо поставить.
    public static Map<String, Integer> calculateGlobalOrder(List<Map<String, Integer>> orders) {
        return orders.stream()
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum));
    }
}
