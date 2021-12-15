package club.server.model.query;
import lombok.Data;
@Data
public class StudentClubQuery extends PageQuery<StudentClubQuery>{
  private String studentNo;
  private String name;
  private String sex;
  private String college;
  private String major;
  private String post;
  private String clubName;
  private String clubCode;
}
