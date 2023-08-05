package com.yupi.springbootinit.service.impl;

import static com.yupi.springbootinit.constant.UserConstant.USER_LOGIN_STATE;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.constant.CommonConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.mapper.UserMapper;
import com.yupi.springbootinit.model.dto.user.UserQueryRequest;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.model.enums.UserRoleEnum;
import com.yupi.springbootinit.model.vo.LoginUserVO;
import com.yupi.springbootinit.model.vo.UserVO;
import com.yupi.springbootinit.service.UserService;
import com.yupi.springbootinit.utils.NameUtils;
import com.yupi.springbootinit.utils.SqlUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * 用户服务实现
 *
 * @author 小鹿
 * @from /
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "yupi";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (userAccount.intern()) {
            // 账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            // 3. 插入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            // 设置随机名字
            String name = new NameUtils().getNameString();
            user.setUserName(name);
            user.setUserAvatar("data:image/jpeg;base64,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");
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getId();
        }
    }

    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = this.baseMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 3. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        return this.getLoginUserVO(user);
    }

    @Override
    public LoginUserVO userLoginByMpOpen(WxOAuth2UserInfo wxOAuth2UserInfo, HttpServletRequest request) {
        String unionId = wxOAuth2UserInfo.getUnionId();
        String mpOpenId = wxOAuth2UserInfo.getOpenid();
        // 单机锁
        synchronized (unionId.intern()) {
            // 查询用户是否已存在
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("unionId", unionId);
            User user = this.getOne(queryWrapper);
            // 被封号，禁止登录
            if (user != null && UserRoleEnum.BAN.getValue().equals(user.getUserRole())) {
                throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "该用户已被封，禁止登录");
            }
            // 用户不存在则创建
            if (user == null) {
                user = new User();
                user.setUnionId(unionId);
                user.setMpOpenId(mpOpenId);
                user.setUserAvatar(wxOAuth2UserInfo.getHeadImgUrl());
                user.setUserName(wxOAuth2UserInfo.getNickname());
                boolean result = this.save(user);
                if (!result) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, "登录失败");
                }
            }
            // 记录用户的登录态
            request.getSession().setAttribute(USER_LOGIN_STATE, user);
            return getLoginUserVO(user);
        }
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    /**
     * 获取当前登录用户（允许未登录）
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUserPermitNull(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            return null;
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        return this.getById(userId);
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return isAdmin(user);
    }

    @Override
    public boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }

    /**
     * 用户注销
     *
     * @param request
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public List<UserVO> getUserVO(List<User> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = userQueryRequest.getId();
        String unionId = userQueryRequest.getUnionId();
        String mpOpenId = userQueryRequest.getMpOpenId();
        String userName = userQueryRequest.getUserName();
        String userProfile = userQueryRequest.getUserProfile();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(unionId), "unionId", unionId);
        queryWrapper.eq(StringUtils.isNotBlank(mpOpenId), "mpOpenId", mpOpenId);
        queryWrapper.eq(StringUtils.isNotBlank(userRole), "userRole", userRole);
        queryWrapper.like(StringUtils.isNotBlank(userProfile), "userProfile", userProfile);
        queryWrapper.like(StringUtils.isNotBlank(userName), "userName", userName);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }
}
