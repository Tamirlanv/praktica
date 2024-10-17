package com.example;

import java.util.Scanner;

public class AppTest {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Введите ваше имя");
        String name = scan.nextLine();

        System.out.println("Введите ваш возраст");
        String age = scan.nextLine();

        System.out.println("Введите ваш пароль");
        String password = scan.nextLine();

        System.out.println("Введите вашу должность (Admin или Client):");
        String role = scan.nextLine();

        if (role.equalsIgnoreCase("Admin")) {
            if (DatabaseManager.authenticateAdmin(name, password)) {
                System.out.println("Добро пожаловать, администратор!");
                adminMenu(); // вызываем меню администратора
            } else {
                System.out.println("Неверные имя или пароль для администратора.");
            }
        } else if (role.equalsIgnoreCase("Client")) {
            if (DatabaseManager.registerClient(name, age, password)) {
                System.out.println("Клиент успешно зарегистрирован.");
                clientMenu(name);
            }
        } else {
            System.out.println("Неверная должность.");
        }

        scan.close();
    }

    public static void adminMenu() {
        Scanner scan = new Scanner(System.in);
        int adminChoice = 0;

        while (adminChoice != 3) {
            System.out.println("Выберите действие:\n1) Показать всех клиентов\n2) Удалить клиента по ID\n3) Выйти");
            adminChoice = scan.nextInt();

            switch (adminChoice) {
                case 1:
                    // Показать всех клиентов
                    DatabaseManager.showClients();
                    break;
                case 2:
                    // Удалить клиента
                    System.out.println("Введите ID клиента для удаления:");
                    int clientId = scan.nextInt();
                    DatabaseManager.deleteClientById(clientId);
                    System.out.println("Клиент с ID " + clientId + " был удален.");
                    break;
                case 3:
                    System.out.println("Завершение работы.");
                    break;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
                    break;
            }
        }

        scan.close();
    }

    //// Меню для клиента (не изменяется)
    public static void clientMenu(String name) {
        Scanner scan = new Scanner(System.in);
        int balance = DatabaseManager.getClientBalance(name);
        int answer = 0;

        while (answer != 4) {
            System.out.println("Какую операцию хотите совершить?\n1) Проверить счёт\n2) Пополнить счёт\n3) Снять наличные\n4) Завершить программу");
            answer = scan.nextInt();

            switch (answer) {
                case 1:
                    // Проверка баланса
                    System.out.println("Ваш текущий счёт: " + balance + " тенге.");
                    break;
                case 2:
                    // Пополнение счёта
                    int deposit;
                    do {
                        System.out.println("На сколько хотите пополнить счёт? Введите положительное число.");
                        deposit = scan.nextInt();
                        if (deposit < 0) {
                            System.out.println("Ошибка: сумма не может быть отрицательной. Попробуйте снова.");
                        }
                    } while (deposit < 0);
                    balance += deposit;
                    DatabaseManager.updateClientBalance(name, balance);
                    System.out.println("Ваш счёт пополнен. Текущий баланс: " + balance + " тенге.");
                    break;
                case 3:
                    // Снятие наличных
                    int snatie;
                    do {
                        System.out.println("Сколько хотите снять? Введите положительное число.");
                        snatie = scan.nextInt();
                        if (snatie < 0) {
                            System.out.println("Ошибка: сумма не может быть отрицательной. Попробуйте снова.");
                        } else if (snatie > balance) {
                            System.out.println("Недостаточно средств на счёте. Попробуйте снова.");
                        }
                    } while (snatie < 0 || snatie > balance);
                    balance -= snatie;
                    DatabaseManager.updateClientBalance(name, balance);
                    System.out.println("Вы сняли " + snatie + " тенге. Остаток на счёте: " + balance + " тенге.");
                    break;
                case 4:
                    // Завершение программы
                    System.out.println("Завершение программы... До свидания, " + name + "!");
                    break;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
                    break;
            }
        }

        scan.close();
    }
}
