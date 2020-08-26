package com.kravel.server.auth.security.factory;

import com.kravel.server.auth.model.Member;
import com.kravel.server.auth.model.MemberContext;
import com.kravel.server.auth.model.RoleType;
import com.kravel.server.auth.security.util.jwt.JwtFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JwtFactoryTest {

    private static final Logger log = LoggerFactory.getLogger(JwtFactory.class);

    private MemberContext context;

    @Autowired
    private JwtFactory factory;

    @Before
    public void setUp() {
        Member member = new Member();
        member.setLoginEmail("ooeunz@gmail.com");
        member.setLoginPw("1234");
        member.setRoleType(RoleType.USER);

        this.context = MemberContext.fromMemberModel(member);
    }

    @Test
    public void TEST_JWT_GENERATE() {
        log.error(factory.generateToken(this.context));
    }
}