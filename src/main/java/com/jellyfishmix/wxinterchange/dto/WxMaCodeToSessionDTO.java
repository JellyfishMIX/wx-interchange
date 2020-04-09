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
    private boolean success;
    private Integer stateCode;
    private String stateInfo;
    private CodeToSessionSuccessResponse codeToSessionSuccessResponse;
    private CodeToSessionErrorResponse codeToSessionErrorResponse;

    /**
     * codeToSession成功时构造方法
     * @param stateCode
     * @param stateInfo
     * @param codeToSessionSuccessResponse
     */
    public WxMaCodeToSessionDTO(Integer stateCode, String stateInfo, CodeToSessionSuccessResponse codeToSessionSuccessResponse) {
        this.success = true;
        this.stateCode = stateCode;
        this.stateInfo = stateInfo;
        this.codeToSessionSuccessResponse = codeToSessionSuccessResponse;
    }

    /**
     * codeToSession失败时构造方法
     * @param stateCode
     * @param stateInfo
     * @param codeToSessionErrorResponse
     */
    public WxMaCodeToSessionDTO(Integer stateCode, String stateInfo, CodeToSessionErrorResponse codeToSessionErrorResponse) {
        this.success = false;
        this.stateCode = stateCode;
        this.stateInfo = stateInfo;
        this.codeToSessionErrorResponse = codeToSessionErrorResponse;
    }

    @Override
    public String toString() {
        return "WxMaCodeToSessionDTO{" +
                "stateCode=" + stateCode +
                ", stateInfo='" + stateInfo + '\'' +
                ", codeToSessionSuccessResponse=" + codeToSessionSuccessResponse +
                ", codeToSessionErrorResponse=" + codeToSessionErrorResponse +
                '}';
    }
}
