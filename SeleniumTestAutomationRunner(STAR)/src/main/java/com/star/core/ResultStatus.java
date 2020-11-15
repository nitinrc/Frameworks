package com.star.core;

import lombok.Data;

@Data
public class ResultStatus {
    private RunStatus runStatus;
    private String failureReason;
}
