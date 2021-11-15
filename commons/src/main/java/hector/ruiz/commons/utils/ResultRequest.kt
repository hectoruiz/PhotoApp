package hector.ruiz.commons.utils

abstract class ResultRequest {

    sealed class Success : ResultRequest() {
        object Add : Success()
        object Remove : Success()
    }

    sealed class Error : ResultRequest() {
        object Get : Error()
        object Add : Error()
        object Remove : Error()
        object Request : Error()
    }
}
