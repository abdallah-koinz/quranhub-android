package app.quranhub.ui.mushaf.interactor;

import androidx.annotation.Nullable;

import java.util.List;

import app.quranhub.data.local.entity.Aya;
import app.quranhub.data.local.entity.AyaBookmark;
import app.quranhub.data.local.entity.BookmarkType;
import app.quranhub.data.local.entity.Note;
import app.quranhub.ui.mushaf.model.BookmarkModel;

public interface QuranPageInteractor {

    void getPageAyaWithPrevious(int pageNumber, int ayaId);

    void getPageAyas(int page);

    void getBookmarkType(int ayaId);

    void insertAyaBookmark(AyaBookmark ayaBookmark);

    void removeBookmark(int ayaId);

    void addNote(Note note);

    void checkAyaNote(int ayaId);

    void getBookmarkTypes();

    void insertCustomBookmark(Aya currentAya, BookmarkType type);


    interface ResultListener {
        void onGetAyaWithPrevious(@Nullable Aya aya, @Nullable Aya previousAya);

        void onGetPageAyas(List<Aya> ayaList);

        void onGetBookmarkType(BookmarkModel bookmarkType);

        void onSuccessRemoveBookmark();

        void showMessage(String message);

        void onSuccessAddNote();

        void onGetAyaNote(Note note);

        void onGetBookmarkTypes(List<BookmarkType> bookmarkTypes);


    }

}
