package ca.bcit.park_tamai.ha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;

import ca.bcit.park_tamai.R;
import ca.bcit.park_tamai.Report;
import ca.bcit.park_tamai.ReportHolder;

public class byHA extends AppCompatActivity {

    private ProgressBar progressBar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_ha);

        setTitle("# of Cases by health authority");

        progressBar = findViewById(R.id.haProgressbar);
        listView = findViewById(R.id.list_ha);

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
                        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(
                                getApplicationContext(),
                                R.layout.list_item,
                                listToList(result.second));
                        listView.setAdapter(listAdapter);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Retrieved: " + result.second.size(), Toast.LENGTH_SHORT).show();
                    } else {
                        finish();
                        Toast.makeText(getApplicationContext(), result.first.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, null);
        } else {
            ArrayAdapter<String> listAdapter = new ArrayAdapter<>(
                    getApplicationContext(),
                    R.layout.list_item,
                    listToList(reportHolder.getReports()));
            listView.setAdapter(listAdapter);
            progressBar.setVisibility(View.GONE);
        }
    }

    /**
     * Converts list to a list that is organized for listView presentation.
     */
    String[] listToList(ArrayList<Report> list) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        for(Report report : list) {
            String haGroup = report.getHA();
            Integer original = hashMap.get(haGroup);
            hashMap.put(haGroup, (original == null)?1:original+1);
        }
        ArrayList<String> result = new ArrayList<>();
        hashMap.keySet().forEach(key->{
            result.add(key + ": " + hashMap.get(key));
        });
        return result.toArray(new String[result.size()]);
    }
}