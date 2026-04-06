package ru.ycan.blacklist.service.cli;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.ycan.blacklist.service.service.BlacklistProductNameProducer;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ClientCommandLineRunner implements CommandLineRunner {
    private static final Scanner SCANNER = new Scanner(System.in);

    private final BlacklistProductNameProducer producer;

    @Override
    public void run(String... args) {
        System.out.println("Blacklist started...");
        while (true) {
            System.out.print("Введите 'name' товара для блокировки (или \\exit для выхода): ");
            String productName = SCANNER.nextLine();
            if (productName.isBlank()) {
                System.out.println("Некорректное значение, повторите попытку");
                continue;
            }
            if (productName.equals("\\exit")) {
                break;
            }
            producer.addProductNameInBlacklist(productName);
        }
    }
}
