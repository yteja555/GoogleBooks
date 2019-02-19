package com.example.googlebooks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Description extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        TextView title =(TextView)findViewById(R.id.title);
        TextView author =(TextView)findViewById(R.id.author);
        TextView rating =(TextView)findViewById(R.id.rating);
        TextView description =(TextView)findViewById(R.id.description);
        Intent intent=getIntent();
        String[] data =intent.getStringArrayExtra("message");
        title.setText(data[0]);
        author.setText(data[1]);
        rating.setText(data[2]);
        description.setText(data[3]);

    }
}
