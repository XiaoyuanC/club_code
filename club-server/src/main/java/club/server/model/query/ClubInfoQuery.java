package club.server.model.query;
import lombok.Data;
@Data
public class ClubInfoQuery extends PageQuery{
  private String clubName;
  private String clubCode;
  private String department;
  private String clubPrincipal;
}
