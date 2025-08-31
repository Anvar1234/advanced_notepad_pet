package ru.yandex.kingartaved.service.sorting;

import ru.yandex.kingartaved.config.AppConfig;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class UserSortingSettingsRepository {
    private final String sortSettingsFile = AppConfig.PATH_TO_SORT_SETTINGS_FILE;
    private final Properties properties;

    public UserSortingSettingsRepository() {
        this.properties = new Properties();
        load();
    }

    private void load() {
        try (FileReader fileReader = new FileReader(sortSettingsFile)) {
            properties.load(fileReader);
        } catch (IOException e) {
            System.err.println("Не удалось загрузить настройки, используются настройки сортировки по умолчанию" + e.getMessage());
        }
    }


    public SortOrder getSortOrder() {
        String fieldStr = properties.getProperty("sort.field");
        String dirStr = properties.getProperty("sort.direction");

        SortOrder.SortField field = fieldStr != null ? parseField(fieldStr) : SortOrder.SortField.UPDATED_AT;
        SortOrder.SortDirection direction = dirStr != null ? parseDirection(dirStr) : SortOrder.SortDirection.DESC;

        return new SortOrder(field, direction);
    }

    public void saveSortOrder(SortOrder order) {
        properties.setProperty("sort.field", order.field().name());
        properties.setProperty("sort.direction", order.direction().name());

        try (FileWriter output = new FileWriter(sortSettingsFile)) {
            properties.store(output, "Default sorting settings");
        } catch (IOException e) {
            System.err.println("Не удалось сохранить настройки сортировки: " + e.getMessage());
            throw new IllegalArgumentException("Не удалось сохранить настройки сортировки");
        }
    }

    private SortOrder.SortField parseField(String s) {
        try {
            return SortOrder.SortField.valueOf(s);
        } catch (IllegalArgumentException e) {
            return SortOrder.SortField.UPDATED_AT;
        }
    }

    private SortOrder.SortDirection parseDirection(String s) throws IllegalArgumentException {
        try {
            return SortOrder.SortDirection.valueOf(s);
        } catch (IllegalArgumentException e) {
            return SortOrder.SortDirection.DESC;
        }
    }
}
