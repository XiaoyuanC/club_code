package club.server.model.query;
import lombok.Data;
@Data
public class ClubManagerQuery extends PageQuery{
  private String id;
  private String userId;
  private String clubCode;
  private String clubName;
}
