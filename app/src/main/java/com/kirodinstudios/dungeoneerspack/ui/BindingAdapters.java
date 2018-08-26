package com.kirodinstudios.dungeoneerspack.ui;

import android.view.View;

import androidx.databinding.BindingAdapter;

public class BindingAdapters {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean shouldShowView) {
        view.setVisibility(shouldShowView ? View.VISIBLE : View.GONE);
    }
}
