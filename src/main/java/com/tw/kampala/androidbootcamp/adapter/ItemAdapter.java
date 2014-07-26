package com.tw.kampala.androidbootcamp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.tw.kampala.androidbootcamp.R;
import com.tw.kampala.androidbootcamp.models.Item;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {
    private final int layoutId;

    public ItemAdapter(Context context, int textViewResourceId, List<Item> objects) {
        super(context, textViewResourceId, objects);
        this.layoutId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(layoutId, parent, false);
        ImageView itemImageView = (ImageView) rowView.findViewById(R.id.item_image_view);
        TextView nameTextView = (TextView) rowView.findViewById(R.id.name_text_view);
        TextView descriptionTextView = (TextView) rowView.findViewById(R.id.description_text_view);
        TextView companyTextView = (TextView) rowView.findViewById(R.id.company_text_view);
        TextView addressTextView = (TextView) rowView.findViewById(R.id.address_text_view);

        Item item = getItem(position);

        itemImageView.setImageBitmap(item.buildItemBitmap());
        nameTextView.setText(item.getName());
        descriptionTextView.setText(item.getDescription());
        companyTextView.setText(item.getCompany());
        addressTextView.setText(item.getCity());

        return rowView;
    }

}
