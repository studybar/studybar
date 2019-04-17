package com.wedo.studybar.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.wedo.studybar.R;

public class CorrectDialogActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correction_dialog);
    }

    public void onRadioButtonClicked(View v){
        RadioButton radioButtonCover = findViewById(R.id.correction_cover);
        RadioButton radioButtonTitle = findViewById(R.id.correction_title);
        RadioButton radioButtonAuthor = findViewById(R.id.correction_author);
        RadioButton radioButtonPublisher = findViewById(R.id.correction_publisher);
        RadioButton radioButtonCategory = findViewById(R.id.correction_category);

        boolean checked = ((RadioButton)v).isChecked();

        switch (v.getId()){
            case R.id.correction_cover:
                if(checked)
                    break;
            case R.id.correction_title:
                if(checked)
                    break;
            case R.id.correction_author:
                if(checked)
                    break;
            case R.id.correction_publisher:
                if(checked)
                    break;
            case R.id.correction_category:
                if(checked)
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.mistakes_categories)
                    .setSingleChoiceItems(R.array.mistake_category_array, 1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton(R.string.Cancel, null)
                    .show();
                    break;
        }
    }
}
