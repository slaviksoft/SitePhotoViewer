package ua.com.slaviksoft.sitephotoviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import ua.com.slaviksoft.sitephotoviewer.controllers.PinChecker;

public class PinActivity extends AppCompatActivity {

    private EditText editTextPin;
    private TextView textViewTitle;
    private TextView textViewInfo;
    private PinChecker pinChecker;
    private ImageButton imageButtonClear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        pinChecker = new PinChecker(this);

        editTextPin = (EditText) findViewById(R.id.editTextPin);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewInfo = (TextView) findViewById(R.id.textViewInfo);
        imageButtonClear = (ImageButton) findViewById(R.id.imageButtonClear);

        textViewTitle.setText("Enter PIN");
        editTextPin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) start();
                return true;
            }

        });

        updateInfo();

    }

    private void updateInfo(){
        boolean isPinSet = pinChecker.isPinSet();

        imageButtonClear.setEnabled(isPinSet);
        if (isPinSet)
            textViewInfo.setText("pin is activated");
        else
            textViewInfo.setText("enter empty or new");
    }

    public void onPinEnterClick(View view) {
        start();
    }

    private void goNext(){
        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
    }


    private void start(){

        String pin = editTextPin.getText().toString();
        if (pinChecker.isPinSet()){
            if(pinChecker.check(pin)) goNext();
                else editTextPin.setError("wrong pin");

        }else{
            pinChecker.setPIN(pin);
            goNext();
        }
    }

    public void onClearClick(View view) {

        String pin = editTextPin.getText().toString();
        if (pinChecker.isPinSet()){
            if(pinChecker.check(pin)) {
                pinChecker.setPIN("");
                editTextPin.setText("");
            }
                else editTextPin.setError("enter old pin");
        }

        updateInfo();
    }
}
