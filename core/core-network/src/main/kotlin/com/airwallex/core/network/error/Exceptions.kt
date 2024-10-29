package com.airwallex.core.network.error

import com.airwallex.core.basic.model.AirwallexException

data class AuthRequestCancelledException(val errorMsg: String?, val exception: Throwable) : AirwallexException(errorMsg, exception)

/*************************************************************
 *                     network exceptions                    *
 *************************************************************/
open class NetworkException(
    override val cause: Exception? = null,
    override val message: String = cause?.message ?: "Network exception."
) : AirwallexException(message, cause) {
    data class ConnectionException(override val cause: Exception) : NetworkException(cause)
    data class OtherException(override val cause: Exception) : NetworkException(cause)
}

/*************************************************************
 *                  client request exceptions                *
 *************************************************************/
sealed class RequestException(
    override val cause: Exception? = null,
    override val message: String = cause?.message ?: "Client request exception."
) : AirwallexException(message, cause) {
    data object CancelledException : RequestException(message = "Client request is cancelled.")
    data class OtherException(override val cause: Exception) : RequestException(cause)
}

/*****************************************************************************
 *         remote server response exceptions(for both graphql/restful        *
 *****************************************************************************/
sealed class ResponseException(
    override val cause: Exception? = null,
    override val message: String = cause?.message ?: "Remote server Response exception."
) : AirwallexException(message, cause) {
    data object BadRequestException : ResponseException(message = "Bad request.")
    data class UnprocessableEntityException(val displayErrorMessage: String = "Unprocessable entity.") : ResponseException(message = "Unprocessable entity.")
    data object UnauthenticatedException : ResponseException(message = "Access is unauthenticated.")
    data object UnauthenticatedGraphqlException : ResponseException(message = "Graphql Access is unauthenticated.")
    data object ForbiddenException : ResponseException(message = "Access is forbidden.")
    data object NotFoundException : ResponseException(message = "Resource is not found.")
    data object MethodNotAllowedException : ResponseException(message = "Method is not allowed.")
    data object ConflictException : ResponseException(message = "Request has a conflict with existing resource.")
    data object UnsupportedMediaTypeException : ResponseException(message = "Unsupported media type.")
    data object TooManyRequestsException : ResponseException(message = "Too many requests.")
    data object InternalServerErrorException : ResponseException(message = "Internal server error.")
    data object ServiceNotAvailableException : ResponseException(message = "Service not available.")

    data class OtherException(override val cause: Exception) : ResponseException(cause)

    companion object {
        /**
         * map response exception from http status code
         */
        fun matchFrom(statusCode: Int): ResponseException? =
            when (statusCode) {
                400 -> BadRequestException
                401 -> UnauthenticatedException
                403 -> ForbiddenException
                404 -> NotFoundException
                405 -> MethodNotAllowedException
                409 -> ConflictException
                415 -> UnsupportedMediaTypeException
                422 -> UnprocessableEntityException()
                429 -> TooManyRequestsException
                500 -> InternalServerErrorException
                in 501..504 -> ServiceNotAvailableException
                else -> null
            }

        /**
         * map response exception from graphql error code
         */
        fun matchFrom(errorCode: String, displayErrorMessage: String? = null): ResponseException? =
            when (errorCode) {
                "GRAPHQL_PARSE_FAILED", "GRAPHQL_VALIDATION_FAILED", "BAD_USER_INPUT", "BAD_REQUEST" -> BadRequestException
                "UNAUTHENTICATED" -> UnauthenticatedGraphqlException
                "FORBIDDEN" -> ForbiddenException
                "INTERNAL_SERVER_ERROR" -> InternalServerErrorException
                "CONFLICT" -> ConflictException
                "UNPROCESSABLE_ENTITY" -> UnprocessableEntityException(displayErrorMessage ?: "Unprocessable entity.")
                "VALIDATION_ERROR" ->  UnprocessableEntityException(displayErrorMessage ?: "Unprocessable entity.")
                "TOO_MANY_REQUESTS" -> TooManyRequestsException
                else -> null
            }
    }
}
