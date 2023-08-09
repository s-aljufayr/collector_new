package tech.wakeb.collector.controller.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import tech.wakeb.collector.model.User;
import tech.wakeb.collector.repository.UserRepository;
import tech.wakeb.collector.valid.UserForm;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/users")
public class UsersController {
    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "")
    public ResponseEntity<Map<String, Object>> index(
            // pagination
            @RequestParam(required = false, defaultValue = "1") int page ,
            @RequestParam(required = false, defaultValue = "4") int perPage ,
            // filtration
            @RequestParam String search,
            // sort
            @RequestParam(value = "sort-field", defaultValue = "id") String[] sortField,
            @RequestParam(value = "sort-type", defaultValue = "desc") String[] sortType) {

        Pageable pageable = PageRequest.of(page-1, perPage);
        Page<User> usersPage = userRepository.findAll(pageable);

        Collection<User> users = usersPage.getContent();

        if(users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("users ", users);
        response.put("totalItems", usersPage.getTotalElements());
        response.put("totalPageItems", usersPage.getNumberOfElements());
        response.put("page", usersPage.getNumber()-1);
        response.put("totalPages", usersPage.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);


    }

    public Optional<User> getId(Long id){

        Optional<User> user = Optional.of(userRepository.getById(id));

        return user;

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<User>> getById(@PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent())
            return new ResponseEntity<>(user, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "start/{username}")
    public ResponseEntity<Collection<User>> getByUsernameStartsWith(@PathVariable("username") String username) {
        Collection<User> user = userRepository.findByUsernameStartsWith(username);

        if (!user.isEmpty())
            return new ResponseEntity<>(user, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "end/{username}")
    public ResponseEntity<Collection<User>> getByUsernameEndsWith(@PathVariable("username") String username) {
        Collection<User> user = userRepository.findByUsernameEndsWith(username);

        if (!user.isEmpty())
            return new ResponseEntity<>(user, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "searchByName/{name}")
    public ResponseEntity<Collection<User>> getByUsernameEndsWith_name(@PathVariable("name") String name) {
        Collection<User> user = userRepository.searchByName(name);

        if (!user.isEmpty())
            return new ResponseEntity<>(user, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "NotContaining/{username}")
    public ResponseEntity<Collection<User>> getByUsernameNotContaining(@PathVariable("username") String username) {
        Collection<User> user = userRepository.findByUsernameNotContaining(username);

        if (!user.isEmpty())
            return new ResponseEntity<>(user, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "Containing/{username}")
    public ResponseEntity<Collection<User>> getByUsernameContaining(@PathVariable("username") String username) {
        Collection<User> user = userRepository.findByUsernameContaining(username);

        if (!user.isEmpty())
            return new ResponseEntity<>(user, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "email/{username}/{email}")
    public ResponseEntity<Collection<User>> getByUsername(@PathVariable("username") String username, @PathVariable("email") String email) {
        Collection<User> usern = userRepository.findByUsernameOrEmail(username, email);

        if (!usern.isEmpty())
            return new ResponseEntity<>(usern, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "store")
    public ResponseEntity store(@Valid @RequestBody UserForm userForm, BindingResult bindingResult) {

        try {
            if(bindingResult.hasErrors()){

                Map<String, Object> responce = new HashMap<>();
                responce.put("messege", "validation Errors");
                List<Object> errors = new ArrayList<>();
                responce.put("Errors", errors);

                bindingResult.getFieldErrors().stream().forEach(fieldError -> {
                    Map<String, Object> fieldResponse = new HashMap<>();

                    List<String> fieldErrors = new ArrayList<>();
                    fieldErrors.add(fieldError.getDefaultMessage());

                    fieldResponse.put("field", fieldError.getField());
                    fieldResponse.put("errors", fieldErrors);

                    errors.add(fieldResponse);

                });

                return new ResponseEntity<>( responce, HttpStatus.UNPROCESSABLE_ENTITY);

//                errors = bindingResult.getFieldError().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
//                responce.put(String.valueOf(bindingResult.getFieldError()), errors);
//                    responce.put(message.toString(), errors);


//                 Here you can change ok to badRequest depending on your use case.
//                return new ResponseEntity<>( responce, HttpStatus.UNPROCESSABLE_ENTITY);
//                return ResponseEntity.ok(new ErrorResponse("404", "Validation failure", errors));
//                 In case if you want to fail the request, you need to use the below:
                // return ResponseEntity.badRequest().body(new ErrorResponse("404", "Validation failure", errors));

//                Map<String, Object> responce = new HashMap<>();
//                List errors = new ArrayList();
//                BindingResult
//                Map<String, Object> responce = new HashMap<>();
//                List errors = new ArrayList();
//                errors = bindingResult.getAllErrors();
//                for(Object error : errors){
//                    responce.put( "Errors Count:",bindingResult.getErrorCount());
//                    responce.put("Name: ", bindingResult.get);
//                    responce.put("username: ",bindingResult.getAllErrors());
//                    responce.put("email: ", bindingResult.getFieldError());
//                    responce.put("password: ",bindingResult.getAllErrors());
//                    responce.put("phone: ", bindingResult.getFieldError());
//                }
//
//                return new ResponseEntity<>(responce, HttpStatus.UNPROCESSABLE_ENTITY);

            }

            User user = userRepository.save(User.builder()
                    .name(userForm.getName())
                    .username(userForm.getUsername())
                    .email(userForm.getEmail())
                    .phone(userForm.getPhone())
                    .password(userForm.getPassword())
                    .build());

            return new ResponseEntity<>(user, HttpStatus.CREATED);


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
                //userRepository.save(userForm);
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
