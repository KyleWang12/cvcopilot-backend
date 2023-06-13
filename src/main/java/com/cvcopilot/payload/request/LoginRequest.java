package com.cvcopilot.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoginRequest {
  @NotBlank
  private String email;

  @NotBlank
  private String password;

  @NotNull
  private Boolean rememberMe;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean getRememberMe() {
    return rememberMe;
  }

  public void setRememberMe(Boolean rememberMe) {
    this.rememberMe = rememberMe;
  }
}
