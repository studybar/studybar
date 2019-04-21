package com.wedo.studybar.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.wedo.studybar.R;

public class AddBookActivity extends AppCompatActivity {

    private ImageView imageView;
    public static final int REQUEST_CODE_OPEN = 1;
    Uri imageUri;
    private Button buttonAdd;
    private Button buttonCancle;
    private EditText addBookTitle;
    private EditText addBookAuthor;
    private EditText addBookPublisher;
    private Spinner spinner;
    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;
    private long bookCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_book);

        imageView = (ImageView)findViewById(R.id.add_book_cover);
        spinner = (Spinner)findViewById(R.id.add_book_category);
        addBookTitle=(EditText)findViewById(R.id.add_book_name);
        addBookAuthor=(EditText)findViewById(R.id.add_book_author);
        addBookPublisher =(EditText)findViewById(R.id.add_book_press);
        buttonAdd = (Button)findViewById(R.id.button_add);
        buttonCancle = (Button)findViewById(R.id.button_cancel);

        ArrayAdapter<CharSequence> categoryAdapter;

        String bookId;
        String mistake_item = getIntent().getStringExtra("MISTAKE_ITEM");
        /**
         * 区分修改信息/添加信息
         * */
        if(mistake_item != null && mistake_item.length() != 0){
            this.setTitle(R.string.correct_book_info);
            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bookTitle = addBookTitle.getText().toString();
                    bookAuthor = addBookAuthor.getText().toString();
                    bookPublisher = addBookPublisher.getText().toString();
                    if(bookTitle.matches("")||bookAuthor.matches("")||bookPublisher.matches("")||bookCategory==0){
                        Toast.makeText(getApplicationContext(),R.string.plz_input_full_info,Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //todo: process the info
                        finish();
                    }
                }
            });
            buttonCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            switch(mistake_item) {
                case "COVER":
                    bookId = getIntent().getStringExtra("BOOK_ID");
                    Toast.makeText(this, String.valueOf(R.string.plz_correct_cover) + " " + bookId, Toast.LENGTH_SHORT).show();
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent openIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            openIntent.addCategory(Intent.CATEGORY_OPENABLE);
                            openIntent.setType("image/*");
                            startActivityForResult(openIntent, REQUEST_CODE_OPEN);
                        }
                    });
                    addBookTitle.setEnabled(false);
                    addBookAuthor.setEnabled(false);
                    addBookPublisher.setEnabled(false);
                    //todo:put the right content in other element
                    break;
                case "TITLE":
                    bookId = getIntent().getStringExtra("BOOK_ID");
                    addBookAuthor.setEnabled(false);
                    addBookPublisher.setEnabled(false);
                    //todo:put the right content in other element
                    Toast.makeText(this, String.valueOf(R.string.plz_correct_title) + " " + bookId, Toast.LENGTH_SHORT).show();
                    break;
                case "AUTHOR":
                    bookId = getIntent().getStringExtra("BOOK_ID");
                    addBookTitle.setEnabled(false);
                    addBookPublisher.setEnabled(false);
                    //todo:put the right content in other element
                    Toast.makeText(this, String.valueOf(R.string.plz_correct_author) + " " + bookId, Toast.LENGTH_SHORT).show();
                    break;
                case "PUBLISHER":
                    bookId = getIntent().getStringExtra("BOOK_ID");
                    addBookTitle.setEnabled(false);
                    addBookAuthor.setEnabled(false);
                    //todo:put the right content in other element
                    Toast.makeText(this, String.valueOf(R.string.plz_correct_publisher) + " " + bookId, Toast.LENGTH_SHORT).show();
                    break;
                case "CATEGORY":
                    bookId = getIntent().getStringExtra("BOOK_ID");
                    addBookTitle.setEnabled(false);
                    addBookAuthor.setEnabled(false);
                    addBookPublisher.setEnabled(false);
                    //todo:put the right content in other element
                    Toast.makeText(this, String.valueOf(R.string.plz_correct_category) + " " + bookId, Toast.LENGTH_SHORT).show();
                    categoryAdapter = ArrayAdapter.createFromResource(this,
                            R.array.category_array, android.R.layout.simple_spinner_item);
                    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(categoryAdapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            bookCategory = id;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    break;
            }
        }
        else{
                    /**
                     * default state is add book
                     * */
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent openIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            openIntent.addCategory(Intent.CATEGORY_OPENABLE);
                            openIntent.setType("image/*");
                            startActivityForResult(openIntent, REQUEST_CODE_OPEN);
                        }
                    });

                    categoryAdapter = ArrayAdapter.createFromResource(this,
                            R.array.category_array, android.R.layout.simple_spinner_item);
                    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(categoryAdapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            bookCategory = id;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    buttonAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bookTitle = addBookTitle.getText().toString();
                            bookAuthor = addBookAuthor.getText().toString();
                            bookPublisher = addBookPublisher.getText().toString();
                            if(bookTitle.matches("")||bookAuthor.matches("")||bookPublisher.matches("")||bookCategory==0){
                                Toast.makeText(getApplicationContext(),R.string.plz_input_full_info,Toast.LENGTH_SHORT).show();
                            }
                            else{
                                //todo: process the info
                                finish();
                            }
                        }
                    });
                    buttonCancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_OPEN && resultCode == RESULT_OK ){
            if(data != null){
                imageUri = data.getData();
                imageView.setImageURI(imageUri);
            }
        }
    }
}
