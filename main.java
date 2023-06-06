import myrationalnumbers.*;

import java.util.*;


public class main {

    public static void main(String[] args){



        while (true) {
            try {
                ExpressionReader reader = new ExpressionReader();
                Scanner scn = new Scanner(System.in);
                String expression = scn.nextLine();

                if (expression.equals("quit") || expression.equals("exit")) {
                    System.exit(0);
                }

                Rnum n;
                n = reader.count(expression);
                System.out.println("Результат: " + n.get_value());
                System.out.println("1 - представить в виде десятичной дроби\n" +
                        "2 - продолжить");

                boolean b = true;
                while (b){
                    String in = scn.nextLine().trim();
                    switch (in) {
                        case "1" ->{
                            System.out.println(n.get_value("double"));
                            b = false;
                        }
                        case "2" -> {
                            b = false;
                        }
                        case "quit", "exit" -> System.exit(0);
                        default -> {
                            System.out.println("Некорректный ввод");
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("некорректное выражение");
            }
        }
    }

}
