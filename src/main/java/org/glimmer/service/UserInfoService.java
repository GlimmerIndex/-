package org.glimmer.service;

import org.glimmer.domain.ResponseResult;

public interface UserInfoService {
    public ResponseResult getInfoByName(String userId);
}
