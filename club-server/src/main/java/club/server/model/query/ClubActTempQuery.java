package club.server.model.query;

import club.server.model.entity.ClubActTempEntity;
import lombok.Data;

@Data
public class ClubActTempQuery extends PageQuery<ClubActTempEntity>{
  private Long id;
  private String actName;
  private String clubCode;
  private String clubName;
}
