package com.haku.wallet.settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends PreferenceActivity {
    private static final List<String> fragmentNames = Arrays.asList(
            "com.haku.wallet.settings.SettingsFragment"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SettingsFragment()).commit();
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return SettingsActivity.fragmentNames.contains(fragmentName);
    }
}
