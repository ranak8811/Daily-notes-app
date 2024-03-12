package com.example.dailynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteDetailsActivity extends AppCompatActivity {

    private TextView titleOfNoteDetailsTv, contentOfNoteDetailsTv;
    FloatingActionButton gotoEditNoteFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);


        titleOfNoteDetailsTv = findViewById(R.id.titleOfNoteDetailsTvId);
        contentOfNoteDetailsTv = findViewById(R.id.contentOfNoteDetailsTvId);
        gotoEditNoteFAB = findViewById(R.id.gotoEditNoteDetailsFABId);

        Toolbar toolbar = findViewById(R.id.toolbarOfNoteDetailsId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent data = getIntent();
        gotoEditNoteFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditNoteActivity.class);
                intent.putExtra("title", data.getStringExtra("title"));
                intent.putExtra("content", data.getStringExtra("content"));
                intent.putExtra("noteId", data.getStringExtra("noteId"));
                view.getContext().startActivity(intent);

            }
        });

        titleOfNoteDetailsTv.setText(data.getStringExtra("title"));
        contentOfNoteDetailsTv.setText(data.getStringExtra("content"));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}