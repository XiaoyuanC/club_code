package club.server.service.impl;

import club.common.utils.R;
import club.common.utils.StringUtil;
import club.server.dao.ClubActTempDao;
import club.server.model.entity.ClubActEntity;
import club.server.model.entity.ClubActTempEntity;
import club.server.model.entity.ClubInfoEntity;
import club.server.model.query.ClubActTempQuery;
import club.server.model.query.ClubInfoTempQuery;
import club.server.model.vo.ClubInfoTempVo;
import club.server.service.ClubActService;
import club.server.service.ClubActTempService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/27 22:33
 */
@Service("clubActTemp")
public class ClubActTempServiceImpl extends ServiceImpl<ClubActTempDao, ClubActTempEntity> implements ClubActTempService {
    @Autowired
    private ClubActService clubActService;

    @Override
    public R actTempAdd(ClubActTempEntity entity) {
        entity.setMark("0");
        boolean save = this.save(entity);
        if (save) {
            return R.success("Data submitted successfully");
        }
        return R.error("Data submitted failing");
    }

    @Override
    public R getEntityPendingByClubCodeId(String clubCode) {
        LambdaQueryWrapper<ClubActTempEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(StringUtil.notNullOrEmpty(clubCode), ClubActTempEntity::getClubCode, clubCode);
        qw.eq(true, ClubActTempEntity::getMark, "0");
        List<ClubActTempEntity> list = this.list(qw);
        return R.success(list);
    }

    @Override
    public R getAllEntityByClubCodeId(String clubCode) {
        LambdaQueryWrapper<ClubActTempEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(StringUtil.notNullOrEmpty(clubCode), ClubActTempEntity::getClubCode, clubCode);
        List<ClubActTempEntity> list = this.list(qw);
        return R.success(list);
    }

    @Override
    public R getClubActTemp(ClubActTempQuery query) {
        LambdaQueryWrapper<ClubActTempEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(true, ClubActTempEntity::getClubCode, query.getClubCode());
        qw.like(StringUtil.notNullOrEmpty(query.getActName()), ClubActTempEntity::getActName, query.getActName());
        //Insert time descending
        qw.orderByDesc(ClubActTempEntity::getCreateDate);
        return R.success(this.page(query.getIPage(), qw));
    }

    @Override
    public R queryClubActTemp(ClubActTempQuery query) {
        QueryWrapper<ClubActEntity> qw = new QueryWrapper<>();
        qw.eq(StringUtil.notNullOrEmpty(query.getClubCode()),"t1.club_code", query.getClubCode());
        qw.eq(StringUtil.notNullOrEmpty(query.getId()),"t1.id", query.getId());
        qw.eq(true,"t1.mark", 0);
        qw.eq(true,"t2.club_exit", 1);
        qw.like(StringUtil.notNullOrEmpty(query.getActName()),"t1.act_name", query.getActName());
        qw.like(StringUtil.notNullOrEmpty(query.getClubName()),"t2.club_name", query.getClubName());
        qw.orderByDesc("t1.create_date");
        IPage iPage = this.baseMapper.queryClubActTemp(query.getIPage(), qw);
        return R.success(iPage);
    }

    @Override
    public R pass(String id) {
        //get information
        ClubActTempEntity byId = this.getById(id);
        if (byId == null) {
            return R.error("No corresponding primary key information");
        }
        //Modify information
        LambdaUpdateWrapper<ClubActTempEntity> uw = new LambdaUpdateWrapper<>();
        uw.eq(StringUtil.notNullOrEmpty(id), ClubActTempEntity::getId, id);
        uw.eq(true, ClubActTempEntity::getMark, "0");
        uw.set(true, ClubActTempEntity::getMark, "1");
        uw.set(true, ClubActTempEntity::getUpdateDate, LocalDateTime.now());
        //Insert data
        ClubActEntity clubActEntity = new ClubActEntity();
        clubActEntity.setId(id);
        clubActEntity.setActName(byId.getActName());
        clubActEntity.setActContent(byId.getActContent());
        clubActEntity.setClubCode(byId.getClubCode());
        //data migration
        if (clubActService.save(clubActEntity)&&this.update(uw)) {
            return R.success("Successful operation");
        }
        throw new RuntimeException();
    }

    @Override
    public R forbid(String id) {
        //get information
        ClubActTempEntity byId = this.getById(id);
        if (byId == null) {
            return R.error("No corresponding primary key information");
        }
        //Modify information
        LambdaUpdateWrapper<ClubActTempEntity> uw = new LambdaUpdateWrapper<>();
        uw.eq(StringUtil.notNullOrEmpty(id), ClubActTempEntity::getId, id);
        uw.eq(true, ClubActTempEntity::getMark, "0");
        uw.set(true, ClubActTempEntity::getMark, "-1");
        uw.set(true, ClubActTempEntity::getUpdateDate, LocalDateTime.now());
        //data migration
        if (this.update(uw)) {
            return R.success("Successful operation");
        }
        return R.error("Unsuccessful operation");
    }
}
