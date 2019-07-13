package start;

import java.util.Calendar;

import common.collection.Int2ObjectHashMap;
import common.marshal.Octets;
import gen.cmd.Utils;
import io.netty.channel.nio.NioEventLoopGroup;
import net.WebManager;
import net.CabManager;

import org.apache.log4j.PropertyConfigurator;


public class Main {

    public static void main(String[] args) {

    	try {
    		/*
    		byte[] bytes= new byte[6];
    		bytes[0] = 0X12;
    		bytes[1] = 0X09;
    		bytes[2] = 0X12;
    		bytes[3] = 0X11;
    		bytes[4] = 0X1D;
    		bytes[5] = 0X1F;
    		Octets os = new Octets();
    		for (int i=0; i<bytes.length; i++) {
    			System.out.println(bytes[i]);
    			os.writeByte(bytes[i]);
    		}
    		
    		long time = Utils.unmarshalDate(os);
    		
    		Octets os2 = new Octets();
    		Utils.marshalDate(time, os2);
    		
    		long time2 = Utils.unmarshalDate(os2);
    		*/
    		
	        Config.getInstance().load(args[0]);

	        PropertyConfigurator.configure(args[1]);
	        
	        startNetwork();

    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }

    private static void startNetwork() {

        NioEventLoopGroup worker = new NioEventLoopGroup();

        Config conf = Config.getInstance();

        {
        	mongo.Module.INSTANCE.start(conf.getMongoConf());
        }
        
        {
            
              conf.getWebConf().init(worker, WebManager.getCoderFactory(), 
            		WebManager.getDispatcher(), new Int2ObjectHashMap<>());
            WebManager.start(conf.getWebConf());
            
        }

        {
            conf.getCabConf().init(worker, CabManager.getCoderFactory(), 
            		CabManager.getDispatcher(), new Int2ObjectHashMap<>());
            CabManager.start(conf.getCabConf());
        }
        
    }
}
