package com.assignment.musiclibrary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePasswordRequest
{
    @NotBlank
    @Size(min = 6)
    private String oldPassword;
    @NotBlank
    @Size(min = 6)
    private String newPassword;

    public UpdatePasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    // Getter for oldPassword
    public String getOldPassword() {
        return oldPassword;
    }

    // Setter for oldPassword
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    // Getter for newPassword
    public String getNewPassword() {
        return newPassword;
    }

    // Setter for newPassword
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
