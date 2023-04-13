/*
Task 4
When I rotate The screen, The activity restarts, and set the clock to 00.
that's interesting, but my design and placement for the button stays in the right place.
*/


/*
Mobile App Development I -- COMP.4630 Honor Statement
The practice of good ethical behavior is essential for
maintaining good order in the classroom, providing an
enriching learning experience for students, and
training as a practicing computing professional upon
graduation. This practice is manifested in the
University's Academic Integrity policy. Students are
expected to strictly avoid academic dishonesty and
adhere to the Academic Integrity policy as outlined in
the course catalog. Violations will be dealt with as
outlined therein. All programming assignments in this
class are to be done by the student alone unless
otherwise specified. No outside help is permitted
except the instructor and approved tutors.
I certify that the work submitted with this assignment
is mine and was generated in a manner consistent with
this document, the course academic policy on the
course website on Blackboard, and the UMass Lowell
academic code.
Date: Friday oct 7
Name:John Ersen Youte
*/

package com.example.lecture8exer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {

    private Button start_and_pause_button;
    private Button pause_button;
    private Button reset_button;
    private Chronometer chronometer_obj;
    private long real_time;
    private boolean isRunning = false;
    private long offset;
    private long base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_and_pause_button = findViewById(R.id.start);
        reset_button = findViewById(R.id.reset);
        chronometer_obj = findViewById(R.id.chronometer);
        real_time = SystemClock.elapsedRealtime();

        //restoring the data from the saved instances and i added some logic
        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("isRunning") != false) {
                chronometer_obj.setBase(savedInstanceState.getLong("base"));
                base = savedInstanceState.getLong("base");
                isRunning = savedInstanceState.getBoolean("isRunning");
                offset = savedInstanceState.getLong("offset");
                chronometer_obj.start();
                start_and_pause_button.setText(R.string.Pause);
                isRunning = true;
            } else {
                chronometer_obj.stop();
                offset = SystemClock.elapsedRealtime() - chronometer_obj.getBase();
                start_and_pause_button.setText(R.string.Start);
                isRunning = false;
            }

        }

        //button on click listener
        start_and_pause_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (start_and_pause_button.getText().toString().equalsIgnoreCase(getText(R.string.Start).toString())) {
                    if (!isRunning) {
                        chronometer_obj.setBase(SystemClock.elapsedRealtime() - offset);
                        chronometer_obj.start();
                        start_and_pause_button.setText(R.string.Pause);
                        isRunning = true;
                    }
                } else if (start_and_pause_button.getText().toString().equalsIgnoreCase(getText(R.string.Pause).toString())) {
                    if (isRunning) {
                        offset = SystemClock.elapsedRealtime() - chronometer_obj.getBase();
                        chronometer_obj.stop();
                        start_and_pause_button.setText(R.string.Start);
                        isRunning = false;
                    }
                }

            }
        });

        reset_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                chronometer_obj.setBase(SystemClock.elapsedRealtime());
                chronometer_obj.stop();
                System.out.println("clicked3");
                offset = 0;
                isRunning = false;
                start_and_pause_button.setText(getText(R.string.Start));
            }
        });

    }

    //and the methods were overritten
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isRunning", isRunning);
        outState.putLong("offset", offset);
        outState.putLong("base", chronometer_obj.getBase());
    }

    @Override
    protected void onStop() {
        super.onStop();
        chronometer_obj.stop();
        offset = SystemClock.elapsedRealtime() - chronometer_obj.getBase();
        start_and_pause_button.setText(getText(R.string.Start).toString());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        chronometer_obj.setBase(SystemClock.elapsedRealtime() - offset);
        chronometer_obj.start();
        start_and_pause_button.setText(R.string.Pause);
        isRunning = true;
    }
}

