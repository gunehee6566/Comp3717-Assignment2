package ca.bcit.park_tamai;

import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ReportHolder {
    private final ArrayList<Report> reports;
    private final FirebaseDatabase database;
    private final DatabaseReference reference;
    private static ReportHolder reportHolder = null;

    private ReportHolder(){
        reports = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    public static ReportHolder getInstance() {
        if (reportHolder == null) {
            reportHolder = new ReportHolder();
        }
        return reportHolder;
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public DatabaseReference getReference() {
        return reference;
    }

    public void update(Consumer<Pair<Task<DataSnapshot>,ArrayList<Report>>> onFinish, Query query) {
        ArrayList<Report> toSave;
        if (query == null) {
            query = reference;
            toSave = reports;
        } else {
            //if the update was queried, I do not want to save to this singleton class.
            toSave = new ArrayList<>();
        }
        query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    toSave.clear();
                    task.getResult().getChildren().forEach(child->{
                        Report report = child.getValue(Report.class);
                        toSave.add(report);
                    });
                }
                Pair<Task<DataSnapshot>,ArrayList<Report>> result = new Pair<Task<DataSnapshot>, ArrayList<Report>>(task, toSave);
                onFinish.accept(result);
            }
        });
    }
}
