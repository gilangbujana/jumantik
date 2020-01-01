package id.co.wow.jumantik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import id.co.wow.jumantik.Quiz.QuizActivity;

public class ScoreActivity extends AppCompatActivity {

    TextView tv_score;
    Button btn_kerjakan_lagi, btn_ke_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        String skor = getIntent().getStringExtra("score");
        tv_score = (TextView) findViewById(R.id.tv_skor_score);
        btn_kerjakan_lagi = (Button) findViewById(R.id.btn_kerjakan_lagi_score);
        btn_ke_home = (Button) findViewById(R.id.btn_kembali_ke_home_score);

        tv_score.setText(skor);

        btn_kerjakan_lagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(ScoreActivity.this, QuizActivity.class));
            }
        });
        btn_ke_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
