package slim3_crud.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slim3.controller.validator.Errors;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import slim3_crud.meta.PersonMeta;
import slim3_crud.model.Person;

import com.google.appengine.api.datastore.Transaction;


public class PersonService {
    
    private String UNIQUE_NAME_INDEX = "unique_name";
    private String UNIQUE_EMAIL_INDEX = "unique_email";

    private PersonMeta p = new PersonMeta();
    
    public Person register(Map<String, Object> input) throws Exception {
        Person person = new Person();
        BeanUtil.copy(input, person);
        Transaction tx = Datastore.beginTransaction();
        if (Datastore.putUniqueValue(UNIQUE_NAME_INDEX, person.getName())) {
            if (Datastore.putUniqueValue(UNIQUE_EMAIL_INDEX, person.getEmail())) {
                Datastore.put(tx, person);
                tx.commit();
            } else {
                Datastore.deleteUniqueValue(UNIQUE_NAME_INDEX, person.getName());
                throw new UniqueViolatedExeption("メールアドレスが重複しています。");
            }
        } else {
            throw new UniqueViolatedExeption("名前が重複しています。");
        }
        return person;
    }
    
    public void delete(Map<String, Object> input) {
        Person person = new Person();
        BeanUtil.copy(input, person);
        Transaction tx = Datastore.beginTransaction();
        person = Datastore.query(p).filter(p.key.equal(person.getKey())).asSingle();
        Datastore.deleteUniqueValue(UNIQUE_NAME_INDEX, person.getName());
        Datastore.deleteUniqueValue(UNIQUE_EMAIL_INDEX, person.getEmail());
        Datastore.delete(tx, person.getKey());
        tx.commit();
    }
    
    public Person update(Map<String, Object> input) throws Exception {
        Person person = new Person();
        BeanUtil.copy(input, person);
        Transaction tx = Datastore.beginTransaction();
        Person before = Datastore.query(p).filter(p.key.equal(person.getKey())).asSingle();
        
        // update前にユニーク制約用のエンティティを削除しておく.
        deleteUniqueValueForUpdate(before);
        
        if (Datastore.putUniqueValue(UNIQUE_NAME_INDEX, person.getName())) {
            if (Datastore.putUniqueValue(UNIQUE_EMAIL_INDEX, person.getEmail())) {
                Datastore.put(tx, person);
                tx.commit();
            } else {
                Datastore.deleteUniqueValue(UNIQUE_NAME_INDEX, person.getName());
                reputUniqueValue(before);
                throw new UniqueViolatedExeption("メールアドレスが重複しています。");
            }
        } else {
            reputUniqueValue(before);
            throw new UniqueViolatedExeption("名前が重複しています。");
        }
        return person;
    }
    
    private void deleteUniqueValueForUpdate(Person before) {
        Datastore.deleteUniqueValue(UNIQUE_NAME_INDEX, before.getName());
        Datastore.deleteUniqueValue(UNIQUE_EMAIL_INDEX, before.getEmail());
    }
    
    private void reputUniqueValue(Person before) {
        Datastore.putUniqueValue(UNIQUE_NAME_INDEX, before.getName());
        Datastore.putUniqueValue(UNIQUE_EMAIL_INDEX, before.getEmail());
    }
    
    public List<Person> getPersonList() {
        return Datastore.query(p).sort(p.name.asc).asList();
    }
    
    public List<String> validate(HttpServletRequest request) {
        List<String> errMsgList = new ArrayList<String>();
        Validators v = new Validators(request);
        v.add("name", v.required());
        v.add("email", v.required());
        if (!v.validate()) {
            //NG
            Errors errors = v.getErrors();
            if (errors.get("name") != null) errMsgList.add(errors.get("name"));
            if (errors.get("email") != null) errMsgList.add(errors.get("email"));
        }
        return errMsgList;
    }
}
