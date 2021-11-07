package ca.bcit.park_tamai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class byGender extends AppCompatActivity {

    private HashMap<String, List<Report>> reports;
    private TextView female;
    private TextView male;
    private TextView unknown;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_gender);

        reports = new HashMap<String, List<Report>>();

        female = findViewById(R.id.femaleValue);
        male = findViewById(R.id.maleValue);
        unknown = findViewById(R.id.unknownValue);
        progressBar = findViewById(R.id.genderProgress);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        ref.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren().forEach(child->{
                    Report report = child.getValue(Report.class);
                    if (reports.get(report.getSex()) != null){
                        reports.get(report.getSex()).add(report);
                    } else {
                        List<Report> list = new ArrayList<>();
                        list.add(report);
                        reports.put(report.getSex(), list);
                    }
                });
                reports.keySet().forEach(key->{
                    Log.d("key",key);
                    switch(key) {
                        case "F": {
                            female.setText("" + reports.get(key).size());
                            break;
                        }
                        case "M": {
                            male.setText("" + reports.get(key).size());
                            break;
                        }
                        default: {
                            unknown.setText("" + reports.get(key).size());
                            break;
                        }
                    }
                });
                progressBar.setVisibility(View.INVISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                finish();
            }
        });
    }

}