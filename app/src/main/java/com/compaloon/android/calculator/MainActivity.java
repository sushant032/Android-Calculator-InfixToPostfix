package com.compaloon.android.calculator;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList <Object> expression =new ArrayList<Object>();
    String operand = "";
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View view) {
        TextView tv = findViewById(R.id.calculation);
        String exp = tv.getText().toString();
        switch (view.getId()){
            case R.id.n0:
                exp+='0';
                operand+='0';
                tv.setText(exp);
                break;
            case R.id.n1:
                exp+='1';
                operand+='1';
                tv.setText(exp);
                break;
            case R.id.n2:
                exp+='2';
                operand+='2';
                tv.setText(exp);
                break;
            case R.id.n3:
                exp+='3';
                operand+='3';
                tv.setText(exp);
                break;
            case R.id.n4:
                exp+='4';
                operand+='4';
                tv.setText(exp);
                break;
            case R.id.n5:
                exp+='5';
                operand+='5';
                tv.setText(exp);
                break;
            case R.id.n6:
                exp+='6';
                operand+='6';
                tv.setText(exp);
                break;
            case R.id.n7:
                exp+='7';
                operand+='7';
                tv.setText(exp);
                break;
            case R.id.n8:
                exp+='8';
                operand+='8';
                tv.setText(exp);
                break;
            case R.id.n9:
                exp+='9';
                operand+='9';
                tv.setText(exp);
                break;
            case R.id.add:
                if(exp.charAt(exp.length()-1)=='+'||exp.charAt(exp.length()-1)=='*'||exp.charAt(exp.length()-1)=='-'||exp.charAt(exp.length()-1)=='/'){
                    exp = exp.substring(0,exp.length()-1)+'+';
                    expression.remove(expression.size()-1);
                }
                else{
                    exp+='+';
                    expression.add(Double.parseDouble(operand));
                }
                expression.add('+');
                operand="";
                tv.setText(exp);
                break;
            case R.id.sub:
                if(flag!=0){
                    if(exp.charAt(exp.length()-1)=='+'||exp.charAt(exp.length()-1)=='*'||exp.charAt(exp.length()-1)=='-'||exp.charAt(exp.length()-1)=='/'){
                        exp = exp.substring(0,exp.length()-1)+'-';
                        expression.remove(expression.size()-1);
                    }
                    else{
                        exp+='-';
                        expression.add(Double.parseDouble(operand));
                    }
                    expression.add('-');
                    operand="";
                    tv.setText(exp);
                }else{
                    operand+="-";
                    exp+="-";
                    tv.setText(exp);
                    flag=1;
                }
                break;
            case R.id.mul:
                if(exp.charAt(exp.length()-1)=='+'||exp.charAt(exp.length()-1)=='*'||exp.charAt(exp.length()-1)=='-'||exp.charAt(exp.length()-1)=='/'){
                    exp = exp.substring(0,exp.length()-1)+'*';
                    expression.remove(expression.size()-1);
                }
                else{
                    exp+='*';
                    expression.add(Double.parseDouble(operand));
                }
                expression.add('*');
                operand="";
                tv.setText(exp);
                break;
            case R.id.div:
                if(exp.charAt(exp.length()-1)=='+'||exp.charAt(exp.length()-1)=='*'||exp.charAt(exp.length()-1)=='-'||exp.charAt(exp.length()-1)=='/'){
                    exp = exp.substring(0,exp.length()-1)+'/';
                    expression.remove(expression.size()-1);
                }
                else{
                    exp+='/';
                    expression.add(Double.parseDouble(operand));
                }
                expression.add('/');
                operand="";
                tv.setText(exp);
                break;
            case R.id.ndot:
                exp+='.';
                operand+='.';
                tv.setText(exp);
                break;
            case R.id.eql:
                expression.add(Double.parseDouble(operand));
                operand="";
                ArrayList<Object>postfix = createPostfix(expression);
                tv.setText("");
                Double ans = evaluate(postfix);
//                for(Object x:expression)
//                    tv.setText(tv.getText()+x.toString());
                operand=ans.toString();
                expression.clear();
                tv.setText(ans.toString());
                break;
            case R.id.clear:
                tv.setText("");
                operand="";
                flag=0;
                expression.clear();
        }

        }
        public static ArrayList<Object> createPostfix(ArrayList<Object>al){
            ArrayList<Object> postfix = new ArrayList<>();
            Stack st = new Stack();
            st.push('#');
            for(Object a: al){
                if(a instanceof Character){
                    Character top = (Character) st.peek();
                    if(top=='#')
                        st.push(a);
                    else if(getPrecedence((Character)a)>getPrecedence(top))
                        st.push(a);
                    else{
                        while(true){
                            top = (Character)st.pop();
                            postfix.add(top);
                            top = (Character)st.peek();
                            if(top=='#'||(getPrecedence(top)<getPrecedence((Character)a)))
                                break;
                        }
                        st.push(a);
                    }
                }
                else
                    postfix.add(a);
            }
            Character a;
            while((a = (Character)st.pop())!='#'){
                postfix.add(a);
            }
            return postfix;
        }
    public static int getPrecedence(char operand){
        switch(operand){
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;
        }
        return -1;
    }
    public static Double evaluate(ArrayList <Object> a){
        Stack st = new Stack();
        for(Object b: a){
            if(b instanceof Number)
                st.push(b);
            else{
                Character operator = (Character)b;
                Double op2=(Double)st.pop(),op1=(Double)st.pop();
                switch(operator){
                    case '+':  st.push(op1 + op2);
                        break;
                    case '-':  st.push(op1 - op2);
                        break;
                    case '/':  st.push(op1 / op2);
                        break;
                    case '*':  st.push(op1 * op2);
                        break;
                }
            }
        }
        return (Double)st.pop();
    }
}
