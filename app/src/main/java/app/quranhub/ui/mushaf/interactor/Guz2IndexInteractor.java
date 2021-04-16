package app.quranhub.ui.mushaf.interactor;

import androidx.lifecycle.LiveData;

import java.util.List;

import app.quranhub.ui.mushaf.model.HizbQuarterDataModel;

public interface Guz2IndexInteractor {

    LiveData<List<HizbQuarterDataModel>> getAllHizbQuarterDataModel();

}
