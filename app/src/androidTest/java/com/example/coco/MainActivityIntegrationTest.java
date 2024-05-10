package com.example.coco;

import android.content.Intent;
import android.os.SystemClock;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityIntegrationTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }
    @Test
    public void testLoginSuccess() {
        // Type email and password
        onView(withId(R.id.login_email)).perform(typeText("user1@gmail.com"));
        onView(withId(R.id.login_password)).perform(typeText("12345678"), closeSoftKeyboard());

        // Click login button
        onView(withId(R.id.login_btn)).perform(click());



        // Wait for a brief moment to ensure the login message is displayed before proceeding
        SystemClock.sleep(2000); // Adjust the delay as needed

        // Wait for the toast message to appear
//        onView(withText("Login Successfully !")).check(matches(isDisplayed()));

        // Verify the intent to start the Home activity
        Intent homeIntent = new Intent(ApplicationProvider.getApplicationContext(), Home.class);
        homeIntent.putExtra("userRole", "user");


    }

    @Test
    public void testLoginFailureInvalidCredentials() {

        onView(withId(R.id.login_email)).perform(typeText("invalid@example.com"));
        onView(withId(R.id.login_password)).perform(typeText("wrongpassword"), closeSoftKeyboard());


        onView(withId(R.id.login_btn)).perform(click());

//        onView(withText("Incorrect email or password!")).check(matches(isDisplayed()));
    }

}
