package com.example.svenwesterlaken.bolcombrowser;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class ProductDetailActivity extends AppCompatActivity {
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        this.product = (Product) extras.getSerializable("Product");

        TextView title = (TextView) findViewById(R.id.productTitle);
        TextView specs = (TextView) findViewById(R.id.productSpecs);
        TextView summary = (TextView) findViewById(R.id.productSummary);
        TextView description = (TextView) findViewById(R.id.productDescription);
        ImageView image = (ImageView) findViewById(R.id.productImage);

        title.setText(product.getTitle());
        specs.setText(product.getSpecsTag());
        summary.setText(product.getSummary());

        //Replace html tags with compatible ones
        String longDiscription = product.getLongDescription().replace("bold", "b").replace("br /", "br").replace("h3", "h5");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            description.setText(Html.fromHtml(longDiscription, Html.FROM_HTML_MODE_LEGACY));
        } else {
            description.setText(Html.fromHtml(longDiscription));
        }

        Picasso.with(getApplicationContext()).load(product.getLargeImageUrl()).into(image);



    }

}
