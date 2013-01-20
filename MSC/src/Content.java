import com.soundcloud.api.*;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

public class Content 
{
    public static final int MAXTRACKS = 100;
    private String[] song_title;
    private String[] stream_url;
    private String download_url;
    private ApiWrapper wrapper;
     
    public Content(ApiWrapper wrapper) throws IOException
    {
        this.wrapper = wrapper;
        song_title = new String[MAXTRACKS];
        stream_url = new String[MAXTRACKS];
        download_url = "";
    }
    
    public HttpResponse getContent() throws IOException
    {
        HttpResponse response;
        String response_string;
        
        response = wrapper.get(Request.to("/me/tracks/"));
        response_string = Http.formatJSON(Http.getString(response));

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) 
        {
            //System.out.println("\n" + response_string);
            getTrackInfo(response_string);
            

            
        } 
        else 
        {
            System.err.println("Invalid status received: " + response.getStatusLine());
        }  

        return response;
    }
    
    public void getTrackInfo(String data)
    {
        int i = 0;
        int starting_index = 0;             // starting index of the matching string 
        int starting_index_offset;
        int ending_index = 0;
        String search1 = "download_url";
        String search2 = "\"title";
        
        while((starting_index = data.indexOf(search1, starting_index)) != -1)
        {
            starting_index_offset = 16;
            starting_index += starting_index_offset;
            ending_index = data.indexOf(",", starting_index) - 1;
            
            //System.out.println("\n+++++\n" + starting_index + " hh " + ending_index);
            stream_url[i] = data.substring(starting_index , ending_index);
            //System.out.println("\n" + stream_url[i]);
            
            starting_index_offset = 10;
            starting_index = data.indexOf(search2, starting_index) + starting_index_offset;
            ending_index = data.indexOf(",", starting_index) - 1;
            
            song_title[i] = data.substring(starting_index , ending_index);
            //System.out.println("\n" + song_title[i]);
            
            starting_index = ending_index;
            i++;
                   
        }
    }
    
    /**
     *
     * @param response
     * @return
     */
    public String getDownloadLink( int urlIndex) throws IOException, JSONException
    {
            HttpResponse response = wrapper.get(Request.to(stream_url[urlIndex]));
            JSONObject response_string = new JSONObject (Http.formatJSON(Http.getString(response)));
            return response_string.getString("location"); 
    }
    public String getSongTitle(int index) {
        return song_title[index];
    }
}
