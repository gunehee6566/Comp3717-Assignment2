package ca.bcit.park_tamai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import ca.bcit.park_tamai.age.byAge;
import ca.bcit.park_tamai.date.byDate;
import ca.bcit.park_tamai.ha.byHA;

public class MainActivity extends AppCompatActivity {

    private Button btn_month_year;
    private Button btn_ha;
    private Button btn_gender;
    private Button btn_age;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogout = findViewById(R.id.btnLogout);
        btn_month_year = findViewById(R.id.btn_month_year);
        btn_ha = findViewById(R.id.btn_ha);
        btn_gender = findViewById(R.id.btn_gender);
        btn_age = findViewById(R.id.btn_age);

        Intent date_intent = new Intent(this, byDate.class);
        Intent ha_intent = new Intent(this, byHA.class);
        Intent gender_intent = new Intent(this, byGender.class);
        Intent age_intent = new Intent(this, byAge.class);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut(); // logout
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        btn_month_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(date_intent);
            }
        });

        btn_ha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ha_intent);
            }
        });

        btn_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(gender_intent);
            }
        });

        btn_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(age_intent);
            }
        });
    }
}