package requestHelpers;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConvertInfo {
    public ConvertInfo() {
    }

    @XmlElement
    public String from;
    @XmlElement
    public String to;
    @XmlElement
    public String howMuchTo;
}
