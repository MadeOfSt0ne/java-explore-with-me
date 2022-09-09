package ru.practicum.explore.exception;

public enum ErrorStatus {
    _100_CONTINUE,
    _101_SWITCHING_PROTOCOLS,
    _102_PROCESSING,
    _103_CHECKPOINT,
    _200_OK,
    _201_CREATED,
    _202_ACCEPTED,
    _203_NON_AUTHORITATIVE_INFORMATION,
    _204_NO_CONTENT,
    _205_RESET_CONTENT,
    _206_PARTIAL_CONTENT,
    _207_MULTI_STATUS,
    _208_ALREADY_REPORTED,
    _226_IM_USED,
    _300_MULTIPLE_CHOICES,
    _301_MOVED_PERMANENTLY,
    _302_FOUND,
    _302_MOVED_TEMPORARILY,
    _303_SEE_OTHER,
    _304_NOT_MODIFIED,
    _305_USE_PROXY,
    _307_TEMPORARY_REDIRECT,
    _308_PERMANENT_REDIRECT,
    _400_BAD_REQUEST,
    _401_UNAUTHORIZED,
    _402_PAYMENT_REQUIRED,
    _403_FORBIDDEN,
    _404_NOT_FOUND,
    _405_METHOD_NOT_ALLOWED,
    _406_NOT_ACCEPTABLE,
    _407_PROXY_AUTHENTICATION_REQUIRED,
    _408_REQUEST_TIMEOUT,
    _409_CONFLICT,
    _410_GONE,
    _411_LENGTH_REQUIRED,
    _412_PRECONDITION_FAILED,
    _413_PAYLOAD_TOO_LARGE,
    _413_REQUEST_ENTITY_TOO_LARGE,
    _414_URI_TOO_LONG,
    _414_REQUEST_URI_TOO_LONG,
    _415_UNSUPPORTED_MEDIA_TYPE,
    _416_REQUESTED_RANGE_NOT_SATISFIABLE,
    _417_EXPECTATION_FAILED,
    _418_I_AM_A_TEAPOT,
    _419_INSUFFICIENT_SPACE_ON_RESOURCE,
    _420_METHOD_FAILURE,
    _421_DESTINATION_LOCKED,
    _422_UNPROCESSABLE_ENTITY,
    _423_LOCKED,
    _424_FAILED_DEPENDENCY,
    _425_TOO_EARLY,
    _426_UPGRADE_REQUIRED,
    _428_PRECONDITION_REQUIRED,
    _429_TOO_MANY_REQUESTS,
    _431_REQUEST_HEADER_FIELDS_TOO_LARGE,
    _451_UNAVAILABLE_FOR_LEGAL_REASONS,
    _500_INTERNAL_SERVER_ERROR,
    _501_NOT_IMPLEMENTED,
    _502_BAD_GATEWAY,
    _503_SERVICE_UNAVAILABLE,
    _504_GATEWAY_TIMEOUT,
    _505_HTTP_VERSION_NOT_SUPPORTED,
    _506_VARIANT_ALSO_NEGOTIATES,
    _507_INSUFFICIENT_STORAGE,
    _508_LOOP_DETECTED,
    _509_BANDWIDTH_LIMIT_EXCEEDED,
    _510_NOT_EXTENDED,
    _511_NETWORK_AUTHENTICATION_REQUIRED
}
