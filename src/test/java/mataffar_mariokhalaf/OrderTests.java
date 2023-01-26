package mataffar_mariokhalaf;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderTests {
    
    private List<Map<String, Object>> existingOrders;

    @Test
    public void shouldReturnNullIfFileDoesNotExist(){
        try {
            ObjectMapper objectmapper = new ObjectMapper();
             existingOrders = objectmapper.readValue("notafile.json", new TypeReference<List<Map<String, Object>>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
