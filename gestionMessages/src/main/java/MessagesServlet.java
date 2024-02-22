import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mongoPojo.Message;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.Date;



public class MessagesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	// --------------
	//
	// DO - GET
	//
	// --------------
	
	
	public List<Message> getListeMessagesMongoDB() {
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
		ConnectionString connectionString = new ConnectionString("mongodb://obiwan.univ-brest.fr:27017");
		MongoClient mongoClient = MongoClients.create(connectionString);
		MongoDatabase database = mongoClient.getDatabase("ygasc").withCodecRegistry(pojoCodecRegistry);
		//System.out.println("Connexion établie\n");
		
		MongoCollection<Message> mess = database.getCollection("messages", Message.class);
		List<Message> messagesClones = new ArrayList<>();
		
		for(Message mes : mess.find()) {
			Message clone = new Message();
			clone.setContenu(mes.getContenu());
			clone.setDate(mes.getDate());
			clone.setIdEvent(mes.getIdEvent());
			clone.setIdMembre(mes.getIdMembre());
			messagesClones.add(clone);
		}
		
		return messagesClones;
	}
	
	
	public List<Message> getMessagesMongoByEventId(int idEvent) {
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
		ConnectionString connectionString = new ConnectionString("mongodb://obiwan.univ-brest.fr:27017");
		MongoClient mongoClient = MongoClients.create(connectionString);
		MongoDatabase database = mongoClient.getDatabase("ygasc").withCodecRegistry(pojoCodecRegistry);
		//System.out.println("Connexion établie\n");
		
		MongoCollection<Message> mess = database.getCollection("messages", Message.class);
		List<Message> messagesClones = new ArrayList<>();
		
		for(Message mes : mess.find()) {
			if(idEvent==mes.getIdEvent()) {
				Message clone = new Message();
				clone.setContenu(mes.getContenu());
				clone.setDate(mes.getDate());
				clone.setIdEvent(idEvent);
				clone.setIdMembre(mes.getIdMembre());
				
				messagesClones.add(clone);
			}
		}
		
		return messagesClones;
	}
	
	
	public List<Message> getMessagesMongoByMembreId(int idMembre) {
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
		ConnectionString connectionString = new ConnectionString("mongodb://obiwan.univ-brest.fr:27017");
		MongoClient mongoClient = MongoClients.create(connectionString);
		MongoDatabase database = mongoClient.getDatabase("ygasc").withCodecRegistry(pojoCodecRegistry);
		//System.out.println("Connexion établie\n");
		
		MongoCollection<Message> mess = database.getCollection("messages", Message.class);
		List<Message> messagesClones = new ArrayList<>();
		
		for(Message mes : mess.find()) {
			if(idMembre==mes.getIdMembre()) {
				Message clone = new Message();
				clone.setContenu(mes.getContenu());
				clone.setDate(mes.getDate());
				clone.setIdEvent(mes.getIdEvent());
				clone.setIdMembre(idMembre);
				
				messagesClones.add(clone);
			}
		}
		
		return messagesClones;
	}
		
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idMembre = request.getParameter("userId");
		String idEvent = request.getParameter("eventId");
			
		if (idMembre!=null) {
			List<Message> messagesClones = this.getMessagesMongoByMembreId(Integer.parseInt(idMembre));
			
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			String json = "[";
			for (Message msg : messagesClones) {
				json += "\"{\"contenu\" : \""+msg.getContenu() + "\", \"idMembre\" : \""+msg.getIdMembre()
						+ "\", \"idEvent\" : \""+msg.getIdEvent()+"\", \"date\" : \""+msg.getDate()+"\"}";
			}
			json += "]";
			out.write(json);
			out.close();
		} else if (idEvent!=null) {
			List<Message> messagesClones = this.getMessagesMongoByEventId(Integer.parseInt(idEvent));
			
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			String json = "[";
			for (Message msg : messagesClones) {
				json += "\"{\"contenu\" : \""+msg.getContenu() + "\", \"idMembre\" : \""+msg.getIdMembre()
						+ "\", \"idEvent\" : \""+msg.getIdEvent()+"\", \"date\" : \""+msg.getDate()+"\"}";
			}
			json += "]";
			out.write(json);
			out.close();
		} else {
			
			List<Message> messagesClones = this.getListeMessagesMongoDB();
			
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			String json = "[";
			for (Message msg : messagesClones) {
				json += "\"{\"contenu\" : \""+msg.getContenu() + "\", \"idMembre\" : \""+msg.getIdMembre()
						+ "\", \"idEvent\" : \""+msg.getIdEvent()+"\", \"date\" : \""+msg.getDate()+"\"}";
			}
			json += "]";
			out.write(json);
			out.close();
		}
		
	}
	
	
	// --------------
	//
	// DO - POST
	//
	// --------------
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//doGet(request, response);
		
		// Récupérer les paramètres de la requête
	    String contenuMessage = request.getParameter("contenu");
	    String idMembre = request.getParameter("userId");
	    String idEvent = request.getParameter("eventId");
	    
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date date = new Date();
	    String datePublication = dateFormat.format(date);

	    if (contenuMessage != null && idMembre != null && idEvent != null) {
	        insererMessageDansMongoDB(contenuMessage, Integer.parseInt(idMembre), Integer.parseInt(idEvent), datePublication);
	        
	    }
	}
	
	
	private boolean insererMessageDansMongoDB(String contenuMessage, int idMembre, int idEvent, String datePublication) {

	    CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
	    ConnectionString connectionString = new ConnectionString("mongodb://obiwan.univ-brest.fr:27017");
	    MongoClient mongoClient = MongoClients.create(connectionString);
	    MongoDatabase database = mongoClient.getDatabase("ygasc").withCodecRegistry(pojoCodecRegistry);


	    MongoCollection<Message> mess = database.getCollection("messages", Message.class);

	    Message nouveauMessage = new Message();
	    nouveauMessage.setContenu(contenuMessage);
	    nouveauMessage.setIdMembre(idMembre);
	    nouveauMessage.setIdEvent(idEvent);
	    nouveauMessage.setDate(datePublication);

	    mess.insertOne(nouveauMessage);
	    mongoClient.close();
	    return true;
	}


}