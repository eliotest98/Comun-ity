package controller.gestione.utenza.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import model.Utente;
import model.UtenteDao;

/**
 * Implementation class of GestioneUtenzaService.
 */
public class GestioneUtenzaServiceImpl implements GestioneUtenzaService {

  /**
   * exclude.
   **/
  private final UtenteDao utenteDao = new UtenteDao();

  /**
   * Empty Constructor.
   **/
  public GestioneUtenzaServiceImpl() {
  }

  /**
   * Registers a new user account.
   *
   * @param utente user into the db.
   * @return true if the operation ends in success.
   * @throws IllegalArgumentException //
   */
  @Override
  public boolean registerAccount(Utente utente) throws IllegalArgumentException {

    try {
      if (doesUserExist(utente.getMail())) {
        utenteDao.saveUtente(utente);
        return true;
      } else {
        throw new IllegalArgumentException(
            "Email già presente" + " nel DB, utilizza una nuova email");
      }
    } catch (InterruptedException | ExecutionException | IOException e) {
      e.printStackTrace();
    }

    return false;
  }

  /**
   * Registers a new user account.
   *
   * @param email is the email of the user to remove from the db.
   * @return true if the user has been removed correctly.
   */
  @Override
  public boolean removeUtente(String email) {
    return utenteDao.deleteUtente(email);
  }

  /**
   * Update user datas in the db.
   *
   * @param utente is the User Object already stored in the db to update.
   * @return true if the user datas have been updated correctly.
   */
  @Override
  public boolean updateUtente(Utente utente) {
    return utenteDao.updateUtente(utente);
  }

  /**
   * Checks if an account exists in the database.
   *
   * @param email referring to the account to search for
   * @return true if the account exists, false if not
   */
  @Override
  public boolean doesUserExist(String email)
      throws InterruptedException, ExecutionException, IOException {
    return utenteDao.findUtenteByMail(email) == null;
  }

  /**
   * This method return the account given its mail.
   *
   * @param email referring to the account to search for
   * @return Utente if it exists, null if not
   */
  @Override
  public Utente getAccountByEmail(String email) {
    return utenteDao.findUtenteByMail(email);
  }

  /**
   * Checks if the credential entered are correct.
   *
   * @param email    of the user
   * @param password of the user
   * @return true if the user credentials correspond.
   */
  @Override
  public boolean checkCredentials(String email, String password) {

    Utente utente = getAccountByEmail(email);

    if (utente == null) {
      return false;
    }

    return utente.getPassword().equals(password);

  }

  /**
   * Changes the password of an account.
   *
   * @param email    of the account
   * @param password updated
   * @return true after the change has taken place
   */
  @Override
  public boolean changePassword(String email, String password) {

    Utente utente = utenteDao.findUtenteByMail(email);

    utente.setPassword(password);

    return utenteDao.updateUtente(utente);
  }

  /**
   * Checks if the user is an Admin.
   *
   * @param utente is the registred user into the DataBase.
   * @return true if the following account is a Comun-ity Admin.
   */
  @Override
  public boolean isAdmin(Utente utente) {
    return utente.getRuolo().equals("admin");
  }

  /**
   * Checks if the user is a Pro.
   *
   * @param utente is the registred user into the DataBase.
   * @return true if the following account is a certified pro.
   */
  @Override
  public boolean isPro(Utente utente) {
    return utente.getRuolo().equals("professionista");
  }

  /**
   * Retrieve all the Users from the db.
   *
   * @return a List of Utente that contains all the users.
   */
  @Override
  public List<Utente> getAllUsers() {
    return utenteDao.findAllUsers();
  }

  /**
   * Retrieve all the Admins' email from the db.
   *
   * @return a List of all Admins' email.
   */
  @Override
  public List<String> getAllAdminsEmails() {

    List<Utente> admins = utenteDao.findAllAdmins();
    List<String> emails = new ArrayList<>();

    if (!admins.isEmpty()) {
      emails = admins.stream().map(Utente::getMail).collect(Collectors.toList());
    }

    return emails;
  }

  /**
   * Implements a research filter based on the email. As the Admin types in, the filter updates the
   * users' list each time a charachter is typed.
   *
   * @param email is the typed part of the email to base the research on.
   * @return List of Utente that contains users with the matching email characters.
   */
  @Override
  public List<Utente> searchUser(String email) {
    return utenteDao.searchUser(email);
  }

  /**
   * Ban a user from accessing the system.
   *
   * @param email is the email of the user to ban.
   * @return true if user is banned correctly.
   */
  @Override
  public boolean banUser(String email) {

    Utente utente = utenteDao.findUtenteByMail(email);

    utente.setBlacklist(true);

    return utenteDao.updateUtente(utente);
  }

  /**
   * Temporarily ban a user from accessing the system.
   *
   * @param email    is the email of the user to ban.
   * @param duration is the date until the user is banned.
   * @return true if user is banned correctly.
   */
  @Override
  public boolean timeoutUser(String email, LocalDateTime duration) {

    Utente utente = utenteDao.findUtenteByMail(email);

    utente.setBlacklist(true);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String until = formatter.format(duration);
    utente.setDurataBl(until);

    return utenteDao.updateUtente(utente);
  }

}
