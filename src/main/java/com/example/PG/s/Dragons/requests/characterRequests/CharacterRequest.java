package com.example.PG.s.Dragons.requests.characterRequests;

import com.example.PG.s.Dragons.enums.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashMap;
import java.util.Set;
@Data
public class CharacterRequest {
    @NotNull
    private Integer userId;
}
