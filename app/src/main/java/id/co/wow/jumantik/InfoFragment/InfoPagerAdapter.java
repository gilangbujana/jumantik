package id.co.wow.jumantik.InfoFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class InfoPagerAdapter extends FragmentPagerAdapter {

    private String[] judul = new String[]{"DBD", "JUMANTIK"};

    public InfoPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return (position == 0) ? new DBDFragment() : new JumantikFragment();
    }

    @Override
    public int getCount() {
        return judul.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return judul[position];
    }


}
