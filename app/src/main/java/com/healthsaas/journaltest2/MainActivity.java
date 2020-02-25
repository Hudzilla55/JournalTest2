package com.healthsaas.journaltest2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String EXTRA_JOURNAL_LIST = "com.healthsaas.journalTest2.journal_list";
    private static final int JOURNAL_ACTIVITY = 100;

    // TODO: For now if a new journal comes in while Journal Activity runs we will lose that new journal.
    private JournalList journals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Listen to the FAB button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Journal chosen from fab");
                startJournalActivity();
            }
        });

        journals = new JournalList();
        // Start with a pending Sepsis journal
        journals.addJournalEntry(new JournalSepsis().getSepsisEntry());
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

        switch (id) {
            case R.id.action_journal:
                Log.d(TAG, "Journal chosen");
                startJournalActivity();
                return true;

            case R.id.action_settings:
                journals.addJournalEntry(new JournalSepsis().getSepsisEntry());
                showPendingJournal();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Call Back method  to get the Message form Journal Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d(TAG, "Journal Activity resultCode : " + resultCode);

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            Log.d(TAG, "Journal Activity did not end OK");
            return;
        }

        // check if the request code is same as what is passed
        if(requestCode == JOURNAL_ACTIVITY)
        {
            journals = (JournalList)data.getSerializableExtra(EXTRA_JOURNAL_LIST);

            showPendingJournal();
        }
    }

    /**
     * Start the Journal Handling Activity if there is a pending Journal, otherwise disply note that
     * no Journal is pending.
     */
    protected void startJournalActivity() {
        if (journals.journalsPending() > 0) {
            Intent intent = JournalActivity.newIntent(MainActivity.this, journals);
            startActivityForResult(intent, JOURNAL_ACTIVITY);
        } else {
            Snackbar.make(getWindow().findViewById(android.R.id.content),
                    "No pending Journals", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    /**
     * Make the FAB button for pending Journal visible and active.
     */
    private void showPendingJournal() {
        FloatingActionButton fab = findViewById(R.id.fab);
        if (journals.journalsPending() == 0)
            fab.hide();
        else
            fab.show();
    }
}
