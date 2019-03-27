package beans;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dao.GenericDao;
import entities.StockEntity;
import entities.StockEntityPK;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("stocks")
public class StocksBean {

    @GET
    public Response showStocks() {
        GenericDao<StockEntity, StockEntityPK> stockDao = new GenericDao<>(StockEntity.class);
        JsonArray jsonStocks = new JsonArray();
        for (StockEntity stock:
                stockDao.selectAll()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("naming", stock.getNaming());
            jsonObject.addProperty("date", "("+ stock.getStockDay() + "." + stock.getStockMonth() + ")");
            jsonObject.addProperty("addition", stock.getAddition());
            jsonStocks.add(jsonObject);
        }
        return Response.ok(jsonStocks.toString(), MediaType.APPLICATION_JSON).build();
    }
}
