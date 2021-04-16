package app.quranhub.data.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.PropertyName;

import java.util.Map;

public class Reciter {

    @DocumentId
    private String reciterId;
    private Map<String, String> name;
    private Map<String, String> nationality;
    private String audioBaseUrl;

    public Reciter() {
        // No arg constructor
    }

    public Reciter(String reciterId, Map<String, String> name, Map<String, String> nationality,
                   String audioBaseUrl) {
        this.reciterId = reciterId;
        this.name = name;
        this.nationality = nationality;
        this.audioBaseUrl = audioBaseUrl;
    }

    public String getReciterId() {
        return reciterId;
    }

    public void setReciterId(String reciterId) {
        this.reciterId = reciterId;
    }

    public Map<String, String> getName() {
        return name;
    }

    public void setName(Map<String, String> name) {
        this.name = name;
    }

    public Map<String, String> getNationality() {
        return nationality;
    }

    public void setNationality(Map<String, String> nationality) {
        this.nationality = nationality;
    }

    @PropertyName("audio_base_url")
    public String getAudioBaseUrl() {
        return audioBaseUrl;
    }

    @PropertyName("audio_base_url")
    public void setAudioBaseUrl(String audioBaseUrl) {
        this.audioBaseUrl = audioBaseUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return "Reciter{" +
                "reciterId='" + reciterId + '\'' +
                ", name=" + name +
                ", nationality=" + nationality +
                ", audioBaseUrl='" + audioBaseUrl + '\'' +
                '}';
    }
}
