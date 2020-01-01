package id.co.wow.jumantik.Laporan;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LaporanPagerAdapter extends FragmentPagerAdapter {

    String judul[] = {"DALAM RUMAH", "LUAR RUMAH"};
    DalamRumahFragment dalamRumahFragment;
    LuarRumahFragment luarRumahFragment;
    public LaporanPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        luarRumahFragment =new LuarRumahFragment();
        dalamRumahFragment = new DalamRumahFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return dalamRumahFragment;
        return luarRumahFragment;
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
