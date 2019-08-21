package com.hosnydev.recyclerviewjson;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ViewImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        // findView
        final ProgressBar progressView = findViewById(R.id.progressView);
        final ImageView imageView = findViewById(R.id.imageView);
        TextView name = findViewById(R.id.nameView);
        TextView like = findViewById(R.id.likeView);

        // get String intent
        Intent intent = getIntent();
        String nameIntent = intent.getStringExtra("name");
        String imageIntent = intent.getStringExtra("image");
        String likeIntent = "Like: ";
        likeIntent += intent.getStringExtra("like");

        // set string intent
        name.setText(nameIntent);
        like.setText(likeIntent);
        Picasso.with(this)
                .load(imageIntent)
                .fit()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                        progressView.setVisibility(View.GONE);
                        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));

                    }
                });

    }
}
