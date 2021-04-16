package app.quranhub.ui.mushaf.interactor;

import java.util.List;

import app.quranhub.ui.mushaf.model.TopicModel;

public interface SubjectInteractor {

    void getSubjects(List<String> subjects, List<String> subjectsCategory);


    interface SubjectListener {
        void onGetSubjects(List<TopicModel> topicModels);
    }
}
