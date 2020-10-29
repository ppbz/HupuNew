package com.sjtu.hupu.Fragment;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sjtu.hupu.R;
import com.sjtu.hupu.ViewGroup.MyFramelayout;
import com.sjtu.hupu.bean.ImageViewBean;
import com.sjtu.hupu.bean.LineBean;
import com.sjtu.hupu.databinding.MatchFragmentBinding;

public class MatchFragment extends Fragment {

    private MatchViewModel mViewModel;
    private MatchFragmentBinding binding;

    public static MatchFragment newInstance() {
        return new MatchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MatchFragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MatchViewModel.class);
        binding.view.setParnt(binding.mfl);
        LineBean lineBean = new LineBean();
        lineBean.imageView = new ImageView(getContext());
        lineBean.imageView1 = new ImageView(getContext());
        //Drawable icon = ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_clear_24);
        lineBean.imageView.setImageResource(R.drawable.ic_baseline_clear_24);
        lineBean.imageView1.setImageResource(R.drawable.ic_baseline_clear_24);
        lineBean.line = 1;
        binding.mfl.arrayListLines.add(lineBean);

    }
}