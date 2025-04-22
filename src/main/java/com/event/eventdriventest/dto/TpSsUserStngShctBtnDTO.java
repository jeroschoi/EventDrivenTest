package com.event.eventdriventest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class TpSsUserStngShctBtnDTO {
    private String regUserId;
    private LocalDateTime regDttm;
    private String lastModUserId;
    private LocalDateTime lastModDttm;
}
