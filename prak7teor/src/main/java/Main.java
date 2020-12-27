import javafx.util.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Main {
    static String firstLetter = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";
    static String word = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_0123456789";
    static String digit = "0123456789.";
    static String action = "+-/*";
    static final HashMaker.HashTable table = new OpenHashTable();

    public static void main(String[] args) {
        method("ret1:= (12 - 6) * 8;ret2 := (12 - ret1) * 3;ret3:=ret1+ret2;ret4 := ret3  +ret2- 2* ret1");
    }


    public static void method(String str) {
        String[] strs = str.split(";");
        for (int i = 0; i < strs.length; i++) {
            System.out.println(i+1 + " строка обрабатывается");
            String[] arr = help(strs[i]).split(" := ");
            if(test(arr[0])){
                String res1 = getPostFormat(arr[1]);
                if (res1 == null) {
                    System.out.println("Синтаксический анализ не пройден. Лишние скобки");
                } else {
                    Double res2 = getResult(res1);
                    if (res2 != null) {
                        table.push(arr[0], res2);
                        System.out.println(i+1 + " строка прошла обработку");
                    }
                }
            } else {
                System.out.println("Синтаксический анализ не пройден");
            }
        }
        table.getTable();
    }

    public static boolean test(String str) {
        if (firstLetter.contains(str.charAt(0) + "")) {
            return eachLetter(str.substring(1));
        }
        return false;
    }

    public static boolean eachLetter(String str) {
        for (char c : str.toCharArray()) {
            if (!word.contains(c + "")) {
                return false;
            }
        }
        return true;
    }

    private static String help(String str) {
        str = str.replaceAll(" ", "");
        str = str.replaceAll(":=", " := ");
        str = str.replaceAll("\\+", " + ");
        str = str.replaceAll("-", " - ");
        str = str.replaceAll("\\*", " * ");
        str = str.replaceAll("/", " / ");
        str = str.replaceAll("\\(", "( ");
        str = str.replaceAll("\\)", " )");
        return str;
    }

    private static String getPostFormat(String res) {
        String res2 = "";
        Deque<String> deque = new ArrayDeque<>();
        for (String s : res.split(" ")) {
            switch (s) {
                case "+":
                case "-":
                    if (!deque.isEmpty()) {
                        if ((deque.getLast().equals("+")) || (deque.getLast().equals("-"))
                                || (deque.getLast().equals("*")) || (deque.getLast().equals("/"))) {
                            res2 += ";" + deque.removeLast();
                        }
                    }
                    deque.add(s);
                    break;
                case "*":
                case "/":
                    if (!deque.isEmpty()) {
                        if ((deque.getLast().equals("*")) || (deque.getLast().equals("/"))) {
                            res2 += ";" + deque.removeLast();
                        }
                    }
                    deque.add(s);
                    break;
                case "(":
                    deque.add(s);
                    break;
                case ")":
                    if (deque.isEmpty()) {
                        return null;
                    }
                    while (!deque.getLast().equals("(")) {
                        res2 += ";" + deque.removeLast();
                        if (deque.isEmpty()) {
                            return null;
                        }
                    }
                    deque.removeLast();
                    break;
                default:
                    res2 += ';' + s;
            }
        }
        while (!deque.isEmpty()) {
            String str = deque.removeLast();
            if (str.equals("(")) {
                return null;
            }
            res2 += ";" + str;
        }
        return res2.substring(1);
    }

    private static Double getResult(String res2) {
        Deque<Double> deque = new ArrayDeque<>();
        for (String s : res2.split(";")) {
            try {
                if(test(s)){
                    Double num = table.get(s);
                    if(num != null){
                        deque.add(num);
                    } else {
                        System.out.println("Использована неизвестная переменнная");
                        return null;
                    }
                } else {
                    deque.add(Double.parseDouble(s));
                }
            } catch (NumberFormatException e) {
                if(!action.contains(s)){
                    System.out.println("Лексический анализ не пройден");
                    return null;
                }
                if (deque.size() < 2) {
                    System.out.println("Синтаксический анализ не пройден");
                    return null;
                }
                double second = deque.removeLast();
                double first = deque.removeLast();
                switch (s) {
                    case "+":
                        deque.add(first + second);
                        break;
                    case "-":
                        deque.add(first - second);
                        break;
                    case "*":
                        deque.add(first * second);
                        break;
                    case "/":
                        deque.add(first / second);
                        break;
                }
            }
        }
        if (deque.size() > 1) {
            System.out.println("Синтаксический анализ не пройден");
            return null;
        }
        return deque.removeLast();
    }
}
