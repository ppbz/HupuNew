package com.sjtu.hupu.Fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sjtu.hupu.MainActivity;
import com.sjtu.hupu.R;
import com.sjtu.hupu.ViewGroup.CustomerFlowLayout;
import com.sjtu.hupu.databinding.ReBangFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class ReBangFragment extends Fragment {

    private ReBangViewModel mViewModel;

    List<String> tags = new ArrayList<String>();

    private ReBangFragmentBinding binding;

    public static ReBangFragment newInstance() {
        return new ReBangFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ReBangFragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ReBangViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        binding.customerFlowLayout.setOnTagItemClickListener(new CustomerFlowLayout.OnTagItemClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                //Toast.makeText(getContext(), tv.getText().toString(), Toast.LENGTH_SHORT).show();
                if(tv.getText().toString().equals("阳哥你好！")){
                    Log.e("111","1111");
                    tags.add("阳哥牛逼！");
                    binding.customerFlowLayout.removeViewAt(4);
                    //binding.customerFlowLayout.setTags(tags);
                }
            }
        });
        binding.customerFlowLayout.setTags(tags);
    }

    private void initData() {
        tags.add("阳哥你好！");
        tags.add("Android开发");
        tags.add("新闻热点");
        tags.add("热水进宿舍啦！");
        tags.add("I love you");
        tags.add("成都妹子");
        tags.add("新余妹子");
        tags.add("仙女湖");
        tags.add("创新工厂");
        tags.add("孵化园");
        tags.add("神州100发射");
        tags.add("有毒疫苗");
        tags.add("顶你阳哥阳哥");
        tags.add("Hello World");
        tags.add("闲逛的蚂蚁");
        tags.add("闲逛的蚂蚁");
        tags.add("闲逛的蚂蚁");
        tags.add("闲逛的蚂蚁");
        tags.add("闲逛的蚂蚁");
        tags.add("闲逛的蚂蚁");
    }


}