package beans;

import dao.GenericDao;
import entities.FeedbackEntity;
import org.json.simple.JSONObject;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Stateless
@Path("placeFeedback")
public class FeedbackBean {

    @Inject
    FeedbackSender feedbackSender = new FeedbackSender();

    @POST
    @Produces("application/json")
    public JSONObject placeFeedback(@FormParam("name") String name,
                              @FormParam("email") String email,
                              @FormParam("message") String message) {

        FeedbackEntity feedbackEntity = new FeedbackEntity(name, email, message);

        GenericDao<FeedbackEntity, Integer> feedbackDao = new GenericDao<>(FeedbackEntity.class);

        JSONObject responseJSON = new JSONObject();

        feedbackDao.create(feedbackEntity);

        feedbackSender.notify(name);

        responseJSON.put("description", "Report feedback sent");

        return responseJSON;
    }
}
