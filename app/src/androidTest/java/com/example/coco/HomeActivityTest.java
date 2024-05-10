package com.example.coco;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    @Rule
    public ActivityScenarioRule<Home> activityScenarioRule = new ActivityScenarioRule<>(Home.class);

    @Test
    public void testHomeActivity() {
        // Check if the views in the Home activity are displayed
        onView(withId(R.id.imageView18)).check(matches(isDisplayed()));
        onView(withId(R.id.hamburgerIcon)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView20)).check(matches(isDisplayed()));
        onView(withId(R.id.textView4)).check(matches(isDisplayed()));
        onView(withId(R.id.textView5)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView21)).check(matches(isDisplayed()));
        onView(withId(R.id.account_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.diseases_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.identifyDiseases_btn)).check(matches(isDisplayed()));

        // Perform click on account button
        onView(withId(R.id.account_btn)).perform(click());

        // Perform click on diseases button
        onView(withId(R.id.diseases_btn)).perform(click());

        // Perform click on identify diseases button
        onView(withId(R.id.identifyDiseases_btn)).perform(click());

    }
}

