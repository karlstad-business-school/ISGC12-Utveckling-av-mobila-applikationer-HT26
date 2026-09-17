package com.example.hantera_studenter_v2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView idText = findViewById(R.id.text_id);
        TextView nameText = findViewById(R.id.text_name);
        TextView courseText = findViewById(R.id.text_course);
        Button removeBtn = findViewById(R.id.remove_btn);

        Intent intent = getIntent();
        if(intent.hasExtra("id") == false){
            Toast.makeText(StudentActivity.this, "Det finns inget ID", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = intent.getIntExtra("id", -1);

        if(id == -1){
            Toast.makeText(StudentActivity.this, "ID får inte vara -1", Toast.LENGTH_SHORT).show();
        }else{
            Log.e("Loggtagg", "ID:t vi sökte ut var: " + id);
            Student student = Database.instance.get(id);
            if(student != null){
                idText.setText(student.getId() + "");
                nameText.setText(student.getName());
                courseText.setText(student.getCourse());
            }
        }


        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database.instance.remove(id);
                finish();
            }
        });
    }
}