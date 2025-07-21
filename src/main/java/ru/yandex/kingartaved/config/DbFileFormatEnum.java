package ru.yandex.kingartaved.config;

public enum DbFileFormatEnum {

    TXT("txt", "src/main/resources/note_db.txt");
//    JSON("json", "src/main/resources/note_db.json");

    private final String format;
    private final String stringPath;

    DbFileFormatEnum(String format, String stringPath) {
        this.format = format;
        this.stringPath = stringPath;
    }

    public String getFormat(){
        return format;
    }

    public String getStringPath(){
        return stringPath;
    }
}