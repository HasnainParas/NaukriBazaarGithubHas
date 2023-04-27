package com.appstacks.indiannaukribazaar.ChatActivities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appstacks.indiannaukribazaar.ChatActivities.Models.MessageModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ItemRecieverBinding;
import com.appstacks.indiannaukribazaar.databinding.ItemSendBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<MessageModel> messagesModel;

    final int ITEM_SENT = 1;
    final  int ITEM_RECIEVE = 2;


    public MessageAdapter(Context context, ArrayList<MessageModel> messagesModel) {
        this.context = context;
        this.messagesModel = messagesModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == ITEM_SENT){
            View view = LayoutInflater.from(context).inflate(R.layout.item_send,parent,false);
            return new SentViewHolder(view);
        }else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.item_reciever,parent,false);
            return new RecieverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel message = messagesModel.get(position);

        if (holder.getClass() == SentViewHolder.class){
            SentViewHolder viewHolder = (SentViewHolder)holder;

//            if (message.getMessage().equals("photo")){
//                viewHolder.binding.imageSenderAttach.setVisibility(View.VISIBLE);
//                viewHolder.binding.senderText.setVisibility(View.GONE);
//                Glide.with(context).load(message.getImageUrl())
//                        .placeholder(R.drawable.placeholderimage).into(viewHolder.binding.imageSenderAttach);
//            }

            viewHolder.binding.senderText.setText(message.getMessage());
            

        }else {
            RecieverViewHolder viewHolder = (RecieverViewHolder) holder;
//            if (message.getMessage().equals("photo")){
//                viewHolder.binding.imageReceiverAttach.setVisibility(View.VISIBLE);
//                viewHolder.binding.recieverText.setVisibility(View.GONE);
//                Glide.with(context).load(message.getImageUrl())
//                        .placeholder(R.drawable.placeholderimage).into(viewHolder.binding.imageReceiverAttach);
//            }
            viewHolder.binding.recieverText.setText(message.getMessage());
        }

    }

    @Override
    public int getItemViewType(int position) {
        MessageModel message = messagesModel.get(position);
        if (FirebaseAuth.getInstance().getUid().equals(message.getSenderId())){
            return ITEM_SENT;
        }else
        {
            return ITEM_RECIEVE;
        }
//        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return messagesModel.size();
    }

    public class SentViewHolder extends RecyclerView.ViewHolder {

        ItemSendBinding binding;

        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemSendBinding.bind(itemView);

        }
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder {

        ItemRecieverBinding binding;

        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemRecieverBinding.bind(itemView);
        }
    }


}
