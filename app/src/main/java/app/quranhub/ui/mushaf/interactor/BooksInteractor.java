package app.quranhub.ui.mushaf.interactor;

import androidx.lifecycle.LiveData;

import java.util.List;

import app.quranhub.data.local.entity.Book;
import app.quranhub.data.remote.model.BookContent;
import app.quranhub.ui.mushaf.model.TafseerModel;

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
