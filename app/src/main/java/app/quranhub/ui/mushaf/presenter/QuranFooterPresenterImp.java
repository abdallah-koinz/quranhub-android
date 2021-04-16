package app.quranhub.ui.mushaf.presenter;

import app.quranhub.ui.base.BasePresenterImp;
import app.quranhub.ui.mushaf.view.QuranFooterView;

public class QuranFooterPresenterImp extends BasePresenterImp<QuranFooterView>
        implements QuranFooterPresenter {

    @Override
    public void displaySearchDialog() {
        baseView.displaySearchDialog();
    }

    @Override
    public void toggleNightMode() {
        baseView.toggleNightMode();
    }

}
