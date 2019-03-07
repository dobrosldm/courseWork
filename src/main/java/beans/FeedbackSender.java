package beans;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.jms.*;

@ApplicationScoped
public class FeedbackSender {

    @Resource(name="jms/FeedbackPool")
    private ConnectionFactory connectionFactory;

    @Resource(name="jms/FeedbackTopic")
    private Destination destination;

    public void notify(String from) {
        try {

            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage();

            message.setStringProperty("from", from);

            producer.send(message);

            session.close();
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}