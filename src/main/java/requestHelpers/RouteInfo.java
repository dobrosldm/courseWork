package requestHelpers;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RouteInfo {
    public RouteInfo() {
    }

    @XmlElement
    public String from;
    @XmlElement
    public String to;
    @XmlElement
    public String spend;
}
