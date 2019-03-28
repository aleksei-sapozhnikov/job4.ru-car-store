package carstore.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class UserTest {

    @Test
    public void whenCreatedUsingStaticOfMethodThenGetValuesRight() {
        var user = User.of("login", "password", "phone");
        // initialized by default
        assertEquals(user.getId(), 0);
        // defined params
        assertEquals("login", user.getLogin());
        assertEquals("password", user.getPassword());
        assertEquals("phone", user.getPhone());
    }

    @Test
    public void whenValuesSetBySettersThenGetNewValues() {
        var user = User.of("login", "password", "phone");
        // initialized by default
        user.setId(56);
        assertEquals(56, user.getId());
        // defined params
        user.setLogin("newLogin");
        assertEquals(user.getLogin(), "newLogin");
        user.setPassword("newPassword");
        assertEquals(user.getPassword(), "newPassword");
        user.setPhone("newPhone");
        assertEquals(user.getPhone(), "newPhone");
    }

}