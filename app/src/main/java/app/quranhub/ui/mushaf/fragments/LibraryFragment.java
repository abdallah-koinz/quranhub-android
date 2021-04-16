package app.quranhub.ui.mushaf.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import app.quranhub.R;
import app.quranhub.ui.main.MainActivity;
import app.quranhub.data.local.entity.TranslationBook;
import app.quranhub.util.AppPreferencesUtils;
import butterknife.ButterKnife;
import butterknife.Unbinder;


// TODO completely refactor LibraryFragment
public class LibraryFragment extends Fragment implements TranslationsDataFragment.TranslationSelectionListener {

    private static final String TAG = LibraryFragment.class.getSimpleName();


    Unbinder butterknifeUnbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        butterknifeUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        butterknifeUnbinder.unbind();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            TranslationsDataFragment translationsDataFragment = TranslationsDataFragment.newInstance(
                    AppPreferencesUtils.getQuranTranslationLanguage(getContext()));
            getChildFragmentManager().beginTransaction()
                    .add(R.id.container_data_fragment, translationsDataFragment, "TransDataFragment")
                    .commit();
        }
    }

    public void search(String input) {
        TranslationsDataFragment translationsDataFragment = (TranslationsDataFragment) getChildFragmentManager()
                .findFragmentByTag("TransDataFragment");
        if (translationsDataFragment != null) {
            translationsDataFragment.search(input);
        }
    }


    @Override
    public void onTranslationSelected(TranslationBook translationBook) {
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.openTafseerScreen(translationBook.getDatabaseName(), translationBook.getName());
        }
    }
}
