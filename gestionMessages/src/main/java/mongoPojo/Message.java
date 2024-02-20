package mongoPojo;

import java.util.Objects;

public class Message {

		private String contenu;
		
		private String date;
		
		private int idEvent;
		
		private int idMembre;

		public String getContenu() {
			return contenu;
		}

		public void setContenu(String contenu) {
			this.contenu = contenu;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public int getIdEvent() {
			return idEvent;
		}

		public void setIdEvent(int idEvent) {
			this.idEvent = idEvent;
		}

		public int getIdMembre() {
			return idMembre;
		}

		public void setIdMembre(int idMembre) {
			this.idMembre = idMembre;
		}

		public Message(String contenu, String date, int idEvent,int idMembre) {
			super();
			this.contenu = contenu;
			this.date = date;
			this.idEvent = idEvent;
			this.idMembre = idMembre;
		}
		
		public Message() {
			super();
		}

		@Override
		public int hashCode() {
			return Objects.hash(contenu, date, idEvent,idMembre);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Message other = (Message) obj;
			return contenu == other.contenu && Objects.equals(date, other.date)
					&& Objects.equals(idEvent, other.idEvent)
					&& Objects.equals(idMembre, other.idMembre);
		}
		
		@Override
		public String toString() {
			return this.contenu + " - "+this.date + " "+this.idEvent+ " "+this.idMembre;
		}
		
}
