package com.task_manager.zhsaidk.dto;

import lombok.Value;

@Value
public class ResponseRequest {
    String access_token;
    String refresh_token;
}
