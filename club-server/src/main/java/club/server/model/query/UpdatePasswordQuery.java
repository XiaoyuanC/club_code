package club.server.model.query;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdatePasswordQuery {
  @NotBlank
  private String newPassword;
  @NotBlank
  private String oldPassword;
}
