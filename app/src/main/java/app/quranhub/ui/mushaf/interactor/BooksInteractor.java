package app.quranhub.ui.mushaf.interactor;

import androidx.lifecycle.LiveData;

import java.util.List;

import app.quranhub.ui.mushaf.data.entity.Book;
import app.quranhub.ui.mushaf.model.TafseerModel;
import app.quranhub.ui.mushaf.network.model.BookContent;

public interface BooksInteractor {

    void getAllTranslations();

    LiveData<List<Book>> getLocallyTranslations();

    LiveData<List<TafseerModel>> getSuraTafseers(int suraNumber);


    void updateTranslationDownload(int id, int type, long downloadId);

    void updateFinishedDownload(long downloadId, int type);


    interface TranslationsListener {
        void onError();

        void onGetAllTranslation(List<BookContent> contents);
    }

}
