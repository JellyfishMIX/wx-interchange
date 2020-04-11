package com.jellyfishmix.wxinterchange.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jellyfishmix.wxinterchange.config.WxMaConfig;
import com.jellyfishmix.wxinterchange.dto.WxMaCodeToSessionDTO;
import com.jellyfishmix.wxinterchange.enums.WxMaAuthEnum;
import com.jellyfishmix.wxinterchange.query.wxma.CodeToSessionErrorResponse;
import com.jellyfishmix.wxinterchange.query.wxma.CodeToSessionSuccessResponse;
import com.jellyfishmix.wxinterchange.service.WxMaAuthService;
import com.jellyfishmix.wxinterchange.utils.StateCodeEnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author JellyfishMIX
 * @date 2020/4/9 4:28 下午
 */
@Service("wxMaAuthService")
@Slf4j
public class WxMaAuthServiceImpl implements WxMaAuthService {
    @Autowired
    private WxMaConfig wxMaConfig;

    /**
     * 微信code换取openid和session_key
     * @param code
     * @return
     */
    @Override
    public WxMaCodeToSessionDTO codeToSession(String code) {
        log.info("进入codeToSession方法");
        log.info("code = {}", code);

        // 拉取access_token和openid, method = GET
        String url = "https://api.weixin.qq.com/sns/jscode2session?"
                + "appid=" + wxMaConfig.getAppid()
                + "&secret=" + wxMaConfig.getSecret()
                + "&js_code=" + code
                + "&grant_type=authorization_code";

        RestTemplate restTemplate = new RestTemplate();
        // access_token和openid在responseJSON中，responseJSON是一个JSON字符串
        String responseJSON = restTemplate.getForObject(url, String.class);
        log.info("response={}", responseJSON);

        WxMaCodeToSessionDTO wxMaCodeToSessionDTO = null;

        // responseJSON为失败的逻辑

        if (responseJSON == null) {
            CodeToSessionErrorResponse codeToSessionErrorResponse = new CodeToSessionErrorResponse(WxMaAuthEnum.NULL);
            wxMaCodeToSessionDTO = new WxMaCodeToSessionDTO(codeToSessionErrorResponse.getErrcode(), codeToSessionErrorResponse.getErrmsg(), codeToSessionErrorResponse);
            return wxMaCodeToSessionDTO;
        }

        String errorCode = "errcode";
        if (responseJSON.contains(errorCode)) {
            CodeToSessionErrorResponse codeToSessionErrorResponse = null;
            JSONObject jsonObject = new JSONObject(responseJSON);
            Integer errcode = jsonObject.getInt("errcode");
            WxMaAuthEnum wxMaAuthEnum = StateCodeEnumUtil.getByStateCode(errcode, WxMaAuthEnum.class);
            if (wxMaAuthEnum == null) {
                codeToSessionErrorResponse = new CodeToSessionErrorResponse(WxMaAuthEnum.UNKNOWN);
            } else {
                codeToSessionErrorResponse = new CodeToSessionErrorResponse(wxMaAuthEnum);
            }
            wxMaCodeToSessionDTO = new WxMaCodeToSessionDTO(codeToSessionErrorResponse.getErrcode(), codeToSessionErrorResponse.getErrmsg(), codeToSessionErrorResponse);
            return wxMaCodeToSessionDTO;
        }

        // responseJSON为成功的逻辑

        CodeToSessionSuccessResponse codeToSessionSuccessResponse = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            codeToSessionSuccessResponse = objectMapper.readValue(responseJSON, CodeToSessionSuccessResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        wxMaCodeToSessionDTO = new WxMaCodeToSessionDTO(WxMaAuthEnum.REQUEST_SUCCESS.getStateCode(), WxMaAuthEnum.REQUEST_SUCCESS.getStateMsg(), codeToSessionSuccessResponse);

        return wxMaCodeToSessionDTO;
    }
}
