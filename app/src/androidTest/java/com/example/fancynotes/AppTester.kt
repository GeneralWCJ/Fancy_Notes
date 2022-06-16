package com.example.fancynotes

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fancynotes.data.NoteDao
import com.example.fancynotes.data.NoteRoomDatabase
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppTester : TestCase() {
    private lateinit var noteDao: NoteDao
    private lateinit var db: NoteRoomDatabase

    @Before
    fun setup() {
        //      launchActivity<MainActivity>()

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val noteScenario = launchFragmentInContainer<FragmentNotesList>()

    }

    @Test
    fun navigatetoNote() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val noteListScenario = launchFragmentInContainer<FragmentNotesList>()

        noteListScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.noteHolder))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click())
            )

        assertEquals(navController.currentDestination?.id, R.id.fragmentIndividualNote)
    }

    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, NoteRoomDatabase::class.java
        ).build()
        noteDao = db.itemDao()
    }
}