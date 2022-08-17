package edu.gatech.seclass.sdpencryptor;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    EditText entryTextIDValue, argInput1IDValue, argInput2IDValue;
    Button encryptButtonIDValue;
    TextView textEncryptedIDValue;

    int[] coPrime = {1,5,7,11,13,17,19,23,25,29,31,35};

    char[] letters_smallCase = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    char[] letters_UpperCase = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
    char[] number_Omega = "0123456789".toCharArray();
    char[] number_Omega2 = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    char[] number_Omega2U = "ABCDEFGHIJKLMNOPQRSTUVWZYZ0123456789".toCharArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        encryptButtonIDValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entryTextID = entryTextIDValue.getText().toString().trim();
                String argInput1ID = argInput1IDValue.getText().toString().trim();
                String argInput2ID = argInput2IDValue.getText().toString().trim();

                if(entryTextID.isEmpty() || containsOnlyNum(entryTextID)){
                    entryTextIDValue.setError("Invalid Entry Text");
                }else if(argInput1ID.isEmpty() || !containsCoPrime(Integer.parseInt(argInput1ID),coPrime)){
                    argInput1IDValue.setError("Invalid Arg 1 Entry");
                }else if(argInput2ID.isEmpty() || (Integer.parseInt(argInput2ID) < 1 || Integer.parseInt(argInput2ID) >= 36 )){
                    argInput2IDValue.setError("Invalid Arg 2 Entry");
                }else{

                    int a0 = Integer.parseInt(argInput1ID);
                    int a1 = Integer.parseInt(argInput2ID);
                    encodeMessage(entryTextID,a0,a1);
                }
            }
        });
    }

    private boolean containsOnlyNum(String entryTextID) {
        int flag = 0;
        for (int i=0; i<entryTextID.length(); i++){
            char ch = entryTextID.charAt(i);
            if(Character.isLetter(ch)){
                flag = 1;
            }
        }
        return flag == 0;
    }

    private void encodeMessage(String entryTextIDValue, int a0, int a1) {
        String encipheredMessage = "";
        for (int i=0; i<entryTextIDValue.length(); i++){
            char ch = entryTextIDValue.charAt(i);
            if (Character.isLetter(ch)){
                if (Character.isUpperCase(ch)){
                    // searching for index number using its corresponding letter
                    int encoded_num = (Arrays.binarySearch(letters_UpperCase,ch) * a0 + a1) % 36;
                    char encoded_char = number_Omega2[encoded_num];
                    encipheredMessage += encoded_char;
                }else{
                    int encoded_num = (Arrays.binarySearch(letters_smallCase,ch) * a0 + a1) % 36;
                    char encoded_char = number_Omega2U[encoded_num];
                    encipheredMessage += encoded_char;
                }
            }
            else if (Character.isDigit(ch)){
                int encoded_num = ((Arrays.binarySearch(number_Omega,ch) + 26) * a0+ a1) % 36;
                char encoded_char = number_Omega2[encoded_num];
                encipheredMessage += encoded_char;
            }
            else{
                encipheredMessage += ch;
            }


        }
        textEncryptedIDValue.setText(encipheredMessage);
        textEncryptedIDValue.setVisibility(View.VISIBLE);
    }

    private boolean containsCoPrime(int arg, int[] coPrime) {
        for(int num : coPrime){
            if(arg == num){
                return true;
            }
        }
        return  false;
    }

    private void initViews() {
        entryTextIDValue = findViewById(R.id.entryTextID);
        argInput1IDValue = findViewById(R.id.argInput1ID);
        argInput2IDValue = findViewById(R.id.argInput2ID);

        encryptButtonIDValue = findViewById(R.id.encryptButtonID);

        textEncryptedIDValue = findViewById(R.id.textEncryptedID);
    }
}