package tech.android.tcmp13.robotsapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import tech.android.tcmp13.robotsapp.data.DbHandler;

import static tech.android.tcmp13.robotsapp.data.RobotsContract.RobotEntry.*;

public class MainActivity extends AppCompatActivity {

    private SimpleCursorAdapter adapter;
    private DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setupDbHandler();
        setupUi();
    }

    private void setupDbHandler() {

        dbHandler = new DbHandler(this);
    }

    private void setupUi() {
        setContentView(R.layout.activity_main);
        ListView robotsListView = (ListView) findViewById(R.id.robotsListView);
        adapter = getSimpleCursorAdapter();
        robotsListView.setAdapter(adapter);
    }

    private SimpleCursorAdapter getSimpleCursorAdapter() {

        String[] from = {COLUMNS_NAME, COLUMNS_BRAND, COLUMNS_TYPE};
        int[] to = {R.id.robotItemTitle, R.id.robotItemBrand, R.id.robotItemType};

        return new SimpleCursorAdapter(this, //The Context
                R.layout.list_item_robot,//Each item's layout
                null, //Cursor will be updated at onStart
                from, //The Table Cols
                to, //The views' ids to load the cols content by the SAME ORDER
                0); //Flag for querying, almost always unnecessary
    }

    @Override
    protected void onStart() {

        super.onStart();
        updateAdapterCursor(dbHandler.queryAll());
    }

    @Override
    protected void onStop() {

        super.onStop();
        updateAdapterCursor(null);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode != 169) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        if (resultCode != RESULT_OK)
            return;

        Robot robot = data.getParcelableExtra(AppConstants.ROBOT_RESULT);
        dbHandler.insert(robot);
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
                startActivityForResult(new Intent(this, AddRobotActivity.class), 169);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
