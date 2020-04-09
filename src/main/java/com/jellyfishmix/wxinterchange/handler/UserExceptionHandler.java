package com.jellyfishmix.wxinterchange.handler;

import com.jellyfishmix.wxinterchange.exception.UserException;
import com.jellyfishmix.wxinterchange.utils.ResultVOUtil;
import com.jellyfishmix.wxinterchange.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author JellyfishMIX
 * @date 2020/4/8 9:24 下午
 */
@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(value = UserException.class)
    @ResponseBody
    public ResultVO handleUserException(UserException userException) {
        return ResultVOUtil.fail(userException.getStateCode(), userException.getStateInfo());
    }
}
