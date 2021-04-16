package app.quranhub.ui.mushaf.data.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import app.quranhub.ui.mushaf.data.entity.QuranSubject;
import io.reactivex.Single;

@Dao
public interface QuranSubjectDao {

    @Query("SELECT * FROM QuranSubject")
    Single<List<QuranSubject>> getAll();

    @Query("SELECT * FROM QuranSubject WHERE id IN(:ids)")
    List<QuranSubject> getAllByIds(int... ids);


}
