package hector.ruiz.datasource.mapper

import hector.ruiz.commons.entities.Photo
import hector.ruiz.domain.PhotoUi
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class PhotoMapperTest {

    private val photoMapper by lazy {
        PhotoMapper()
    }

    @Test
    fun uiModelToModel() {
        val uiModel = mockk<PhotoUi>(relaxed = true)

        val result = photoMapper.uiModelToModel(uiModel)

        with(result) {
            assertEquals(uiModel.id, this.id)
            assertEquals(uiModel.date, this.date)
            assertEquals(uiModel.path, this.path)
        }
    }

    @Test
    fun modelToUiModel() {
        val model = mockk<Photo>(relaxed = true)

        val result = photoMapper.modelToUiModel(model)

        with(result) {
            assertEquals(model.id, this.id)
            assertEquals(model.date, this.date)
            assertEquals(model.path, this.path)
        }
    }
}
