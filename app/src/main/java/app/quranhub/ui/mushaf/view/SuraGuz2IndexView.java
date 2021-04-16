package app.quranhub.ui.mushaf.view;

import java.util.List;

import app.quranhub.ui.base.BaseView;
import app.quranhub.ui.mushaf.model.SuraIndexModelMapper;

public interface SuraGuz2IndexView extends BaseView {

    void onGetIndex(List<SuraIndexModelMapper> indexList);
}
