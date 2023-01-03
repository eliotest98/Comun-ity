package controller.gestioneUtenza;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import model.Utente;

public interface GestioneUtenzaService {

	/**
	 * Registers a new user into the system.
	 * @param utente user into the db
	 * @return true if is possible to insert the
	 * account after check of the credentials,
	 * false when fails the check of the
	 * credentials or the email is just registered
	 * @throws IOException //
	 * @throws IllegalArgumentException //
	 */
	boolean registerAccount(Utente utente)
			throws IOException, IllegalArgumentException,
			ExecutionException, InterruptedException;
	
	/**
	 * Remove the user account with the given email from the db.
	 * @param email is the email of the user to remove from the db.
	 * @return true if the user has been removed correctly.
	 */
	boolean removeUtente(String email);
	
	/**
	 * Checks the credentials of the client before insert it into the db.
	 * @param email is the email of the client
	 * @param newPassword is the password to update
	 * @return true if the credentials follow the regular-expression,
	 * false when something is typed wrong.
	 * @throws IllegalArgumentException //
	 * @throws IOException 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	boolean checkCredentials(String email, String password)
			throws IllegalArgumentException, InterruptedException, ExecutionException, IOException;

	/**
	 * Checks if an account exists in the database.
	 * @param email referring to the account to search for
	 * @return true if the account exists, false if not
	 */
	boolean searchAccountByEmail(String email)
			throws InterruptedException, ExecutionException, IOException;

	/**
	 * Return the account given its mail.
	 * @param email referring to the account to search for
	 * @return the Account if it exists, null if not
	 */
	Utente getAccountByEmail(String email)
			throws InterruptedException, ExecutionException, IOException;

	/**
	 * Changes the password of an account.
	 * @param email of the account
	 * @param password updated
	 * @return true after the change has taken place
	 */
	boolean changePassword(String email, String password)
			throws IOException, ExecutionException, InterruptedException;

	/**
	 * Checks if the user is an Admin.
     * @param utente is the registred user into the DataBase.
     * @return true if the following account is
     * a Comun-ity Admin.
     */
	boolean IsAdmin(Utente utente);
	
	/**
	 * Checks if the user is a Pro.
     * @param utente is the registred user into the DataBase.
     * @return true if the following account is
     * a certified pro.
     */
	boolean isPro(Utente utente);

	/**
	 * Retrieve all the Admins' email from the db.
     * @return a List of all Admins' email.
     */
	List<String> getAllAdminsEmails();
	
	/**
	 * Retrieve all the Users from the db.
     * @return a List of Utente that contains all the users.
     */
	List<Utente> getAllUsers();
	
	List<Utente> searchUser(String email);
	
}
