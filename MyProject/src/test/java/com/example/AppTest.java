package com.example;

import java.util.Scanner;

public class AppTest {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        try {
            System.out.println("Введите ваше имя:");
            String name = scan.nextLine();
            System.out.println("Введите ваш возраст:");
            int age = -1;
            while (age < 18 || age > 100) {
                if (scan.hasNextInt()) {
                    age = scan.nextInt();
                    scan.nextLine(); // очищаем буфер
                    if (age < 18) {
                        System.out.println("Соси соску, малыш!");
                        return; //Завершаем программу
                    } else if (age > 100) {
                        System.out.println("Возраст должен быть от 18 до 100 лет.");
                        return; //Завершаем программу
                    }
                } else {
                    System.out.println("Некорректный ввод. Введите целое число.");
                    scan.next(); // очищаем некорректный ввод
                }
            }
            System.out.println("Введите ваш пароль:");
            String password = scan.nextLine();
            String role = "";
            while (true) {
                System.out.println("Введите вашу должность (Admin или Client):");
                role = scan.nextLine();
                if (role.equalsIgnoreCase("Admin") || role.equalsIgnoreCase("Client")) {
                    break; // Выход из цикла, если роль корректная
                } else {
                    System.out.println("Неверная должность. Попробуйте снова.");
                }
            }
            if (role.equalsIgnoreCase("Admin")) {
                if (DatabaseManagerTest.authenticateAdmin(name, password)) {
                    System.out.println("Добро пожаловать, администратор!");
                    adminMenu(); // вызываем меню администратора
                } else {
                    System.out.println("Неверные имя или пароль для администратора.");
                }
            } else if (role.equalsIgnoreCase("Client")) {
                if (DatabaseManagerTest.authenticateClient(name, password)) {
                    System.out.println("Добро пожаловать, " + name + "!");
                    clientMenu(name, password); // вызываем меню клиента
                } else {
                    if (DatabaseManagerTest.registerClient(name, age, password)) {
                        System.out.println("Клиент успешно зарегистрирован.");
                        clientMenu(name, password); // вызываем меню клиента
                    } else {
                        System.out.println("Ошибка при регистрации клиента.");
                    }
                }
            }
        } finally {
            scan.close(); // Закрываем сканер в блоке finally, чтобы он всегда закрывался
        }
    }

    public static void adminMenu() {
        Scanner scan = new Scanner(System.in);
        int adminChoice = 0;

        while (adminChoice != 3) {
            System.out.println("Выберите действие:\n1) Показать всех клиентов\n2) Удалить клиента по ID\n3) Выйти");
            adminChoice = scan.nextInt();
            scan.nextLine(); // очищаем буфер после nextInt()

            switch (adminChoice) {
                case 1:
                    // Показать всех клиентов
                    DatabaseManagerTest.showClients();
                    break;
                case 2:
                    // Удалить клиента
                    System.out.println("Введите ID клиента для удаления:");
                    int clientId = scan.nextInt();
                    DatabaseManagerTest.deleteClientById(clientId);
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
    public static void clientMenu(String name, String password) {
        Scanner scan = new Scanner(System.in);
        int balance = DatabaseManagerTest.getClientBalance(name);
        int answer = 0;

        while (answer != 5) {
            System.out.println("Какую операцию хотите совершить?\n1) Проверить счёт\n2) Пополнить счёт\n3) Снять наличные\n4) Обновить информвциюю о себе\n5) Завершить программу");
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
                    DatabaseManagerTest.updateClientBalance(name, balance);
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
                    DatabaseManagerTest.updateClientBalance(name, balance);
                    System.out.println("Вы сняли " + snatie + " тенге. Остаток на счёте: " + balance + " тенге.");
                    break;
                case 4:
                    //Обновить информвциюю о себе
                    System.out.println("Введите ваш новый возраст:");
                    scan.nextLine();
                    String newAge = scan.nextLine();
                    System.out.println("Введите ваш новый пароль:");
                    String newPassword = scan.nextLine();
                    if (DatabaseManagerTest.updateClientInfo(name, newAge, password, newPassword)) {
                        System.out.println("Ваша информация успешно обновлена.");
                        password = newPassword; // Обновляем локальную переменную пароля
                    } else {
                        System.out.println("Ошибка при обновлении информации.");
                    }
                    break;

                case 5:
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
