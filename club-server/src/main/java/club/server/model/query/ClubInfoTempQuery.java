package club.server.model.query;

import club.server.model.entity.ClubInfoTempEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ClubInfoTempQuery extends PageQuery<ClubInfoTempEntity>{

  private String id;
  private String clubCode;
  private String clubName;
  private String department;
  private String clubPrincipal;
}
