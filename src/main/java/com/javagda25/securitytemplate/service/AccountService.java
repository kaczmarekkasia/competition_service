package com.javagda25.securitytemplate.service;

import com.javagda25.securitytemplate.model.Account;
import com.javagda25.securitytemplate.model.AccountRole;
import com.javagda25.securitytemplate.model.RiderType;
import com.javagda25.securitytemplate.model.Stance;
import com.javagda25.securitytemplate.model.dto.AccountPasswordResetRequest;
import com.javagda25.securitytemplate.repository.AccountRepository;
import com.javagda25.securitytemplate.repository.AccountRoleRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private AccountRoleService accountRoleService;
    private AccountRoleRepository accountRoleRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, AccountRoleService accountRoleService, AccountRoleRepository accountRoleRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountRoleService = accountRoleService;
        this.accountRoleRepository = accountRoleRepository;
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
}
