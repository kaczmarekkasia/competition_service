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

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private AccountRepository accountRepository;
    private AccountRoleRepository accountRoleRepository;
    private PasswordEncoder passwordEncoder;
    private EventRepository eventRepository;


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
        addTestEvent("EVENT_1", LocalDate.of(2020, 9,30), "Ustka", EventStatus.PLANNED);
        addTestEvent("EVENT_2", LocalDate.of(2019, 9,30), "Władysławowo", EventStatus.PLANNED);
        addTestEvent("EVENT_3", LocalDate.of(2016, 9,30), "Darłowo", EventStatus.PAST);
//       riders: 3 woman, 3 junior, 13 man -> for 4 by heat); 1 referee
        addDefaultUser("referee", "referee", "USER", "REFEREE");


        addDefaultUser("rider1", "rider1", "USER", "RIDER");
        addDefaultUser("rider2", "rider2", "USER", "RIDER");
        addDefaultUser("rider3", "rider3", "USER", "RIDER");

        addDefaultUser("rider4", "rider4", "USER", "RIDER");
        addDefaultUser("rider5", "rider5", "USER", "RIDER");
        addDefaultUser("rider6", "rider6", "USER", "RIDER");

        addDefaultUser("rider7", "rider7", "USER", "RIDER");
        addDefaultUser("rider8", "rider8", "USER", "RIDER");
        addDefaultUser("rider9", "rider9", "USER", "RIDER");
        addDefaultUser("rider10", "rider10", "USER", "RIDER");
        addDefaultUser("rider11", "rider11", "USER", "RIDER");
        addDefaultUser("rider12", "rider12", "USER", "RIDER");
        addDefaultUser("rider13", "rider13", "USER", "RIDER");
        addDefaultUser("rider14", "rider14", "USER", "RIDER");
        addDefaultUser("rider15", "rider15", "USER", "RIDER");
        addDefaultUser("rider16", "rider16", "USER", "RIDER");
        addDefaultUser("rider17", "rider17", "USER", "RIDER");
        addDefaultUser("rider18", "rider18", "USER", "RIDER");
        addDefaultUser("rider19", "rider19", "USER", "RIDER");

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
        addUserToEventForTest("EVENT_1", "rider1");
        addUserToEventForTest("EVENT_1", "rider2");
        addUserToEventForTest("EVENT_1", "rider3");
        addUserToEventForTest("EVENT_1", "rider4");
        addUserToEventForTest("EVENT_1", "rider5");
        addUserToEventForTest("EVENT_1", "rider6");
        addUserToEventForTest("EVENT_1", "rider7");
        addUserToEventForTest("EVENT_1", "rider8");
        addUserToEventForTest("EVENT_1", "rider9");
        addUserToEventForTest("EVENT_1", "rider10");
        addUserToEventForTest("EVENT_1", "rider11");
        addUserToEventForTest("EVENT_1", "rider12");
        addUserToEventForTest("EVENT_1", "rider13");
        addUserToEventForTest("EVENT_1", "rider14");
        addUserToEventForTest("EVENT_1", "rider15");
        addUserToEventForTest("EVENT_1", "rider16");
        addUserToEventForTest("EVENT_1", "rider17");
        addUserToEventForTest("EVENT_1", "rider18");
        addUserToEventForTest("EVENT_1", "rider19");

        addUserToEventForTest("EVENT_2", "rider1");
        addUserToEventForTest("EVENT_2", "rider2");
        addUserToEventForTest("EVENT_2", "rider3");
        addUserToEventForTest("EVENT_2", "rider4");
        addUserToEventForTest("EVENT_2", "rider5");
        addUserToEventForTest("EVENT_2", "rider6");
        addUserToEventForTest("EVENT_2", "rider7");
        addUserToEventForTest("EVENT_2", "rider8");
        addUserToEventForTest("EVENT_2", "rider9");
        addUserToEventForTest("EVENT_2", "rider10");
        addUserToEventForTest("EVENT_2", "rider11");
        addUserToEventForTest("EVENT_2", "rider12");
        addUserToEventForTest("EVENT_2", "rider13");
        addUserToEventForTest("EVENT_2", "rider14");
        addUserToEventForTest("EVENT_2", "rider15");
        addUserToEventForTest("EVENT_2", "rider16");
        addUserToEventForTest("EVENT_2", "rider17");
        addUserToEventForTest("EVENT_2", "rider18");
        addUserToEventForTest("EVENT_2", "rider19");

        addUserToEventForTest("EVENT_3", "rider1");
        addUserToEventForTest("EVENT_3", "rider2");
        addUserToEventForTest("EVENT_3", "rider3");
        addUserToEventForTest("EVENT_3", "rider4");
        addUserToEventForTest("EVENT_3", "rider5");
        addUserToEventForTest("EVENT_3", "rider6");
        addUserToEventForTest("EVENT_3", "rider7");
        addUserToEventForTest("EVENT_3", "rider8");
        addUserToEventForTest("EVENT_3", "rider9");
        addUserToEventForTest("EVENT_3", "rider10");
        addUserToEventForTest("EVENT_3", "rider11");
        addUserToEventForTest("EVENT_3", "rider12");
        addUserToEventForTest("EVENT_3", "rider13");
        addUserToEventForTest("EVENT_3", "rider14");
        addUserToEventForTest("EVENT_3", "rider15");
        addUserToEventForTest("EVENT_3", "rider16");
        addUserToEventForTest("EVENT_3", "rider17");
        addUserToEventForTest("EVENT_3", "rider18");
        addUserToEventForTest("EVENT_3", "rider19");

    }

    private void addTestRider(String username, String lycraSize, RiderType riderType, Stance stance, String boardBrand, String kiteBrand, String city) {
        if(accountRepository.existsByUsername(username)){
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
        if(!eventRepository.existsByName(eventName)){
            Event event = new Event();
            event.setName(eventName);
            event.setDate(eventDate);
            event.setStatus(eventStatus);
            event.setLocalization(eventLocalization);

            eventRepository.save(event);
        }
    }

    private void addUserToEventForTest(String eventName, String username){
        if(eventRepository.existsByName(eventName) && accountRepository.existsByUsername(username)) {
            Event event = eventRepository.findByName(eventName);
            Account rider = accountRepository.findByUsername(username).get();

            event.getAccounts().add(rider);
            eventRepository.save(event);
        }

    }

    private void addDefaultUser(String username, String password, String... roles) {
        if (!accountRepository.existsByUsername(username)) {
            Account account = new Account();
            account.setUsername(username);
            account.setPassword(passwordEncoder.encode(password));

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


    private void addTestEvents(Event event){
        eventRepository.save(event);
    }

}
