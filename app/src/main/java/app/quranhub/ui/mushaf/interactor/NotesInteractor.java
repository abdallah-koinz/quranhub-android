package app.quranhub.ui.mushaf.interactor;

import androidx.lifecycle.LiveData;

import java.util.List;

import app.quranhub.ui.mushaf.data.entity.Note;
import app.quranhub.ui.mushaf.model.DisplayedNote;

public interface NotesInteractor {

    LiveData<List<DisplayedNote>> getNotes();

    void editNote(Note note);

    void deleteNote(int ayaId);

}
