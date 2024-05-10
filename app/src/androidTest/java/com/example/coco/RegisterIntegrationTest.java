package com.example.coco;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class RegisterIntegrationTest {

    @Rule
    public ActivityScenarioRule<Register> activityRule = new ActivityScenarioRule<>(Register.class);

    @Test
    public void testRegisterButtonClicked() {

        Espresso.onView(withId(R.id.userName)).perform(ViewActions.typeText("lasindu_test"));
        Espresso.onView(withId(R.id.userPhone)).perform(ViewActions.typeText("0712345678"));
        Espresso.onView(withId(R.id.userEmail)).perform(ViewActions.typeText("Ltest@gmail.com"));
        Espresso.onView(withId(R.id.userPassword)).perform(ViewActions.typeText("lasindu123"), ViewActions.closeSoftKeyboard());

        Espresso.onView(withId(R.id.register_btn)).perform(ViewActions.click());
        Espresso.onView(withText("Registration Successful")).check(matches(isDisplayed()));
    }
}
