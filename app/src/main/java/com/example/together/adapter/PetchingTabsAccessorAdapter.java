package com.example.together.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.together.fragment.PetBunyangFragment;
import com.example.together.fragment.PetFirendsFragment;
import com.example.together.fragment.PetchingLoungeFragment;

public class PetchingTabsAccessorAdapter extends FragmentPagerAdapter
{

    public PetchingTabsAccessorAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {

        switch (position)
        {
            case 0:
                PetBunyangFragment petBunyangFragment = new PetBunyangFragment();
                return petBunyangFragment;

            case 1:
                PetFirendsFragment petFirendsFragment = new PetFirendsFragment();
                return petFirendsFragment;

            case 2:
                PetchingLoungeFragment petchingLoungeFragment = new PetchingLoungeFragment();
                return petchingLoungeFragment;

             default:
                 return null;

        }

    }

    @Override
    public int getCount()
    {
        return 3;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return "펫칭 분양";

            case 1:
                return "펫칭 프랜드";

            case 2:
                return "라운지";

            default:
                return null;

        }

    }
}
