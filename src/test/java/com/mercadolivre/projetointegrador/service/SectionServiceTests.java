package com.mercadolivre.projetointegrador.service;

import com.mercadolivre.projetointegrador.enums.ProductType;
import com.mercadolivre.projetointegrador.section.dto.SectionRequestDto;
import com.mercadolivre.projetointegrador.section.dto.SectionResponseDto;
import com.mercadolivre.projetointegrador.section.model.Section;
import com.mercadolivre.projetointegrador.section.service.SectionService;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SectionServiceTests {

    @Mock
    SectionService sectionService;

    @Test
    public void shouldCreateSectionWithSuccess() {
        SectionRequestDto sectionRequest = SectionRequestDto.builder()
                .sectionCode("FZ01").sectionType("Frozen").warehouseCode("RJ01").maxCapacity(35).build();
        SectionResponseDto sectionResponse = SectionResponseDto.builder()
                .sectionCode("FZ01").sectionType("Frozen").maxCapacity(35).warehouseCode("RJ01").build();

        Mockito.doNothing().when(sectionService).checkIfSectionCodeExists("RJ01");
        Mockito.when(sectionService.createSection(sectionRequest)).thenReturn(sectionResponse);
        sectionResponse = sectionService.createSection(sectionRequest);

        Assertions.assertEquals(sectionRequest.getSectionCode(), sectionResponse.getSectionCode());
        Assertions.assertEquals(sectionRequest.getSectionType(), sectionResponse.getSectionType());
        Assertions.assertEquals(sectionRequest.getWarehouseCode(), sectionResponse.getWarehouseCode());
        Assertions.assertEquals(sectionRequest.getMaxCapacity(), sectionResponse.getMaxCapacity());
    }

    @Test
    public void shouldReturnSectionListWithSuccess() {
        List<Section> mockedSectionList = new ArrayList<>();
        Section section = Section.builder().id(1L).code("FZ01").sectionType(ProductType.FROZEN).maxCapacity(35).build();
        mockedSectionList.add(section);
        Warehouse warehouse = Warehouse.builder().id(1L).code("RJ01").sections(mockedSectionList).build();
        section.setWarehouse(warehouse);
        SectionResponseDto sectionResponse = SectionResponseDto.ConvertToResponseDto(section);
        List<SectionResponseDto> sectionsResponseList = new ArrayList<>();
        sectionsResponseList.add(sectionResponse);

        Mockito.when(sectionService.findAllSections()).thenReturn(sectionsResponseList);
        List<SectionResponseDto> sectionResponseDtoList = sectionService.findAllSections();
        List<SectionResponseDto> sectionResponseDtos = SectionResponseDto.ConvertToResponseDto(mockedSectionList);

        Assertions.assertEquals(sectionResponseDtos.size(), 1);
        Assertions.assertEquals(sectionResponseDtos.get(0).getId(), sectionResponseDtoList.get(0).getId());
        Assertions.assertEquals(sectionResponseDtos.get(0).getSectionCode(), sectionResponseDtoList.get(0).getSectionCode());
        Assertions.assertEquals(sectionResponseDtos.get(0).getSectionType(), sectionResponseDtoList.get(0).getSectionType());
        Assertions.assertEquals(sectionResponseDtos.get(0).getWarehouseCode(), sectionResponseDtoList.get(0).getWarehouseCode());
        Assertions.assertEquals(sectionResponseDtos.get(0).getMaxCapacity(), sectionResponseDtoList.get(0).getMaxCapacity());
    }

    @Test
    public void shouldFindSectionByCodeWithSuccess() {
        List<Section> mockedSectionList = new ArrayList<>();
        Section section = Section.builder().id(1L).code("FZ01").sectionType(ProductType.FROZEN).maxCapacity(35).build();
        mockedSectionList.add(section);
        Warehouse warehouse = Warehouse.builder().id(1L).code("RJ01").sections(mockedSectionList).build();
        section.setWarehouse(warehouse);

        Mockito.when(sectionService.getSectionBySectionCode("FZ01")).thenReturn(section);
        Section sectionResponse = sectionService.getSectionBySectionCode("FZ01");
        SectionResponseDto sectionResponseDto = SectionResponseDto.ConvertToResponseDto(sectionResponse);

        Assertions.assertEquals(section.getId(), sectionResponseDto.getId());
        Assertions.assertEquals(section.getCode(), sectionResponseDto.getSectionCode());
        Assertions.assertEquals(section.getSectionType().getDescription(), sectionResponseDto.getSectionType());
        Assertions.assertEquals(section.getWarehouse().getCode(), sectionResponseDto.getWarehouseCode());
        Assertions.assertEquals(section.getMaxCapacity(), sectionResponseDto.getMaxCapacity());
    }

    @Test
    public void shouldNotFindSectionByCode() {
        Mockito.when(sectionService.getSectionBySectionCode("ABC")).thenThrow(ResponseStatusException.class);
        Assertions.assertThrows(ResponseStatusException.class, () -> sectionService.getSectionBySectionCode("ABC"));
    }

}
