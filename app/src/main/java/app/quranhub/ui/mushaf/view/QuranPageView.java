package app.quranhub.ui.mushaf.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import app.quranhub.ui.base.BaseView;
import app.quranhub.ui.mushaf.data.entity.Aya;
import app.quranhub.ui.mushaf.data.entity.BookmarkType;
import app.quranhub.ui.mushaf.data.entity.Note;
import app.quranhub.ui.mushaf.model.BookmarkModel;

public interface QuranPageView extends BaseView {

    void drawInitAyaShadow(@NonNull Aya aya, @Nullable Aya previousAya);

    void onGetPageAya(List<Aya> ayaList);

    void onGetAyaBookmarkType(BookmarkModel bookmarkModel);

    void onSuccessRemoveBookmark();

    void onAyaHasNote(Note note);

    void onGetAyaBookmarkTypes(List<BookmarkType> bookmarkTypes);

}
