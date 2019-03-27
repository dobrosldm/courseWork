package requestHelpers;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FeedbackInfo {
    public FeedbackInfo() {
    }

    @XmlElement
    public String name;
    @XmlElement
    public String email;
    @XmlElement
    public String message;
}
