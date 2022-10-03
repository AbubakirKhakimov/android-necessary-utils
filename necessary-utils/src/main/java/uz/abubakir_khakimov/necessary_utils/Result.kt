package uz.abubakir_khakimov.necessary_utils


data class Result <out T> (
    val status: Status,
    val data: T?,
    val message: String?,
    val error: Throwable?
    ) {

    companion object {
        fun <T> success(data: T?, msg: String? = null): Result<T> {
            return Result(Status.SUCCESS, data, msg, null)
        }

        fun <T> error(error: Throwable?, msg: String? = null): Result<T> {
            return Result(Status.ERROR, null, msg, error)
        }
    }

}