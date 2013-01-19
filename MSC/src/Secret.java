import com.soundcloud.api.*;
import java.io.IOException;

public class Secret 
{  
    public static void main(String[] args) throws IOException
    {
        ApiWrapper wrapper;
        Connection connection;
        Content content;
        String client_id;
        String client_secret;
        String username;
        String password;
        
        client_id = "59300a92df9799f95258a9ba20992375";
        client_secret = "1efc4b68c039a18d5ba9305d4ea6a0ba";
        username = "jo.paul.91@gmail.com";
        password = "nim2006";
        
        wrapper = new ApiWrapper(client_id, client_secret, null, null);
        connection = new Connection (wrapper);
        content = new Content();
        
        connection.Connect(username, password);
        content.getContent(wrapper);
    }
}
