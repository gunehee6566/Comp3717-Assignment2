package ca.bcit.park_tamai.date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
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

import ca.bcit.park_tamai.R;
import ca.bcit.park_tamai.Report;

public class byDate extends AppCompatActivity {

    private Button btn_find;
    private TextView year_field;
    private TextView month_field;
    private RecyclerView recyclerView;
    private DateRecyclerAdapter recyclerAdapter;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_date);

        setTitle("Cases by month & year");

        btn_find = findViewById(R.id.btn_find);
        year_field = findViewById(R.id.year_field);
        month_field = findViewById(R.id.month_field);
        progressBar = findViewById(R.id.dateProgress);

        recyclerView = findViewById(R.id.dateRecycler);
        recyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerAdapter = new DateRecyclerAdapter(byDate.this, new ArrayList<Report>());
        recyclerView.setAdapter(recyclerAdapter);

        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                ArrayList<Report> reports = new ArrayList<>();
                String year = year_field.getText().toString();
                String month = month_field.getText().toString();
                if(year.isEmpty() || month.isEmpty()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Please enter the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                String nextMonth = (month.equals("12"))?"1":"" + (Integer.parseInt(month) + 1);
                String nextYear = (nextMonth.equals("1"))?""+(Integer.parseInt(year)+1):year;
                month = (month.length() == 1)?"0"+month:month;
                nextMonth = (nextMonth.length() == 1)?"0"+nextMonth:nextMonth;
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference();
                ref.orderByChild("Reported_Date").startAt(year+"-"+month).endBefore(nextYear+"-"+nextMonth).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        dataSnapshot.getChildren().forEach(child->{
                            Report report = child.getValue(Report.class);
                            reports.add(report);
                        });
                        progressBar.setVisibility(View.INVISIBLE);
                        recyclerAdapter.updateData(reports);
                        Toast.makeText(getApplicationContext(), "Retrieved: " + reports.size(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}