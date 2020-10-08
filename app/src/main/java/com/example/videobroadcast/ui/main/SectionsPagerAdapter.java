package com.example.videobroadcast.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.videobroadcast.R;
import com.example.videobroadcast.broadcast.BroadcastFragment;
import com.example.videobroadcast.download.DownloadFragment;
import com.example.videobroadcast.stream.StreamFragment;

import java.util.ArrayList;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> pages = new ArrayList<>();

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_download, R.string.tab_broadcast, R.string.tab_stream};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        pages.add(new DownloadFragment());
        pages.add(new BroadcastFragment());
        pages.add(new StreamFragment());
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        return pages.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return pages.size();
    }
}