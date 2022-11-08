package com.example.health_app;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.lang.annotation.Target;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Kommunicate.init(context, 248edb5f13266b9a5e9e6978878f144fb);
    }

    public void BMI_method(android.view.View View){
        Intent bmi_intent = new Intent(this, BMI.class);
        startActivity(bmi_intent);
    }

    public void steps_method(android.view.View View){
        Intent steps_intent = new Intent(this, Target.class);
        startActivity(steps_intent);
    }

    public void disease_method(android.view.View View){
        Intent disease_intent = new Intent(this, Target.class);
        startActivity(disease_intent);
    }

    public void meditation_method(android.view.View View){
        Intent meditation_intent = new Intent(this, Target.class);
        startActivity(meditation_intent);
    }

    public void calories_method(android.view.View View){
        Intent calories_intent = new Intent(this, Target.class);
        startActivity(calories_intent);
    }

    public void alarm_method(android.view.View View){
        Intent alarm_intent = new Intent(this, Target.class);
        startActivity(alarm_intent);
    }

    public void chatbot_method(android.view.View View){
        Intent chat_intent = new Intent(this, Target.class);
        startActivity(chat_intent);
    }

}