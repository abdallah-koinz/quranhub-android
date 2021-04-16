package app.quranhub.ui.mushaf.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.io.File;
import java.util.Objects;

import app.quranhub.data.Constants;
import app.quranhub.R;
import app.quranhub.util.RecorderMediaHelper;
import app.quranhub.util.AppPreferencesUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AyaRecorderPlayerDialog extends DialogFragment implements RecorderMediaHelper.MediaPlayerCallback {

    private View dialogView;
    private Dialog dialog;
    private static final String ARG_AYA_ID = "ARG_AYA_ID";
    private AyaRecorderPlayerListener listener;
    private RecorderMediaHelper recorderMediaHelper;
    private String outputRecorderPath;
    private int ayaId;
    private boolean isPlaying = false, userIsSeeking = false, firstPlay = true;
    private int userSelectedPosition;

    @BindView(R.id.play_iv)
    ImageView playIv;
    @BindView(R.id.recorder_progress)
    SeekBar progressRecorder;
    @BindView(R.id.recorder_time_tv)
    TextView recorderTime;


    public static AyaRecorderPlayerDialog getInstance(int ayaId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_AYA_ID, ayaId);
        AyaRecorderPlayerDialog dialog = new AyaRecorderPlayerDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (AyaRecorderPlayerListener) getParentFragment();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_play_aya_recorder, null);
        ButterKnife.bind(this, dialogView);
        initializeDialog();
        setRecordingFile();
        initSoundMedia();
        getPrevState(savedInstanceState);
        listenToSeekbarChanges();
        return dialog;
    }

    private void getPrevState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            recorderMediaHelper.seekTo(savedInstanceState.getInt("player_position"));
            isPlaying = savedInstanceState.getBoolean("is_playing");
            restorePlayingState();
        }
    }

    private void restorePlayingState() {
        if (isPlaying) {
            playIv.setImageResource(R.drawable.ic_pause);
            recorderMediaHelper.play();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_playing", isPlaying);
        outState.putInt("player_position", recorderMediaHelper.getCurrentPosition());

    }


    public void initializeDialog() {
        dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        dialog.setContentView(dialogView);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        if (getArguments() != null) {
            ayaId = getArguments().getInt(ARG_AYA_ID);
        }

    }

    private void setRecordingFile() {
        int recitation = AppPreferencesUtils.getRecitationSetting(getActivity());
        File file = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_MUSIC), Constants.Directory.AYA_VOICE_RECORDER
                + File.separator + recitation + File.separator
                + ayaId + ".3gp");
        if (file.exists()) {
            outputRecorderPath = file.getPath();
        } else {
            listener.onClickDeleteRecorder();
            Toast.makeText(getActivity(), getString(R.string.file_not_exist), Toast.LENGTH_LONG).show();
            dismiss();
        }
    }

    private void listenToSeekbarChanges() {
        progressRecorder.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                userIsSeeking = true;
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    userSelectedPosition = progress;
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                userIsSeeking = false;
                recorderMediaHelper.seekTo(userSelectedPosition);
            }
        });
    }

    private void initSoundMedia() {
        if (outputRecorderPath != null) {
            recorderMediaHelper = new RecorderMediaHelper();
            recorderMediaHelper.setMediaPlayerCallback(this);
            recorderMediaHelper.setAudioPath(outputRecorderPath);
        }
    }

    @OnClick(R.id.remove_record_iv)
    public void onRemoveRecorder() {
        recorderMediaHelper.release();
        listener.onClickDeleteRecorder();
        dismiss();
    }

    @OnClick(R.id.play_iv)
    public void onPlayRecorder() {
        if (isPlaying) {
            playIv.setImageResource(R.drawable.player_play_white_ic);
            recorderMediaHelper.pause();
        } else {
            playIv.setImageResource(R.drawable.ic_pause);
            recorderMediaHelper.play();
            recorderMediaHelper.startUpdatingAudioTime();
            if (firstPlay) {
                firstPlay = false;
                recorderTime.setText("0:00");
            }
        }
        isPlaying = !isPlaying;
    }

    @Override
    public void onGetMaxDuration(int duration) {
        progressRecorder.setMax(duration);
    }

    @Override
    public void onPositionChanged(int position) {
        if (!userIsSeeking) {
            if (Build.VERSION.SDK_INT >= 24) {
                progressRecorder.setProgress(position, true);
            } else {
                progressRecorder.setProgress(position);
            }
        }
    }

    @Override
    public void onUpdatedTime(String time) {
        recorderTime.setText(time);
    }

    @Override
    public void onStateChanged(int state) {
        if (state == State.COMPLETED) {
            progressRecorder.setProgress(0);
            isPlaying = false;
            firstPlay = true;
            playIv.setImageResource(R.drawable.player_play_white_ic);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!getActivity().isChangingConfigurations() && recorderMediaHelper != null) {
            recorderMediaHelper.release();
        }
    }

    public interface AyaRecorderPlayerListener {
        void onClickDeleteRecorder();
    }
}
