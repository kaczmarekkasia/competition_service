package com.javagda25.securitytemplate.service;

import com.javagda25.securitytemplate.model.Heat;
import com.javagda25.securitytemplate.model.dto.MultipleHeatsDto;
import com.javagda25.securitytemplate.repository.HeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HeatService {

    private final HeatRepository heatRepository;

    public void save(MultipleHeatsDto multipleHeatsDto) {
        List<Long> heatIds = mapHeatsToHeatIds(multipleHeatsDto);

        List<Heat> heatsFromDb = heatRepository.findAllById(heatIds);

        copyRiderData(multipleHeatsDto, heatsFromDb);
        updateToFullOfRiders(heatsFromDb);

        heatRepository.saveAll(heatsFromDb);
    }

    private List<Long> mapHeatsToHeatIds(MultipleHeatsDto multipleHeatsDto) {
        return multipleHeatsDto.getHeats()
                .stream()
                .map(Heat::getId)
                .collect(Collectors.toList());
    }

    private void copyRiderData(MultipleHeatsDto multipleHeatsDto, List<Heat> heatsFromDb) {
        heatsFromDb.forEach(heat -> multipleHeatsDto.getHeats().stream()
                .filter(updatedHeat -> updatedHeat.getId().equals(heat.getId()))
                .findAny()
                .ifPresent(updatedHeatWithMatchingId -> heat.setRiders(updatedHeatWithMatchingId.getRiders())));
    }

    private void updateToFullOfRiders(List<Heat> heatsFromDb) {
        heatsFromDb.forEach(heat -> heat.setIsFullOfRiders(heat.getRiders().size() == 4));
    }
}
