package com.example.demo.init;

import com.example.demo.entity.LocationEntity;
import com.example.demo.entity.ParcelEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.ParcelRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class InitData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ParcelRepository parcelRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public InitData(UserRepository userRepository, ParcelRepository parcelRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.parcelRepository = parcelRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public void run(String... args) {

        // Создание локаций
        LocationEntity location1 = new LocationEntity();
        location1.setAddress("123 Main St");
        location1.setName("Main Hub");

        LocationEntity location2 = new LocationEntity();
        location2.setAddress("456 Elm St");
        location2.setName("Elm Depot");

        // Сохранение локаций
        locationRepository.saveAll(Arrays.asList(location1, location2));

        // Создание пользователей
        UserEntity user1 = new UserEntity();
        user1.setUsername("john_doe");
        user1.setEmail("john@example.com");
        user1.setPassword("password123"); // В реальном приложении используйте хеширование паролей
        user1.setPhone("1234567890");

        UserEntity user2 = new UserEntity();
        user2.setUsername("jane_smith");
        user2.setEmail("jane@example.com");
        user2.setPassword("password123");
        user2.setPhone("0987654321");

        // Сохранение пользователей
        userRepository.saveAll(Arrays.asList(user1, user2));

        // Создание отправлений
        ParcelEntity parcel1 = new ParcelEntity();
        parcel1.setWeight(2.5);
        parcel1.setSize("SMALL");
        parcel1.setType("STANDARD");
        parcel1.setSender(user1);
        parcel1.setRecipient(user2);
        parcel1.setLocation(location1);

        ParcelEntity parcel2 = new ParcelEntity();
        parcel2.setWeight(5.0);
        parcel2.setSize("MEDIUM");
        parcel2.setType("EXPRESS");
        parcel2.setSender(user2);
        parcel2.setRecipient(user1);
        parcel2.setLocation(location2);

        // Сохранение отправлений
        parcelRepository.saveAll(Arrays.asList(parcel1, parcel2));

        System.out.println("Initial data has been added to the database.");
    }
}
