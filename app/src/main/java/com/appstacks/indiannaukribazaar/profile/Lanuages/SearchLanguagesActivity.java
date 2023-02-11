package com.appstacks.indiannaukribazaar.profile.Lanuages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivitySearchLanguagesBinding;

import java.util.ArrayList;

public class SearchLanguagesActivity extends AppCompatActivity {
    private ActivitySearchLanguagesBinding binding;
    private ArrayList<SelectedLanguages> list;
    private LanguageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchLanguagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        list = new ArrayList<>();
        list.add(new SelectedLanguages("Arabic", R.drawable.saudi_arabia));
        list.add(new SelectedLanguages("Indonesian", R.drawable.indonesia));
        list.add(new SelectedLanguages("Malaysian", R.drawable.malaysia));
        list.add(new SelectedLanguages("English", R.drawable.united_kingdom));
        list.add(new SelectedLanguages("French", R.drawable.france));
        list.add(new SelectedLanguages("German", R.drawable.german));
        list.add(new SelectedLanguages("Hindi", R.drawable.india_flag));
        list.add(new SelectedLanguages("Urdu", R.drawable.pak_flag));
        list.add(new SelectedLanguages("Punjabi", R.drawable.india_flag));
        list.add(new SelectedLanguages("Marathi", R.drawable.india_flag));
        list.add(new SelectedLanguages("Gujrati", R.drawable.india_flag));
        list.add(new SelectedLanguages("Tamil", R.drawable.india_flag));
        list.add(new SelectedLanguages("Telugu", R.drawable.india_flag));
        list.add(new SelectedLanguages("Italian", R.drawable.italy));
        list.add(new SelectedLanguages("Japanese", R.drawable.japan));
        list.add(new SelectedLanguages("Korean", R.drawable.korea));

        adapter = new LanguageAdapter(list, this);
        binding.listViewAddlanguage1.setLayoutManager(new LinearLayoutManager(this));
        binding.listViewAddlanguage1.setAdapter(adapter);


        binding.btnBackAddLanguage1.setOnClickListener(view -> {
            finish();
        });
        binding.searchViewAddLanguage1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });


    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<SelectedLanguages> filteredlist = new ArrayList<SelectedLanguages>();

        // running a for loop to compare elements.
        for (SelectedLanguages item : list) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.

            adapter.filterList(filteredlist);
        }
    }
}