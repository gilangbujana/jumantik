package id.co.wow.jumantik;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import id.co.wow.jumantik.InfoFragment.InfoPagerAdapter;


public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ViewPager viewPager = findViewById(R.id.info_viewpager);
        viewPager.setAdapter(new InfoPagerAdapter(getSupportFragmentManager()));
        TabLayout tabs = findViewById(R.id.info_tabs);
        tabs.setupWithViewPager(viewPager);




    }
}