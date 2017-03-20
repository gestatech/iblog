package com.alexkbit.iblog.services.impl;

import com.alexkbit.iblog.model.Role;
import com.alexkbit.iblog.model.User;
import com.alexkbit.iblog.repositories.api.UserRepository;
import com.alexkbit.iblog.servvices.api.exception.ServiceException;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * Test for {@link UserServiceImpl}
 */
@RunWith(EasyMockRunner.class)
public class UserServiceImplTest extends EasyMockSupport {

    private static final String USER_LOGIN = "user";
    private static final String USER_EMAIL = "email";

    @Mock
    private UserRepository userRepository;

    @TestSubject
    private UserServiceImpl userService = new UserServiceImpl();

    @Test
    public void testGetByUuid() {
        String id = UUID.randomUUID().toString();
        User user = new User();
        user.setId(id);
        expect(userRepository.findOne(id)).andReturn(user);
        replayAll();
        assertEquals(id, userService.get(id).getId());
        verifyAll();
    }

    @Test
    public void testGetByUuidNotFound() {
        expect(userRepository.findOne(anyObject())).andReturn(null);
        replayAll();
        assertNull(userService.get(UUID.randomUUID().toString()));
        verifyAll();
    }

    @Test
    public void testGetByEmail() {
        String id = UUID.randomUUID().toString();
        User user = new User();
        user.setId(id);
        user.setEmail("email");
        expect(userRepository.findByEmail(eq(user.getEmail()))).andReturn(user);
        replayAll();
        assertEquals(id, userService.getByEmail(user.getEmail()).getId());
        verifyAll();
    }

    @Test
    public void testLoadUserByUsername() {
        String id = UUID.randomUUID().toString();
        User user = new User();
        user.setId(id);
        user.setEmail("email");
        user.setLogin("login");
        user.setPassword("pass");
        user.setRole(Role.ADMIN);
        expect(userRepository.findByLoginOrEmail(eq(user.getEmail()))).andReturn(user);
        replayAll();
        assertEquals(id, userService.loadUserByUsername(user.getEmail()).getId());
        verifyAll();
    }

    @Test
    public void testLoadUserByUsernameThrow() {
        expect(userRepository.findByLoginOrEmail(anyString())).andReturn(null);
        replayAll();
        try {
            userService.loadUserByUsername("");
            fail("Should be throw exception");
        } catch (UsernameNotFoundException ex) {
            assertNotNull(ex.getMessage());
        }
        verifyAll();
    }

    @Test
    public void testGetByEmailNotFound() {
        replayAll();
        assertNull(userService.getByEmail(null));
        verifyAll();
    }

    @Test
    public void testGetByLogin() {
        String id = UUID.randomUUID().toString();
        User user = new User();
        user.setId(id);
        user.setLogin("login");
        expect(userRepository.findByLogin(eq(user.getLogin()))).andReturn(user);
        replayAll();
        assertEquals(id, userService.getByLogin(user.getLogin()).getId());
        verifyAll();
    }

    @Test
    public void testGetByLoginNotFound() {
        replayAll();
        assertNull(userService.getByLogin(null));
        verifyAll();
    }

    @Test
    public void testGetByUuidIsNull() {
        replayAll();
        assertNull(userService.get(null));
        verifyAll();
    }

    @Test
    public void testRegister() {
        User user = createUser();
        expect(userRepository.findByLogin(eq(USER_LOGIN))).andReturn(null);
        expect(userRepository.findByEmail(eq(USER_EMAIL))).andReturn(null);
        expect(userRepository.save(eq(user))).andReturn(user);
        replayAll();
        userService.register(user);
        verifyAll();
    }

    @Test
    public void testRegisterLoginException() {
        User user = createUser();
        expect(userRepository.findByLogin(eq(USER_LOGIN))).andReturn(new User());
        replayAll();
        try {
            userService.register(user);
            fail("Should throw exception.");
        } catch (ServiceException se) {
            verifyAll();
        }
    }

    @Test
    public void testRegisterEmailException() {
        User user = createUser();
        expect(userRepository.findByLogin(eq(USER_LOGIN))).andReturn(null);
        expect(userRepository.findByEmail(eq(USER_EMAIL))).andReturn(new User());
        replayAll();
        try {
            userService.register(user);
            fail("Should throw exception.");
        } catch (ServiceException se) {
            verifyAll();
        }
    }

    private User createUser() {
        User user = new User();
        user.setLogin(USER_LOGIN);
        user.setEmail(USER_EMAIL);
        return user;
    }

}