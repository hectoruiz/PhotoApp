package hector.ruiz.commons.base

interface BaseMapper<UiModel, Model> {

    fun uiModelToModel(uiModel: UiModel): Model

    fun modelToUiModel(model: Model): UiModel
}
