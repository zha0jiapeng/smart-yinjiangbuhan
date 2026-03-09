package com.ruoyi.web.controller.basic.yinjiangbuhan.utils.RSA;

import java.time.LocalDate;

/**
 * 验证结果模型
 * @author System
 * @date 2024-01-15
 */
public class ValidationResult {
    private final boolean valid;
    private final LocalDate expiryDate;    // 到期日期
    private final LocalDate currentDate;   // 当前日期
    private final long daysRemaining;      // 剩余天数（正数）或已过天数（负数）

    public ValidationResult(boolean valid, LocalDate expiryDate,
                            LocalDate currentDate, long daysRemaining) {
        this.valid = valid;
        this.expiryDate = expiryDate;
        this.currentDate = currentDate;
        this.daysRemaining = daysRemaining;
    }

    public boolean isValid() {
        return valid;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public long getDaysRemaining() {
        return daysRemaining;
    }

    @Override
    public String toString() {
        return "ValidationResult{" +
                "valid=" + valid +
                ", expiryDate=" + expiryDate +
                ", currentDate=" + currentDate +
                ", daysRemaining=" + daysRemaining +
                '}';
    }
}