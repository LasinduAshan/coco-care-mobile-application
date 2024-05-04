package com.example.coco;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityIntegrationTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testLoginSuccess() {
        // Start the activity
        try (ActivityScenario<MainActivity> scenario = activityRule.getScenario()) {
            scenario.onActivity(activity -> {

                Espresso.onView(ViewMatchers.withId(R.id.login_email)).perform(ViewActions.typeText("lasindu@gmail.com"));
                Espresso.onView(ViewMatchers.withId(R.id.login_password)).perform(ViewActions.typeText("password"), ViewActions.closeSoftKeyboard());


                Espresso.onView(ViewMatchers.withId(R.id.login_btn)).perform(ViewActions.click());

                /
                Espresso.onView(withText("Login Successfully !")).check(matches(isDisplayed()));
            });
        }
    }

    @Test
    public void testLoginFailureInvalidCredentials() {

        try (ActivityScenario<MainActivity> scenario = activityRule.getScenario()) {
            scenario.onActivity(activity -> {

                Espresso.onView(ViewMatchers.withId(R.id.login_email)).perform(ViewActions.typeText("invalid@example.com"));
                Espresso.onView(ViewMatchers.withId(R.id.login_password)).perform(ViewActions.typeText("wrongpassword"), ViewActions.closeSoftKeyboard());


                Espresso.onView(ViewMatchers.withId(R.id.login_btn)).perform(ViewActions.click());

                Espresso.onView(withText("Incorrect email or password!")).check(matches(isDisplayed()));
            });
        }
    }

    // Add more integration tests for other scenarios as needed
}
