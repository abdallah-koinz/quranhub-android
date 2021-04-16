package app.quranhub.ui.mushaf.view;

import androidx.annotation.NonNull;

import app.quranhub.ui.base.BaseView;

public interface BookmarksView extends BaseView {

    void enableEditList();

    void finishEditList();

    void filterList();

    void searchList(@NonNull String text);

}
