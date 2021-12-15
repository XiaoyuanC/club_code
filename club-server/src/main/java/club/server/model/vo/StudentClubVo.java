package club.server.model.vo;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentClubVo {
  //t1
  private String id;
  private String post;
  private String type;
  private String mark;
  //t3
  private String studentNo;
  private String name;
  private String sex;
  private String college;
  private String major;
  //t2
  private String clubName;
  private String clubCode;
  private Long clubPeople;
  private String department;
  private String clubPrincipal;
  private String clubDescribe;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;
}
