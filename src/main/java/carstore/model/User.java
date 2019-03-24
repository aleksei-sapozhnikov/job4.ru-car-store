package carstore.model;

import carstore.model.car.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static carstore.model.User.Params.*;

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
    public static User from(Map<String, String> params) {
        var user = new User();
        user.setLogin(params.get(LOGIN.v()));
        user.setPassword(params.get(PASSWORD.v()));
        user.setPhone(params.get(PHONE.v()));
        return user;
    }

    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(User.class);
    /**
     * Unique id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;
    /**
     * User login to authenticate in system.
     */
    @Column(name = "login", unique = true)
    private String login;
    /**
     * User password to authenticate in system.
     */
    @Column(name = "password")
    private String password;
    /**
     * User phone.
     */
    @Column(name = "phone")
    private String phone;
    /**
     * List of cars the user sells.
     */
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Car> cars = new HashSet<>();

    /**
     * Parameters defining user.
     */
    public enum Params {
        LOGIN("login"),
        PASSWORD("password"),
        PHONE("phone");

        private String value;

        Params(String value) {
            this.value = value;
        }

        public String v() {
            return this.value;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(login, user.login)
                && Objects.equals(password, user.password)
                && Objects.equals(phone, user.phone)
                && Objects.equals(cars, user.cars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, phone, cars);
    }

    /**
     * Returns id.
     *
     * @return Value of id field.
     */
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

    /**
     * Returns cars.
     *
     * @return Value of cars field.
     */
    public Set<Car> getCars() {
        return this.cars;
    }

    /**
     * Sets cars value.
     *
     * @param cars Value to set.
     */
    public User setCars(Set<Car> cars) {
        this.cars = cars;
        return this;
    }
}
