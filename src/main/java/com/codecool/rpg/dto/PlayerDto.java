package com.codecool.rpg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerDto {
    private Long id;
    private String userName;
    private String name;
    private String password;
    private String email;
    private String profilePicture;
    private Long dungeonMasterId;
}
