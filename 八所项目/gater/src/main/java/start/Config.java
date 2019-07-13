package start;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.CabManager;
import net.WebManager;

import java.io.FileReader;

public class Config {
    private static final Config instance = new Config();
    private final WebManager.Conf webConf = new WebManager.Conf();
    private final CabManager.Conf cabConf = new CabManager.Conf();
    private final mongo.Module.Conf mongoConf = new mongo.Module.Conf();
    
    private int serverId;

    public static Config getInstance() {
        return instance;
    }

    public WebManager.Conf getWebConf() {
        return webConf;
    }

    public CabManager.Conf getCabConf() {
        return cabConf;
    }
    
    public mongo.Module.Conf getMongoConf() {
        return mongoConf;
    }

    public int getServerId() {
        return serverId;
    }

    public void load(String jsonFile) throws Exception {
    	JsonObject jo = (JsonObject) new JsonParser().parse(new FileReader(jsonFile));

        serverId = jo.get("serverId").getAsInt();
        webConf.parse(jo.getAsJsonObject("webClient"));
        cabConf.parse(jo.getAsJsonObject("cabClient"));
        mongoConf.parse(jo.getAsJsonObject("mongoClient"));
    }
}