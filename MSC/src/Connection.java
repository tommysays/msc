import com.soundcloud.api.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.*;

public class Connection 
{
    private ApiWrapper wrapper;
    private Token token;
    
    public Connection(ApiWrapper wrapper)
    {
        this.wrapper = wrapper;
    }
    
    public Token Connect(String username, String password)
    {
        try 
        {
            token = wrapper.login(username, password);
        } 
        catch (IOException ex) 
        {
            //make this a pop up window later
            System.out.println("Incorrect username or password");
        }
        
        return token;
    }    
}
