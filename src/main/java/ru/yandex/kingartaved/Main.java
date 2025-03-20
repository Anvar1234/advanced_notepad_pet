package ru.yandex.kingartaved;

import ru.yandex.kingartaved.data.model.TextNote;

public class Main {
    public static void main(String[] args) {
        TextNote textNote = new TextNote.TextNoteBuilder().build();
        System.out.println(textNote);
    }
}