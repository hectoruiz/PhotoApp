package hector.ruiz.photoapp.ui

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import hector.ruiz.photoapp.R
import hector.ruiz.photoapp.ui.utils.EspressoTools
import hector.ruiz.photoapp.ui.utils.launchFragmentInHiltContainer
import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class PhotoFragmentTest {

    init {
        MockKAnnotations.init(this)
    }

    @Before
    fun setUp() {
        launchFragmentInHiltContainer<PhotoFragment>()
    }

    @Test
    fun testFragmentWhenIsCreated() {
        EspressoTools.viewDisplayed(R.id.photoCamera)
        EspressoTools.viewNotDisplayed(R.id.photoListProgress)
        EspressoTools.viewNotDisplayed(R.id.photoList)
    }
}
