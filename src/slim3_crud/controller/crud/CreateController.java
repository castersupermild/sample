package slim3_crud.controller.crud;

import java.util.List;
import java.util.Map;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.BeanUtil;
import org.slim3.util.RequestMap;

import slim3_crud.model.Person;
import slim3_crud.service.PersonService;
import slim3_crud.service.UniqueViolatedExeption;

public class CreateController extends Controller {
    
    PersonService service = new PersonService();

    @Override
    public Navigation run() throws Exception {
        Map<String, Object> requestMap = new RequestMap(request);
        List<String> errMsgList = service.validate(request);
        if (errMsgList.size() != 0) {
            setupErr(requestMap);
            requestScope("errMsg", errMsgList);
            return forward("register.jsp");
        }
        try {
            service.register(requestMap);
        } catch (UniqueViolatedExeption e) {
            errMsgList.add(e.getMessage());
            setupErr(requestMap);
            requestScope("errMsg", errMsgList);
            return forward("register.jsp");
        }
        return redirect(basePath);
    }
    
    private void setupErr(Map<String, Object> requestMap) {
        Person p = new Person();
        BeanUtil.copy(requestMap, p);
        requestScope("person", p);
    }
}
