package com.jackshaw.proyekakhir.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.jackshaw.proyekakhir.R;
import com.jackshaw.proyekakhir.adapter.PagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    String film, tv;
    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_favourite, container, false);
        tabLayout = v.findViewById(R.id.tab_Layout);
        viewPager = v.findViewById(R.id.view_pager);
        pagerAdapter = new PagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        film = getResources().getString(R.string.movies);
        tv = getResources().getString(R.string.tv_show);

        //tambahkan fragment
        pagerAdapter.AddFragment(new MovieFavourit(), film);
        pagerAdapter.AddFragment(new TVShowFavourit(), tv);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return v;
    }

}
