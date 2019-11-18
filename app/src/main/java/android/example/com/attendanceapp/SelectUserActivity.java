package android.example.com.attendanceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SelectUserActivity extends AppCompatActivity {

    Button btnStaff, btnStudent;
    String email;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
        mToolbar = findViewById(R.id.app_bar2);
        mToolbar.setTitle("Select User");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        btnStaff = findViewById(R.id.btn_staff);
        btnStudent = findViewById(R.id.btn_student);
        Intent intent1 = getIntent();
        email = intent1.getStringExtra("email");
        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(SelectUserActivity.this, StudentActivity.class);
                intent2.putExtra("email",email);
                startActivity(intent2);
            }
        });
        btnStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectUserActivity.this, CourseActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.it_upload).setVisible(false);
        return super.onPrepareOptionsMenu(menu);

    }
}
