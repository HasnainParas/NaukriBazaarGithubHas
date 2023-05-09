package com.appstacks.indiannaukribazaar.ChatActivities.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appstacks.indiannaukribazaar.ChatActivities.Models.MessageModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ItemRecieverBinding;
import com.appstacks.indiannaukribazaar.databinding.ItemSendBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MessageAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<MessageModel> messagesModel;

    final int ITEM_SENT = 1;
    final int ITEM_RECIEVE = 2;
    DatabaseReference profileRef;


    public MessageAdapter(Context context, ArrayList<MessageModel> messagesModel) {
        this.context = context;
        this.messagesModel = messagesModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == ITEM_SENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_send, parent, false);
            return new SentViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_reciever, parent, false);
            return new RecieverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel message = messagesModel.get(position);
        profileRef = FirebaseDatabase.getInstance().getReference();

        Calendar calendar = null;
        if (holder.getClass() == SentViewHolder.class) {
            SentViewHolder viewHolder = (SentViewHolder) holder;

//            if (message.getMessage().equals("photo")){
//                viewHolder.binding.imageSenderAttach.setVisibility(View.VISIBLE);
//                viewHolder.binding.senderText.setVisibility(View.GONE);
//                Glide.with(context).load(message.getImageUrl())
//                        .placeholder(R.drawable.placeholderimage).into(viewHolder.binding.imageSenderAttach);
//            }
            viewHolder.binding.senderText.setText(message.getMessage());
            String timeString = message.getMsgtime();

            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            Date time = null;
            try {
                time = sdf.parse(timeString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            calendar = Calendar.getInstance();
            assert time != null;
            calendar.setTime(time);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int am_pm = calendar.get(Calendar.AM_PM);

            @SuppressLint("DefaultLocale")
            String formattedTime = String.format("%d:%02d %s", hour == 0 ? 12 : hour > 12 ? hour - 12 : hour, minute, am_pm == Calendar.AM ? "AM" : "PM");
            viewHolder.binding.messageTimeTxt.setText(formattedTime);


        } else {
            RecieverViewHolder viewHolder = (RecieverViewHolder) holder;
//            if (message.getMessage().equals("photo")){
//                viewHolder.binding.imageReceiverAttach.setVisibility(View.VISIBLE);
//                viewHolder.binding.recieverText.setVisibility(View.GONE);
//                Glide.with(context).load(message.getImageUrl())
//                        .placeholder(R.drawable.placeholderimage).into(viewHolder.binding.imageReceiverAttach);
//            }
            viewHolder.binding.recieverText.setText(message.getMessage());
            viewHolder.binding.recieverText.setOnClickListener(v ->
                    Toast.makeText(context, message.getSenderId() + "", Toast.LENGTH_SHORT).show());
            profileRef.child("UsersProfile").child(message.getSenderId())
                    .child("UserImage").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        String pic = snapshot.getValue(String.class);
                        Glide.with(context).load(pic).placeholder(R.drawable.profileplace).into(viewHolder.binding.recieverDpPic);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            String recTimeString = message.getMsgtime();


            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            Date time2 = null;
            try {
                time2 = sdf2.parse(recTimeString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar2 = Calendar.getInstance();

            calendar2.setTime(time2);
            int hour1 = calendar2.get(Calendar.HOUR_OF_DAY);
            int minute1 = calendar2.get(Calendar.MINUTE);
            int am_pm1 = calendar2.get(Calendar.AM_PM);

            @SuppressLint("DefaultLocale")
            String formattedTime2 = String.format("%d:%02d %s", hour1 == 0 ? 12 : hour1 > 12 ? hour1 - 12 : hour1, minute1, am_pm1 == Calendar.AM ? "AM" : "PM");
            viewHolder.binding.recMsgTime.setText(formattedTime2);


        }

    }

    @Override
    public int getItemViewType(int position) {
        MessageModel message = messagesModel.get(position);
        if (FirebaseAuth.getInstance().getUid().equals(message.getSenderId())) {
            return ITEM_SENT;
        } else {
            return ITEM_RECIEVE;
        }
//        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return messagesModel.size();
    }

    public static class SentViewHolder extends RecyclerView.ViewHolder {

        ItemSendBinding binding;

        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemSendBinding.bind(itemView);

        }
    }

    public static class RecieverViewHolder extends RecyclerView.ViewHolder {

        ItemRecieverBinding binding;

        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemRecieverBinding.bind(itemView);
        }
    }


}
