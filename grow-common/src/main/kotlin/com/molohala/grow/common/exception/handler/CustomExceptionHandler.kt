package com.molohala.grow.common.exception.handler

import com.molohala.grow.common.exception.ErrorResponseEntity
import com.molohala.grow.common.exception.GlobalExceptionCode
import com.molohala.grow.common.exception.custom.CustomException
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class CustomExceptionHandler {
    @ExceptionHandler(CustomException::class)
    protected fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponseEntity> {
        return ErrorResponseEntity.responseEntity(e.exceptionCode)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponseEntity> {
        val message = StringBuilder()
        e.bindingResult.allErrors.forEach { error ->
            message.append((error as FieldError).field).append(" ")
            message.append(error.defaultMessage).append(", ")
        }
        return ResponseEntity
            .status(e.statusCode)
            .body(
                ErrorResponseEntity(
                    status = e.statusCode.value(),
                    code = GlobalExceptionCode.INVALID_PARAMETER.name,
                    message = message.substring(0, message.length - 2)
                )
            )
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    protected fun handleMissingServletRequestParameterException(e: MissingServletRequestParameterException): ResponseEntity<ErrorResponseEntity> {
        return ResponseEntity
            .status(400)
            .body(
                ErrorResponseEntity(
                    status = GlobalExceptionCode.PARAMETER_NOT_FOUND.getHttpStatus().value(),
                    code = GlobalExceptionCode.PARAMETER_NOT_FOUND.name,
                    message = GlobalExceptionCode.PARAMETER_NOT_FOUND.getMessage()
                )
            )
    }

//    @ExceptionHandler(HttpMessageNotReadableException::class)
//    protected fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponseEntity> {
//        return ResponseEntity
//            .status(400)
//            .body(
//                ErrorResponseEntity(
//                status = GlobalExceptionCode.PARAMETER_NOT_FOUND.getHttpStatus().value(),
//                code = GlobalExceptionCode.PARAMETER_NOT_FOUND.name,
//                message = GlobalExceptionCode.PARAMETER_NOT_FOUND.getMessage()
//            )
//            )
//    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    protected fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ResponseEntity<ErrorResponseEntity> {
        return ResponseEntity
            .status(400)
            .body(
                ErrorResponseEntity(
                    status = GlobalExceptionCode.METHOD_NOT_SUPPORTED.getHttpStatus().value(),
                    code = GlobalExceptionCode.METHOD_NOT_SUPPORTED.name,
                    message = GlobalExceptionCode.METHOD_NOT_SUPPORTED.getMessage()
                )
            )
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    protected fun handleHttpMediaTypeNotSupportedException(e: HttpMediaTypeNotSupportedException): ResponseEntity<ErrorResponseEntity> {
        return ResponseEntity
            .status(400)
            .body(
                ErrorResponseEntity(
                    status = GlobalExceptionCode.MEDIA_TYPE_NOT_SUPPORTED.getHttpStatus().value(),
                    code = GlobalExceptionCode.MEDIA_TYPE_NOT_SUPPORTED.name,
                    message = GlobalExceptionCode.MEDIA_TYPE_NOT_SUPPORTED.getMessage()
                )
            )
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    protected fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponseEntity> {
        return ResponseEntity
            .status(400)
            .body(
                ErrorResponseEntity(
                    status = GlobalExceptionCode.MEDIA_TYPE_MISS_MATCHED.getHttpStatus().value(),
                    code = GlobalExceptionCode.MEDIA_TYPE_MISS_MATCHED.name,
                    message = GlobalExceptionCode.MEDIA_TYPE_MISS_MATCHED.getMessage()
                )
            )
    }

    @ExceptionHandler(NoResourceFoundException::class)
    protected fun handleException(e: NoResourceFoundException): ResponseEntity<ErrorResponseEntity> {
        return ResponseEntity
            .status(404)
            .body(
                ErrorResponseEntity(
                    status = GlobalExceptionCode.RESOURCE_NOT_FOUND.getHttpStatus().value(),
                    code = GlobalExceptionCode.RESOURCE_NOT_FOUND.name,
                    message = GlobalExceptionCode.RESOURCE_NOT_FOUND.getMessage()
                )
            )
    }

//    @ExceptionHandler(Exception::class)
//    protected fun handleException(e: Exception): ResponseEntity<ErrorResponseEntity> {
//        return ResponseEntity
//            .status(500)
//            .body(
//                ErrorResponseEntity(
//                status = GlobalExceptionCode.INTERNAL_SERVER.getHttpStatus().value(),
//                code = GlobalExceptionCode.INTERNAL_SERVER.name,
//                message = GlobalExceptionCode.INTERNAL_SERVER.getMessage()
//            )
//            )
//    }
}