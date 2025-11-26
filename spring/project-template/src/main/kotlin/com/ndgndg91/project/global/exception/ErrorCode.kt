package com.ndgndg91.project.global.exception

enum class ErrorCode(val code: Int, val message: String) {
    // COMMON
    UNKNOWN_ERROR(-1, "알 수 없는 에러입니다."),
    UNKNOWN_CODE(-2, "알 수 없는 코드입니다."),
    UNKNOWN_EXTERNAL_ERROR(-3, "알 수 없는 에러입니다."),
    MAINTENANCE(-4, "점검이 진행중입니다."),
    SUCCESS(0, "성공"),
    PROCESSING(1, "진행중 입니다"),
    INVALID_PARAMETER(41, "유효하지 않은 파라메터입니다."),
    KYC_ERROR(50, "KYC 에러"),
    RESTRICTED_BY_KYC(51, "RESTRICTED BY KYC"),
    FORBIDDEN_BY_KYC(52, "FORBIDDEN BY KYC"),
    RESTRICTED_BY_KYC_LEVEL(53, "RESTRICTED BY KYC LEVEL"),
    BANK_ACCOUNT_ERROR(60, "원화 계좌 에러"),
    UNREGISTERED_BANK_ACCOUNT_STATUS(61, "원화 계좌 미등록"),
    IN_TRANSITION(62, "전환 중"),
    IN_DENY(63, "거절 중"),
    NOT_SUPPORTED_VERSION(70, "지원하지 않는 버전"),
    NOT_SUPPORTED_FUNCTION(71, "개선 중인 기능입니다. 나중에 다시 이용해주세요."),
    MFA_ERROR(80, "MFA 에러"),
    MFA_VERIFICATION_FAILED(81, "검증에 실패했습니다."),
    MFA_INVALID_PARAMETER(82, "유효하지 않은 파라메터입니다."),
    MFA_CI_MISMATCHED(83, "CI 불일치"),
    MFA_SECURITY_VIOLATION(84, "데이터 위변조 감지")
    ;

    companion object {
        fun findByCode(code: Int): ErrorCode {
            return entries.find { it.code == code }
                ?: throw UnknownCodeException(IllegalArgumentException(code.toString()))
        }
    }
}
