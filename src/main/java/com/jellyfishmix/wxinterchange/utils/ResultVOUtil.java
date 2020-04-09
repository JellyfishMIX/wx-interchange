package com.jellyfishmix.wxinterchange.utils;

import com.jellyfishmix.wxinterchange.vo.ResultVO;

/**
 * @author JellyfishMIX
 * @date 2020/4/8 10:03 下午
 */
public class ResultVOUtil {
    public static ResultVO success(Integer stateCode, String stateInfo) {
        ResultVO resultVO = new ResultVO();
        resultVO.setSuccess(true);
        resultVO.setStateCode(stateCode);
        resultVO.setStateInfo(stateInfo);
        return resultVO;
    }

    public static ResultVO success(Integer stateCode, String stateInfo, Object data) {
        ResultVO resultVO = new ResultVO();
        resultVO.setSuccess(true);
        resultVO.setStateCode(stateCode);
        resultVO.setStateInfo(stateInfo);
        resultVO.setData(data);
        return resultVO;
    }

    public static ResultVO fail(Integer stateCode, String stateInfo) {
        ResultVO resultVO = new ResultVO();
        resultVO.setSuccess(false);
        resultVO.setStateCode(stateCode);
        resultVO.setStateInfo(stateInfo);
        return resultVO;
    }
}
