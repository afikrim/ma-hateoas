package com.github.afikrim.flop.users;

import java.util.List;

import com.github.afikrim.flop.utils.Response;
import com.github.afikrim.flop.utils.ResponseCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users", produces = { MediaType.APPLICATION_JSON_VALUE })
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> index() {
        List<User> users = userService.getAll();
        Response<?> response = new Response<>(true, ResponseCode.HTTP_OK, "Successfully retrieved all users", users);

        UserRequest userRequest = new UserRequest();
        Link store = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).store(userRequest))
                .withRel("store");

        response.add(store);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<?> store(@RequestBody UserRequest userRequest) {
        User user = userService.store(userRequest);
        Response<?> response = new Response<>(true, ResponseCode.CREATED, "Successfully store new user", user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        User user = userService.getOne(id);
        Response<?> response = new Response<>(true, ResponseCode.HTTP_OK, "Successfully retrieved user with id " + id,
                user);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        User user = userService.updateOne(id, userRequest);
        Response<?> response = new Response<>(true, ResponseCode.CREATED, "Successfully store new user", user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> destroy(@PathVariable Long id) {
        User user = userService.destroyOne(id);
        Response<?> response = new Response<>(true, ResponseCode.CREATED, "Successfully destroy user", user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
