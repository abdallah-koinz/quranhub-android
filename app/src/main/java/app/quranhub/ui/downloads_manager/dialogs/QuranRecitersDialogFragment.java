package app.quranhub.ui.downloads_manager.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.quranhub.R;
import app.quranhub.data.Constants;
import app.quranhub.data.model.ReciterModel;
import app.quranhub.data.repository.RecitationsRepository;
import app.quranhub.data.local.dao.ReciterDao;
import app.quranhub.data.local.db.UserDatabase;
import app.quranhub.data.local.entity.Reciter;
import app.quranhub.ui.settings.dialogs.OptionsListAdapter;
import app.quranhub.util.AppPreferencesUtils;
import app.quranhub.util.DialogUtils;
import app.quranhub.util.FragmentUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * A {@code DialogFragment} that displays the available Quran reciters for the user to choose from.
 * <p>
 * Activities or parent fragments that shows this DialogFragment must implement the
 * {@link ReciterSelectionListener} interface to handle interaction events.
 * Use the {@link QuranRecitersDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuranRecitersDialogFragment extends DialogFragment
        implements OptionsListAdapter.ItemClickListener {

    private static final String TAG = QuranRecitersDialogFragment.class.getSimpleName();

    private static final String ARG_RECITATION_ID = "ARG_RECITATION_ID";
    private static final String ARG_SELECTED_RECITER_ID = "ARG_SELECTED_RECITER_ID";

    private int recitationId;
    @Nullable
    private String selectedReciterId;

    private int selectedReciterIndex = -1;

    @BindView(R.id.tv_msg_downloaded_reciters_only)
    TextView downloadedRecitersOnlyMsgTextView;
    @BindView(R.id.rv_reciters)
    RecyclerView recitersRecyclerView;
    @BindView(R.id.tv_msg_internet_connection_failed)
    TextView internetConnectionFailedMsgTextView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.btn_select)
    Button selectButton;

    private Unbinder butterknifeUnbinder;

    private OptionsListAdapter adapter;

    private ReciterSelectionListener reciterSelectionListener;

    private final RecitationsRepository recitationsRepository = new RecitationsRepository();
    private List<ReciterModel> reciterModels;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public QuranRecitersDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recitationId A recitation ID as in {@link Constants.Recitation}.
     * @return A new instance of fragment QuranRecitersDialogFragment.
     */
    public static QuranRecitersDialogFragment newInstance(int recitationId) {
        return newInstance(recitationId, null);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recitationId      A recitation ID as in {@link Constants.Recitation}.
     * @param selectedReciterId The current selected reciter ID.
     * @return A new instance of fragment QuranRecitersDialogFragment.
     */
    public static QuranRecitersDialogFragment newInstance(int recitationId,
                                                          @Nullable String selectedReciterId) {
        QuranRecitersDialogFragment recitersDialogFragment = new QuranRecitersDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RECITATION_ID, recitationId);
        args.putString(ARG_SELECTED_RECITER_ID, selectedReciterId);
        recitersDialogFragment.setArguments(args);
        return recitersDialogFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ReciterSelectionListener) {
            reciterSelectionListener = (ReciterSelectionListener) context;
        } else if (getParentFragment() instanceof ReciterSelectionListener) {
            reciterSelectionListener = (ReciterSelectionListener) getParentFragment();
        } else {
            throw new RuntimeException("Activities or parent fragments that shows this DialogFragment"
                    + " must implement ReciterSelectionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            recitationId = getArguments().getInt(ARG_RECITATION_ID);
            selectedReciterId = getArguments().getString(ARG_SELECTED_RECITER_ID);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_quran_reciters, container);
        butterknifeUnbinder = ButterKnife.bind(this, dialogView);
        initDialogView();
        return dialogView;
    }

    private void initDialogView() {
        downloadedRecitersOnlyMsgTextView.setVisibility(View.GONE);
        internetConnectionFailedMsgTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        selectButton.setEnabled(false);
    }

    private void setupRecitersRecyclerView() {
        if (reciterModels != null) {
            List<String> recitersNames = new ArrayList<>();
            for (int i = 0; i < reciterModels.size(); i++) {
                ReciterModel r = reciterModels.get(i);
                recitersNames.add(r.getLocalizedName(requireContext()));
                if (r.getId().equals(selectedReciterId)) {
                    selectedReciterIndex = i;
                    selectButton.setEnabled(true);
                }
            }

            recitersRecyclerView.setHasFixedSize(true);
            recitersRecyclerView.setLayoutManager(new LinearLayoutManager(
                    getContext(), RecyclerView.VERTICAL, false));
            recitersRecyclerView.addItemDecoration(new DividerItemDecoration(
                    getContext(), DividerItemDecoration.VERTICAL));
            adapter = new OptionsListAdapter(recitersNames, selectedReciterIndex, this);
            recitersRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        DialogUtils.adjustDialogSize(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        String recitationKey = null;
        if (recitationId == Constants.Recitation.HAFS_ID)
            recitationKey = Constants.Recitation.HAFS_KEY;
        else if (recitationId == Constants.Recitation.WARSH_ID)
            recitationKey = Constants.Recitation.WARSH_KEY;

        Disposable disposable = recitationsRepository.getRecitersForRecitation(recitationKey)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<ReciterModel>>() {
                    @Override
                    public void onSuccess(@NonNull List<ReciterModel> reciters) {
                        Log.d(TAG, "reciters: " + reciters);

                        if (FragmentUtils.isSafeFragment(QuranRecitersDialogFragment.this)) {
                            if (reciters.size() > 0) {
                                QuranRecitersDialogFragment.this.reciterModels = reciters;
                                setupRecitersRecyclerView();
                            } else {
                                Log.e(TAG, "The fetched reciters list is empty!");
                                Toast.makeText(requireContext(), R.string.no_reciters,
                                        Toast.LENGTH_SHORT).show();
                            }

                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "Error fetching reciters", e);

                        if (FragmentUtils.isSafeFragment(QuranRecitersDialogFragment.this)) {

                            // try to display the downloaded reciters
                            loadRecitersFromDb();

                        }
                    }
                });
        compositeDisposable.add(disposable);
    }

    @SuppressLint("StaticFieldLeak")
    private void loadRecitersFromDb() {
        // TODO load from DB only for a selected aya, or page or sura, to guarantee reciter is downloaded
        new AsyncTask<Void, Void, List<ReciterModel>>() {

            @Override
            protected List<ReciterModel> doInBackground(Void... voids) {
                if (getContext() != null) {
                    ReciterDao reciterDao = UserDatabase.getInstance(getContext()).getReciterDao();
                    final List<Reciter> recitersList = reciterDao.getAllForRecitation(recitationId);

                    // Convert from Reciter to ReciterModel
                    final List<ReciterModel> reciterModelsList = new ArrayList<>(recitersList.size());
                    for (Reciter r : recitersList) {
                        reciterModelsList.add(new ReciterModel(
                                r.getId(),
                                r.getName(),
                                r.getNationality(),
                                r.getAudioBaseUrl()
                        ));
                    }
                    return reciterModelsList;
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<ReciterModel> recitersList) {
                if (recitersList != null &&
                        FragmentUtils.isSafeFragment(QuranRecitersDialogFragment.this)) {
                    reciterModels = recitersList;
                    if (reciterModels.size() > 0) {
                        downloadedRecitersOnlyMsgTextView.setVisibility(View.VISIBLE);
                        setupRecitersRecyclerView();
                    } else {
                        // User hasn't downloaded any Quran audio before
                        internetConnectionFailedMsgTextView.setVisibility(View.VISIBLE);
                    }

                    progressBar.setVisibility(View.GONE);
                }
            }
        }.execute();
    }

    @Override
    public void onItemClick(int clickedItemIndex) {
        selectedReciterIndex = clickedItemIndex;
        if (!selectButton.isEnabled()) selectButton.setEnabled(true);
    }

    @OnClick(R.id.btn_back)
    void onBackClick() {
        dismiss();
    }

    @SuppressLint("StaticFieldLeak")
    @OnClick(R.id.btn_select)
    void onSelectClick() {
        ReciterModel selectedReciterModel = reciterModels.get(selectedReciterIndex);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                selectButton.setEnabled(false);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                if (FragmentUtils.isSafeFragment(QuranRecitersDialogFragment.this)) {

                    // Store selected reciter in DB
                    UserDatabase userDatabase = UserDatabase.getInstance(requireContext());
                    if (userDatabase.getReciterDao().getById(selectedReciterModel.getId()) == null) {
                        userDatabase.getReciterDao().insert(
                                new Reciter(selectedReciterModel.getId(),
                                        selectedReciterModel.getLocalizedName(requireContext()),
                                        selectedReciterModel.getLocalizedNationality(requireContext()),
                                        selectedReciterModel.getAudioBaseUrl())
                        );
                    }

                    // persist selected reciter as preference if recitation id matches the one in preferences
                    int recitationIdPreference = AppPreferencesUtils.getRecitationSetting(requireContext());
                    if (recitationIdPreference == recitationId) {
                        AppPreferencesUtils.persistReciterSheikhSetting(requireContext(), selectedReciterModel.getId());
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                reciterSelectionListener.onReciterSelected(recitationId, selectedReciterModel);
                dismiss();
            }
        }.execute();
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        butterknifeUnbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        reciterSelectionListener = null;
    }

    /**
     * This interface must be implemented by activities or parent fragments that contain this
     * dialog fragment to allow an interaction in this fragment to be communicated
     * to the activity or parent fragment.
     */
    public interface ReciterSelectionListener {
        void onReciterSelected(int recitationId, @NonNull ReciterModel reciterModel);
    }
}
