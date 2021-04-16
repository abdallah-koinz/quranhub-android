package app.quranhub.ui.downloads_manager.network.model;

import androidx.annotation.NonNull;

import java.util.List;

import app.quranhub.data.local.entity.Reciter;

public class RecitersResponse {

    @NonNull
    private List<Reciter> reciters;

    public RecitersResponse(@NonNull List<Reciter> reciters) {
        this.reciters = reciters;
    }

    @NonNull
    public List<Reciter> getReciters() {
        return reciters;
    }

    public void setReciters(@NonNull List<Reciter> reciters) {
        this.reciters = reciters;
    }

    @Override
    public String toString() {
        return "RecitersResponse{" +
                "reciters=" + reciters +
                '}';
    }
}
