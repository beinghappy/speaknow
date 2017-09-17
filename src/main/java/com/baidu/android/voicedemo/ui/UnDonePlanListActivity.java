package com.baidu.android.voicedemo.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.baidu.android.voicedemo.frg.FailFragment;
import com.baidu.android.voicedemo.frg.UndoneFragment;
import com.baidu.speech.recognizerdemo.R;

import java.util.ArrayList;
import java.util.List;

public class UnDonePlanListActivity extends AppCompatActivity {

    List<Fragment> frgList;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undone_plan_list);
        initTabLayout();
        initViewPager();
        tabLayout.setupWithViewPager(viewPager);

    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.vp_FindFragment_pager);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),
                this);
        viewPager.setAdapter(adapter);
    }

    private void initTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.tab_FindFragment_title);
        frgList = new ArrayList<>();
        Fragment undoneFrg = new UndoneFragment();
        Fragment failFrg = new FailFragment();
        frgList.add(undoneFrg);
        frgList.add(failFrg);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public final int COUNT = 2;
        private String[] titles = new String[]{"未完成计划", "残次品"};

        public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return frgList.get(position);
        }

        @Override
        public int getCount() {
            return COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


}
