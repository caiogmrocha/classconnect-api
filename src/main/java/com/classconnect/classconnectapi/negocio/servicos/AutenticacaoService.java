package com.classconnect.classconnectapi.negocio.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.classconnect.classconnectapi.dados.PerfilRepository;

@Service
public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private PerfilRepository perfilRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.perfilRepository.findByEmail(username);
    }
}
