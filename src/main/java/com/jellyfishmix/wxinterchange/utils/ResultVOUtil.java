package com.jellyfishmix.wxinterchange.utils;

import com.jellyfishmix.wxinterchange.vo.ResultVO;

/**
 * @author JellyfishMIX
 * @date 2020/4/8 10:03 下午
 */
public class ResultVOUtil {
    public static ResultVO success(Integer stateCode, String stateMsg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setSuccess(true);
        resultVO.setStateCode(stateCode);
        resultVO.setStateMsg(stateMsg);
        return resultVO;
    }

    public static ResultVO success(Integer stateCode, String stateMsg, Object data) {
        ResultVO resultVO = new ResultVO();
        resultVO.setSuccess(true);
        resultVO.setStateCode(stateCode);
        resultVO.setStateMsg(stateMsg);
        resultVO.setData(data);
        return resultVO;
    }

    public static ResultVO fail(Integer stateCode, String stateMsg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setSuccess(false);
        resultVO.setStateCode(stateCode);
        resultVO.setStateMsg(stateMsg);
        return resultVO;
    }
}
