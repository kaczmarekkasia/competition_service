package com.javagda25.securitytemplate.component;

import com.javagda25.securitytemplate.model.*;
import com.javagda25.securitytemplate.repository.AccountRepository;
import com.javagda25.securitytemplate.repository.AccountRoleRepository;
import com.javagda25.securitytemplate.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private AccountRepository accountRepository;
    private AccountRoleRepository accountRoleRepository;
    private PasswordEncoder passwordEncoder;
    private EventRepository eventRepository;
    private List<String> listOfTestsEventsNames = new ArrayList<>(Arrays.asList("EVENT_1", "EVENT_2", "EVENT_3"));


    @Autowired
    public DataInitializer(AccountRepository accountRepository, AccountRoleRepository accountRoleRepository, PasswordEncoder passwordEncoder, EventRepository eventRepository) {
        this.accountRepository = accountRepository;
        this.accountRoleRepository = accountRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.eventRepository = eventRepository;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        addDefaultRole("USER");
        addDefaultRole("ADMIN");
        addDefaultRole("RIDER");
        addDefaultRole("REFEREE");

        addDefaultUser("admin", "admin", "ADMIN", "USER");
        addDefaultUser("user", "user", "USER");


//      added for tests only
//      events
        addTestEvent("EVENT_1", LocalDate.of(2020, 9, 30), "Ustka", EventStatus.PLANNED);
        addTestEvent("EVENT_2", LocalDate.of(2019, 9, 30), "Władysławowo", EventStatus.PLANNED);
        addTestEvent("EVENT_3", LocalDate.of(2016, 9, 30), "Darłowo", EventStatus.PAST);
//       riders: 3 woman, 3 junior, 13 man -> for 4 by heat); 1 referee
        addDefaultUser("referee", "referee", "USER", "REFEREE");


        addDefaultUser("rider1", "USER", "RIDER");
        addDefaultUser("rider2", "USER", "RIDER");
        addDefaultUser("rider3", "USER", "RIDER");

        addDefaultUser("rider4", "USER", "RIDER");
        addDefaultUser("rider5", "USER", "RIDER");
        addDefaultUser("rider6", "USER", "RIDER");

        addDefaultUser("rider7", "USER", "RIDER");
        addDefaultUser("rider8", "USER", "RIDER");
        addDefaultUser("rider9", "USER", "RIDER");
        addDefaultUser("rider10", "USER", "RIDER");
        addDefaultUser("rider11", "USER", "RIDER");
        addDefaultUser("rider12", "USER", "RIDER");
        addDefaultUser("rider13", "USER", "RIDER");
        addDefaultUser("rider14", "USER", "RIDER");
        addDefaultUser("rider15", "USER", "RIDER");
        addDefaultUser("rider16", "USER", "RIDER");
        addDefaultUser("rider17", "USER", "RIDER");
        addDefaultUser("rider18", "USER", "RIDER");
        addDefaultUser("rider19", "USER", "RIDER");

        addTestRider("rider1", "S", RiderType.JUNIOR, Stance.GOOFY, "wss", "ws", "Gdynia");
        addTestRider("rider2", "M", RiderType.JUNIOR, Stance.REGULAR, "wss", "ws", "Gdynia");
        addTestRider("rider3", "S", RiderType.JUNIOR, Stance.GOOFY, "wss", "ws", "Gdynia");

        addTestRider("rider4", "S", RiderType.WOMAN, Stance.REGULAR, "wss", "ws", "Gdynia");
        addTestRider("rider5", "S", RiderType.WOMAN, Stance.REGULAR, "wss", "ws", "Gdynia");
        addTestRider("rider6", "S", RiderType.WOMAN, Stance.GOOFY, "wss", "ws", "Gdynia");

        addTestRider("rider7", "S", RiderType.MAN, Stance.REGULAR, "wss", "ws", "Gdynia");
        addTestRider("rider8", "S", RiderType.MAN, Stance.REGULAR, "wss", "ws", "Gdynia");
        addTestRider("rider9", "S", RiderType.MAN, Stance.GOOFY, "wss", "ws", "Gdynia");
        addTestRider("rider10", "S", RiderType.MAN, Stance.REGULAR, "wss", "ws", "Gdynia");
        addTestRider("rider11", "S", RiderType.MAN, Stance.REGULAR, "wss", "ws", "Gdynia");
        addTestRider("rider12", "S", RiderType.MAN, Stance.GOOFY, "wss", "ws", "Gdynia");
        addTestRider("rider13", "S", RiderType.MAN, Stance.REGULAR, "wss", "ws", "Gdynia");
        addTestRider("rider14", "S", RiderType.MAN, Stance.REGULAR, "wss", "ws", "Gdynia");
        addTestRider("rider15", "S", RiderType.MAN, Stance.GOOFY, "wss", "ws", "Gdynia");
        addTestRider("rider16", "S", RiderType.MAN, Stance.GOOFY, "wss", "ws", "Gdynia");
        addTestRider("rider17", "S", RiderType.MAN, Stance.GOOFY, "wss", "ws", "Gdynia");
        addTestRider("rider18", "S", RiderType.MAN, Stance.GOOFY, "wss", "ws", "Gdynia");
        addTestRider("rider19", "S", RiderType.MAN, Stance.GOOFY, "wss", "ws", "Gdynia");

//        adding users to events for tests
        for (String eventName : listOfTestsEventsNames) {
            addUserToEventForTest(eventName, "rider1");
            addUserToEventForTest(eventName, "rider2");
            addUserToEventForTest(eventName, "rider3");
            addUserToEventForTest(eventName, "rider4");
            addUserToEventForTest(eventName, "rider5");
            addUserToEventForTest(eventName, "rider6");
            addUserToEventForTest(eventName, "rider7");
            addUserToEventForTest(eventName, "rider8");
            addUserToEventForTest(eventName, "rider9");
            addUserToEventForTest(eventName, "rider10");
            addUserToEventForTest(eventName, "rider11");
            addUserToEventForTest(eventName, "rider12");
            addUserToEventForTest(eventName, "rider13");
            addUserToEventForTest(eventName, "rider14");
            addUserToEventForTest(eventName, "rider15");
            addUserToEventForTest(eventName, "rider16");
            addUserToEventForTest(eventName, "rider17");
            addUserToEventForTest(eventName, "rider18");
            addUserToEventForTest(eventName, "rider19");
        }
    }

    private void addTestRider(String username, String lycraSize, RiderType riderType, Stance stance, String boardBrand, String kiteBrand, String city) {
        if (accountRepository.existsByUsername(username)) {
            Account rider = accountRepository.findByUsername(username).get();
            rider.setLycraSize(lycraSize);
            rider.setRiderType(riderType);
            rider.setStance(stance);
            rider.setBoardBrand(boardBrand);
            rider.setKiteBrand(kiteBrand);
            rider.setCity(city);

            accountRepository.save(rider);

        }
    }

    private void addTestEvent(String eventName, LocalDate eventDate, String eventLocalization, EventStatus eventStatus) {
        if (!eventRepository.existsByName(eventName)) {
            Event event = new Event();
            event.setName(eventName);
            event.setDate(eventDate);
            event.setStatus(eventStatus);
            event.setLocalization(eventLocalization);

            eventRepository.save(event);
        }
    }

    private void addUserToEventForTest(String eventName, String username) {
        if (eventRepository.existsByName(eventName) && accountRepository.existsByUsername(username)) {
            Event event = eventRepository.findByName(eventName);
            Account rider = accountRepository.findByUsername(username).get();

            event.getAccounts().add(rider);
            eventRepository.save(event);
        }

    }

    private void addDefaultUser(String usernameAndPassword, String... roles) {
        if (!accountRepository.existsByUsername(usernameAndPassword)) {
            Account account = new Account();
            account.setUsername(usernameAndPassword);
            account.setPassword(passwordEncoder.encode(usernameAndPassword));

            Set<AccountRole> userRoles = findRoles(roles);
            account.setAccountRoles(userRoles);

            accountRepository.save(account);
        }
    }

    private Set<AccountRole> findRoles(String[] roles) {
        Set<AccountRole> accountRoles = new HashSet<>();
        for (String role : roles) {
            accountRoleRepository.findByName(role).ifPresent(accountRoles::add);
        }
        return accountRoles;
    }

    private void addDefaultRole(String role) {
        if (!accountRoleRepository.existsByName(role)) {
            AccountRole newRole = new AccountRole();
            newRole.setName(role);

            accountRoleRepository.save(newRole);
        }
    }


    private void addTestEvents(Event event) {
        eventRepository.save(event);
    }

}
