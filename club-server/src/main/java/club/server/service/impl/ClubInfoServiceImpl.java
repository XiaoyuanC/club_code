package club.server.service.impl;

import club.common.utils.R;
import club.common.utils.StringUtil;
import club.server.dao.ClubInfoDao;
import club.server.model.entity.ClubInfoEntity;
import club.server.model.entity.ClubMemberEntity;
import club.server.model.query.ClubInfoQuery;
import club.server.service.ClubInfoService;
import club.server.service.ClubManagerService;
import club.server.service.ClubMemberService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/27 19:23
 */
@Service("clubInfoService")
public class ClubInfoServiceImpl extends ServiceImpl<ClubInfoDao, ClubInfoEntity> implements ClubInfoService {
    @Autowired
    private ClubMemberService clubMemberService;
    @Autowired
    private ClubManagerService clubManagerService;
    @Override
    public R addClub(ClubInfoEntity entity) {
        //Query data
        QueryWrapper<ClubInfoEntity> qw = new QueryWrapper<>();
        qw.eq("club_code",entity.getClubCode());
        qw.or().eq("club_name",entity.getClubName());
        if(this.count(qw)>0){
            return R.error("The same club code or club name already exists");
        }
        entity.setClubExit("1");
        if (this.save(entity)) {
            return R.success("Data added successfully");
        }
        return R.error("Data added failing");
    }

    @Override
    @Transactional
    public R deleteClubById(String id) {
        //Check if there are any members
        QueryWrapper<ClubMemberEntity> qw = new QueryWrapper<>();
        qw.eq("club_code",id);
        if(clubMemberService.count(qw)>0){
            return R.error("There are club members in this club and cannot be deleted");
        }
        //Delete manager account

        ClubInfoEntity byId = this.getById(id);
        if (byId == null) {
            return R.error("No corresponding primary key information");
        }
        if (this.removeById(id)) {
            return R.success("Successfully deleted data");
        }
        return R.success("Failed to delete data");
    }

    @Override
    @Transactional
    public R updateClubById(ClubInfoEntity entity) {
        if (StringUtil.isNullOrEmpty(entity.getClubCode())) {
            return R.error("Please bring the primary key parameter access");
        }
        ClubInfoEntity byId = this.getById(entity.getClubCode());
        if (byId == null) {
            return R.error("No corresponding primary key information");
        }
        //Check whether the name can be modified
        if(!byId.getClubName().equals(entity.getClubName())){
            QueryWrapper<ClubInfoEntity> qw = new QueryWrapper<>();
            qw.eq("club_name",entity.getClubCode());
            if(this.count(qw)>0){
                return R.error("Modification failed, the same club name already exists");
            }
        }

        entity.setCreateDate(null);
        entity.setUpdateDate(null);
        if (this.updateById(entity)) {
            return R.success("Modified data successfully");
        }
        return R.error("Modified data failing");
    }

    @Override
    public R getClubInfoById(String id) {
        ClubInfoEntity byId = this.getById(id);
        return R.success(byId);
    }

    @Override
    public R getClubInfoByClubCode(String clubCode) {
        return R.success(this.getById(clubCode));
    }

    @Override
    public R getAllEntity() {
        List<ClubInfoEntity> list = this.list();
        return R.success(list);
    }

    @Override
    public R getAllClubMap() {
        QueryWrapper<ClubInfoEntity> qw = new QueryWrapper<>();
        //Primary key number, name
        qw.select("club_code", "club_name");
        List<Map<String, String>> maps = new ArrayList<>();
        this.list(qw).forEach(item -> {
            Map<String, String> obj = new HashMap<>();
            obj.put("key", item.getClubCode());
            obj.put("value", item.getClubName());
            maps.add(obj);
        });
        return R.success(maps);
    }

    @Override
    public R queryClubInfo(ClubInfoQuery entity) {
        LambdaQueryWrapper<ClubInfoEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(StringUtil.notNullOrEmpty(
                entity.getClubCode()),
                ClubInfoEntity::getClubCode,
                entity.getClubCode());
        qw.like(StringUtil.notNullOrEmpty(
                entity.getClubName()),
                ClubInfoEntity::getClubName,
                entity.getClubName());
        qw.like(StringUtil.notNullOrEmpty(
                entity.getDepartment()),
                ClubInfoEntity::getDepartment,
                entity.getDepartment());
        qw.like(StringUtil.notNullOrEmpty(
                entity.getClubPrincipal()),
                ClubInfoEntity::getClubPrincipal,
                entity.getClubPrincipal());
        Page page = this.page(entity.getIPage(), qw);
        return R.success(page);
    }

    @Override
    public R getAllClubInfoToOption() {
        List<ClubInfoEntity> list = this.list();
        List<Object> result = new ArrayList<>();
        list.forEach(item -> {
            Map<String, String> obj = new HashMap<>();
            obj.put(item.getClubCode(), item.getClubName());
            result.add(obj);
        });
        return R.success(result);
    }

    @Override
    public int addClubPeople(String clubCode) {
        return this.baseMapper.addClubPeople(clubCode);
    }

    @Override
    public int subClubPeople(String clubCode) {
        return this.baseMapper.subClubPeople(clubCode);
    }
}
