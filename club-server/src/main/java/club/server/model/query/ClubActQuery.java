package club.server.model.query;
import lombok.Data;
@Data
public class ClubActQuery extends PageQuery{
  private Long id;
  private String actName;
  private String clubCode;
  private String clubName;
}
