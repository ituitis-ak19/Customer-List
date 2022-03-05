package com.ibt.userList.Exception;
import org.thymeleaf.util.StringUtils;


public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class clazz, String searchParams){
        super(EntityNotFoundException.generateMessage(clazz.getSimpleName(), searchParams));
    }

    private static String generateMessage(String entity, String searchParams) {
        return StringUtils.capitalize(entity) + " with ID: " + searchParams + " not found.";
    }

}
