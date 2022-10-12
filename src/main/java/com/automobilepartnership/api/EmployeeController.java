package com.automobilepartnership.api;

import com.automobilepartnership.api.dto.Response;
import com.automobilepartnership.api.dto.employee.EmployeeRequestDto;
import com.automobilepartnership.domain.counsel.service.EmployeeService;
import com.automobilepartnership.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
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

import javax.validation.Valid;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public Response getEmployee(@PathVariable String id) {
        return Response.success(HttpStatus.OK, employeeService.findEmployeeDto(Long.valueOf(id)));
    }

    @GetMapping("/list")
    public Response getEmployeeList() {
        return Response.success(HttpStatus.OK, employeeService.findEmployeeDtoList());
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public Response join(@Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        return Response.success(HttpStatus.CREATED, employeeService.join(employeeRequestDto));
    }

    @PatchMapping("/allocate")
    public Response allocateToCounsel(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam String counselId) {
        employeeService.allocateToCounsel(userPrincipal.getId(), Long.valueOf(counselId));
        return Response.success(HttpStatus.CREATED);
    }

    @PatchMapping("/resolve")
    public Response resolveCounsel(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam String counselId) {
        employeeService.resolveCounsel(userPrincipal.getId(), Long.valueOf(counselId));
        return Response.success(HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping
    public Response withdraw(@RequestParam String employeeId) {
        employeeService.withdraw(Long.valueOf(employeeId));
        return Response.success(HttpStatus.NO_CONTENT);
    }
}