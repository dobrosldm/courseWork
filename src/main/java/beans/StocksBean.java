package beans;

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
        JsonObject jsonObject = new JsonObject();
        for (StockEntity stock:
                stockDao.selectAll()) {
            jsonObject.addProperty(stock.getNaming() + " (" + stock.getStockDay() + "." + stock.getStockMonth() +
                    ")", String.valueOf(stock.getAddition()));
        }
        return Response.ok(jsonObject.toString(), MediaType.APPLICATION_JSON).build();
    }
}
