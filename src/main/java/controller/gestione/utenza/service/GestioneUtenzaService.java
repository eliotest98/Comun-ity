package controller.gestione.utenza.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import model.Utente;

/**
 * Inteface implemented in GestioneUtenzaServiceImpl.
 */
public interface GestioneUtenzaService {

  /**
   * Registers a new user into the system.
   *
   * @param utente user into the db
   * @return true if account can be added, false if not or the email is just registered.
   * @throws IOException //
   * @throws IllegalArgumentException //
   */
  boolean registerAccount(Utente utente)
      throws IOException, IllegalArgumentException, ExecutionException, InterruptedException;

  /**
   * Remove the user account with the given email from the db.
   *
   * @param email is the email of the user to remove from the db.
   * @return true if the user has been removed correctly.
   */
  boolean removeUtente(String email);

  /**
   * Update user datas in the db.
   *
   * @param utente is the User Object already stored in the db to update.
   * @return true if the user datas have been updated correctly.
   */
  boolean updateUtente(Utente utente);

  /**
   * Checks if an account exists in the database.
   *
   * @param email referring to the account to search for
   * @return true if the account exists, false if not
   */
  boolean doesUserExist(String email) throws InterruptedException, ExecutionException, IOException;

  /**
   * Return the account given its mail.
   *
   * @param email referring to the account to search for
   * @return the Account if it exists, null if not
   */
  Utente getAccountByEmail(String email)
      throws InterruptedException, ExecutionException, IOException;

  /**
   * Checks the credentials of the client before insert it into the db.
   *
   * @param email       is the email of the client
   * @param password is the password to update
   * @return true ifc regex check is valid, false if not.
   * @throws IllegalArgumentException //
   * @throws IOException //
   * @throws ExecutionException //
   * @throws InterruptedException //
   */
  boolean checkCredentials(String email, String password)
      throws IllegalArgumentException, InterruptedException, ExecutionException, IOException;

  /**
   * Changes the password of an account.
   *
   * @param email    of the account
   * @param password updated
   * @return true after the change has taken place
   */
  boolean changePassword(String email, String password);

  /**
   * Checks if the user is an Admin.
   *
   * @param utente is the registred user into the DataBase.
   * @return true if the following account is a Comun-ity Admin.
   */
  boolean isAdmin(Utente utente);

  /**
   * Checks if the user is a Pro.
   *
   * @param utente is the registred user into the DataBase.
   * @return true if the following account is a certified pro.
   */
  boolean isPro(Utente utente);

  /**
   * Retrieve all the Users from the db.
   *
   * @return a List of Utente that contains all the users.
   */
  List<Utente> getAllUsers();

  /**
   * Retrieve all the Admins' email from the db.
   *
   * @return a List of all Admins' email.
   */
  List<String> getAllAdminsEmails();

  /**
   * Implements a research filter based on the email. As the Admin types in, the filter updates the
   * users' list each time a charachter is typed.
   *
   * @param email is the typed part of the email to base the research on.
   * @return List of Utente that contains users with the matching email characters.
   */
  List<Utente> searchUser(String email);

  /**
   * Ban a user from accessing the system.
   *
   * @param email is the email of the user to ban.
   * @return true if user is banned correctly.
   */
  boolean banUser(String email);

  /**
   * Temporarily ban a user from accessing the system.
   *
   * @param email    is the email of the user to ban.
   * @param duration is the date until the user is banned.
   * @return true if user is banned correctly.
   */
  boolean timeoutUser(String email, LocalDateTime duration);

}
