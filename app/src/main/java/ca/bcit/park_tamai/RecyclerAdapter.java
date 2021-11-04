package ca.bcit.park_tamai;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Report> reports;

    public RecyclerAdapter(Context context, ArrayList<Report> reports) {
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
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(context).inflate(R.layout.report_list, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
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
