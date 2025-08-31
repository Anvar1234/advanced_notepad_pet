//package ru.yandex.kingartaved.service.note_reminder;
//
//import ru.yandex.kingartaved.repository.NoteRepository;
//import ru.yandex.kingartaved.service.NoteService;
//
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//
//public class NoteReminder {
//    private NoteService noteService;
//    private ScheduledExecutorService scheduler;
//
//    public NoteReminder(NoteService noteService) {
//        this.noteService = noteService;
//        this.scheduler = Executors.newSingleThreadScheduledExecutor();
//        startBackgroundChecker();
//    }
//
//}
