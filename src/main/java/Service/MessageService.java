package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Account;
import Model.Message;

public class MessageService {
    public MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    /**
     * TODO: Use the messageDAO to retrieve all messages.
     * @return all messages.
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * TODO: Use the messageDAO to retrieve all messages from an associated account.
     * @return all messages from an account.
     */
    public List<Message> getAllMessagesByAccount(int account_id) {
        return messageDAO.getAllMessagesByAccount(account_id);
    }

    /**
     * TODO: Use the messageDAO to persist a message to the database.
     * @return The persisted message if the persistence is successful.
     */
    public Message addMessage(Message message) {
        return messageDAO.insertMessage(message);
    }

    /**
     * TODO: Use the messageDAO to retrieve a message associated with a message_id.
     * @return a message.
     */
    public Message getMessageByMessageID(int message_id) {
        return messageDAO.getMessageByMessageID(message_id);
    }

    /**
     * TODO: Use the messageDAO to delete a message associated with a message_id.
     */
    public void deleteMessage(int message_id) {
        messageDAO.deleteMessage(message_id);
    }

    /**
     * TODO: Use the messageDAO to update a message's text associated with a message_id.
     */
    public Message updateMessageText(int message_id, String new_message_text) {
        return messageDAO.updateMessageText(message_id, new_message_text);
    }
}
