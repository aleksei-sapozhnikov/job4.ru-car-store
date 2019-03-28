package carstore.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;


/**
 * User object. For authentication and info.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name = "users")
public class User {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(User.class);
    /**
     * Unique id.
     */
    private long id;
    /**
     * User login to authenticate in system.
     */
    private String login;
    /**
     * User password to authenticate in system.
     */
    private String password;
    /**
     * User phone.
     */
    private String phone;

    /**
     * Creates new User object by given parameters.
     *
     * @param login    Login.
     * @param password Password.
     * @param phone    Phone.
     * @return New object.
     */
    public static User of(String login, String password, String phone) {
        var user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setPhone(phone);
        return user;
    }

    /* * * * * * * * * * * * * * * * * *
     * equals(), hashCode(), toString()
     * * * * * * * * * * * * * * * * * */


    /* * * * * * * * * * * *
     * getters and setters
     * * * * * * * * * * * */

    /**
     * Returns id.
     *
     * @return Value of id field.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public long getId() {
        return this.id;
    }

    /**
     * Sets id value.
     *
     * @param id Value to set.
     */
    public User setId(long id) {
        this.id = id;
        return this;
    }

    /**
     * Returns login.
     *
     * @return Value of login field.
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * Sets login value.
     *
     * @param login Value to set.
     */
    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    /**
     * Returns password.
     *
     * @return Value of password field.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets password value.
     *
     * @param password Value to set.
     */
    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Returns phone.
     *
     * @return Value of phone field.
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Sets phone value.
     *
     * @param phone Value to set.
     */
    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
