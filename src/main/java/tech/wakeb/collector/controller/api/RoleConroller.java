package tech.wakeb.collector.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.wakeb.collector.model.User;
import tech.wakeb.collector.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/roles")
public class RoleConroller {

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "")
    public ResponseEntity<List<User>> index() {
        List<User> users = userRepository.findAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Optional<User>> getById(@PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent())
            return new ResponseEntity<>(user, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "store")
    public ResponseEntity<User> store(@RequestBody User userForm) {
        try {
            User users = userRepository.save(userForm);
            return new ResponseEntity<>(users, HttpStatus.CREATED);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // TODO:: implementation
    }

    @PutMapping(value = "update/{id}")
    public ResponseEntity <Optional<User>> update(@PathVariable("id") Long id,@RequestBody User userForm) {
        // TODO:: implementation
        Optional<User> users = userRepository.findById(id);
        if(users.isPresent()) {
            try {
                userForm.setId(id);
                users = Optional.of(userRepository.save(userForm));
                return new ResponseEntity<>(users, HttpStatus.CREATED);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<Optional<User>> delete(@PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);
        // TODO:: implementation
//        User user = new User();
        try {
            if (user.isPresent()) {
                userRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            //System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
