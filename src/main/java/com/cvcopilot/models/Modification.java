package com.cvcopilot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "modifications", indexes = {
    @Index(name = "user_id_index", columnList = "userId", unique = false),
    @Index(name = "last_updated_index", columnList = "lastUpdated", unique = false)
})
public class Modification {

  @Id
  private String modificationId;

  @NotBlank
  private String result;

  public Modification(String modificationId, String result, Long userId, Long lastUpdated) {
    this.modificationId = modificationId;
    this.result = result;
    this.userId = userId;
    this.lastUpdated = lastUpdated;
  }

  @NotBlank
  private Long userId;

  @NotBlank
  private Long lastUpdated;

  public Modification() {

  }

  public String getModificationId() {
    return modificationId;
  }

  public void setModificationId(String modificationId) {
    this.modificationId = modificationId;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(Long lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Modification that)) {
      return false;
    }
    return Objects.equals(modificationId, that.modificationId) && Objects.equals(result, that.result)
        && Objects.equals(userId, that.userId) && Objects.equals(lastUpdated, that.lastUpdated);
  }

  @Override
  public int hashCode() {
    return Objects.hash(modificationId, result, userId, lastUpdated);
  }

  @Override
  public String toString() {
    return "Modification{" +
        "modificationId='" + modificationId + '\'' +
        ", result='" + result + '\'' +
        ", userId=" + userId +
        ", lastUpdated=" + lastUpdated +
        '}';
  }
}
