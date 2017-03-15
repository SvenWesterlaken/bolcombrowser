package com.example.svenwesterlaken.bolcombrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BolApiConnector.ProductListener {
    private ArrayList<Product> products;
    private ProductAdapter pa;
    private ArrayList<String> searches = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        products = new ArrayList<>();

        final FloatingSearchView mSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        ListView productListView = (ListView) findViewById(R.id.productListView);
        pa = new ProductAdapter(this, getLayoutInflater(), products);

        productListView.setAdapter(pa);


        productListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
                intent.putExtra("Product", products.get(position));
                startActivity(intent);
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            //Not used
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                getProducts(searchSuggestion.getBody());
                pa.notifyDataSetChanged();
            }

            @Override
            public void onSearchAction(String currentQuery) {
                getProducts(currentQuery);
                searches.add(currentQuery);
                pa.notifyDataSetChanged();
            }
        });
    }

    public void getProducts(String query) {
        products.clear();
        BolApiConnector task = new BolApiConnector(this);
        try {
            query = java.net.URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e){
            Log.e("QUERY", e.getLocalizedMessage());
        }
        String[] urls = new String[] {"https://api.bol.com/catalog/v4/search/?apikey=25C4742A92BF468EB2BD888FC8FBFF40&format=json&q=" + query};
        task.execute(urls);
    }


    @Override
    public void onProductAvailable(Product p) {
        products.add(p);
        pa.notifyDataSetChanged();

    }

}
