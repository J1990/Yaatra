package com.tcd.yaatra.ui.fragments;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment<DataBindingClass extends ViewDataBinding> extends Fragment {

    public DataBindingClass layoutDataBinding;
    protected FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        configureDagger();
        initDataBinding(inflater, container);
        initEventHandlers();
        fragmentManager = getActivity().getSupportFragmentManager();
        return this.layoutDataBinding.getRoot();
    }

    private void initDataBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        this.layoutDataBinding = DataBindingUtil.inflate(inflater, getFragmentResourceId(), container, false);
    }

    protected void configureDagger() {
//        AndroidInjection.inject(getActivity());
        AndroidSupportInjection.inject(this);
    }

    protected abstract int getFragmentResourceId();

    protected void initEventHandlers(){};

}
