package com.workflow.config;

import com.workflow.entity.*;
import com.workflow.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Date;
import java.util.Optional;

@Configuration
public class InitialConfiguration {

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository, PositionRepository positionRepository, DepartmentRepository departmentRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            if (roleRepository.count() == 0) {
                createRoleIfNotFound(roleRepository, "ROLE_SUPERADMIN", "Administrator with full access");
                createRoleIfNotFound(roleRepository, "ROLE_ADMIN", "Administrator with limited access");
                createRoleIfNotFound(roleRepository, "ROLE_USER", "Regular user with standard access");
            }

            if (userRepository.count() == 0) {

                Optional<Role> role = roleRepository.findByRoleType("ROLE_SUPERADMIN");

                if (role.isPresent()) {
                    User user = new User();
                    user.setUsername("superAdmin");
                    user.setPassword(passwordEncoder.encode("superAdmin"));
                    user.setEmail("nitesh6g@gmail.com");
                    user.setPhone("8448805135");
                    user.setFirstName("Nitesh");
                    user.setCreatedON(String.valueOf(new Date()));
                    userRepository.save(user);

                    if (userRoleRepository.count() == 0) {

                        Optional<User> current = userRepository.findByUsername("superAdmin");

                        if(current.isPresent()){
                            UserRole userRole = new UserRole();
                            userRole.setUserId(current.get().getUserId());
                            userRole.setRoleId(role.get().getRoleId());
                            userRole.setCreatedBy("superAdmin");
                            userRole.setCreatedON(String.valueOf(new Date()));
                            userRoleRepository.save(userRole);
                        }
                    }
                }
            }

            if(positionRepository.count()==0){
                createPositionIfNotExists(positionRepository,"CEO","Chief Executive Officer","Executive");
                createPositionIfNotExists(positionRepository,"General Manager","General Manager","Management");
                createPositionIfNotExists(positionRepository,"Manager","Manager","Management");
                createPositionIfNotExists(positionRepository,"Team Leader","Team Leader","Executive");
                createPositionIfNotExists(positionRepository,"Executive","Executive","Executive");
            }

            if(departmentRepository.count()==0){
                createDepartmentIfNotExists(departmentRepository,"IT","Information Technology");
                createDepartmentIfNotExists(departmentRepository,"HR","Human Resource");
                createDepartmentIfNotExists(departmentRepository,"Marketing","Marketing");
                createDepartmentIfNotExists(departmentRepository,"Finance","Finance");
                createDepartmentIfNotExists(departmentRepository,"Sales","Sales");
                createDepartmentIfNotExists(departmentRepository,"Customer Service","Customer Service");
            }

        };
    }

    private void createRoleIfNotFound(RoleRepository roleRepository, String roleType, String roleDescription) {
        Optional<Role> roleOptional = roleRepository.findByRoleType(roleType);
        if (roleOptional.isEmpty()) {
            Role role = new Role();
            role.setRoleType(roleType);
            role.setRoleDescription(roleDescription);
            role.setCreatedBy("superAdmin");
            role.setCreatedDate(String.valueOf(new Date()));
            roleRepository.save(role);
        }
    }

    private void createPositionIfNotExists(PositionRepository positionRepository, String title, String description,String level) {
        Optional<Position> positionOptional = positionRepository.findByTitle(title);
        if (positionOptional.isEmpty()) {
            Position position = new Position();
            position.setTitle(title);
            position.setDescription(description);
            position.setLevel(level);
            position.setCreatedBy("superAdmin");
            position.setCreatedON(String.valueOf(new Date()));
            positionRepository.save(position);
        }
    }

    private void createDepartmentIfNotExists(DepartmentRepository departmentRepository, String name, String description) {
        Optional<Department> departmentOptional = departmentRepository.findByName(name);
        if (departmentOptional.isEmpty()) {
            Department department = new Department();
            department.setName(name);
            department.setDescription(description);
            department.setCreatedBy("superAdmin");
            department.setCreatedON(String.valueOf(new Date()));
            departmentRepository.save(department);
        }
    }

}
