package com.example.e_commerceapplication.products;

import static com.example.e_commerceapplication.general.Constants.SHOW_ALL;
import static com.example.e_commerceapplication.general.Constants.USERS;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.e_commerceapplication.adapter.ShowAllAdapter;
import com.example.e_commerceapplication.databinding.ActivitySearchBinding;
import com.example.e_commerceapplication.general.data.DataLayer;
import com.example.e_commerceapplication.models.product.ShowAllModel;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding binding;
    DataLayer dataLayer;
    ShowAllAdapter adapter;
    List<ShowAllModel> list;
    ActivityResultLauncher<ScanOptions> barScanner = registerForActivityResult(new ScanContract(), res -> {
        if (res.getContents() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
            builder.setTitle("Result");
            builder.setMessage(res.getContents());
            builder.setPositiveButton("OK", (dialog, which) -> {
                dialog.dismiss();
                binding.searchBar.setText(res.getContents());
                searchData(binding.searchBar.getText().toString());
            }).show();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataLayer = new DataLayer(USERS);

        binding.searchRec.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();

        adapter = new ShowAllAdapter(this, list);

        binding.searchRec.setAdapter(adapter);

        binding.searchBtn.setOnClickListener(v -> searchData(binding.searchBar.getText().toString()));

        binding.voiceSearch.setOnClickListener(v -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Looks For A Product?");
            startActivityForResult(intent, 1);
        });

        binding.barcodeSearch.setOnClickListener(v -> {
            ScanOptions options = new ScanOptions();
            options.setPrompt("Volume up to flash on");
            options.setBeepEnabled(true);
            options.setOrientationLocked(true);
            options.setCaptureActivity(CaptureAct.class);
            barScanner.launch(options);
        });


        binding.exit.setOnClickListener(v -> finish());
    }

    @SuppressLint("NotifyDataSetChanged")
    private void searchData(String query) {
        dataLayer.getFireStore().collection(SHOW_ALL)
                .whereEqualTo("name", query)
                .get()
                .addOnCompleteListener(task -> {
                    list.clear();
                    task.getResult().getDocuments().forEach(documentSnapshot -> {
                        ShowAllModel showAllModel = documentSnapshot.toObject(ShowAllModel.class);
                        list.add(showAllModel);
                        adapter.notifyDataSetChanged();
                    });
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {

        if (requestCode == 1 && resultCode == RESULT_OK) {
            assert intent != null;
            ArrayList<String> matches = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            assert matches != null;
            binding.searchBar.setText(matches.get(0));
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }
}