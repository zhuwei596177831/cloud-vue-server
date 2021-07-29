package com.example.system;

import com.example.shiroAuthencation.sessioncache.ShiroReisCache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MainApplicationTests {

    @Autowired
    private ShiroReisCache shiroReisCache;

    @Test
    void contextLoads() {
        SimpleSession simpleSession = (SimpleSession) shiroReisCache.get("cd59e408-3875-4593-ac43-473e2b32d34f");
        SimpleSession simpleSession1 = (SimpleSession) shiroReisCache.get("483da75c-5652-410e-9cde-98efd7df4289");
        System.out.println(simpleSession.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));
        System.out.println(simpleSession1.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));
    }

}
