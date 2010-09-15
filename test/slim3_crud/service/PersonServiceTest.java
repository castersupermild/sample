package slim3_crud.service;

import java.util.HashMap;
import java.util.Map;

import org.slim3.datastore.Datastore;
import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;


import slim3_crud.model.Person;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class PersonServiceTest extends AppEngineTestCase {

    private PersonService service = new PersonService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
    
    @Test
    public void register() throws Exception {
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("name", "Hoge");
        input.put("email", "fuga@foo.bar");
        Person person = service.register(input);
        assertThat(person, is(notNullValue()));
        Person registered = Datastore.get(Person.class, person.getKey());
        assertThat(registered.getName(), is("Hoge"));
        assertThat(registered.getEmail(), is("fuga@foo.bar"));
    }
}
