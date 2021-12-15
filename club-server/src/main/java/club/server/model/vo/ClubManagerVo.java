package club.server.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClubManagerVo {
  private String id;
  private String userId;
  private String clubCode;
  private String clubName;
  private String createDate;
}
