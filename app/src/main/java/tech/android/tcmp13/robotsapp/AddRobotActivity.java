package tech.android.tcmp13.robotsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import tech.android.tcmp13.robotsapp.db.RobotsContract;

import static tech.android.tcmp13.robotsapp.db.RobotsContract.RobotEntry.ROBOTS_CONTENT_URI;

/**
 * Created by tcmp13-t on 12/4/2016.
 */
public class AddRobotActivity extends AppCompatActivity {

    private EditText nameInput;
    private EditText brandInput;
    private EditText typeInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_robot);
        nameInput = (EditText) findViewById(R.id.addRobotNameInput);
        brandInput = (EditText) findViewById(R.id.addRobotBrandInput);
        typeInput = (EditText) findViewById(R.id.addRobotTypeInput);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_add_robot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.saveRobotActionItem:
                Robot robot = generateRobotFromInput();
                finishWithResult(robot);
                return true;
            case android.R.id.home: //In case the user clicked the up button, raise the canceled flag and fallthrough.
                setResult(RESULT_CANCELED);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void finishWithResult(Robot robot) {

        getContentResolver().insert(ROBOTS_CONTENT_URI, Utility.generateContentValues(robot));

        finish();
    }

    private Robot generateRobotFromInput() {

        String name = nameInput.getText().toString();
        String brand = brandInput.getText().toString();
        RobotType type = RobotType.fromRawValue(Integer.valueOf(typeInput.getText().toString()));
        return new Robot(name, brand, type);
    }

    @Override
    public void onBackPressed() {

        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}
