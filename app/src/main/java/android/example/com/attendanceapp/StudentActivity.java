package android.example.com.attendanceapp;

import android.content.Intent;
import android.example.com.attendanceapp.fragments.StudentAddCourseFragment;
import android.example.com.attendanceapp.fragments.StudentViewFragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class StudentActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    public String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Intent intent =getIntent();
        email = intent.getStringExtra("email");
        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("View Attendance");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bottomNavigationView= findViewById(R.id.bottom_navigation_student);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        final StudentViewFragment studentViewFragment =new StudentViewFragment();
        transaction.replace(R.id.frame_layout_student, studentViewFragment);
        transaction.commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_view_attendance:
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        StudentViewFragment studentViewFragment =new StudentViewFragment();
                        transaction.replace(R.id.frame_layout_student, studentViewFragment);
                        toolbar.setTitle("View Attendance");
                        transaction.commit();
                        return true;
                    case R.id.action_add_course:
                        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                        StudentAddCourseFragment studentAddCourseFragment =new StudentAddCourseFragment();
                        transaction2.replace(R.id.frame_layout_student, studentAddCourseFragment);
                        toolbar.setTitle("Add Course");
                        transaction2.commit();
                        return true;

                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.it_logout2:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(StudentActivity.this,LoginActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

