package anneleenx.frequencycalc;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.getInteger;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void calculateNewFrequency(View view) {
        EditText frequencyEntry = findViewById(R.id.baseFrequency);
        String baseFrequency = frequencyEntry.getText().toString();

        EditText semitonesEntry = findViewById(R.id.numberOfSemitones);
        String numberOfSemitones = semitonesEntry.getText().toString();

        if (baseFrequency.equals(null) || baseFrequency.isEmpty()) {
            Toast.makeText(getBaseContext(), "Frequency value should not be empty", Toast.LENGTH_SHORT).show();
        } else if (numberOfSemitones.equals(null) || numberOfSemitones.isEmpty()) {
            Toast.makeText(getBaseContext(), "Enter valid number of semitones", Toast.LENGTH_SHORT).show();
        } else {
            CheckBox backwardCalculateCheckbox = findViewById(R.id.backwardCheckbox);
            float result = getCalculatedFreqeuncy(Integer.valueOf(baseFrequency), Integer.valueOf(numberOfSemitones), backwardCalculateCheckbox.isChecked());

            TextView textView = findViewById(R.id.resultView);
            textView.setText(String.valueOf(result));
        }
    }

    private float getCalculatedFreqeuncy(int frequency, int semitones, boolean isReverse){
        float result;

        if (isReverse) {
            semitones = 0 - semitones;
        }
        float exp = (float) semitones / 12;
        result = (float) (frequency * Math.pow(2.0, exp));
        return result;
    }

    public void exitApp(View view) {
        finish();
        System.exit(0);
    }

    public void copyToClipboard(View view) {
        TextView resultView = findViewById(R.id.resultView);
        if (!resultView.getText().toString().isEmpty()) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("resultValue", resultView.getText());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getBaseContext(), "Value copied", Toast.LENGTH_SHORT).show();
        }
    }
}


