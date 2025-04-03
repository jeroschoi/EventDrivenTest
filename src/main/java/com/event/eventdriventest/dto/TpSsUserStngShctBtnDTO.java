package com.event.eventdriventest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Builder
public class TpSsUserStngShctBtnDTO {
    private String cvsSiteCd;
    private String posNo;
    private String userStngSpCd;
    private int btnSeqNo;
    private String btnNm;
    private String shctDistnVal;
    private String otherRefVal;
    private String regUserId;
    private Timestamp regDttm;
    private String lastModUserId;
    private Timestamp lastModDttm;
}
