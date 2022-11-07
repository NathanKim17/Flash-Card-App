package application.personalIndexCardManager;

import java.util.List;

public class Course {

	private String name;
	private String id;
	private List<String> indexCards;
	
	public Course(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getID() {
		return produceID();
	}
	
	/*
	 * Creates a unique ID based on 
	 * ascii value of name
	 * 
	 */
	private String produceID() {
		String id = "";
		char[] list = name.toCharArray();
		for (char c: list) {
			id += (int)c;
		}
		return id;
	}
}
