package com.example.lab19_room_mvvm_repository_viewmodel_livedata_et_recyclerview.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.lab19_room_mvvm_repository_viewmodel_livedata_et_recyclerview.data.NoteRepository;
import com.example.lab19_room_mvvm_repository_viewmodel_livedata_et_recyclerview.data.local.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private final NoteRepository repository;
    private final LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
