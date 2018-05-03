package com.wavy_app.wavytestapplication;

import android.support.test.espresso.assertion.ViewAssertions;

import com.wavy_app.wavytestapplication.ui.UserProfileActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class UserProfileActivityTest {

    @Rule
    public final ActivityRule<UserProfileActivity> main = new ActivityRule<>(UserProfileActivity.class);

    @Test
    public void launchMainScreen() {
        onView(withId(R.id.bDelete)).check(ViewAssertions.matches(isDisplayed()));
    }
}
