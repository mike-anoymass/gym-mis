package com.example.myfirstapp;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SettingsFragment extends PreferenceFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Toast.makeText(ge, "settings Fragment", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.xml.preferences_xml, container, false);
    }
}
