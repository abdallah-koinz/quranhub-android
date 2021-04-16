package app.quranhub.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import app.quranhub.data.Constants;
import app.quranhub.R;
import app.quranhub.ui.base.BaseActivity;
import app.quranhub.ui.downloads_manager.DownloadsManagerActivity;
import app.quranhub.ui.downloads_manager.dialogs.QuranRecitersDialogFragment;
import app.quranhub.ui.mushaf.data.entity.Sheikh;
import app.quranhub.ui.settings.custom.MushafSetting;
import app.quranhub.ui.settings.custom.MushafSettingSwitch;
import app.quranhub.ui.settings.dialogs.OptionsListDialogFragment;
import app.quranhub.util.LocaleUtils;
import app.quranhub.util.AppPreferencesUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SettingsFragment extends Fragment implements OptionsListDialogFragment.ItemSelectionListener
        , QuranRecitersDialogFragment.ReciterSelectionListener {

    private static final String TAG = SettingsFragment.class.getSimpleName();

    private static final int RC_APP_LANG_SETTING = 1;
    private static final int RC_TRANS_LANG_SETTING = 2;
    private static final int RC_RECITATION_SETTING = 3;

    @BindView(R.id.setting_app_lang)
    MushafSetting appLangSetting;
    @BindView(R.id.setting_translation_lang)
    MushafSetting translationLangSetting;
    @BindView(R.id.setting_screen_reading_backlight)
    MushafSettingSwitch screenReadingBacklightSettingSwitch;
    @BindView(R.id.setting_last_read_page)
    MushafSettingSwitch lastReadPageSettingSwitch;
    @BindView(R.id.setting_reading_warnings)
    MushafSettingSwitch readingWarningsSettingSwitch;
    @BindView(R.id.setting_reading_alarm)
    MushafSetting readingAlarmSetting;
    @BindView(R.id.setting_recitation)
    MushafSetting recitationSetting;
    @BindView(R.id.setting_quran_reader)
    MushafSetting quranReaderSetting;
    @BindView(R.id.setting_audio_download_manager)
    MushafSetting audioDownloadManagerSetting;
    @BindView(R.id.setting_help)
    MushafSetting helpSetting;
    @BindView(R.id.setting_about_app_version)
    MushafSetting aboutAppVersionSetting;
    @BindView(R.id.setting_share_app)
    MushafSetting shareAppSetting;

    private Unbinder butterknifeUnbinder;

    public SettingsFragment() {
        // required private constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        butterknifeUnbinder = ButterKnife.bind(this, view);
        initSettingsViews();
        setSettingsViewsListeners();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        butterknifeUnbinder.unbind();
    }

    private void initSettingsViews() {
        // appLangSetting
        int currentAppLanguageIndex = Constants.Language.CODES.indexOf(
                AppPreferencesUtils.getAppLangSetting(requireContext()));
        appLangSetting.setCurrentValue(
                getString(Constants.Language.NAMES_STR_IDS[currentAppLanguageIndex]));

        // translationLangSetting
        int currentTranslationLanguageIndex = Constants.Language.CODES.indexOf(
                AppPreferencesUtils.getQuranTranslationLanguage(requireContext()));
        translationLangSetting.setCurrentValue(
                getString(Constants.Language.NAMES_STR_IDS[currentTranslationLanguageIndex]));

        // screenReadingBacklightSettingSwitch
        screenReadingBacklightSettingSwitch.setChecked(
                AppPreferencesUtils.getScreenReadingBacklightSetting(requireContext()));

        // lastReadPageSettingSwitch
        lastReadPageSettingSwitch.setChecked(
                AppPreferencesUtils.getLastReadPageSetting(requireContext()));

        // recitationSetting
        int selectedRecitationIndex = AppPreferencesUtils.getRecitationSetting(requireContext());
        recitationSetting.setCurrentValue(getString(Constants.Recitation.NAMES_STR_IDS[selectedRecitationIndex]));

        // quranReaderSetting
        String reciterId = AppPreferencesUtils.getReciterSheikhSetting(requireContext());
        if (reciterId != null) {
            quranReaderSetting.setCurrentValue(Sheikh.getLocalizedName(requireContext(), reciterId));
        }

    }

    private void setSettingsViewsListeners() {
        appLangSetting.setOnClickListener(v -> {
            // TODO apply MVP or MVVM
            int currentAppLanguageIndex = Constants.Language.CODES.indexOf(
                    AppPreferencesUtils.getAppLangSetting(requireContext()));
            OptionsListDialogFragment appLangDialog = OptionsListDialogFragment.getInstance(
                    getString(R.string.app_lang_setting_dialog_title),
                    Constants.Language.NAMES_STR_IDS,
                    Constants.Language.FLAGS_DRAWABLE_IDS,
                    currentAppLanguageIndex, this, RC_APP_LANG_SETTING);
            appLangDialog.show(getActivity().getSupportFragmentManager()
                    , "AppLangDialog");
        });

        translationLangSetting.setOnClickListener(v -> {
            // TODO apply MVP or MVVM
            int currentTranslationLanguageIndex = Constants.Language.CODES.indexOf(
                    AppPreferencesUtils.getQuranTranslationLanguage(requireContext()));
            OptionsListDialogFragment translationLangDialog = OptionsListDialogFragment.getInstance(
                    getString(R.string.translation_lang_setting_dialog_title),
                    Constants.Language.NAMES_STR_IDS,
                    Constants.Language.FLAGS_DRAWABLE_IDS,
                    currentTranslationLanguageIndex, this, RC_TRANS_LANG_SETTING);
            translationLangDialog.show(getActivity().getSupportFragmentManager()
                    , "TransLangDialog");
        });

        screenReadingBacklightSettingSwitch.setOnCheckedChangeListener((settingSwitch, checked) -> {
            // TODO apply MVP or MVVM
            AppPreferencesUtils.persistScreenReadingBacklightSetting(requireContext(), checked);
        });

        lastReadPageSettingSwitch.setOnCheckedChangeListener((settingSwitch, checked) -> {
            // TODO apply MVP or MVVM
            AppPreferencesUtils.persistLastReadPageSetting(requireContext(), checked);
        });

        readingWarningsSettingSwitch.setOnCheckedChangeListener((settingSwitch, checked) -> {
            // TODO readingWarningsSettingSwitch change listener
        });

        readingAlarmSetting.setOnClickListener(v -> {
            // TODO readingAlarmSetting click listener
        });

        recitationSetting.setOnClickListener(v -> {
            // TODO apply MVP or MVVM
            int selectedRecitationSettingIndex = AppPreferencesUtils.getRecitationSetting(requireContext());
            OptionsListDialogFragment recitationDialog = OptionsListDialogFragment.getInstance(
                    getResources().getString(R.string.recitation_setting_dialog_title),
                    Constants.Recitation.NAMES_STR_IDS, selectedRecitationSettingIndex
                    , this, RC_RECITATION_SETTING);
            recitationDialog.show(getActivity().getSupportFragmentManager(), "RecitationDialog");
        });

        quranReaderSetting.setOnClickListener(v -> {
            // TODO apply MVP or MVVM
            int recitationId = AppPreferencesUtils.getRecitationSetting(requireContext());
            String reciterId = AppPreferencesUtils.getReciterSheikhSetting(requireContext());
            QuranRecitersDialogFragment recitersDialog = QuranRecitersDialogFragment
                    .newInstance(recitationId, reciterId);
            recitersDialog.show(getChildFragmentManager(), "QuranRecitersDialogFragment");
        });

        audioDownloadManagerSetting.setOnClickListener(v -> {
            // TODO apply MVP or MVVM
            startActivity(new Intent(requireContext(), DownloadsManagerActivity.class));
        });

        helpSetting.setOnClickListener(v -> {
            // TODO helpSetting click listener
        });

        aboutAppVersionSetting.setOnClickListener(v -> {
            // TODO aboutAppVersionSetting click listener
        });

        shareAppSetting.setOnClickListener(v -> {
            // TODO shareAppSetting click listener
        });
    }

    @Override
    public void onItemSelected(int requestCode, int itemIndex) {
        switch (requestCode) {
            case RC_APP_LANG_SETTING:
                int currentAppLanguageIndex = Constants.Language.CODES.indexOf(
                        AppPreferencesUtils.getAppLangSetting(requireContext()));
                if (itemIndex != currentAppLanguageIndex) {
                    // save user setting & change app language
                    String langCode = Constants.Language.CODES.get(itemIndex);
                    AppPreferencesUtils.persistAppLangSetting(requireContext(), langCode);
                    LocaleUtils.setAppLanguage(requireContext(), langCode);

                    ((BaseActivity) requireActivity()).restart();
                }
                break;
            case RC_TRANS_LANG_SETTING:
                // save user setting & change translation language
                String langCode = Constants.Language.CODES.get(itemIndex);
                AppPreferencesUtils.persistQuranTranslationLanguage(requireContext(), langCode);
                translationLangSetting.setCurrentValue(
                        getString(Constants.Language.NAMES_STR_IDS[itemIndex]));
                break;
            case RC_RECITATION_SETTING:
                int selectedRecitationId = itemIndex;
                boolean isChanged = AppPreferencesUtils.persistRecitationSetting(requireContext(), selectedRecitationId);
                if (isChanged) {
                    // update the current recitation setting & reset quran reader setting
                    recitationSetting.setCurrentValue(
                            getString(Constants.Recitation.NAMES_STR_IDS[selectedRecitationId]));
                    quranReaderSetting.setCurrentValue(null);
                }
                break;
            default:
                Log.e(TAG, "onItemSelected() - unknown requestCode: " + requestCode);
        }

    }

    @Override
    public void onReciterSelected(int recitationId, @NonNull Sheikh reciter) {
        Log.d(TAG, "onReciterSelected: recitationId=" + recitationId + " , reciterId=" + reciter.getId());

        AppPreferencesUtils.persistReciterSheikhSetting(requireContext(), reciter.getId());
        quranReaderSetting.setCurrentValue(reciter.getLocalizedName(requireContext()));
    }
}
