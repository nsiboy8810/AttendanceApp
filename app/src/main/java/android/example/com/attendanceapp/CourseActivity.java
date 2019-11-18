package android.example.com.attendanceapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.example.com.attendanceapp.adapters.CourseListAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseActivity extends AppCompatActivity implements CourseListAdapter.ListItemClickListener {

    RecyclerView rvCourse;
    List<String> courseList = new ArrayList();
    CourseListAdapter courseListAdapter;
    ProgressBar pbLoadCourse;
    TextView textView;
    String email;
    Toolbar toolbar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        toolbar = findViewById(R.id.app_bar);
        toolbar.setTitle("Courses");
        setSupportActionBar(toolbar);
        textView = findViewById(R.id.tv_course_status);

        pbLoadCourse = findViewById(R.id.pb_course);
        rvCourse = findViewById(R.id.rv_course);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        courseListAdapter = new CourseListAdapter(this);
        rvCourse.hasFixedSize();
        rvCourse.setLayoutManager(layoutManager);
        rvCourse.setAdapter(courseListAdapter);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        getCourses();
    }

    @Override
    public void onListItemClickListener(int clickIndex) {
        Intent intent = new Intent(CourseActivity.this, HomeActivity.class);
        intent.putExtra("courseCode",courseList.get(clickIndex));
        intent.putExtra("email", email);
        startActivity(intent);
    }
    void getCourses(){
        showProgressBar();
        db.collection("Staff").document(email).collection("Courses").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                for (DocumentSnapshot d : documentSnapshots){
                    courseList.add(d.getId());
                }
                courseListAdapter.coursesList = courseList;
                courseListAdapter.notifyDataSetChanged();
                hideProgressbar();
                if (courseListAdapter == null){
                    textView.setVisibility(View.VISIBLE);
                }
            }
        });

    }
    void showProgressBar(){
        pbLoadCourse.setVisibility(View.VISIBLE);
        rvCourse.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
    }
    void hideProgressbar(){
        pbLoadCourse.setVisibility(View.INVISIBLE);
        rvCourse.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.it_add:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CourseActivity.this);
                alertDialog.setTitle("Add Course");
                alertDialog.setMessage("Enter Course Code");
                alertDialog.setIcon(R.drawable.ic_add_box);
                final EditText input = new EditText(CourseActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Map<String, Integer> attend = new HashMap<>();
                        attend.put("totalAttendnce", 0);
                        String course = input.getText().toString().toLowerCase();
                        db.collection("Staff").document(email).collection("Courses").document(course).set(attend).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(CourseActivity.this, "Course Added Successfully", Toast.LENGTH_LONG).show();
                                getCourses();
                            }
                        });


                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alertDialog.show();
                return true;

            case R.id.it_logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(CourseActivity.this,LoginActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);


    }
}
