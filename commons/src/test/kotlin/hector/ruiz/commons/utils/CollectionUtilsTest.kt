package hector.ruiz.commons.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class CollectionUtilsTest {

    @Test
    fun listToMutableList() {
        assertEquals(mutableList, CollectionUtils.listToMutableList(list))
    }

    @Test
    fun mutableListToList() {
        assertEquals(list, CollectionUtils.mutableListToList(mutableList))
    }

    private companion object {
        val mutableList = mutableListOf(2, 3)
        val list = listOf(2, 3)
    }
}
