package com.ats.hreasy.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ats.hreasy.R;
import com.ats.hreasy.interfaces.AddClaimInterface;
import com.ats.hreasy.interfaces.ClaimHistoryInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClaimFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tab;
    FragmentPagerAdapter adapterViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_claim, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        tab = view.findViewById(R.id.tab);

        adapterViewPager = new ViewPagerAdapter(getChildFragmentManager(), getContext());
        viewPager.setAdapter(adapterViewPager);
        tab.post(new Runnable() {
            @Override
            public void run() {
                try {
                    tab.setupWithViewPager(viewPager);
                } catch (Exception e) {
                }
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ////Log.e("POSITION : ", "----------------------" + position);

                if (position == 0) {
                    AddClaimInterface fragmentAddCliam = (AddClaimInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentAddCliam != null) {
                        fragmentAddCliam.fragmentBecameVisible();
                    }
                } else if (position == 1) {
                    ClaimHistoryInterface fragmentClaimHistory = (ClaimHistoryInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentClaimHistory != null) {
                        fragmentClaimHistory.fragmentBecameVisible();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {

        private Context mContext;

        public ViewPagerAdapter(FragmentManager fm, Context mContext) {
            super(fm);
            this.mContext = mContext;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new AddClaimFragment();
            } else {
                return new ClaimHistoryFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Add Claim";
                case 1:
                    return "Claim History";
                default:
                    return null;
            }
        }
    }


}
