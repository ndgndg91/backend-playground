package com.ndgndg91.pointapi.entity

enum class TransactionType(val code: Int) {
    GRANT(1),
    EXCHANGE_KRW(2),
    EXCHANGE_COUPON(3),
    EXPIRED(10),
    RECLAIM(11),
    COMPACTION_DEBIT(40),
    COMPACTION_CREDIT(41);

    companion object {
        fun fromDbValue(dbValue: Int): TransactionType {
            return entries.firstOrNull { it.code == dbValue }
                ?: throw RuntimeException("Cannot find transaction type with code $dbValue")
        }
    }
}