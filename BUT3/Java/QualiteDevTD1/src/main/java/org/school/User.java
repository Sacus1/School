package org.school;

public class User {

	public Object getLogin() {
		// TODO Auto-generated method stub
		// Rien d’implémenter pour l’instant :
		// votre collègue est en train de l’implémenter ;-)
		return null;
	}
	private final String firstName;
	private final String hashedPassword;

	public User(String firstName, String hashedPassword) {
		this.firstName = firstName;
		this.hashedPassword = hashedPassword;
	}

	public String firstName() {
		return firstName;
	}

	public String hashedPassword() {
		return hashedPassword;
	}
}
