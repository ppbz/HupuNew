package com.sjtu.hupu.Fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sjtu.hupu.R;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {

    private CommunityViewModel mViewModel;
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private List<String> list = new ArrayList<>();

    public static CommunityFragment newInstance() {
        return new CommunityFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.community_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CommunityViewModel.class);
        // TODO: Use the ViewModel

        viewPager2 = getView().findViewById(R.id.community_view_pager);
        tabLayout = getView().findViewById(R.id.community_tablayout1);
        initData();
        viewPager2.setAdapter(new MainAdapter(getActivity()));
        viewPager2.setCurrentItem(1,false);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(list.get(position));
            }
        });
        tabLayoutMediator.attach();

    }

    public void initData() {
        list.add("关注");
        list.add("广场");
        list.add("热榜");
    }

    private class MainAdapter extends FragmentStateAdapter{

        public MainAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0 :
                    return new GuanZhuFragment();
                case 1 :
                    return new GuangChangFragment();
                case 2 :
                    return new ReBangFragment();
                default :
                    return new LolFragment();
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

}