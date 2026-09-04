package com.example.hantera_studenter_v1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.datepicker.DateValidatorPointBackward;

public class MainActivity extends AppCompatActivity {

    private EditText idET, nameET, courseET;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);


        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        idET = findViewById(R.id.editTextID);
        nameET = findViewById(R.id.editTextName);
        courseET = findViewById(R.id.editTextCourse);

        addBtn = findViewById(R.id.add_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idText = idET.getText().toString();
                int id = Integer.parseInt(idText);
                String name = nameET.getText().toString();
                String course = courseET.getText().toString();

                Student student = new Student(id, name, course);
                Database.instance.add(student);

                TextView list = findViewById(R.id.student_list);
                list.setText(Database.instance.printStudents());
            }
        });
    }


    public void removeStudent(View view){
        EditText idEdit = findViewById(R.id.remove_id);
        String idText = idEdit.getText().toString();
        int id = Integer.parseInt(idText);

        Database.instance.remove(id);

        TextView list = findViewById(R.id.student_list);
        list.setText(Database.instance.printStudents());
    }
}