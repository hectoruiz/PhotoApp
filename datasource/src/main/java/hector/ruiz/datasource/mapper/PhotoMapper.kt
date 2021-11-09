package hector.ruiz.datasource.mapper

import hector.ruiz.commons.base.BaseMapper
import hector.ruiz.commons.entities.Photo
import hector.ruiz.domain.PhotoUi

class PhotoMapper : BaseMapper<PhotoUi, Photo> {

    override fun uiModelToModel(uiModel: PhotoUi): Photo {
        return with(uiModel) {
            Photo(this.id, this.date, this.path)
        }
    }

    override fun modelToUiModel(model: Photo): PhotoUi {
        return with(model) {
            PhotoUi(this.id, this.date, this.path)
        }
    }
}
