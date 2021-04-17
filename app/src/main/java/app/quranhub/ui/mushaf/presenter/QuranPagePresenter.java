package app.quranhub.ui.mushaf.presenter;

import app.quranhub.data.local.entity.Aya;
import app.quranhub.data.local.entity.AyaBookmark;
import app.quranhub.data.local.entity.BookmarkType;
import app.quranhub.data.local.entity.Note;
import app.quranhub.ui.base.BasePresenter;
import app.quranhub.ui.base.BaseView;

public interface QuranPagePresenter<T extends BaseView> extends BasePresenter<T> {

    void getPageAyas(int page);

    void insertAyaBookmark(AyaBookmark ayaBookmark);

    void removeBookmark(int ayaId);

    void getAyaBookmarkType(int ayaId);

    void drawInitAyaShadow(int pageNumber, int ayaId);

    void handleQuranPageClick();

    void addNote(Note note);

    void checkAyaHasNote(int id);


    void getBookmarkTypes();

    void insertCustomBookmark(Aya currentAya, BookmarkType type);


}
