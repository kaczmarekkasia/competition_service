package com.javagda25.securitytemplate.model.dto;

import com.javagda25.securitytemplate.model.Heat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MultipleHeatsDto {

    private List<Heat> heats;

}
