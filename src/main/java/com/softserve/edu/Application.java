package com.softserve.edu;


import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.Sprint;
import com.softserve.edu.model.User;
import com.softserve.edu.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@SpringBootApplication
public class Application implements CommandLineRunner {

    static UserService userService;
    static MarathonService marathonService;
    private final SprintService sprintService;
    private final TaskService taskService;
    private final ProgressService progressService;

    public Application(UserService userService, MarathonService marathonService, SprintService sprintService, TaskService taskService, ProgressService progressService) {
        this.userService = userService;
        this.marathonService = marathonService;
        this.sprintService = sprintService;
        this.taskService = taskService;
        this.progressService = progressService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        for (int i = 0; i < 5; i++) {
            Marathon marathon = new Marathon();
            marathon.setTitle("Marathon "+i);
            marathonService.createOrUpdate(marathon);
            for (int j = 0; j <  new Random().nextInt(20); j++) {
                User user = new User();
                user.setFirstName("User "+j);
                user.setLastName(""+j+j+j);
                user.setPassword(""+j+j+j);
                user.setRole(User.Role.TRAINEE);
                user.setEmail("user."+System.currentTimeMillis()+"@email.com");
                userService.createOrUpdateUser(user);
                userService.addUserToMarathon(user,marathon);
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {
    }
}