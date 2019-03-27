package beans;

import dao.GenericDao;
import entities.FeedbackEntity;
import requestHelpers.FeedbackInfo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("placeFeedback")
public class FeedbackBean {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response placeFeedback(final FeedbackInfo input) {

        FeedbackEntity feedbackEntity = new FeedbackEntity(input.name, input.email, input.message);

        GenericDao<FeedbackEntity, Integer> feedbackDao = new GenericDao<>(FeedbackEntity.class);

        feedbackDao.create(feedbackEntity);

        return Response.ok().entity("Report feedback sent").build();
    }
}
