package ru.ycan.client.service.cli;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.ycan.client.service.service.impl.ProductSearchServiceImpl;
import ru.ycan.client.service.service.impl.RecommendationService;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ClientCommandLineRunner implements CommandLineRunner {
    private static final Scanner SCANNER = new Scanner(System.in);

    private final ProductSearchServiceImpl searchService;
    private final RecommendationService recommendationService;

    @Override
    public void run(String... args) {
        System.out.println("Client API Emulator started.");
        System.out.print("Введите ваш user_id: ");
        String userId = SCANNER.nextLine();
        while (true) {
            System.out.println("Введите команду: search <product_name> | recommend | exit");
            String input = SCANNER.nextLine();
            if (input.equals("exit")) {
                break;
            }

            String[] parts = input.split(" ", 2);
            switch (parts[0]) {
                case "search" -> searchProcess(userId, parts);
                case "recommend" ->  recommendationService.getRecommendations(userId);
                default -> System.out.println("Неизвестная команда");
            }
        }
    }

    private void searchProcess(String userId, String[] parts) {
        if (parts.length <= 1 || parts[1].isBlank()) {
            System.out.println("Некорректное значение <product_name>");
            return;
        }
        searchService.searchProduct(userId, parts[1]);
    }
}
