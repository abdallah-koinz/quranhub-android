package app.quranhub.ui.mushaf.interactor;

import java.util.ArrayList;

import app.quranhub.ui.mushaf.data.entity.Aya;
import app.quranhub.ui.mushaf.data.entity.TranslationBook;
import app.quranhub.ui.mushaf.model.QuranPageInfo;
import app.quranhub.ui.mushaf.model.SuraVersesNumber;

public interface Mus7fInteractor {

    void getPageInfo(int curentPage);

    void getAyaTafseer(int ayaId);

    void getTafseerBook(String currentTafsserId);

    void initTranslationDB(String dbName);

    void getPageSuras();

    void checkAyaHasRecorder(int id);

    void saveRecorderPath(int ayaId, String recorderPath);

    void deleteAyaVoiceRecorder(int ayaId);

    void getSuraNumofVerses();

    void getFromAyaPage(int fromAya);

    void getAya(int currentAyaId);


    interface ResultListener {
        void onGetPageInfo(QuranPageInfo pageInfo);

        void onGetAyaTafseer(String tafseer);

        void onGetTafsserBook(TranslationBook book);

        void onGetSuraPage(ArrayList<ArrayList<Integer>> suras);

        void onErroOccured();

        void onNoBooks();

        void onAyaHasRecorder(String recorderPath);

        void onGetSuraVersesNumber(ArrayList<SuraVersesNumber> suraVersesNumbers);

        void onGetAyaPage(int page);

        void onGetAya(Aya aya);
    }
}
