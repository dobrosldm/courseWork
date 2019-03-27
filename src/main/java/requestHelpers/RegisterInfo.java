package requestHelpers;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;

@XmlRootElement
public class RegisterInfo {
    public RegisterInfo() {
    }

    public RegisterInfo(String email, String firstPassword, String secondPassword, String name, String surname, String middlename, String sex, Date birthDate, String mobileTelephone, String preference) {
        this.email = email;
        this.firstPassword = firstPassword;
        this.secondPassword = secondPassword;
        this.name = name;
        this.surname = surname;
        this.middlename = middlename;
        this.sex = sex;
        this.birthDate = birthDate;
        this.mobileTelephone = mobileTelephone;
        this.preference = preference;
    }

    @XmlElement
    public String email;
    @XmlElement
    public String firstPassword;
    @XmlElement
    public String secondPassword;
    @XmlElement
    public String name;
    @XmlElement
    public String surname;
    @XmlElement
    public String middlename;
    @XmlElement
    public String sex;
    @XmlElement
    public Date birthDate;
    @XmlElement
    public String mobileTelephone;
    @XmlElement
    public String preference;
}
