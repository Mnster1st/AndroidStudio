package com.vincent202102264.tugasakhir;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SteamID extends AppCompatActivity {
    EditText idsteam,nickname,urqoute,besthero;
    String isiid,isiname,isiurqoute,isihero;
    Button simpan,tampil,edit,hapus;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steam_information);

        idsteam = findViewById(R.id.id);
        nickname = findViewById(R.id.nick);
        urqoute = findViewById(R.id.qoute);
        besthero = findViewById(R.id.hero);
        simpan = findViewById(R.id.btntambah);
        tampil = findViewById(R.id.btntampil);
        hapus = findViewById(R.id.btnhapus);
        edit = findViewById(R.id.btnedit);
        db = new DBHelper(this);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isiid = idsteam.getText().toString();
                String isiname = nickname.getText().toString();
                String isiurqoute = urqoute.getText().toString();
                String isihero = besthero.getText().toString();

                if (TextUtils.isEmpty(isiid) || TextUtils.isEmpty(isiname) || TextUtils.isEmpty(isiurqoute)
                        || TextUtils.isEmpty(isihero))
                    Toast.makeText(SteamID.this,"Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                else
                {
                    Boolean check = db.checkID(isiid);
                    if (check == false){
                        Boolean insert = db.insertSteamID(isiid,isiname,isiurqoute,isihero);
                        if (insert == true){
                            Toast.makeText(SteamID.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(SteamID.this,"Data gagal disimpan", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(SteamID.this,"Data Mahasiswa Sudah Ada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.tampilSTEAMINFORMATION();
                if(res.getCount()==0){
                    Toast.makeText(SteamID.this,"Tidak ada Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("ID : "+res.getString(0)+"\n");
                    buffer.append("NICKNAME : "+res.getString(1)+"\n");
                    buffer.append("QOUTE : "+res.getString(2)+"\n");
                    buffer.append("HERO : "+res.getString(3)+"\n");

                }
                AlertDialog.Builder builder =  new AlertDialog.Builder(SteamID.this);
                builder.setCancelable(true);
                builder.setTitle("ID INFORMATION");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String si = idsteam.getText().toString();
                Boolean cekHapusData = db.hapusDataSteam(si);
                if (cekHapusData == true)
                    Toast.makeText(SteamID.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(SteamID.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isiid = idsteam.getText().toString();
                String isiname = nickname.getText().toString();
                String isiurqoute = urqoute.getText().toString();
                String isihero = besthero.getText().toString();

                if (TextUtils.isEmpty(isiid) || TextUtils.isEmpty(isiname) || TextUtils.isEmpty(isiurqoute)
                        || TextUtils.isEmpty(isihero))
                    Toast.makeText(SteamID.this,"Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                else {
                    Boolean edit = db.editSteamID(isiid,isiname,isiurqoute,isihero);
                    if (edit == true){
                        Toast.makeText(SteamID.this, "Data Berhasil di Edit", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SteamID.this,"Data Gagal Diedit", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
