package com.example.coco;

import static junit.framework.TestCase.assertEquals;

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

@RunWith(AndroidJUnit4.class)
public class MainActivityUnitTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testViewsInitialized() {
        try (ActivityScenario<MainActivity> scenario = activityRule.getScenario()) {
            scenario.onActivity(activity -> {
                // Verify views are initialized
                EditText loginEmail = activity.findViewById(R.id.login_email);
                EditText loginPassword = activity.findViewById(R.id.login_password);
//                Button loginBtn = activity.findViewById(R.id.login_btn);
                TextView registerLink = activity.findViewById(R.id.reigterLink);
                TextView forgotPassword = activity.findViewById(R.id.forgotPassword);

                assertNotNull(loginEmail);
                assertNotNull(loginPassword);
//                assertNotNull(loginBtn);
                assertNotNull(registerLink);
                assertNotNull(forgotPassword);
            });
        }
    }

    @Test
    public void testRegisterLinkClicked() {
        try (ActivityScenario<MainActivity> scenario = activityRule.getScenario()) {
            scenario.onActivity(activity -> {
                TextView registerLink = activity.findViewById(R.id.reigterLink);
                registerLink.performClick();
                Intent expectedIntent = new Intent(activity, Register.class);
//                Intent actualIntent = ShadowApplication.getInstance().getNextStartedActivity();
                Intent actualIntent = new Intent();
                assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
            });
        }
    }

    @Test
    public void testForgotPasswordClicked() {
        try (ActivityScenario<MainActivity> scenario = activityRule.getScenario()) {
            scenario.onActivity(activity -> {
                TextView forgotPassword = activity.findViewById(R.id.forgotPassword);
                forgotPassword.performClick();
                Intent expectedIntent = new Intent(activity, ForgotPassword.class);
//                Intent actualIntent = ShadowApplication.getInstance().getNextStartedActivity();
                Intent actualIntent = new Intent();
                assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
            });
        }
    }
}
