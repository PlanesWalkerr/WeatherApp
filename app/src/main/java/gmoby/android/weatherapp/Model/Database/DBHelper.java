package gmoby.android.weatherapp.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import gmoby.android.weatherapp.Model.City;


public class DBHelper extends SQLiteOpenHelper {

    private final String TAG = "weatherTag";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CitiesDB";
    public static final String TABLE_CITIES = "Cities";

    public static final String CITY_ID = "_id";
    public static final String CITY_LNG = "lng";
    public static final String CITY_LAT = "lat";
    public static final String CITY_NAME = "name";
    public static final String CITY_COUNTRY_CODE = "country_code";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CITIES_TABLE_CREATE = "create table " + TABLE_CITIES + " (" + CITY_ID
                + " integer primary key," + CITY_LNG + " integer," + CITY_LAT + " integer,"
                + CITY_NAME + " text," + CITY_COUNTRY_CODE + " text)";

        sqLiteDatabase.execSQL(CITIES_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_CITIES);
        onCreate(sqLiteDatabase);
    }

    public boolean isEmpty() {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_CITIES, null);
        boolean res = true;
        if (cursor.moveToFirst()) {
            res = false;
        }

        cursor.close();
        return res;
    }

    public void dropTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CITIES, null, null);
    }

    public void writeCities(List<City> cities) {
        this.getWritableDatabase().beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            for (City c : cities) {
                cv.put(DBHelper.CITY_ID, c.getId());
                cv.put(DBHelper.CITY_LAT, c.getLat());
                cv.put(DBHelper.CITY_LNG, c.getLng());
                cv.put(DBHelper.CITY_NAME, c.getName());
                cv.put(DBHelper.CITY_COUNTRY_CODE, c.getCountryCode());
                this.getWritableDatabase().insert(DBHelper.TABLE_CITIES, null, cv);
            }
            this.getWritableDatabase().setTransactionSuccessful();
        } finally {
            Log.d(TAG, "done");
            this.getWritableDatabase().endTransaction();
        }
    }

    public List<City> searchCity(String query) {
        String sqlQuery = "select * from " + TABLE_CITIES + " where " + CITY_NAME + " like '"
                + query + "%'";
        Cursor cursor = this.getReadableDatabase().rawQuery(sqlQuery, null);
        List<City> list = new ArrayList<City>();
        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(CITY_ID);
                int nameIndex = cursor.getColumnIndex(CITY_NAME);
                int latIndex = cursor.getColumnIndex(CITY_LAT);
                int lngIndex = cursor.getColumnIndex(CITY_LNG);
                int codeIndex = cursor.getColumnIndex(CITY_COUNTRY_CODE);
                City city = new City();
                city.setId(cursor.getInt(idIndex));
                city.setName(cursor.getString(nameIndex));
                city.setLat(cursor.getDouble(latIndex));
                city.setLng(cursor.getDouble(lngIndex));
                city.setCountryCode(cursor.getString(codeIndex));
                list.add(city);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

}
