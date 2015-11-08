package com.app.floppysoftware.activitylifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Clase que implementa LOGs en los principales métodos que son
 * llamados en los cambios de estado de la aplicación.
 *
 * Alias repositorio local = githubActivityLifeCycle
 * URL repositorio remoto  = https://github.com/MiguelVis/ActivityLifeCycle.git
 *
 * @author Miguel García
 * @since  02 Oct 2015
 */
public class MainActivity extends Activity {

    // Tag a utilizar en los logs
    private static final String TAG = "ActivityLifeCycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //
        Log.i(TAG, "onCreate");

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart(){
        super.onStart();

        //
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        //
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();

        //
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //
        Log.i(TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();

        //
        Log.i(TAG, "onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        //
        savedInstanceState.putString("test", "test_value");

        //
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //
        String s = savedInstanceState.getString("test");

        //
        Log.i(TAG, "onRestoreInstanceState");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
