package com.travelplanner.travelplanner_server.model.validator;

import com.travelplanner.travelplanner_server.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//Custom Validations
//Spring validator docs: https://docs.spring.io/autorepo/docs/spring/3.2.x/spring-framework-reference/html/validation.html

//Component is the generic stereotype for any Spring-managed component like Repository/Service/Controller .etc
@Component
public class UserValidator implements Validator {
    //1
    @Override
    public boolean supports(Class<?> clazz){
        //supports(Class<?>): Specifies that a instance of the User Domain Model can be validated with this custom validator
        return User.class.equals(clazz);
    }

    //2
    @Override
    public void validate(Object target, Errors errors){
        User user = (User) target;
        if(!user.getPasswordConfirmation().equals(user.getPassword())){
            //3
            System.out.println("not matching!");
            errors.rejectValue("passwordConfirmation", "Match");
        }
    }
}
