package com.example.orderfood.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;
//一个管理fragment的adapter,通过这个adapter,在BusinessActivity中可以将其他一些fragment添加到这个adapter中,以实现管理
public class FragmentAdapter extends FragmentStateAdapter {
    private final List<Fragment> fragmentList;
    private final List<String> titleList;

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity,List<Fragment> fragmentList,List<String> titleList) {
        super(fragmentActivity);
        this.fragmentList=fragmentList;
        this.titleList=titleList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
    public List<String> getTitleList(){
        return titleList;
    }
}
