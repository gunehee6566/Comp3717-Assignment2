package ca.bcit.park_tamai.date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.bcit.park_tamai.R;
import ca.bcit.park_tamai.Report;

public class DateRecyclerAdapter extends RecyclerView.Adapter<DateRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Report> reports;

    public DateRecyclerAdapter(Context context, ArrayList<Report> reports) {
        this.context = context;
        this.reports = reports;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    public void updateData(ArrayList<Report> newReport) {
        this.reports.clear();
        this.reports.addAll(newReport);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DateRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(context).inflate(R.layout.report_list, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull DateRecyclerAdapter.ViewHolder holder, int position) {
        final CardView cardView = holder.cardView;
        Report report = reports.get(position);

        TextView age_group = cardView.findViewById(R.id.age_group);
        TextView classification = cardView.findViewById(R.id.classification);
        TextView ha = cardView.findViewById(R.id.ha);
        TextView reported_date = cardView.findViewById(R.id.reported_date);
        TextView sex = cardView.findViewById(R.id.sex);

        age_group.setText(report.getAge_Group());
        classification.setText(report.getClassification_Reported());
        ha.setText(report.getHA());
        reported_date.setText(report.getReported_Date());
        sex.setText(report.getSex());

    }

    @Override
    public int getItemCount() {
        return reports.size();
    }
}
