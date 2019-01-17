package com.kier.companytest.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.kier.companytest.R;


public class PreviewActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        imageView = findViewById(R.id.image);
        byte[] jpeg = ResultHolder.getImage();
        if (jpeg != null) {
            imageView.setVisibility(View.VISIBLE);
            Bitmap bitmap = BitmapFactory.decodeByteArray(jpeg, 0, jpeg.length);
            if (bitmap == null) {
                finish();
                return;
            }
            imageView.setImageBitmap(bitmap);
        }

        ((Button)findViewById(R.id.btn)).setText(ResultHolder.getText());
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void back(View view) {
        finish();
    }
}