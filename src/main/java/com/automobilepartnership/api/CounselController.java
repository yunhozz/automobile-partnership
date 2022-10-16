package com.automobilepartnership.api;

import com.automobilepartnership.api.dto.Response;
import com.automobilepartnership.api.dto.counsel.CounselRequestDto;
import com.automobilepartnership.api.dto.counsel.UpdateRequestDto;
import com.automobilepartnership.domain.counsel.persistence.repository.CounselRepository;
import com.automobilepartnership.domain.counsel.service.CounselService;
import com.automobilepartnership.domain.counsel.service.ImageService;
import com.automobilepartnership.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/counsel")
@RequiredArgsConstructor
public class CounselController {

    private final CounselService counselService;
    private final ImageService imageService;
    private final CounselRepository counselRepository;

    @GetMapping("/{id}")
    public Response getCounsel(@PathVariable String id) {
        return Response.success(HttpStatus.OK, counselService.findCounselDto(Long.valueOf(id)));
    }

    @GetMapping("/page")
    public Response getCounselPage(@RequestParam(required = false, defaultValue = "전체") String sortTypeDesc, Pageable pageable) {
        return Response.success(HttpStatus.OK, counselRepository.sortPage(sortTypeDesc, pageable));
    }

    @GetMapping("/page/search")
    public Response searchCounselPage(@RequestParam(required = false) String keyword, Pageable pageable) {
        if (keyword != null && keyword.length() < 2) {
            return Response.failure(HttpStatus.LENGTH_REQUIRED, -1000, "2글자 이상의 키워드를 입력해주세요");
        }
        return Response.success(HttpStatus.OK, counselRepository.searchPageWithKeyword(keyword, pageable));
    }

    // TODO : 권한 설정 ?
    @GetMapping("/list")
    public Response getCounselListByEmployee(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return Response.success(HttpStatus.OK, counselService.findCounselDtoListByEmployee(String.valueOf(userPrincipal.getId())));
    }

    @PostMapping
    public Response createCounsel(@AuthenticationPrincipal UserPrincipal userPrincipal, @Valid @RequestBody CounselRequestDto counselRequestDto,
                                  @RequestParam(required = false) MultipartFile[] files) throws IOException {
        Long counselId = counselService.create(userPrincipal.getId(), counselRequestDto);
        imageService.saveAndUpload(counselId, files);

        return Response.success(HttpStatus.CREATED, counselId);
    }

    @PatchMapping
    public Response updateCounsel(@RequestParam String counselId, @Valid @RequestBody UpdateRequestDto updateRequestDto) {
        return Response.success(
                HttpStatus.CREATED,
                counselService.updateTypeAndTitleAndDetail(
                        Long.valueOf(counselId),
                        updateRequestDto.getType(),
                        updateRequestDto.getTitle(),
                        updateRequestDto.getDetail()
                )
        );
    }

    @DeleteMapping
    public Response deleteCounsel(@RequestParam String counselId) {
        counselService.delete(Long.valueOf(counselId));
        return Response.success(HttpStatus.NO_CONTENT);
    }
}