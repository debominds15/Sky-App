package com.sky.demo.presentation

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.sky.demo.R
import com.sky.demo.presentation.movie_list.MovieListFragment
import com.sky.demo.utility.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MovieListTest {
    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun movieActivityTest() {
        launchActivity<MovieActivity>()
    }

    @Test
    fun testFragment() {
        launchFragmentInHiltContainer<MovieListFragment>()
    }

    @Test
    fun testSearchViewFragment() {
        launchFragmentInHiltContainer<MovieListFragment>()
        onView(withId(R.id.search_movie)).perform(click())
    }

    @Test
    fun testMoviesFragment() {
        launchFragmentInHiltContainer<MovieListFragment>()
        onView(withText("Movies")).check(doesNotExist())
    }
}