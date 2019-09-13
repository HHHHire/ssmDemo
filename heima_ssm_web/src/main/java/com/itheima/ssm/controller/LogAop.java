package com.itheima.ssm.controller;

import com.itheima.ssm.domain.SysLog;
import com.itheima.ssm.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ISysLogService sysLogService;

    // 访问时间
    private Date visiteTime;
    // 访问的类
    private Class clazz;
    // 访问的方法
    private Method method;

    // 前置通知 获取开始时间，执行的类和方法是哪一个
    @Before("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        // 开始时间
        visiteTime = new Date();
        // 要访问的类
        clazz = jp.getTarget().getClass();
        // 方法名称
        String name = jp.getSignature().getName();
        // 获取访问方法的参数
        Object[] args = jp.getArgs();
        if (args == null || args.length == 0) {
            method = clazz.getMethod(name);
        } else {
            Class[] classes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classes[i] = args[i].getClass();
            }
            clazz.getMethod(name, classes);
        }

    }

    @After("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doAfter(JoinPoint jp) throws Exception {
        // 访问时长
        long time = new Date().getTime() - visiteTime.getTime();

        String url = "";
        // 获取url
        if (clazz != null && method != null && clazz != LogAop.class) {
            // 获取类上的RequestMapping
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (classAnnotation != null){
                String[] classValue = classAnnotation.value();
                // 获取方法上的RequestMapping
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if (methodAnnotation != null){
                    String[] methodValue = methodAnnotation.value();
                    url = classValue[0] + methodValue[0];
                }
            }
        }

        // 获取IP地址
        String ip = request.getRemoteAddr();

        // 获取当前操作的用户
        // 从上下文中获取当前登陆的用户
        SecurityContext context = SecurityContextHolder.getContext();
        User user = (User) context.getAuthentication().getPrincipal();
        String username = user.getUsername();
//        request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");

        // 将日志相关信息封装到 SysLog 中
        SysLog sysLog = new SysLog();
        sysLog.setIp(ip);
        sysLog.setMethod("[类名]" + clazz.getName() + "[方法名]" + method.getName());
        sysLog.setUrl(url);
        sysLog.setUsername(username);
        sysLog.setExecutionTime(time);
        sysLog.setVisitTime(visiteTime);

        sysLogService.save(sysLog);
    }
}
