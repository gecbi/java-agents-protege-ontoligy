package uni.fmi.masters.RoadOnto;

import java.util.ArrayList;
import java.util.List;


import jade.content.Concept;
import jade.content.onto.annotations.Slot;

public class Box implements Concept {
	private static final long serialVersionUID = 682220847015981325L;

	private String id;
	private List<Object> data;
	
	public Box() {
		this.data = new ArrayList<Object>();
	}
	public Box(String id, List<Object> data) {
		this.id = id;
		this.data = data;
	}
	
	public String getId() {
		return id;
	}
	
	@Slot(mandatory = true)
	public void setId(String id) {
		this.id = id;
	}
	
	public List<Object> getData() {
		return data;
	}
	
	@Slot(mandatory = false)
	public void setData(List<Object> data) {
		this.data = data;
	}
	
	public boolean hasId() {
		return id != null;
	}
	
	public boolean hasData() {
		return !(data == null || data.isEmpty());
	}
	
	@Override
	public String toString() {
		return "{ " + (hasId() && !id.isEmpty() ? id : "\"\"") + " = " + (hasData() ? data.toString() : "[]") + " }"; 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
        if (this == obj) return true;
        if (obj instanceof Box) {
        	Box v = (Box)obj;
        	if (id != v.id && data != v.data && id != null && data != null) {
        		return id.equals(v.id) && data.equals(v.data);
        	} else {
        		return true;
        	}
        }
        return false;
	}
	
	@Override
	public int hashCode() {
		int i = 31;
		i = 17 * i + (id != null ? id.hashCode() : 0);
		i = 17 * i + (data != null ? data.hashCode() : 0);
		return i;
	}
	
}
