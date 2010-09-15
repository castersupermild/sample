package slim3_crud.controller.crud;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3_crud.model.Person;
import slim3_crud.service.PersonService;

public class IndexController extends Controller {
    
    private PersonService service = new PersonService();

    @Override
    public Navigation run() throws Exception {
        List<Person> pList = service.getPersonList();
        requestScope("persons", pList);
        return forward("index.jsp");
    }
}
