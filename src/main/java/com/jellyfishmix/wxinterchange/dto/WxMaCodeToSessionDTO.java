package com.jellyfishmix.wxinterchange.dto;

import com.jellyfishmix.wxinterchange.query.wxma.CodeToSessionErrorResponse;
import com.jellyfishmix.wxinterchange.query.wxma.CodeToSessionSuccessResponse;
import lombok.Data;

/**
 * @author JellyfishMIX
 * @date 2020/4/9 4:00 下午
 * code-openid换取逻辑DTO
 */
@Data
public class WxMaCodeToSessionDTO {
    private Boolean success;
    private Integer stateCode;
    private String stateMsg;
    private CodeToSessionSuccessResponse codeToSessionSuccessResponse;
    private CodeToSessionErrorResponse codeToSessionErrorResponse;

    /**
     * codeToSession成功时构造方法
     * @param stateCode
     * @param stateMsg
     * @param codeToSessionSuccessResponse
     */
    public WxMaCodeToSessionDTO(Integer stateCode, String stateMsg, CodeToSessionSuccessResponse codeToSessionSuccessResponse) {
        this.success = true;
        this.stateCode = stateCode;
        this.stateMsg = stateMsg;
        this.codeToSessionSuccessResponse = codeToSessionSuccessResponse;
    }

    /**
     * codeToSession失败时构造方法
     * @param stateCode
     * @param stateMsg
     * @param codeToSessionErrorResponse
     */
    public WxMaCodeToSessionDTO(Integer stateCode, String stateMsg, CodeToSessionErrorResponse codeToSessionErrorResponse) {
        this.success = false;
        this.stateCode = stateCode;
        this.stateMsg = stateMsg;
        this.codeToSessionErrorResponse = codeToSessionErrorResponse;
    }

    @Override
    public String toString() {
        return "WxMaCodeToSessionDTO{" +
                "success=" + success +
                ", stateCode=" + stateCode +
                ", stateMsg='" + stateMsg + '\'' +
                ", codeToSessionSuccessResponse=" + codeToSessionSuccessResponse +
                ", codeToSessionErrorResponse=" + codeToSessionErrorResponse +
                '}';
    }
}
