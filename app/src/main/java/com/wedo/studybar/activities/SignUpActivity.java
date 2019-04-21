package com.wedo.studybar.activities;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.wedo.studybar.R;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    private ArrayList selectedItems;
    static final int TAKE_AVATAR_CAMERA_REQUEST = 2;
    public static final int REQUEST_CODE_OPEN = 1;
    Uri imageUri;
    private ImageView imageViewAvatar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);

        imageViewAvatar = findViewById(R.id.sign_up_avatar);
        imageViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pickAvatarIntent = new Intent(Intent.ACTION_PICK);
                pickAvatarIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(pickAvatarIntent,""),TAKE_AVATAR_CAMERA_REQUEST);

                /*
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(SignUpActivity.this);
            */
            }
        });

        Button buttonSelectInterest = findViewById(R.id.sign_up_button_select_interest);
        selectedItems = new ArrayList();
        buttonSelectInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle(R.string.your_interest)
                        .setMultiChoiceItems(R.array.category_array, null,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if(isChecked){
                                            selectedItems.add(which);
                                        }else if(selectedItems.contains(which)){
                                            selectedItems.remove(Integer.valueOf(which));
                                        }
                                    }
                                })
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //todo:return the selected item
                            }
                        })
                        .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == TAKE_AVATAR_CAMERA_REQUEST && resultCode == RESULT_OK){
            if(data != null){
                imageUri = data.getData();
                imageViewAvatar.setImageURI(imageUri);
            }
        }

        /*
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                imageViewAvatar.setImageURI(imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_confirm_button:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_confirm,menu);
        return super.onCreateOptionsMenu(menu);    }
}
