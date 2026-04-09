package ru.ycan.blacklist.service.cli;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.ycan.blacklist.service.service.BlacklistProductNameProducer;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ClientCommandLineRunner implements CommandLineRunner {
    private static final Scanner SCANNER = new Scanner(System.in);

    private final ApplicationContext context;
    private final BlacklistProductNameProducer producer;

    @Override
    public void run(String... args) {
        System.out.println("Blacklist started...");
        while (true) {
            System.out.print("Введите 'name' товара для блокировки (или \\exit для выхода): ");
            String productName = SCANNER.nextLine().trim();
            if (productName.isBlank()) {
                System.out.println("Некорректное значение, повторите попытку");
                continue;
            }
            if (productName.equals("\\exit")) {
                SpringApplication.exit(context, () -> 0);
                break;
            }
            producer.addProductNameInBlacklist(productName);
        }
    }
}
