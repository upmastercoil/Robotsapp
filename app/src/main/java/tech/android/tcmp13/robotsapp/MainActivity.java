package tech.android.tcmp13.robotsapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import tech.android.tcmp13.robotsapp.db.DbOpenHelper;

import static android.provider.BaseColumns._ID;
import static tech.android.tcmp13.robotsapp.db.RobotsContract.RobotEntry.*;
import static tech.android.tcmp13.robotsapp.db.RobotsContract.RobotEntry.QUERY_ALL_LOADER_ID;
import static tech.android.tcmp13.robotsapp.db.RobotsContract.RobotEntry.ROBOTS_CONTENT_URI;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private RobotsCursorAdapter adapter;
    private DbOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setupDbOpenHandler();
        setupUi();
        getLoaderManager().initLoader(QUERY_ALL_LOADER_ID, null, this);
    }

    private void setupDbOpenHandler() {

        dbOpenHelper = new DbOpenHelper(this);
    }

    private void setupUi() {
        setContentView(R.layout.activity_main);
        ListView robotsListView = (ListView) findViewById(R.id.robotsListView);
        adapter = new RobotsCursorAdapter(this, null);
        robotsListView.setAdapter(adapter);
    }

    /**
     * @param cursor if null will clear the adapter.
     */
    private void updateAdapterCursor(@Nullable Cursor cursor) {
        Cursor oldCursor = adapter.swapCursor(cursor);
        if (oldCursor != null)
            oldCursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addRobotActionItem:
                startActivity(new Intent(this, AddRobotActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent = new Intent(this, AddRobotActivity.class);
        intent.setData(ContentUris.withAppendedId(ROBOTS_CONTENT_URI, l));
        startActivity(intent);
    }

    @Override
    protected void onResume() {

        super.onResume();
        //updateAdapterCursor(null);
    }

    @Override
    protected void onPause() {

        super.onPause();
        //updateAdapterCursor(null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        switch (i){
            case QUERY_ALL_LOADER_ID:
                return new CursorLoader(this,
                        ROBOTS_CONTENT_URI,
                        new String[]{_ID, COLUMNS_NAME, COLUMNS_BRAND, COLUMNS_TYPE},
                        null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        updateAdapterCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        updateAdapterCursor(null);
    }
}
