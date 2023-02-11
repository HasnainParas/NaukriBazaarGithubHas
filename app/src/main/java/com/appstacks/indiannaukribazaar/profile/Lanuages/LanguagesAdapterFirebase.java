package com.appstacks.indiannaukribazaar.profile.Lanuages;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import com.appstacks.indiannaukribazaar.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

public class LanguagesAdapterFirebase extends RecyclerView.Adapter<LanguagesAdapterFirebase.viewHolder> {
    private ArrayList<SelectedLanguages> list;
    private Context context;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String userId;

    public LanguagesAdapterFirebase(ArrayList<SelectedLanguages> list, Context context) {
        this.list = list;
        this.context = context;
        databaseReference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_language_addedlayout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        SelectedLanguages data = list.get(position);
        holder.oralLevel.setText(data.getName());
        holder.oralLevel.setText(data.getOralLevel());
        holder.writtenLevel.setText(data.getWrittenLevel());
        holder.languageName.setText(data.getName());
        if (data.isFirstLanguage())
            holder.languageName.setText(data.getName() + " ( First language )");
        Picasso.get().load(data.getFlag()).into(holder.flag);

        
        holder.btnDelete.setOnClickListener(view -> {
            deleteLanguage(data.getUUID(),position);
        });

    }

    private void deleteLanguage(String uID,int position) {
        storageReference.child(context.getString(R.string.user_profile)).child("Languages").child(userId).child("LanguageFlag" + uID)
                .delete().addOnSuccessListener(unused -> {
                    databaseReference.child(context.getString(R.string.user_profile)).child(userId).child("Languages").child(uID).removeValue().addOnSuccessListener(unused1 -> {
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                        list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,list.size());

                    }).addOnFailureListener(e -> Toast.makeText(context, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
          ImageView flag;
          TextView languageName,writtenLevel,oralLevel;
          ImageView btnDelete;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            flag=itemView.findViewById(R.id.addedLanguageFlag);
            languageName=itemView.findViewById(R.id.addedLanguageName);
            writtenLevel=itemView.findViewById(R.id.addedlanguageWrittenlevel);
            oralLevel=itemView.findViewById(R.id.addedlangaugeOralLevel);
            btnDelete=itemView.findViewById(R.id.addedLanguageDelete);
        }
    }
}
