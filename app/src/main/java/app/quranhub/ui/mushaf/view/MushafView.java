package app.quranhub.ui.mushaf.view;

import java.util.ArrayList;

import app.quranhub.ui.base.BaseView;
import app.quranhub.ui.mushaf.data.entity.Aya;
import app.quranhub.ui.mushaf.data.entity.TranslationBook;
import app.quranhub.ui.mushaf.model.QuranPageInfo;
import app.quranhub.ui.mushaf.model.SuraVersesNumber;

public interface MushafView extends BaseView {

    void showQuranPageInfo(QuranPageInfo quranPageInfo);

    void onGetAyaTafseer(String tafseer);

    void onGetTafseerBook(TranslationBook book);

    void onNoBooksExist();

    void onGetPageSuras(ArrayList<ArrayList<Integer>> suras);

    void onGetAyaRecorder(String recorderPath);

    void onGetSuraVersesNumber(ArrayList<SuraVersesNumber> suraVersesNumbers);

    void onGetAyaPage(int page);

    void onGetCurrentAyaFromNotification(Aya aya);

}
