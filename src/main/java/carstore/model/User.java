package carstore.model;

import carstore.model.car.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.List;

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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_car",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id"))
    private List<Car> cars;

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
    public List<Car> getCars() {
        return this.cars;
    }

    /**
     * Sets cars value.
     *
     * @param cars Value to set.
     */
    public User setCars(List<Car> cars) {
        this.cars = cars;
        return this;
    }
}
