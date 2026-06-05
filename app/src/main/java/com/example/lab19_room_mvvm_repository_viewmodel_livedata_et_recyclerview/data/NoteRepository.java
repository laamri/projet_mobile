package com.example.lab19_room_mvvm_repository_viewmodel_livedata_et_recyclerview.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.lab19_room_mvvm_repository_viewmodel_livedata_et_recyclerview.data.local.Note;
import com.example.lab19_room_mvvm_repository_viewmodel_livedata_et_recyclerview.data.local.NoteDao;
import com.example.lab19_room_mvvm_repository_viewmodel_livedata_et_recyclerview.data.local.NoteDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {

    private final NoteDao noteDao;
    private final LiveData<List<Note>> allNotes;
    private final ExecutorService executorService;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Note note) {
        executorService.execute(() -> noteDao.insert(note));
    }

    public void delete(Note note) {
        executorService.execute(() -> noteDao.delete(note));
    }

    public void deleteAllNotes() {
        executorService.execute(noteDao::deleteAllNotes);
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
