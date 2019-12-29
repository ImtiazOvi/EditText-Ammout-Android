package com.imitaz.editextamount;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText edtAmount;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtAmount = findViewById(R.id.edt_ammount);
        btnSubmit = findViewById(R.id.btn_submit);
        editTextListenerWithEuroPound(edtAmount, MainActivity.this);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ammount  = edtAmount.getText().toString();
                Toast.makeText(MainActivity.this,"Amount is :"+ammount,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void editTextListenerWithEuroPound(final EditText editText, final Context c) {
        final String[] firstCharector = {""};
        final boolean[] editTexts = {true};
        editText.addTextChangedListener(new TextWatcher() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void afterTextChanged(Editable s) {
                if (s.toString().length() >= 2) {
                    int totalAmmount = Integer.valueOf(NumberUtils.getConverdNumber(edtAmount.getText().toString()));
                    //  spnLoanTerm.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ConvertArray.getArrayBlow(PersistentUser.getLoanTermList(getActivity()), totalAmmount)));
                    editText.removeTextChangedListener(this);
                    try {
                        String originalString = s.toString();
                        originalString = originalString.substring(1);

                        Long longval;
                        if (originalString.contains(",")) {
                            originalString = originalString.replaceAll(",", "");
                        }
                        longval = Long.parseLong(originalString);

                        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                        formatter.applyPattern("#,###,###,###");
                        String formattedString = formatter.format(longval);

                        //setting text after format to EditText
                        editText.setText(AppConst.EURO_SYMBOL + "" + formattedString);
                        editText.setSelection(editText.getText().length());
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }

                    editText.addTextChangedListener(this);

                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (editTexts[0]) {
                    firstCharector[0] = s.toString();
                    editTexts[0] = false;
                } else {
                    firstCharector[0] = "";
                }
                if (s.length() >= 1) {
                    editText.setBackground(c.getResources().getDrawable(R.drawable.round_shape_stroke_blue));

                    if (!s.toString().startsWith(AppConst.EURO_SYMBOL)) {
                        editText.setText(AppConst.EURO_SYMBOL + firstCharector[0]);
                        Selection.setSelection(editText.getText(), editText.getText().length());

                    }
                } else {
                    editText.setBackground(c.getResources().getDrawable(R.drawable.round_shape_stroke_ash));
                    firstCharector[0] = "";
                    editTexts[0] = true;
                }

            }
        });

    }
}
