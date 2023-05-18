package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import net.bytebuddy.build.AccessControllerPlugin;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postNewAccountHandler);
        app.post("/login", this::loginAccountHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{message_id}", this::getMessagesByMessageIDHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromAccountHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
       

        return app;
    }

    private void postNewAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account existingAccount = accountService.getAccountByUsername(account.getUsername());

        if (existingAccount != null || account.getUsername().length() < 1 || account.getPassword().length() < 4) {
            ctx.status(400);
            return; 
        } 

        Account addedAccount = accountService.addAccount(account);
        if (addedAccount != null) {
            ctx.json(addedAccount);
        } else {
            ctx.status(400); 
        }
    }

    private void loginAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account existingAccount = accountService.getAccountByUsername(account.getUsername());

        if (existingAccount != null && existingAccount.getPassword().equals(account.getPassword())) {
            ctx.json(existingAccount);
        } else {
            ctx.status(401);
        }
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Account existingAccount = accountService.getAccountByAccountID(message.getPosted_by());

        if (existingAccount == null || message.getMessage_text().length() < 1 || message.getMessage_text().length() > 254) {
            ctx.status(400);
            return;
        }

        Message addedMessage = messageService.addMessage(message);
        ctx.json(addedMessage);
    }

    private void getMessagesHandler(Context ctx) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessagesByMessageIDHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageByMessageID(message_id);
        
        if (message == null) {
            ctx.status(200);
        } else {
            ctx.json(mapper.writeValueAsString(message));
        }
    }

    private void deleteMessageHandler(Context ctx) throws JsonProcessingException {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageByMessageID(message_id);

        if (message != null) {
            messageService.deleteMessage(message_id);
            ctx.json(message);
        } else {
            ctx.json("");
        }
    }

    private void updateMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message new_message = mapper.readValue(ctx.body(), Message.class);
        String new_message_text = new_message.getMessage_text();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));

        Message message = messageService.getMessageByMessageID(message_id);

        if (message == null || new_message_text.length() < 1 || new_message_text.length() > 254) {
            ctx.status(400);
            return;
        }
        
        Message updated_message = messageService.updateMessageText(message_id, new_message_text);
        System.out.println(updated_message);
        ctx.json(updated_message);
      
    }

    private void getAllMessagesFromAccountHandler(Context ctx) throws JsonProcessingException {
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByAccount(account_id);
        ctx.json(messages);
    }
}