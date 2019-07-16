package com.example.together.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.together.fragment.PetBunYangInfoEditFragment;
import com.example.together.fragment.PetFriendsInfoEditFragment;


public class PetchingMyPetInfoEditAdapter extends FragmentPagerAdapter {

    public PetchingMyPetInfoEditAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {

        switch (position)
        {
            case 0:
                PetBunYangInfoEditFragment petBunYangInfoEditFragment = new PetBunYangInfoEditFragment();
                return petBunYangInfoEditFragment;

            case 1:
                PetFriendsInfoEditFragment petFriendsInfoEditFragment = new PetFriendsInfoEditFragment();
                return petFriendsInfoEditFragment;

            default:
                return null;
        }

    }

    @Override
    public int getCount()
    {
        return 2;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return "펫칭 분양등록";

            case 1:
                return "펫칭 프랜드등록";

            default:
                return null;

        }

    }


}
