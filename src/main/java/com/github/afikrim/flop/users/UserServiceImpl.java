package com.github.afikrim.flop.users;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.github.afikrim.flop.accounts.Account;
import com.github.afikrim.flop.accounts.AccountRepository;
import com.github.afikrim.flop.accounts.AccountRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll().stream().map(user -> {
            UserRequest userRequest = new UserRequest();

            Link self = linkTo(methodOn(UserController.class).get(user.getId())).withRel("self");
            Link update = linkTo(methodOn(UserController.class).update(user.getId(), userRequest)).withRel("update");
            Link delete = linkTo(methodOn(UserController.class).destroy(user.getId())).withRel("delete");

            user.add(self);
            user.add(update);
            user.add(delete);

            return user;
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public User store(UserRequest userRequest) {
        AccountRequest accountRequest = userRequest.getAccount();

        Account account = new Account();
        account.setUsername(accountRequest.getUsername());
        account.setPassword(accountRequest.getPassword());
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());

        User user = new User();
        user.setFullname(userRequest.getFullname());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        user.setAccount(account);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        accountRepository.save(account);
        userRepository.save(user);

        Link self = linkTo(methodOn(UserController.class).get(user.getId())).withRel("self");
        Link update = linkTo(methodOn(UserController.class).update(user.getId(), userRequest)).withRel("update");
        Link delete = linkTo(methodOn(UserController.class).destroy(user.getId())).withRel("delete");

        user.add(self);
        user.add(update);
        user.add(delete);

        return user;
    }

    @Override
    public User getOne(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }

        User tempUser = optionalUser.get();
        UserRequest userRequest = new UserRequest();

        Link update = linkTo(methodOn(UserController.class).update(id, userRequest)).withRel("update");
        Link delete = linkTo(methodOn(UserController.class).destroy(id)).withRel("delete");

        tempUser.add(update);
        tempUser.add(delete);

        return tempUser;
    }

    @Transactional
    @Override
    public User updateOne(Long id, UserRequest userRequest) {
        AccountRequest accountRequest = userRequest.getAccount();
        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }

        User tempUser = optionalUser.get();
        Account tempAccount = tempUser.getAccount();

        if (userRequest.getFullname() != null)
            tempUser.setFullname(userRequest.getFullname());
        if (userRequest.getEmail() != null)
            tempUser.setEmail(userRequest.getEmail());
        if (userRequest.getPhone() != null)
            tempUser.setPhone(userRequest.getPhone());
        if (accountRequest.getUsername() != null)
            tempAccount.setUsername(accountRequest.getUsername());
        if (accountRequest.getPassword() != null)
            tempAccount.setPassword(accountRequest.getPassword());

        tempAccount.setUpdatedAt(new Date());
        tempUser.setUpdatedAt(new Date());

        tempUser.setAccount(tempAccount);

        Link self = linkTo(methodOn(UserController.class).get(id)).withRel("self");
        Link delete = linkTo(methodOn(UserController.class).destroy(id)).withRel("delete");

        tempUser.add(self);
        tempUser.add(delete);

        return userRepository.save(tempUser);
    }

    @Transactional
    @Override
    public User destroyOne(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }

        User tempUser = optionalUser.get();
        accountRepository.deleteById(tempUser.getAccount().getId());

        return null;
    }

}
