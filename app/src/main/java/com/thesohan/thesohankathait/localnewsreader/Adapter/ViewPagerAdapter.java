package com.thesohan.thesohankathait.localnewsreader.Adapter;

import android.util.Log;

import com.thesohan.thesohankathait.localnewsreader.Fragment.LocalNews;
import com.thesohan.thesohankathait.localnewsreader.Fragment.TopNews;
import com.thesohan.thesohankathait.localnewsreader.Fragment.UploadNews;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {

        Log.d("position",""+position);
        switch(position) {
            case 0:
                return TopNews.newInstance();

            case 1:
                return LocalNews.newInstance();

            case 2:
                return UploadNews.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch(position){
            case 0:
                return "TOP NEWS";

            case 1:
                return "LOCAL NEWS";

            case 2:
                return "UPLOAD";

        }
        return null;
    }


}
