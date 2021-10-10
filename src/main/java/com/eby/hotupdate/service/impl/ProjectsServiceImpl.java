package com.eby.hotupdate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eby.hotupdate.mapper.ProjectsMapper;
import com.eby.hotupdate.mapper.UserMapper;
import com.eby.hotupdate.pojo.Projects;
import com.eby.hotupdate.pojo.User;
import com.eby.hotupdate.service.IProjectsService;
import com.eby.hotupdate.utils.RedisUtils;
import org.springframework.beans.BeanInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author eby
 * @since 2021-09-30
 */
@Service
public class ProjectsServiceImpl extends ServiceImpl<ProjectsMapper, Projects> implements IProjectsService{

    @Resource
    private ProjectsMapper projectsMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Override
    public String insert(String token, String name, String description) {
        User user = (User) redisUtils.get("token:user:"+token);//获取user对象
        String url = UUID.randomUUID().toString().replace("-","");

        Projects projects = new Projects();
        projects.setBelong(user.getId());//设置所属
        projects.setDescription(""+description);//设置描述
        projects.setCreateTime(new Date());//创建日期
        projects.setName(name);//设置名字
        projects.setUrl(url);//设置url
        projects.setUpdateTime(new Date());
//        projects.set

        if(projectsMapper.insert(projects)==1)return url;
        return null;
    }
}