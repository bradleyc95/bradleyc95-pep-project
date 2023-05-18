package Service;

import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * TODO: Use the AccountDAO to retrieve all accounts.
     * @return all accounts
     */
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    /**
     * TODO: Use the AccountDAO to persist an account. The given Account will not have an id provided.
     *
     * @param account an account object.
     * @return The persisted account if the persistence is successful.
     */
    public Account addAccount(Account account) {
        return accountDAO.insertAccount(account);
    }

    public Account getAccountByUsername(String username) {
        return accountDAO.getAccountByUsername(username);
    }

    public Account getAccountByAccountID(int account_id) {
        return accountDAO.getAccountByAccountID(account_id);
    }
    
}
