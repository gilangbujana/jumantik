package id.co.wow.jumantik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Button;



public class SplashScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home;
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
                if(sp.contains("jsonDataUser")){
                    home =new Intent(SplashScreen.this, HomeActivity.class);
                }else{
                    home = new Intent(SplashScreen.this, SignInActivity.class);
                }
                //setelah loading maka akan langsung berpindah ke home activity

                startActivity(home);
                finish();

            }
        },400);

    }



}
