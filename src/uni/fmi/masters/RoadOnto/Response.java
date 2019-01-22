package uni.fmi.masters.RoadOnto;

import jade.content.AgentAction;
import jade.content.onto.annotations.Slot;


public class Response implements AgentAction {
	
	private static final long serialVersionUID = 2356L;
	
	@Slot(mandatory = true)
	private Box box ;

	public Response() {
		
	}
	
	public Response(Box v) {
		this.box = v;
	}

	public Box getBox() {
		return box;
	}

	public void setBox(Box box) {
		this.box = box;
	}
	
	public boolean hasBox() {
		return this.box != null;
	}
	
	
	

}
