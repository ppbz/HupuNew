package com.sjtu.hupu.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjtu.hupu.R;
import com.sjtu.hupu.databinding.LolFragmentBinding;

public class LolFragment extends Fragment {

    private LolViewModel mViewModel;
    private LolFragmentBinding binding;

    public static LolFragment newInstance() {
        return new LolFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = LolFragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LolViewModel.class);
        binding.textView3.setText("RNG总冠军");
    }
}