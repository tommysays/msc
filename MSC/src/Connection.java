import com.soundcloud.api.*;
import java.io.IOException;
import org.apache.http.*;

public class Connection 
{
    private ApiWrapper wrapper;
    private Token token;
    
    public Connection(ApiWrapper wrapper)
    {
        this.wrapper = wrapper;
    }
    
    public Token Connect(String username, String password) throws IOException
    {
        token = wrapper.login(username, password);
        
        return token;
    }    
}
