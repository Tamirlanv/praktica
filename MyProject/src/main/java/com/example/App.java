package com.example;

public class App {
    public static void main( String[] args ){
        DatabaseManager dbManager = new DatabaseManager();

        // Создание записи
        dbManager.createRecord("Alice", 30);

        // Чтение записей
        dbManager.readRecords();

        // Обновление записи (например, обновляем запись с id = 1)
        dbManager.updateRecord(1, "Alice Smith", 31);

        // Удаление записи (например, удаляем запись с id = 1)
        dbManager.deleteRecord(1);
    }
}
