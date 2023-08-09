package tech.wakeb.collector.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.wakeb.collector.model.Disk;
import tech.wakeb.collector.model.User;
import tech.wakeb.collector.repository.DiskRepository;
import tech.wakeb.collector.repository.UserRepository;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/disks")
public class DiskController {

    @Autowired
    DiskRepository diskRepository;

    @GetMapping(value = "")
    public ResponseEntity<List<Disk>> index(@RequestParam(required = false, value = "PageNo", defaultValue = "0") int PageNo , @RequestParam(required = false, value = "ElemntNo", defaultValue = "2") int ElemntNo) {

        Pageable pageable =  PageRequest.of(PageNo,ElemntNo);

        Page <Disk> users = diskRepository.findAll(pageable);

        List <Disk> disks = users.getContent();

        return new ResponseEntity<>(disks, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Optional<Disk>> getById(@PathVariable("id") Long id) {
        Optional<Disk> user = diskRepository.findById(id);

        if (user.isPresent())
            return new ResponseEntity<>(user, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "store")
    public ResponseEntity<Disk> store(@RequestBody Disk userForm) {
        try {
            Disk users = diskRepository.save(userForm);
            return new ResponseEntity<>(users, HttpStatus.CREATED);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // TODO:: implementation
    }

    @PutMapping(value = "update/{id}")
    public ResponseEntity <Optional<Disk>> update(@PathVariable("id") Long id,@RequestBody Disk userForm) {
        // TODO:: implementation
        Optional<Disk> users = diskRepository.findById(id);
        if(users.isPresent()) {
            try {
                userForm.setId(id);
                users = Optional.of(diskRepository.save(userForm));
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
    public ResponseEntity<Optional<Disk>> delete(@PathVariable("id") Long id) {
        Optional<Disk> user = diskRepository.findById(id);
        // TODO:: implementation
//        User user = new User();
        try {
            if (user.isPresent()) {
                diskRepository.deleteById(id);
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
