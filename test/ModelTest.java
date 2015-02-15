import org.junit.*;

import testbase.BaseTest;

import java.util.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import models.*;

import com.avaje.ebean.*;

public class ModelTest extends BaseTest
{
    
    private String formatted(Date date) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    @Test
    public void findById() {
        running(fakeApplication(), new Runnable() {
           public void run() {
        	   log("in findById");
//               Simple macintosh = Simple.find.byId(21l);
               Simple macintosh = new Simple();
               
               assertThat(macintosh.getName()).isEqualTo("Macintosh");
           }
        });
    }
    
    
}
