package com.appstacks.indiannaukribazaar.profile.paymentmethods;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.appstacks.indiannaukribazaar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentHolder> {
    private ArrayList<PaymentMethods> list;
    private Context context;
    private DatabaseReference databaseReference;
    private String userId;
    private String uniqueId;
    private PaymentMethods paymentMethods;

    public PaymentAdapter(ArrayList<PaymentMethods> list, Context context, String userId, String uniqueId) {
        this.list = list;
        this.context = context;
        databaseReference = FirebaseDatabase.getInstance().getReference(context.getString(R.string.user_profile));
        this.userId = userId;
        this.uniqueId = uniqueId;
    }

    @NonNull
    @Override
    public PaymentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(context).inflate(R.layout.item_bank_info_layout, parent, false);
        return new PaymentHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PaymentHolder holder, int position) {


        paymentMethods = list.get(position);


        holder.title.setText(paymentMethods.getPrimaryBankName() + " - " + getAccountNumber(paymentMethods.getAccountNumber()) + "xxx");
        holder.subTitle.setText(getAccountNumber(paymentMethods.getUpiID()) + " - " + paymentMethods.getAccountType());


        holder.btnOptions.setOnClickListener(this::showPopup);

    }


    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(context, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_payment, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btn_delete:
                        deleteData();
                        break;
                    case R.id.btn_edit:

                        openActivity();
                        break;
                }
                return true;
            }
        });


    }

    private void openActivity() {

        Intent intent = new Intent(context, BankingInformationActivity.class);
        Gson gson = new Gson();

        intent.putExtra("data", gson.toJson(paymentMethods));
        context.startActivity(intent);

    }

    private void deleteData() {

        databaseReference.child(userId).child("PaymentMethods").child(uniqueId).removeValue().addOnCompleteListener(task -> {
            if (task.isComplete() && task.isSuccessful()) {
                notifyDataSetChanged();
                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(context, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PaymentHolder extends RecyclerView.ViewHolder {
        private ImageView bankImage;
        private TextView title;
        private TextView subTitle;
        private ImageView btnOptions;

        public PaymentHolder(@NonNull View itemView) {
            super(itemView);

            bankImage = itemView.findViewById(R.id.imgBank);
            title = itemView.findViewById(R.id.txtBankTitleInfo);
            subTitle = itemView.findViewById(R.id.txtSubtitleBankInfo);
            btnOptions = itemView.findViewById(R.id.btnBankOptions);
        }
    }

    private StringBuilder getAccountNumber(String accountNumber) {
        StringBuilder accountNoReformatted = new StringBuilder();
        for (int i = 0; i < accountNumber.length() - 7; i++) {

            // accountNoReformatted +=
            accountNoReformatted.append(accountNumber.charAt(i));
        }

        return accountNoReformatted;
    }

}
