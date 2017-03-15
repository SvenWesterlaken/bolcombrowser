package com.example.svenwesterlaken.bolcombrowser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sven Westerlaken on 14-3-2017.
 */

public class ProductAdapter extends BaseAdapter{
    private ArrayList<Product> products;
    private LayoutInflater layoutInflater;
    private Context context;

    public ProductAdapter(Context context, LayoutInflater inflater, ArrayList<Product> products) {
        this.products = products;
        this.layoutInflater = inflater;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (products != null) {
            return products.size();
        }

        return 0;
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.search_list_item, null);

            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.itemThumbnail);
            viewHolder.title = (TextView) convertView.findViewById(R.id.itemTitel);
            viewHolder.specsTag = (TextView) convertView.findViewById(R.id.itemSpec);
            viewHolder.summary = (TextView) convertView.findViewById(R.id.itemSummary);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Product product = products.get(position);

        viewHolder.title.setText(product.getTitle());
        viewHolder.specsTag.setText(product.getSpecsTag());
        viewHolder.summary.setText(product.getSummary());
        Picasso.with(context).load(product.getSmallImageUrl()).into(viewHolder.image);

        return convertView;
    }

    private static class ViewHolder {
        ImageView image;
        TextView title, specsTag, summary;
    }
}
