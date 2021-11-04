package ca.bcit.park_tamai;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class byAge extends AppCompatActivity {

    private HashMap<String, List<Report>> reports;
    private TextView less10;
    private TextView twentyTo;
    private TextView thirtyTo;
    private TextView fortyTo;
    private TextView fiftyTo;
    private TextView sixtyTo;
    private TextView seventyTo;
    private TextView eightyTo;
    private TextView ninetyTo;
    private TextView unknown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_age);
        reports = new HashMap<String, List<Report>>();

        less10 = findViewById(R.id.less10Value);
        twentyTo = findViewById(R.id.twentyToValue);
        thirtyTo = findViewById(R.id.thirtyToValue);
        fortyTo = findViewById(R.id.fortyToValue);
        fiftyTo = findViewById(R.id.fiftyToValue);
        sixtyTo = findViewById(R.id.sixtyToValue);
        seventyTo = findViewById(R.id.seventyToValue);
        eightyTo = findViewById(R.id.eightyToValue);
        ninetyTo = findViewById(R.id.ninetyToValue);
        unknown = findViewById(R.id.unknownValue);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        ref.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren().forEach(child->{
                    Report report = child.getValue(Report.class);
                    if (reports.get(report.getAge_Group()) != null){
                        reports.get(report.getAge_Group()).add(report);
                    } else {
                        List<Report> list = new ArrayList<>();
                        list.add(report);
                        reports.put(report.getAge_Group(), list);
                    }
                });
                reports.keySet().forEach(key->{
                    Log.d("key",key);
                    switch(key) {
                        case "<10": {
                            less10.setText("" + reports.get(key).size());
                            break;
                        }
                        case "20-29": {
                            twentyTo.setText("" + reports.get(key).size());
                            break;
                        }
                        case "30-39": {
                            thirtyTo.setText("" + reports.get(key).size());
                            break;
                        }
                        case "40-49": {
                            fortyTo.setText("" + reports.get(key).size());
                            break;
                        }
                        case "50-59": {
                            fiftyTo.setText("" + reports.get(key).size());
                            break;
                        }
                        case "60-69": {
                            sixtyTo.setText("" + reports.get(key).size());
                            break;
                        }
                        case "70-79": {
                            seventyTo.setText("" + reports.get(key).size());
                            break;
                        }
                        case "80-89": {
                            eightyTo.setText("" + reports.get(key).size());
                            break;
                        }
                        case "90+": {
                            ninetyTo.setText("" + reports.get(key).size());
                            break;
                        }
                        default: {
                            unknown.setText("" + reports.get(key).size());
                            break;
                        }
                    }
                });
            }
        });
    }
}