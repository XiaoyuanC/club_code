package club.server.model.query;

import club.server.model.entity.ClubMemberEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ClubMemberQuery extends PageQuery<ClubMemberEntity>{

  private String id;
  private String sex;
  private String studentNo;
  @NotBlank
  private String clubCode;
  private String post;
}
