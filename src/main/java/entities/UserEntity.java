package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Класс, представляющий объект базы данных(пользователя) в виде Java-объекта.
 * @autor Арсений Порядин, Добровицкий Дмитрий
 * @version 1.0
 */
@Entity
@Table(name = "ПОЛЬЗОВАТЕЛЬ", schema = "s242419", catalog = "studs")
public class UserEntity implements Serializable {
    /** Идентификатор пользователя */
    private int id;
    /** Имя пользователя */
    private String name;
    /** Фамилия пользователя */
    private String surname;
    /** Отчество пользователя */
    private String middleName;
    /** Пол пользователя */
    private String sex;
    /** Дата рождения пользователя */
    private Date birthDate;
    /** Мобильный телефон пользователя */
    private String mobileTelephone;
    /** Эл. почта пользователя */
    private String email;
    /** Идентификатор льготы пользователя */
    private int preferenceId;
    /** Пароль пользователя */
    private String password;

    /** Создает нового пустого пользователя
     *  @see UserEntity#UserEntity(int, String, String, String, String, String, Date, String, String, int)
     */
    public UserEntity() {}

    /** Создает нового пользователя с заданными параметрами
     *  @see UserEntity#UserEntity()
     */
    public UserEntity(int id, String password, String name, String surname, String middleName, String sex, Date birthDate, String mobileTelephone, String email, int preferenceId) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.sex = sex;
        this.birthDate = birthDate;
        this.mobileTelephone = mobileTelephone;
        this.email = email;
        this.preferenceId = preferenceId;
    }

    /**
     * @return Идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ид", nullable = false)
    public int getId() {
        return id;
    }

    /**
     * Функция для определения идентификатора пользователя
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Пароль пользователя
     */
    @Column(name = "пароль", length = 30)
    public String getPassword() {
        return password;
    }

    /**
     * Функция для определения пароля пользователя
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return Имя пользователя
     */
    @Basic
    @Column(name = "имя", nullable = false, length = 30)
    public String getName() {
        return name;
    }

    /**
     * Функция для определения имени пользователя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Фамилию пользователя
     */
    @Basic
    @Column(name = "фамилия", nullable = false, length = 50)
    public String getSurname() {
        return surname;
    }

    /**
     * Функция для определения фамилии пользователя
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return Отчество пользователя
     */
    @Basic
    @Column(name = "отчество", nullable = true, length = 50)
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Функция для определения отчества пользователя
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return Пол пользователя
     */
    @Basic
    @Column(name = "пол", nullable = false, length = 1)
    public String getSex() {
        return sex;
    }

    /**
     * Функция для определения пола пользователя
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return Дату рождения пользователя
     */
    @Basic
    @Column(name = "дата_рождения", nullable = false)
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Функция для определения даты рождения пользователя
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return Мобильный телефон пользователя
     */
    @Basic
    @Column(name = "моб_телефон", nullable = true, length = 12)
    public String getMobileTelephone() {
        return mobileTelephone;
    }

    /**
     * Функция для определения мобильного телефона пользователя
     */
    public void setMobileTelephone(String mobileTelephone) {
        this.mobileTelephone = mobileTelephone;
    }

    /**
     * @return Эл. почту пользователя
     */
    @Basic
    @Column(name = "эл_почта", nullable = true, length = 50)
    public String getEmail() {
        return email;
    }

    /**
     * Функция для определения эл. почты пользователя
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return Идентификатор льготы пользователя
     */
    @Basic
    @Column(name = "ид_льготы", nullable = false)
    public int getPreferenceId() {
        return preferenceId;
    }

    /**
     * Функция для определения льготы пользователя
     */
    public void setPreferenceId(int preferenceId) {
        this.preferenceId = preferenceId;
    }

    /**
     * @return Boolean значение, равны ли сравниваемые объекты
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (preferenceId != that.preferenceId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null) return false;
        if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
        if (birthDate != null ? !birthDate.equals(that.birthDate) : that.birthDate != null) return false;
        if (mobileTelephone != null ? !mobileTelephone.equals(that.mobileTelephone) : that.mobileTelephone != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;

        return true;
    }

    /**
     * @return Хэш-код объекта UserEntity
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (mobileTelephone != null ? mobileTelephone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + preferenceId;
        return result;
    }
}
