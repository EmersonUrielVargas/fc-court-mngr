package com.foodcourt.court.application.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IOwnerRequestMapperTest {

    private IOwnerRequestMapper ownerRequestMapper = Mappers.getMapper(IOwnerRequestMapper.class);

    @Test
    public void testToUser(){
        OwnerRequestDto owner = new OwnerRequestDto();
        owner.setName("Jhon");
        owner.setLastName("Croft");
        owner.setEmail("test@pragma.com");
        owner.setPassword("segura123");
        owner.setPhoneNumber("+571238000123");
        owner.setIdNumber("123456");
        owner.setBirthDate(LocalDate.of(1990,6,17));

        User user = ownerRequestMapper.toUser(owner);

        assertEquals(owner.getName(), user.getName());
        assertEquals(owner.getLastName(), user.getLastName());
        assertEquals(owner.getEmail(), user.getEmail());
        assertEquals(owner.getPassword(), user.getPassword());
        assertEquals(owner.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(owner.getIdNumber(), user.getIdNumber());
        assertEquals(owner.getBirthDate(), user.getBirthDate());
    }
}
