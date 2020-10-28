package com.sjtu.hupu.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjtu.hupu.View.MyRelationshipView;
import com.sjtu.hupu.databinding.LolFragmentBinding;
import com.sjtu.hupu.more.Node;

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
        Node node = new Node("PP帮主");
        node.left = new Node("qq");
        node.right = new Node("微信");
        node.left.left = new Node("小榴莲");
        node.left.right = new Node("xll");
        node.right.left = new Node("gjh");
        node.right.right = new Node("顾家铧");
        binding.myRelationshipView.addData(node);
    }
}