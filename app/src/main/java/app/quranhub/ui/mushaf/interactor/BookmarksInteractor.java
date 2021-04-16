package app.quranhub.ui.mushaf.interactor;

import androidx.lifecycle.LiveData;

import java.util.List;

import app.quranhub.ui.mushaf.data.entity.AyaBookmark;
import app.quranhub.ui.mushaf.data.entity.BookmarkType;
import app.quranhub.ui.mushaf.data.entity.Sura;
import app.quranhub.ui.mushaf.model.DisplayableBookmark;

public interface BookmarksInteractor {

    Sura getSura(int suraId);

    LiveData<List<DisplayableBookmark>> getAllBookmarks();

    void deleteBookmark(int bookmarkId);

    LiveData<List<AyaBookmark>> filterBookmarks(int filterType);

    void changeBookmarkType(int bookmarkId, int bookmarkTypeId);

    LiveData<List<BookmarkType>> getBookmarkTypes();
}
