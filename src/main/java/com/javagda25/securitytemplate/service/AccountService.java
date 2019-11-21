package com.javagda25.securitytemplate.service;

import com.javagda25.securitytemplate.model.*;
import com.javagda25.securitytemplate.model.dto.AccountPasswordResetRequest;
import com.javagda25.securitytemplate.repository.AccountRepository;
import com.javagda25.securitytemplate.repository.AccountRoleRepository;
import com.javagda25.securitytemplate.repository.RiderRankRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private AccountRoleService accountRoleService;
    private AccountRoleRepository accountRoleRepository;
    private RiderRankRepository riderRankRepository;


    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, AccountRoleService accountRoleService, AccountRoleRepository accountRoleRepository, RiderRankRepository riderRankRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountRoleService = accountRoleService;
        this.accountRoleRepository = accountRoleRepository;
        this.riderRankRepository = riderRankRepository;
    }



    public boolean register(Account account) {
        if (accountRepository.existsByUsername(account.getUsername())) {
            return false;
        }

        // szyfrowanie hasła
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setAccountRoles(accountRoleService.getDefaultRoles());

        // zapis do bazy
        accountRepository.save(account);

        return true;
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public void toggleLock(Long accountId) {
        if (accountRepository.existsById(accountId)) {
            Account account = accountRepository.getOne(accountId);
            account.setLocked(!account.isLocked());

            accountRepository.save(account);
        }
    }

    public void remove(Long accountId) {
        if (accountRepository.existsById(accountId)) {
            Account account = accountRepository.getOne(accountId);

            if (!account.isAdmin()) {
                accountRepository.delete(account);
            }
        }
    }

    public Optional<Account> findById(Long accountId) {
        return accountRepository.findById(accountId);

    }

    public void resetPassword(AccountPasswordResetRequest request) {
        if (accountRepository.existsById(request.getAccountId())) {
            Account account = accountRepository.getOne(request.getAccountId());

            account.setPassword(passwordEncoder.encode(request.getResetpassword()));

            accountRepository.save(account);
        }
    }

    public void editRoles(Long accountId, HttpServletRequest request) {
        if (accountRepository.existsById(accountId)) {
            Account account = accountRepository.getOne(accountId);

            // kluczem w form parameters jest nazwa parametru th:name
            Map<String, String[]> formParameters = request.getParameterMap();
            Set<AccountRole> newCollectionOfRoles = new HashSet<>();

            for (String roleName : formParameters.keySet()) {
                String[] values = formParameters.get(roleName);

                if (values[0].equals("on")) {
                    Optional<AccountRole> accountRoleOptional = accountRoleRepository.findByName(roleName);

                    if (accountRoleOptional.isPresent()) {
                        newCollectionOfRoles.add(accountRoleOptional.get());
                    }
                }
            }

            account.setAccountRoles(newCollectionOfRoles);

            accountRepository.save(account);
        }
    }

    public Account findByUsername(String username) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else {
            throw new EntityNotFoundException("No such username found");
        }
    }

    public void saveAsRider(Account rider, Principal principal) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(principal.getName());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            rider.setUsername(account.getUsername());
            rider.setPassword(account.getPassword());
            rider.setAccountRoles(account.getAccountRoles());
            Optional<AccountRole> accountRoleOptional = accountRoleRepository.findByName("RIDER");
            if(accountRoleOptional.isPresent()) {
                rider.getAccountRoles().add(accountRoleOptional.get());
                accountRepository.save(rider);
            }
        }
    }

    public List<Account> getAllRiders() {
        Optional<AccountRole> optionalAccountRole = accountRoleRepository.findByName("RIDER");
        if(optionalAccountRole.isPresent()){
            return accountRepository.findAccountsByAccountRolesContains(optionalAccountRole.get());
        }
        throw new EntityNotFoundException();
    }

    public void setRidersRank(HttpServletRequest request, Event event, Account referee) {
        Map<String, String[]> formParameters = request.getParameterMap();

        for (String riderIdRank : formParameters.keySet()) {
            if (!riderIdRank.contains("_")) {
                continue;
            }
            String id = riderIdRank.split("_")[1];
            Long riderId = Long.parseLong(id);

            Optional<Account> optionalRider = findById(riderId);
            if (optionalRider.isPresent()) {
                Account rider = optionalRider.get();
                String rankValue = formParameters.get(riderIdRank)[0];
                RiderRank rank = new RiderRank(rankValue);
//                saving new rank
                riderRankRepository.save(rank);
//                 adding the rank to the rider
                rider.getRiderRankSet().add(rank);
//                saving rider with a new rank
                accountRepository.save(rider);
//                adding rank to the heat
//                event.getRounds().
            } else throw new EntityNotFoundException();
        }
    }

    public Set<Account> ridersByRiderType (RiderType riderType, Round round){
        return round.getEvent().getAccounts().stream()
                .filter(account -> account.getRiderType().equals(riderType))
                .collect(Collectors.toSet());
    }
}
