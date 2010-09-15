package slim3_crud.controller.crud;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.RequestMap;

import slim3_crud.model.Person;
import slim3_crud.service.PersonService;
import slim3_crud.service.UniqueViolatedExeption;

import com.google.appengine.repackaged.com.google.common.collect.Maps;

public class UpdateController extends Controller {
    
    PersonService service = new PersonService();

    @Override
    protected Navigation run() throws Exception {
        

        Map<String, Object> requestMap = new RequestMap(request);
        HashMap<String, Object> map = Maps.newHashMap();
        List<String> errMsgList = service.validate(request);
        Person person = null;
        if (errMsgList.size() != 0) {
            map.put("msg", errMsgList);
            setupJson(map);
            return null;
        }
        try {
            person = service.update(requestMap);
        } catch (UniqueViolatedExeption e) {
            errMsgList.add(e.getMessage());
            map.put("msg", errMsgList);
            setupJson(map);
            return null;
        }
        
        map.put("person", person);
        setupJson(map);
        return null;
    }
    
    private void setupJson(HashMap<String, Object> map) throws IOException {
        response.setContentType("text/javascript+json; charset=utf-8");
        JSON json = JSONSerializer.toJSON(map);
        json.write(response.getWriter());
        response.flushBuffer();
    }
}
