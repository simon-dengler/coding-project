package de.keywork.backend.service;

import de.keywork.backend.dto.FormDataDto;
import de.keywork.backend.entity.FormData;
import de.keywork.backend.repository.FormDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class FormDataService {
    private final FormDataRepository formDataRepository;

    public long saveFormData(FormDataDto dto) {
        FormData formData;
        if (dto.getId() != null) {
            formData = requireById(dto.getId());
        } else {
            formData = new FormData();
        }

        if (ObjectUtils.isEmpty(dto.getFirstName())
                || ObjectUtils.isEmpty(dto.getLastName())
                ||  ObjectUtils.isEmpty(dto.getEmail())
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Required field not filled");
        }
        formData.setFirstName(dto.getFirstName());
        formData.setLastName(dto.getLastName());
        formData.setEmail(dto.getEmail());
        formData.setPhone(dto.getPhone());
        formData.setFavouriteAnimal(dto.getFavouriteAnimal());
        formData.setZodiac(dto.getZodiac());
        formData = formDataRepository.save(formData);
        return formData.getId();
    }

    public FormDataDto getFormData(long formId) {
        FormData formData = requireById(formId);

        FormDataDto dto = new FormDataDto();
        dto.setEmail(formData.getEmail());
        dto.setFirstName(formData.getFirstName());
        dto.setLastName(formData.getLastName());
        dto.setPhone(formData.getPhone());
        dto.setFavouriteAnimal(formData.getFavouriteAnimal());
        dto.setZodiac(formData.getZodiac());
        dto.setId(formData.getId());

        return dto;
    }

    public FormData requireById(long formId) {
        return formDataRepository.findById(formId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Form data for this id not found"));
    }
}
