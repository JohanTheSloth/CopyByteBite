package dk.zealand;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final int MAX_ORDERS = 10;
    private static final List<Order> ORDERS = new ArrayList<>();
    private static int nextOrderId = 1;

    private record Dish(String name, int price) {
    }

    private record Order(int id, Dish dish, int quantity, String status) {
    }

    private static final Dish[] DISHES = {
            new Dish("Festivalburger", 59),
            new Dish("Sprøde fritter", 35),
            new Dish("Vegansk bowl", 65)
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("ByteBites – festivalens foodtruck");

        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> showDishes();
                case "2" -> createOrder(scanner);
                case "0" -> running = false;
                default -> System.out.println(
                        "Ugyldigt valg. Vælg 0, 1 eller 2."
                );
            }
        }

        System.out.println("Programmet er afsluttet.");
    }

    private static void showMenu() {
        System.out.println();
        System.out.println("1. Vis retter");
        System.out.println("2. Opret bestilling");
        System.out.println("0. Afslut");
        System.out.print("Vælg: ");
    }

    private static void showDishes() {
        System.out.println("Retter:");

        for (int i = 0; i < DISHES.length; i++) {
            Dish dish = DISHES[i];
            System.out.printf("%d. %s - %d kr%n", i + 1, dish.name(), dish.price());
        }
    }

    private static void createOrder(Scanner scanner) {
        if (ORDERS.size() >= MAX_ORDERS) {
            System.out.println("Der kan højst gemmes ti bestillinger.");
            return;
        }

        System.out.println("Vælg en ret:");
        showDishes();
        System.out.print("Ret: ");
        String dishChoice = scanner.nextLine().trim();

        int dishIndex;
        try {
            dishIndex = Integer.parseInt(dishChoice);
        } catch (NumberFormatException e) {
            System.out.println("Ugyldig ret. Vælg 1, 2 eller 3.");
            return;
        }

        if (dishIndex < 1 || dishIndex > DISHES.length) {
            System.out.println("Ugyldig ret. Vælg 1, 2 eller 3.");
            return;
        }

        Dish selectedDish = DISHES[dishIndex - 1];

        System.out.print("Antal: ");
        String quantityInput = scanner.nextLine().trim();

        int quantity;
        try {
            quantity = Integer.parseInt(quantityInput);
        } catch (NumberFormatException e) {
            System.out.println("Ugyldigt antal. Indtast et positivt heltal.");
            return;
        }

        if (quantity <= 0) {
            System.out.println("Ugyldigt antal. Antallet skal være større end 0.");
            return;
        }

        Order order = new Order(nextOrderId++, selectedDish, quantity, "MODTAGET");
        ORDERS.add(order);

        System.out.println("Bestilling oprettet:");
        printOrder(order);
    }

    private static void printOrder(Order order) {
        System.out.printf(
                "Bestilling #%d: %s, antal %d, status %s%n",
                order.id(),
                order.dish().name(),
                order.quantity(),
                order.status()
        );
    }
}
