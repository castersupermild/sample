package slim3_crud.service;

public class UniqueViolatedExeption extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -4831660642581132557L;
    
    public UniqueViolatedExeption (String msg) {
        super(msg);
    }

}
