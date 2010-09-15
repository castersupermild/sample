package slim3_crud.controller.crud;

import java.util.Map;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.RequestMap;

import slim3_crud.service.PersonService;

public class DeleteController extends Controller {
    
    PersonService service = new PersonService();

    @Override
    public Navigation run() throws Exception {
        Map<String, Object> requestMap = new RequestMap(request);
        service.delete(requestMap);
        return redirect(basePath);
    }
}
