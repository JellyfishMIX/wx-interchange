package com.jellyfishmix.wxinterchange.exception;

import com.jellyfishmix.wxinterchange.enums.CollectionEnum;
import lombok.Getter;

/**
 * @author JellyfishMIX
 * @date 2020/4/29 9:27 下午
 */
@Getter
public class CollectionException extends RuntimeException {
    private static final long serialVersionUID = 5174257541557713593L;

    private Integer stateCode;
    private String stateMsg;

    public CollectionException(CollectionEnum collectionEnum) {
        super(collectionEnum.getStateMsg());
        this.stateCode = collectionEnum.getStateCode();
        this.stateMsg = collectionEnum.getStateMsg();
    }
}
