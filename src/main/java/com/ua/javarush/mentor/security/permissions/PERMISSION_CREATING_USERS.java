package com.ua.javarush.mentor.security.permissions;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority(T(com.ua.javarush.mentor.persist.model.PermissionType).CREATING_USERS.name())")
public @interface PERMISSION_CREATING_USERS {
}
