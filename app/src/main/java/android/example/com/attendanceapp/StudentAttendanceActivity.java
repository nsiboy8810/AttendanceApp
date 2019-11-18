package android.example.com.attendanceapp;

import android.example.com.attendanceapp.fragments.OverallAttendanceFragment;
import android.example.com.attendanceapp.fragments.SavedAttendanceFragment;
import android.example.com.attendanceapp.fragments.StudentAddCourseFragment;
import android.example.com.attendanceapp.fragments.StudentFragment;
import android.example.com.attendanceapp.fragments.StudentViewFragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class StudentAttendanceActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);


    }
}
