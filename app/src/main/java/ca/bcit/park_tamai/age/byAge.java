package ca.bcit.park_tamai.age;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import ca.bcit.park_tamai.R;
import ca.bcit.park_tamai.Report;
import ca.bcit.park_tamai.ReportHolder;

public class byAge extends AppCompatActivity {
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
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_age);

        setTitle("# of Cases by age group");

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
        progressBar = findViewById(R.id.ageProgress);

        updateList();
    }

    /**
     * update using singleton.
     * uses saved list if list if previously retried.
     */
    void updateList() {
        progressBar.setVisibility(View.VISIBLE);
        ReportHolder reportHolder = ReportHolder.getInstance();
        if (reportHolder.getReports().size() == 0) {
            reportHolder.update(new Consumer<Pair<Task<DataSnapshot>, ArrayList<Report>>>() {
                @Override
                public void accept(Pair<Task<DataSnapshot>, ArrayList<Report>> result) {
                    if (result.first.isSuccessful()) {
                        HashMap<String, Integer> hashMap = listToMap(result.second);
                        for(String key :hashMap.keySet()) {
                            writeLabel(key, hashMap.get(key));
                        }
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Retrieved: " + result.second.size(), Toast.LENGTH_SHORT).show();
                    } else {
                        finish();
                        Toast.makeText(getApplicationContext(), result.first.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, null);
        } else {
            HashMap<String, Integer> hashMap = listToMap(reportHolder.getReports());
            for(String key :hashMap.keySet()) {
                writeLabel(key, hashMap.get(key));
            }
            progressBar.setVisibility(View.GONE);
        }
    }

    HashMap<String, Integer> listToMap(ArrayList<Report> list) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        for(Report report : list) {
            String ageGroup = report.getAge_Group();
            Integer original = hashMap.get(ageGroup);
            hashMap.put(ageGroup, (original == null)?1:original+1);
        }
        return hashMap;
    }

    void writeLabel(String key, Integer value) {
        switch(key) {
            case "<10": {
                less10.setText(value.toString());
                break;
            }
            case "20-29": {
                twentyTo.setText(value.toString());
                break;
            }
            case "30-39": {
                thirtyTo.setText(value.toString());
                break;
            }
            case "40-49": {
                fortyTo.setText(value.toString());
                break;
            }
            case "50-59": {
                fiftyTo.setText(value.toString());
                break;
            }
            case "60-69": {
                sixtyTo.setText(value.toString());
                break;
            }
            case "70-79": {
                seventyTo.setText(value.toString());
                break;
            }
            case "80-89": {
                eightyTo.setText(value.toString());
                break;
            }
            case "90+": {
                ninetyTo.setText(value.toString());
                break;
            }
            default: {
                unknown.setText(value.toString());
                break;
            }
        }
    }
}