package app.quranhub.ui.mushaf.data.dao;

import androidx.room.Dao;
import androidx.room.Query;

import app.quranhub.ui.mushaf.data.entity.AyaQuranSubject;

@Dao
public interface AyaQuranSubjectDao {

    @Query("SELECT * FROM AyaQuranSubject WHERE id=:id")
    AyaQuranSubject findById(int id);

}
