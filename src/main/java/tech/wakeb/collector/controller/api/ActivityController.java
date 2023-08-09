package tech.wakeb.collector.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.wakeb.collector.model.Activity;
import tech.wakeb.collector.model.User;
import tech.wakeb.collector.model.Activity;
import tech.wakeb.collector.repository.ActivityRepository;
import tech.wakeb.collector.repository.UserRepository;
import tech.wakeb.collector.repository.ActivityRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/activities")
public class ActivityController {

    @Autowired
    ActivityRepository activityRepository;

    @GetMapping(value = "")
    public ResponseEntity<List<Activity>> index() {

        List<Activity> activities = activityRepository.findAll();

        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Optional<Activity>> getById(@PathVariable("id") Long id) {
        Optional<Activity> activity = activityRepository.findById(id);

        if (activity.isPresent())
            return new ResponseEntity<>(activity, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "store")
    public ResponseEntity<Activity> store(@RequestBody Activity activityForm) {
        try {
            Activity activity = activityRepository.save(activityForm);
            return new ResponseEntity<>(activity, HttpStatus.CREATED);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // TODO:: implementation
    }

    @PutMapping(value = "update/{id}")
    public ResponseEntity <Optional<Activity>> update(@PathVariable("id") Long id,@RequestBody Activity activityForm) {
        // TODO:: implementation
        Optional<Activity> activity = activityRepository.findById(id);
        if(activity.isPresent()) {
            try {
                activityForm.setId(id);
                activity = Optional.of(activityRepository.save(activityForm));
                return new ResponseEntity<>(activity, HttpStatus.CREATED);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<Optional<Activity>> delete(@PathVariable("id") Long id) {
        Optional<Activity> activity = activityRepository.findById(id);
        // TODO:: implementation
//        User user = new User();
        try {
            if (activity.isPresent()) {
                activityRepository.deleteById(id);
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
