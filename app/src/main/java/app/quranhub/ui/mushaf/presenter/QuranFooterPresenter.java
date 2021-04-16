package app.quranhub.ui.mushaf.presenter;

import app.quranhub.ui.base.BasePresenter;
import app.quranhub.ui.mushaf.view.QuranFooterView;

public interface QuranFooterPresenter extends BasePresenter<QuranFooterView> {

    void displaySearchDialog();

    void toggleNightMode();

}
