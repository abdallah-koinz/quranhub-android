package app.quranhub.ui.mushaf.interactor;

import java.util.List;

import app.quranhub.ui.mushaf.model.SearchModel;

public interface TopicInteractor {


    void getAyas(int categoryId);

    interface TopicListener {
        void onGetTopics(List<SearchModel> searchModels);
    }
}
