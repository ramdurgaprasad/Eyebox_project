package model;

import lombok.Data;

@Data
public class UpdatePassword {
    private String newPassword;
    private String oldPassword;

}
