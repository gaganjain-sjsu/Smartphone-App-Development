package cmpe277.gaganjain.calculatorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String input, op1, op2, operator, result, sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = (TextView)findViewById(R.id.text_layout);
        input = "";
        sign = "";
        op1 = op2 = operator = result = "";
    }

    private void calculateResult(){

        result = "";
        int operand1 = Integer.parseInt(op1);
        int operand2 = Integer.parseInt(op2);

        switch (operator){
            case "+": result += operand1 + operand2;break;
            case "-": result += operand1 - operand2;break;
            case "/":
                if(operand2 == 0){
                    result = "ERROR!";
                }
                else{

                    double quo = (double)operand1/operand2;
                    int intquo = operand1/operand2;
                    if(Math.abs(quo - intquo) >= 0.5){
                        if(intquo < 0){
                            result += intquo - 1;
                        }
                        else{
                            result += intquo + 1;
                        }
                    }
                    else{
                        result += intquo;
                    }
                    //result += (int) Math.round((double)operand1/operand2);
                }
                break;
            case "*": result += operand1 * operand2;
        }

        if(!result.equals("ERROR!") && (Integer.parseInt(result) > 9999999 || Integer.parseInt(result) < -9999999)){
            result = "OVERFLOW!";
        }

        if(!result.equals("ERROR!") && !result.equals("OVERFLOW!")){
            op1 = result;
        }
        else{
            op1 = "";
        }
        op2 = "";
        operator = "";
        display.setText(result);
    }

    public void buttonClick(View view){
        Button button = (Button) view;
        String user_input = button.getText().toString();


        if(user_input.equals("AC")){
            sign = op1 = op2 = input = result = operator = "";
            display.setText(input);
        }

        else if(input == "" && op1 == "" && "+-".contains(user_input)){
            sign = user_input;
        }

        else if("+-/*=".contains(user_input)){

                if(user_input.equals("=")){
                    if((input == "" && op1 != "") || (input != "" && op1 == "")){
                        display.setText("ERROR!");
                        op1 = op2 = operator = result = "";
                    }

                    else if(input != "" && operator != ""){
                        op2 = input;
                        calculateResult();
                    }
                }

                else if(input != "" && operator != ""){
                    op2 = input;
                    calculateResult();
                }
                else{
                    if(op1 == ""){
                        op1 = input;
                    }
                }

                if(!user_input.equals("=")){
                    if(input != "" || op1 != "") {
                        operator = user_input;
                    }
                }

                sign = input = "";

        }

        else if(input.startsWith("0")){

            if(user_input.equals("0")){
                input = "0";
            }
            else{
                input = user_input;
            }
            display.setText(input);
        }
        else{

            if(operator == ""){
                op1 = op2 = result = "";
            }

            if((sign != "" && input.length() <= 8) || (sign == "" && input.length() <= 7)){
                if(input.length() > 1){
                    input += user_input;
                }
                else{
                    input += sign + user_input;
                }

            }

            if(Integer.parseInt(input) > 9999999 || Integer.parseInt(input) < -9999999){
                input = "OVERFLOW!";
                sign = op1 = op2 = result = operator = "";
            }

            display.setText(input);
        }

    }
}
