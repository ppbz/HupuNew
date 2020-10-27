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

public class RecommendFragment extends Fragment {

    private RecommendViewModel mViewModel;

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private List<String> list = new ArrayList<>();


    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recommend_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RecommendViewModel.class);
        // TODO: Use the ViewModel
        list.add("综合");
        list.add("直播");
        list.add("篮球");
        list.add("游戏");
        list.add("视频");
        list.add("影视娱乐");
        list.add("装备");
        list.add("健身");
        list.add("实战篮球");
        viewPager2 = getView().findViewById(R.id.recommend_view_pager);
        tabLayout = getView().findViewById(R.id.rec_tablayout);
        viewPager2.setAdapter(new MainAdapter(getActivity()));
        viewPager2.setCurrentItem(0,true);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(list.get(position));
            }
        });
        tabLayoutMediator.attach();




    }

    private class MainAdapter extends FragmentStateAdapter {

        public MainAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0 :
                    return new NBAFragment();
                case 2 :
                    return new CBAFragment();
                default:
                    return new LolFragment();
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

}