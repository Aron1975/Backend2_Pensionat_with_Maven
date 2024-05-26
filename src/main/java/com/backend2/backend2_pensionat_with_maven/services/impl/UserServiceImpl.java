package com.backend2.backend2_pensionat_with_maven.services.impl;


/*
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final KundRepo kundRepo;
    private final BokningRepo bokningsRepo;
    private final UserRepo userRepo;


    @Override
    public List<UserDto> getAllUsers() {
        return userRepo.findAll().stream().map(u -> userToUserDto(u)).toList();
    }

    @Override
    public UserDto userToUserDto(User u) {
        return UserDto.builder().id(u.getId()).username(u.getUsername()).password(u.getPassword()).build();
    }

    @Override
    public User userDtoToUser(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;
    }

    public void spara(UserDto u){
        User user = userDtoToUser(u);
        userRepo.save(user);
    }

    @Override
    public void deleteUserById(int id){
        User userToDelete = userRepo.findById(id).orElse(null);
        if (userToDelete != null){
            userRepo.deleteById(id);
        }
    }

    public UserDto getUser(int id){
        User user = userRepo.findById(id).orElse(null);
        if (user != null){
            return userToUserDto(user);
        }
        return null;
    }

    public boolean checkIfUserExists(User user, List<User> userList) {
        String uName = user.getUsername();
        String pWord = user.getPassword();

        boolean userExists = userList.stream().anyMatch(u -> u.getUsername().equals(uName) &&
                u.getPassword().equals(pWord));

        return userExists;
    }

}*/
