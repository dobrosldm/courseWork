package requestHelpers;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;

@XmlRootElement
public class ProfileDataInfo {
    public ProfileDataInfo() {
    }

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
