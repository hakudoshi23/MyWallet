package com.haku.wallet.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import com.avp.wallet.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this.getActivity(), R.xml.preferences, false);
        this.addPreferencesFromResource(R.xml.preferences);
    }
}
