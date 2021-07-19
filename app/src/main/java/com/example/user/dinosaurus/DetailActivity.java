package com.example.user.dinosaurus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {

    TextView txtjudul, txtisi;
    AQuery aQuery;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent a = getIntent();
        id = a.getStringExtra("id");
        getDetailBerita();
    }

    private void getDetailBerita() {
        String url = "http://192.168.100.4/dinosaurus/detaildinosaurus.php";
        HashMap<String, String> hm = new HashMap<>();
        hm.put("id_dinosaurus", id);
        ProgressDialog progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.setMessage("Loading .   .   .");
        aQuery = new AQuery(DetailActivity.this);
        aQuery.progress(progressDialog).ajax(url, hm, String.class, new AjaxCallback<String>(){
            @Override
            public void callback(String url, String object, AjaxStatus status) {
                if (object != null) {
                    try {
                        JSONObject json = new JSONObject(object);
                        String result = json.getString("pesan");
                        String sukses = json.getString("sukses");

                        if (sukses.equalsIgnoreCase("true")){
                            JSONArray jsonArray = json.getJSONArray("Karnivor");
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String judul = jsonObject.getString("judul");
                            String isi = jsonObject.getString("isi");
                            ActionGet(judul, isi);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void ActionGet(String judul, String isi) {
        txtjudul = findViewById(R.id.txtdetailjudul);
        txtisi = findViewById(R.id.txtdetailisi);

        txtjudul.setText(judul);
        txtisi.setText(isi);
    }
}

