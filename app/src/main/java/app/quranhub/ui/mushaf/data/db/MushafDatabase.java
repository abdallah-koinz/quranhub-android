package app.quranhub.ui.mushaf.data.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import app.quranhub.ui.mushaf.data.dao.AyaDao;
import app.quranhub.ui.mushaf.data.dao.AyaQuranSubjectDao;
import app.quranhub.ui.mushaf.data.dao.HizbQuarterDao;
import app.quranhub.ui.mushaf.data.dao.JuzDao;
import app.quranhub.ui.mushaf.data.dao.QuranSubjectCategoryDao;
import app.quranhub.ui.mushaf.data.dao.QuranSubjectDao;
import app.quranhub.ui.mushaf.data.dao.SuraDao;
import app.quranhub.ui.mushaf.data.entity.Aya;
import app.quranhub.ui.mushaf.data.entity.AyaQuranSubject;
import app.quranhub.ui.mushaf.data.entity.HizbQuarter;
import app.quranhub.ui.mushaf.data.entity.Juz;
import app.quranhub.ui.mushaf.data.entity.QuranSubject;
import app.quranhub.ui.mushaf.data.entity.QuranSubjectCategory;
import app.quranhub.ui.mushaf.data.entity.Sura;


@Database(entities = {Sura.class, Aya.class, HizbQuarter.class, Juz.class, QuranSubjectCategory.class
        , QuranSubject.class, AyaQuranSubject.class}, version = 2, exportSchema = false)
public abstract class MushafDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "mushaf_metadata.db";
    public static final int ASSET_DB_VERSION = 1;

    private static volatile MushafDatabase instance;

    public static MushafDatabase getInstance(@NonNull Context context) {
        if (instance == null) {
            synchronized (MushafDatabase.class) {
                if (instance == null) {
                    instance = RoomAsset.databaseBuilder(context.getApplicationContext(),
                            MushafDatabase.class, DATABASE_NAME, ASSET_DB_VERSION)
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract AyaDao getAyaDao();

    public abstract HizbQuarterDao getHizbQuarterDao();

    public abstract JuzDao getJuzDao();

    public abstract SuraDao getSuraDao();

    public abstract QuranSubjectCategoryDao getQuranSubjectCategoryDao();

    public abstract QuranSubjectDao getQuranSubjectDao();

    public abstract AyaQuranSubjectDao getAyaQuranSubjectDao();

}
