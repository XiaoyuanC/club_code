package club.server.configuration;

import club.server.Interceptors.CheckAdminLoginInterceptor;
import club.server.Interceptors.CheckManagerLoginInterceptor;
import club.server.Interceptors.CheckMemberLoginInterceptor;
import club.server.Interceptors.CheckUserLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Interceptor, identity verification
 */
@Configuration
public class ServerConfig implements WebMvcConfigurer {
    @Autowired
    private CheckMemberLoginInterceptor checkMemberLoginInterceptor;
    @Autowired
    private CheckAdminLoginInterceptor checkAdminLoginInterceptor;
    @Autowired
    private CheckManagerLoginInterceptor checkManagerLoginInterceptor;
    @Autowired
    private CheckUserLoginInterceptor checkUserLoginInterceptor;

    //Interceptor configuration
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //Member interception
        InterceptorRegistration member
                = registry.addInterceptor(checkMemberLoginInterceptor);
        member.addPathPatterns("/api/member/**");
        //Administrator interception
        InterceptorRegistration admin
                = registry.addInterceptor(checkAdminLoginInterceptor);
        admin.addPathPatterns("/api/admin/**");

        //Manager interception
        InterceptorRegistration manager
                = registry.addInterceptor(checkManagerLoginInterceptor);
        manager.addPathPatterns("/api/manager/**");
        //Login User interception
        InterceptorRegistration user = registry.addInterceptor(checkUserLoginInterceptor);
        user.addPathPatterns("/api/user/**");
    }
}
