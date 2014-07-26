package com.tw.kampala.androidbootcamp.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.tw.kampala.androidbootcamp.R;
import com.tw.kampala.androidbootcamp.models.Item;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class DetailActivity extends RoboActivity {
    @InjectView(R.id.edit_name)
    EditText editTextName;

    @InjectView(R.id.edit_city)
    EditText editTextCity;

    @InjectView(R.id.edit_company)
    EditText editTextCompany;

    @InjectView(R.id.edit_description)
    EditText editTextDescription;

    @InjectView(R.id.edit_email)
    EditText editTextEmail;

    @InjectView(R.id.edit_phone)
    EditText editTextPhone;

    @InjectView(R.id.item_image_view)
    ImageView itemImageView;


    @InjectView(R.id.save_button)
    Button saveButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle = getIntent().getExtras();
        Item item = (Item) bundle.getSerializable(MainActivity.ITEM);
        if (item != null) {
            setTitle(item.getName());
            editTextCity.setText(item.getCity());
            editTextName.setText(item.getName());
            editTextCompany.setText(item.getCompany());
            editTextDescription.setText(item.getDescription());
            editTextEmail.setText(item.getEmail());
            editTextPhone.setText(item.getPhone());
            itemImageView.setImageBitmap(item.buildItemBitmap());
        }
    }
}
