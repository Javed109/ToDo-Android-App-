package com.example.todolist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    EditText taskInput;
    Button addTaskButton;
    ListView taskListView;
    ArrayList<String> taskList;
    ArrayAdapter<String> taskAdapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskInput = findViewById(R.id.taskInput);
        addTaskButton = findViewById(R.id.addTaskButton);
        taskListView = findViewById(R.id.taskListView);

        sharedPreferences = getSharedPreferences("tasks", MODE_PRIVATE);
        taskList = new ArrayList<>(sharedPreferences.getStringSet("taskList", new HashSet<>()));

        taskAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        taskListView.setAdapter(taskAdapter);

        addTaskButton.setOnClickListener(view -> {
            String task = taskInput.getText().toString();
            if (!task.isEmpty()) {
                taskList.add(task);
                taskAdapter.notifyDataSetChanged();
                saveTasks();
                taskInput.setText("");
            }
        });

        taskListView.setOnItemLongClickListener((adapterView, view, position, id) -> {
            taskList.remove(position);
            taskAdapter.notifyDataSetChanged();
            saveTasks();
            return true;
        });
    }

    private void saveTasks() {
        Set<String> taskSet = new HashSet<>(taskList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("taskList", taskSet);
        editor.apply();
    }
}
