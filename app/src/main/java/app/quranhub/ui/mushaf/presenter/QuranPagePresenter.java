package app.quranhub.ui.mushaf.presenter;

import app.quranhub.ui.base.BasePresenter;
import app.quranhub.ui.base.BaseView;
import app.quranhub.ui.mushaf.data.entity.Aya;
import app.quranhub.ui.mushaf.data.entity.AyaBookmark;
import app.quranhub.ui.mushaf.data.entity.BookmarkType;
import app.quranhub.ui.mushaf.data.entity.Note;

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
