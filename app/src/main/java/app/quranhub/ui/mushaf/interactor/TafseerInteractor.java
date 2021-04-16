package app.quranhub.ui.mushaf.interactor;

import androidx.lifecycle.LiveData;

import java.util.List;

import app.quranhub.data.local.entity.Translation;
import app.quranhub.ui.mushaf.model.TafseerModel;

public interface TafseerInteractor {

    LiveData<List<TafseerModel>> getSuraTafseers(int suraNumber);

    void initTranslationDB(String dbName);

    LiveData<List<Translation>> getSuraBookTafseers(int suraNumber);

    LiveData<List<TafseerModel>> getSuraAyah(int suraNumber);
}
