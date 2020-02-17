package com.javagda25.securitytemplate.model.dto;

import com.javagda25.securitytemplate.model.Heat;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MultipleHeatsDto {

    private List<Heat> heats;

    public void addHeat(Heat heat) {
        this.heats.add(heat);
    }
}
