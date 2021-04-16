package app.quranhub.ui.mushaf.presenter;

import androidx.annotation.NonNull;

import app.quranhub.ui.base.BasePresenter;
import app.quranhub.ui.base.BaseView;

public interface BookmarksPresenter<T extends BaseView> extends BasePresenter<T> {

    void enableEditList();

    void finishEditList();

    void filterList();

    void searchList(@NonNull String text);

}
