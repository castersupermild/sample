package slim3_crud.controller.crud;

import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;
import org.junit.Test;

import slim3_crud.model.Person;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class CreateControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.param("name", "Hoge");
        tester.param("email", "hoge@fuga.bar");
        tester.start("/crud/create");
        CreateController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(true));
        assertThat(tester.getDestinationPath(), is("/crud/"));
        Person registered = Datastore.query(Person.class).asSingle();
        assertThat(registered, is(notNullValue()));
        assertThat(registered.getName(), is("Hoge"));
        assertThat(registered.getEmail(), is("hoge@fuga.bar"));
    }
}
