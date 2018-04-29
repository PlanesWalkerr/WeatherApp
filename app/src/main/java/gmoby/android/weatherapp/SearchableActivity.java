package gmoby.android.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import gmoby.android.weatherapp.Model.City;
import gmoby.android.weatherapp.Model.Database.DBHelper;
import gmoby.android.weatherapp.Utils.ConfigManager;

public class SearchableActivity extends AppCompatActivity {

    ListView listView;
    SearchView searchView;
    DBHelper dbHelper;
    List<City> cityList = new ArrayList<City>();
    List<String> list = new ArrayList<String>();
    ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);


        dbHelper = new DBHelper(getApplicationContext());

        searchView = findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 1) {
                    Log.d("hello", "change");
                    searchCity(newText);
                }
                return true;
            }
        });

        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String string = listAdapter.getItem(position);
                //searchView.setQuery(string, false);
                String city = string.substring(0, string.indexOf(','));
                Log.d("City", city);
                for (City c : cityList) {
                    if (c.getName().equals(city)) {
                        Log.d("City", c.toString());
                        configCity(c.getName(), c.getId());
                        navigateToMain();
                        break;
                    }
                }
            }
        });


    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.expandActionView();
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                Log.d("home","collapse");
                return true;
            }
        });

        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 1) {
                    Log.d("hello", "change");
                    searchCity(newText);
                }
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
*/

    private void searchCity(String query) {
        cityList = dbHelper.searchCity(query);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listAdapter.clear();
                for (City c : cityList) {
                    Log.d("aaa", c.toString());
                    listAdapter.add(c.getName() + ", " + c.getCountryCode());
                }
                listAdapter.notifyDataSetChanged();
            }
        });

    }

    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void configCity(String name, long id) {
        ConfigManager configManager = new ConfigManager(getApplicationContext());
        configManager.setCityId(id);
        configManager.setCityName(name);
    }
}
