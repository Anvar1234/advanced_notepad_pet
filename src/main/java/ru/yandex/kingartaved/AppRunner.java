package ru.yandex.kingartaved;

import ru.yandex.kingartaved.controller.NoteController;
import ru.yandex.kingartaved.controller.impl.DefaultNoteController;
import ru.yandex.kingartaved.data.mapper.NoteMapper;
import ru.yandex.kingartaved.data.mapper.content_mapper.ContentMapperRegistry;
import ru.yandex.kingartaved.data.mapper.impl.DefaultNoteMapper;
import ru.yandex.kingartaved.data.mapper.metadata_mapper.MetadataMapper;
import ru.yandex.kingartaved.data.mapper.metadata_mapper.impl.DefaultMetadataMapper;
import ru.yandex.kingartaved.data.serializer.NoteSerializer;
import ru.yandex.kingartaved.data.serializer.content_serializer.ContentSerializerRegistry;
import ru.yandex.kingartaved.data.serializer.impl.DefaultNoteSerializer;
import ru.yandex.kingartaved.data.serializer.metadata_serializer.impl.DefaultMetadataSerializer;
import ru.yandex.kingartaved.repository.NoteRepository;
import ru.yandex.kingartaved.repository.db_connector.DbConnector;
import ru.yandex.kingartaved.repository.db_connector.impl.FileDbConnector;
import ru.yandex.kingartaved.repository.impl.CachedFileNoteRepository;
import ru.yandex.kingartaved.service.NoteService;
import ru.yandex.kingartaved.service.content_service.ContentServiceRegistry;
import ru.yandex.kingartaved.service.impl.DefaultNoteService;
import ru.yandex.kingartaved.service.metadata_service.MetadataService;
import ru.yandex.kingartaved.service.metadata_service.impl.DefaultMetadataService;
import ru.yandex.kingartaved.service.sorting.UserSortingSettingsRepository;
import ru.yandex.kingartaved.validation.db_line_validator.DbLineValidator;
import ru.yandex.kingartaved.validation.db_line_validator.content_validator.ContentValidatorRegistry;
import ru.yandex.kingartaved.validation.db_line_validator.impl.DefaultDbLineValidator;
import ru.yandex.kingartaved.validation.db_line_validator.metadata_validator.impl.DefaultMetadataValidator;
import ru.yandex.kingartaved.view.content_view.ContentViewRegistry;
import ru.yandex.kingartaved.view.impl.DefaultNoteView;
import ru.yandex.kingartaved.view.metadata_view.impl.DefaultMetadataView;

import java.util.Scanner;

public class AppRunner {
    public static void main(String[] args) {
        MetadataMapper metadataMapper = new DefaultMetadataMapper();
        ContentMapperRegistry contentMapperRegistry = new ContentMapperRegistry();
        NoteMapper noteMapper = new DefaultNoteMapper(metadataMapper, contentMapperRegistry);
        MetadataService metadataService = new DefaultMetadataService();
        ContentServiceRegistry contentServiceRegistry = new ContentServiceRegistry();
        UserSortingSettingsRepository userSortingSettingsRepository = new UserSortingSettingsRepository();
        DbConnector dbConnector = FileDbConnector.INSTANCE;
        DbLineValidator dbLineValidator = new DefaultDbLineValidator(new ContentValidatorRegistry(), new DefaultMetadataValidator());
        NoteSerializer noteSerializer = new DefaultNoteSerializer(new ContentSerializerRegistry(), new DefaultMetadataSerializer(), dbLineValidator);
        NoteRepository noteRepository = new CachedFileNoteRepository(dbConnector, dbLineValidator, noteSerializer);
        NoteService noteService = new DefaultNoteService(noteMapper, noteRepository, metadataService, contentServiceRegistry, userSortingSettingsRepository);
        NoteController controller = new DefaultNoteController(noteService);
        DefaultNoteView noteView = new DefaultNoteView(new DefaultMetadataView(), new ContentViewRegistry(), new Scanner(System.in), controller);

        noteView.startMenu();
    }
}