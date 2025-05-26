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

/**
 * Service provides read/write operations on {@link FormData} objects.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class FormDataService {
    private final FormDataRepository formDataRepository;

    /**
     * Converts the provided {@link FormDataDto} object and stores it as {@link FormData} in the DB.
     * @param dto
     * @return id of the stored object
     * @throws ResponseStatusException if the dto does not contain all required data
     */
    public long saveFormData(FormDataDto dto) throws ResponseStatusException {
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

    /**
     * Fetches {@link FormData} by id and returns it as {@link FormDataDto}.
     * @param formId
     * @return required object as {@link FormDataDto}
     * @throws ResponseStatusException if object with id is not present
     */
    public FormDataDto getFormData(long formId) throws ResponseStatusException {
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Form data for this id not found"));
    }
}
