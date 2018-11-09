package anneleenx.frequencycalc;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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
            Integer baseValue = Integer.valueOf(baseFrequency);
            float result = getCalculatedFreqeuncy(baseValue, Integer.valueOf(numberOfSemitones), backwardCalculateCheckbox.isChecked());

            TextView textViewResult = findViewById(R.id.resultView);
            textViewResult.setText(String.valueOf(result));

            TextView textViewPercentage = findViewById(R.id.resultPercentage);
            textViewPercentage.setText("Difference in percentage: \n" + String.valueOf(getPercentageValue(baseValue, result, backwardCalculateCheckbox.isChecked())) + "%");
        }
    }

    private float getPercentageValue(int baseValue, float resultValue, boolean isReverse){
        float result;
        BigDecimal bigDecimal;
        if (baseValue > resultValue) {
            bigDecimal = new BigDecimal(((baseValue - resultValue) / resultValue) * 100, MathContext.DECIMAL32);
        } else {
            bigDecimal = new BigDecimal(((resultValue - baseValue) / resultValue) * 100, MathContext.DECIMAL32);
        }
        result = bigDecimal.setScale(2, RoundingMode.HALF_UP).floatValue();
        return isReverse ? -result: result;
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
        TextView resultView = findViewById(R.id.resultPercentage);
        if (!resultView.getText().toString().isEmpty()) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("resultValue", resultView.getText());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getBaseContext(), "Value copied", Toast.LENGTH_SHORT).show();
        }
    }
}


