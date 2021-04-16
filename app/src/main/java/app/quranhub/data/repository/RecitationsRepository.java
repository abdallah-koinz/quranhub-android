package app.quranhub.data.repository;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import app.quranhub.data.model.Reciter;
import app.quranhub.ui.mushaf.data.entity.Sheikh;

public class RecitationsRepository {

    private static final String TAG = RecitationsRepository.class.getSimpleName();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public List<Sheikh> getRecitersForRecitation(String recitationKey) {
        db.collection("recitations")
                .document(recitationKey)
                .collection("reciters")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, task.getResult().toObjects(Reciter.class).toString());
                    } else {
                        Log.e(TAG, "Error reading reciters", task.getException());
                    }
                });
        return null;
    }

}
