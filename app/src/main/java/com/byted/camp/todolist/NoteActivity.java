package com.byted.camp.todolist;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.byted.camp.todolist.roomdb.Note;
import com.byted.camp.todolist.roomdb.NoteDataBase;


public class NoteActivity extends AppCompatActivity {

    private EditText editText;
    private Button addBtn;
    private RadioGroup radioGroup;

    NoteDataBase db;

    boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle(R.string.take_a_note);

        Intent intent = getIntent();
        final String content = intent.getStringExtra("content");
        final int priority = intent.getIntExtra("priority", Note.PRIORITY_LOW);
        final int id = intent.getIntExtra("_id", -1);

        db = Room.databaseBuilder(NoteActivity.this,
                NoteDataBase.class, "note-database")
                .allowMainThreadQueries()
                .build();

        editText = findViewById(R.id.edit_text);
        if (content != null) {
            isUpdate = true;
            editText.setText(content);
        }
        editText.setFocusable(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(editText, 0);
        }
        radioGroup = findViewById(R.id.radio_group);
        AppCompatRadioButton radioButton;
        switch (priority) {
            case Note.PRIORITY_HIGH:
                radioButton = findViewById(R.id.btn_high);
                break;
            case Note.PRIORITY_MEDIUM:
                radioButton = findViewById(R.id.btn_medium);
                break;
            default:
                radioButton = findViewById(R.id.btn_low);
                break;
        }
        radioButton.setChecked(true);

        addBtn = findViewById(R.id.btn_add);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence content = editText.getText();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(NoteActivity.this,
                            "No content to add", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean succeed = isUpdate ?
                        updateNote2Database(id, content.toString().trim(), getSelectedPriority()) :
                        saveNote2Database(content.toString().trim(), getSelectedPriority());
                if (succeed) {
                    Toast.makeText(NoteActivity.this,
                            isUpdate ? "Note updated" : "Note added", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                } else {
                    Toast.makeText(NoteActivity.this,
                            "Error", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        db = null;
    }

    private boolean updateNote2Database(int id, String content, int priority) {
        if (db == null || TextUtils.isEmpty(content)) {
            return false;
        }
        Note note = new Note();
        note.setId(id);
        note.setContent(content);
        note.setDate(System.currentTimeMillis());
        note.setState(Note.STATE_TODO);
        note.setPriority(priority);
        int rows = db.noteDao().update(note);
        return rows > 0;
    }

    private boolean saveNote2Database(String content, int priority) {
        if (db == null || TextUtils.isEmpty(content)) {
            return false;
        }
        Note note = new Note();
        note.setContent(content);
        note.setDate(System.currentTimeMillis());
        note.setState(Note.STATE_TODO);
        note.setPriority(priority);
        long rowId = db.noteDao().insert(note);
        return rowId != -1;
    }

    private int getSelectedPriority() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.btn_high:
                return Note.PRIORITY_HIGH;
            case R.id.btn_medium:
                return Note.PRIORITY_MEDIUM;
            default:
                return Note.PRIORITY_LOW;
        }
    }
}
