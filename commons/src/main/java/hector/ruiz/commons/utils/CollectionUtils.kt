package hector.ruiz.commons.utils

object CollectionUtils {

    fun <T> listToMutableList(list: List<T>?): MutableList<T>? =
        list?.toMutableList()

    fun <T> mutableListToList(mutableList: MutableList<T>?): List<T> =
        mutableList?.toList().orEmpty()
}
