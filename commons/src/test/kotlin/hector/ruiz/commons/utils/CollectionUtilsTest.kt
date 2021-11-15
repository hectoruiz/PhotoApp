package hector.ruiz.commons.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class CollectionUtilsTest {

    @Test
    fun `list to mutableList`() {
        assertEquals(mutableList, CollectionUtils.listToMutableList(list))
    }

    @Test
    fun `mutableList to list`() {
        assertEquals(list, CollectionUtils.mutableListToList(mutableList))
    }

    private companion object {
        val mutableList = mutableListOf(2, 3)
        val list = listOf(2, 3)
    }
}
