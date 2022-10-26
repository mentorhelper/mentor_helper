package com.ua.javarush.mentor.mapper;

import com.ua.javarush.mentor.persist.model.Role;
import com.ua.javarush.mentor.persist.model.User;
import com.ua.javarush.mentor.services.impl.UserDetailsImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserDetailsMapper{


    static Set<GrantedAuthority> getAuthoritiesSetForRole(Role role) {
        Set<GrantedAuthority> authorities = new HashSet<>(role.getPermissions().size() + 1);
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        role.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission().name()))
                .forEach(authorities::add);
        return authorities;
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "authorities", expression = "java(com.ua.javarush.mentor.mapper.UserDetailsMapper.getAuthoritiesSetForRole(user.getRoleId()))")
    UserDetailsImpl mapToUserDetails(User user);

}
