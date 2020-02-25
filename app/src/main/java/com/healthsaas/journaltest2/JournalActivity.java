package com.healthsaas.journaltest2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class JournalActivity extends AppCompatActivity {

    private static final String TAG = "JournalActivity";
    private static final String EXTRA_JOURNAL_LIST = "com.healthsaas.journalTest2.journal_list";

    private static final int CHOICE_INVALID = -1;
    private static final int CHOICE_NOT_ANXIOUS = 0;
    private static final int CHOICE_ANXIOUS = 1;
    private static final int CHOICE_HYPERAWARE = 2;
    private static final int CHOICE_SOMETHING_WRONG = 3;
    private static final int CHOICE_SENSE_OF_DOOM = 4;

    private Button mSendAnserButton;
    private JournalList journals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_journal);

        // Get the journal list passed
        journals = (JournalList)getIntent().getSerializableExtra("JournalList");
        // Display the journals pending on the list, for now just present journal

        // Pick the journal to run

        mSendAnserButton = (Button)findViewById(R.id.show_answer_button);
        mSendAnserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.journal_radio_group);
                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                String result = JournalSepsis.getSepsisResponse(radioButtonID);
                Log.d(TAG, "Result : " + result);
                if (result.contains("-1")) {
                    // If no choice made, just notify and wait for choice
                    Log.d(TAG, "No Radio Button Chosen");
                    Snackbar.make(view, "You Must Choose", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                } else {
                    // For now only supporting one pending Journal so clear it for simplicity
                    journals.getPendingJournals().clear();
                    // Send the response
                    Log.d(TAG, "Journal Activity sending");

                    // Send result to main activity
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_JOURNAL_LIST, journals);
                    setResult(Activity.RESULT_OK, intent);
                    Log.d(TAG, "Journal Activity ended");
                    finish();
                }
            }
        });
    }

    public static Intent newIntent(Context context, JournalList journals) {
        Intent intent = new Intent(context, JournalActivity.class);
        intent.putExtra("JournalList", journals);
        return intent;
    }

    public static JournalList retrieveReturnedJournalList(Intent result) {
        return (com.healthsaas.journaltest2.JournalList)result.getSerializableExtra(EXTRA_JOURNAL_LIST);
    }
}
