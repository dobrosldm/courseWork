package requestHelpers;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProfilePasswordInfo {
    public ProfilePasswordInfo() {
    }

    @XmlElement
    public String oldPassword;
    @XmlElement
    public String firstPassword;
    @XmlElement
    public String secondPassword;
}
