package android.example.com.attendanceapp;

import android.content.Intent;
import android.example.com.attendanceapp.fragments.OverallAttendanceFragment;
import android.example.com.attendanceapp.fragments.SavedAttendanceFragment;
import android.example.com.attendanceapp.fragments.StudentFragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    StudentFragment studentFragment = new StudentFragment();
    public String courseCode, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        courseCode = intent.getStringExtra("courseCode");
        email = intent.getStringExtra("email");
        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bottomNavigationView= findViewById(R.id.bottom_navigation);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        final StudentFragment studentFragment =new StudentFragment();
        transaction.replace(R.id.frame_layout, studentFragment);
        toolbar.setTitle("Take Attendance");
        transaction.commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_take_attendance:
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        StudentFragment studentFragment =new StudentFragment();
                        transaction.replace(R.id.frame_layout, studentFragment);
                        toolbar.setTitle("Take Attendance");
                        transaction.commit();
                        return true;
                    case R.id.action_taken_attendance:
                        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                        SavedAttendanceFragment savedAttendanceFragment =new SavedAttendanceFragment();
                        transaction2.replace(R.id.frame_layout, savedAttendanceFragment);
                        toolbar.setTitle("Saved Attendance");
                        transaction2.commit();
                        return true;
                    case R.id.attendance:
                        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                        OverallAttendanceFragment overallAttendanceFragment =new OverallAttendanceFragment();
                        transaction3.replace(R.id.frame_layout, overallAttendanceFragment);
                        toolbar.setTitle("Overall Attendance");
                        transaction3.commit();
                        return true;
                }
                return false;
            }
        });

    }

    void updateTotalAttendance(){
        studentFragment.updateTotalAttendance();

    }
    void uodateIndividualAttendance(){
        Toast.makeText(this, studentFragment.myCheckedList.size() + " ", Toast.LENGTH_LONG).show();
        studentFragment.updateIndividualAttendance();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


}
