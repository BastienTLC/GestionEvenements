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



public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
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
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String operation = request.getParameter("operation");
		
		if (operation.equals("listeMessageMongoDB")) {
			// récupère la liste des sportifs et l'associe ï¿½ la requï¿½te HTTP
			request.setAttribute("messages", this.getListeMessagesMongoDB());
			// forwarde la requï¿½te ï¿½ la page JSP
			getServletConfig().getServletContext().getRequestDispatcher("/afficheMessagesMongoDB.jsp")
				.forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}