package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.NewActivities.Adapters.CompanyAdapter;
import com.appstacks.indiannaukribazaar.NewActivities.Models.CompanyModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityCompanyBinding;

import java.util.ArrayList;

public class CompanyActivity extends AppCompatActivity {


    ActivityCompanyBinding binding;
    SharedPrefe sharedPrefe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCompanyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<CompanyModel> list = new ArrayList<>();
        sharedPrefe = new SharedPrefe(this);


        list.add(new CompanyModel(R.drawable.googleic, "Google", "Internet"));
        list.add(new CompanyModel(R.drawable.ic_apple, "Apple", "Electronic goods"));
        list.add(new CompanyModel(R.drawable.ic_amazon, "Amazon", "Internet"));
        list.add(new CompanyModel(R.drawable.googleic, "Dribble", "Design"));
        list.add(new CompanyModel(R.drawable.googleic, "Twitter", "Internet"));
        list.add(new CompanyModel(R.drawable.googleic, "Facebook", "Internet"));
        list.add(new CompanyModel(R.drawable.googleic, "Microsoft", "Internet"));
        list.add(new CompanyModel(R.drawable.googleic, "Allianz", "Financial Service"));
        list.add(new CompanyModel(R.drawable.googleic, "Adobe", "Computer software"));
        list.add(new CompanyModel(R.drawable.googleic, "AXA", "Insurance"));

        CompanyAdapter adapter = new CompanyAdapter(list, this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerViewCom.setLayoutManager(layoutManager);
        binding.recyclerViewCom.setAdapter(adapter);



        binding.searchViewCom.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

    }

}