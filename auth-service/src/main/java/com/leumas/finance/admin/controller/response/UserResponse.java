package com.leumas.finance.admin.controller.response;

import lombok.Builder;

@Builder
public record UserResponse(Long id, String name, String email) {
}
