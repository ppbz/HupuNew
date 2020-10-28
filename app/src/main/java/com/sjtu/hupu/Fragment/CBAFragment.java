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
import com.sjtu.hupu.View.RelationshipView;
import com.sjtu.hupu.bean.DataBean;
import com.sjtu.hupu.databinding.CbaFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class CBAFragment extends Fragment {

    private CBAViewModel mViewModel;
    private CbaFragmentBinding binding;

    public static CBAFragment newInstance() {
        return new CBAFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = CbaFragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CBAViewModel.class);
        List<DataBean> list = new ArrayList<>();

        for (int i=0;i<3;i++){
            list.add(new DataBean());
        }

        binding.relationshipView.setStories(list,new DataBean());
        binding.relationshipView.setOnClickStoryListener(new RelationshipView.OnClickStoryListener() {
            @Override
            public void onClickStory(DataBean story) {
                List<DataBean> list = new ArrayList<>();

                for (int i=0;i<5;i++){
                    list.add(new DataBean());
                }
                binding.relationshipView.setStories(list,story);
            }
        });
    }
}