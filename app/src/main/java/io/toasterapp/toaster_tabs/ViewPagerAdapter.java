package io.toasterapp.toaster_tabs;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.webkit.WebView;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    Context mContext;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb, Context context) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.mContext = context;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) {
            Tab1 tab1 = new Tab1();
            tab1.setContext(mContext);
            return tab1;
        } else if (position == 1) {
            Tab2 tab2 = new Tab2();
            tab2.setContext(mContext);
            return tab2;
        } else if (position == 2) {
            Tab3 tab3 = new Tab3();
            tab3.setContext(mContext);
            return tab3;
        } else {
            Tab4 tab4 = new Tab4();
            tab4.setContext(mContext);
            return tab4;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
