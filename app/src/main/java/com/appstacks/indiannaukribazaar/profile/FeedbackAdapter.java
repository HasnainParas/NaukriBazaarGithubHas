package com.appstacks.indiannaukribazaar.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appstacks.indiannaukribazaar.R;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyHolder> {
    private Context context;
    private ArrayList<Feedback> list;

    public FeedbackAdapter(Context context, ArrayList<Feedback> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_feedbacks, parent, false);

        return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        Feedback feedback = list.get(position);
        holder.ratingBar.setRating(Integer.parseInt(feedback.getRating()));
        holder.txtRating.setText(feedback.getRating());
        holder.txtDate.setText(feedback.getDate());
        holder.txtJobTitle.setText(feedback.getJobtitle());
        holder.txtFeedback.setText(feedback.getFeedback());
        holder.txtTotalPrice.setText(feedback.getTotalprice());
        holder.txtPricePerHour.setText(feedback.getPerhourprice());
        holder.txtTotalHours.setText(feedback.getTotalhours());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private RatingBar ratingBar;
        private TextView txtRating;
        private TextView txtDate;
        private TextView txtJobTitle;
        private TextView txtFeedback;
        private TextView txtTotalPrice;
        private TextView txtPricePerHour;
        private TextView txtTotalHours;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            ratingBar = itemView.findViewById(R.id.ratingBar);
            txtRating = itemView.findViewById(R.id.txtRating);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtJobTitle = itemView.findViewById(R.id.txtTitleJob);
            txtFeedback = itemView.findViewById(R.id.txtFeedback);
            txtTotalPrice = itemView.findViewById(R.id.txtPriceJob);
            txtPricePerHour = itemView.findViewById(R.id.txtPerHour);
            txtTotalHours = itemView.findViewById(R.id.txtTotalHours);
        }
    }
}
