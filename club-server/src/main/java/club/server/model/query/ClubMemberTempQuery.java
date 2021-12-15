package club.server.model.query;

import club.server.model.entity.ClubMemberTempEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ClubMemberTempQuery extends PageQuery<ClubMemberTempEntity>{

  private String id;
  private String studentNo;
  @NotBlank
  private String clubCode;
}
