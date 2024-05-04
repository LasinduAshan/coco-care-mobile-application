package com.example.coco;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class MainActivityUnitTest {
    //login
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testViewsInitialized() {

        try (ActivityScenario<MainActivity> scenario = activityRule.getScenario()) {
            scenario.onActivity(activity -> {
                // Verify views are initialized
                EditText loginEmail = activity.findViewById(R.id.login_email);
                EditText loginPassword = activity.findViewById(R.id.login_password);
                Button loginBtn = activity.findViewById(R.id.login_btn);
                TextView registerLink = activity.findViewById(R.id.reigterLink);
                TextView forgotPassword = activity.findViewById(R.id.forgotPassword);

                assertNotNull(loginEmail);
                assertNotNull(loginPassword);
                assertNotNull(loginBtn);
                assertNotNull(registerLink);
                assertNotNull(forgotPassword);
            });
        }
    }

    @Test
    public void testRegisterLinkClicked() {

        MainActivity activity = mock(MainActivity.class);
        TextView registerLink = new TextView(activity);
        registerLink.setId(R.id.reigterLink);
        registerLink.performClick();


        verify(activity).startActivity(new Intent(activity, Register.class));
    }

    @Test
    public void testForgotPasswordClicked() {

        MainActivity activity = mock(MainActivity.class);
        TextView forgotPassword = new TextView(activity);
        forgotPassword.setId(R.id.forgotPassword);
        forgotPassword.performClick();


        verify(activity).startActivity(new Intent(activity, ForgotPassword.class));
    }


}
