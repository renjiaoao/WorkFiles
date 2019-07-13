package mongo;

import gen.cmd.Command;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.bson.Document;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import common.marshal.IMarshal;
import common.marshal.Octets;

public enum Module {
	INSTANCE;
	
	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;
	
	public void start(Conf conf) {
		if (mongoClient == null) {
			ServerAddress serverAddress = new ServerAddress(conf.host, conf.port);  
	        List<ServerAddress> addrs = new ArrayList<ServerAddress>();  
	        addrs.add(serverAddress);  
	   
	        MongoCredential credential = MongoCredential.createScramSha1Credential(conf.username, conf.dbname, 
	        		conf.password.toCharArray());  
	        List<MongoCredential> credentials = new ArrayList<MongoCredential>();  
	        credentials.add(credential);  
	        
	        mongoClient = new MongoClient(addrs, credentials);  
		}
		
		if (mongoDatabase == null) {
			mongoDatabase = mongoClient.getDatabase(conf.dbname); 
		}
	}
	
	public void close() {
		if (mongoClient != null) {
			mongoClient.close();
		}
	}
	
	public void insert(boolean send, byte cabId, String cabHost, String serverHost, Object obj) {
		/*String tablename = obj.getClass().getSimpleName().toLowerCase();
		MongoCollection<Document> collection = tryGetCollection(tablename);
		collection.insertOne(makeDocument(send, cabId, cabHost, serverHost, obj));*/
		
	}
	
	public void insert(boolean send, byte cabId, String cabHost, String serverHost, List<?> objs) {
		/*if (!objs.isEmpty()) {
			String tablename = objs.get(0).getClass().getSimpleName().toLowerCase();
			MongoCollection<Document> collection = tryGetCollection(tablename);
			
			List<Document> documents = new ArrayList<>(objs.size());
			for (Object obj : objs) {
				documents.add(makeDocument(send, cabId, cabHost, serverHost, obj));
			}
			
			collection.insertMany(documents);
		}*/
	}
	
	public void insert(boolean send, byte cabId, String cabHost, String serverHost, Object owner, List<?> objs) {
		/*if (!objs.isEmpty()) {
			String tablename = objs.get(0).getClass().getSimpleName().toLowerCase();
			MongoCollection<Document> collection = tryGetCollection(tablename);
			
			List<Document> documents = new ArrayList<>(objs.size());
			for (Object obj : objs) {
				Document document = makeDocument(send, cabId, cabHost, serverHost, obj);
				documents.add(fillDocument(document, owner, false));
			}
			
			collection.insertMany(documents);
		}*/
	}
	
	private MongoCollection<Document> tryGetCollection(String tablename) {
		MongoCollection<Document> collection = mongoDatabase.getCollection(tablename);
		if (collection == null) {
			mongoDatabase.createCollection(tablename);
			collection = mongoDatabase.getCollection(tablename);
			
			collection.createIndex(new Document("send", 1));
			collection.createIndex(new Document("cabId", 1));
			collection.createIndex(new Document("cabHost", 1));
			collection.createIndex(new Document("serverHost", 1));
		} 
		return collection;
	}
	

	
	private Document makeDocument(boolean send, byte cabId, String cabHost, String serverHost, Object obj) {
		Document document = new Document();
		document.append("send", send);
		document.append("removeCabId", cabId);
		document.append("removeCabHost", cabHost);
		document.append("webServerHost", serverHost);
		document.append("logTime", util.TimeUtil.getDateTime());
			
		return fillDocument(document, obj, true);
	}
	
	private Document fillDocument(Document document, Object obj, boolean hexData) {
		try {	
			Field[] fields = obj.getClass().getFields();
			for (int j = 0; j < fields.length; j++) {
				if (!Modifier.isPublic(fields[j].getModifiers())) {
					continue;
				}
				if (Modifier.isStatic(fields[j].getModifiers())) {
					continue;
				}
				
				if (List.class.isAssignableFrom(fields[j].getType())) {
					continue;
				}
				
				String name = fields[j].getName();
				Object value = fields[j].get(obj);
				document.append(name, value);
			}
			
			if (hexData) {
				if (Command.class.isAssignableFrom(obj.getClass())) {
					Octets os = new Octets();
					Command.marshal((Command)obj, os);
					document.append("hexData", os.toHexString(false));
				} else if (IMarshal.class.isAssignableFrom(obj.getClass())) {
					Octets os = new Octets();
					((IMarshal)obj).marshal(os);
					document.append("hexData", os.toHexString(false));
				}
				}
		} catch ( IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return document;
	}
	
	public static class Conf {
		public String host;
		public int port;
		public String username;
		public String password;
		public String dbname;
		
		public void parse(JsonObject jo) {
            for (Entry<String, JsonElement> e : jo.entrySet()) {
            	JsonElement v = e.getValue();
                switch (e.getKey()) {
                    case "host":
                    	host = v.getAsString();
                        break;
                    case "port":
                        port = v.getAsInt();
                        break;
                    case "username":
                    	username = v.getAsString();
                        break;
                    case "password":
                    	password = v.getAsString();
                        break;
                    case "dbname":
                    	dbname = v.getAsString();
                        break;
                }
            }
        }
	}
}
