package club.server.model.query;

import club.server.model.entity.StudentInfoEntity;
import lombok.Data;

@Data
public class StudentInfoQuery extends PageQuery<StudentInfoEntity> {
    private String studentNo;
    private String name;
    private String sex;
    private String college;
    private String major;
}
