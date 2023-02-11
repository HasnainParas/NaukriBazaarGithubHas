package com.appstacks.indiannaukribazaar.profile.Education;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityInstituteNameBinding;
import com.appstacks.indiannaukribazaar.profile.ProfileUtils;

import java.util.ArrayList;

public class InstituteNameActivity extends AppCompatActivity {
    private ActivityInstituteNameBinding binding;
    private ProfileUtils profileUtils;
    private static ArrayList<String> list = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInstituteNameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        profileUtils = new ProfileUtils(this);

        list.add("Karakoram Internation University");
        list.add("Oxford University");
        list.add("University of Punjab");
        list.add("National University of Science and Technology");
        list.add("University of Engineering and Technology ");
        list.add("National University of Modern Languages");
        list.add("Bahria University");
        list.add("Islamic University");
        list.add("University of Peshawar");
        list.add("Institute of Business and Accounting");
        searchView(list);

        binding.btnBackInstituteName.setOnClickListener(view -> {
            finish();
        });


        binding.listViewSearchData.setOnItemClickListener((adapterView, view15, i, l) -> {
            String instituteName = list.get(i).toString();

            profileUtils.saveInstituteName(instituteName);
            finish();

        });


    }

    private void searchView(ArrayList<String> list) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        binding.listViewSearchData.setAdapter(adapter);
        binding.searchViewSearchData.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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