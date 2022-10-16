package com.automobilepartnership.domain.counsel.persistence;

import com.automobilepartnership.domain.counsel.dto.CounselQueryDto;
import com.automobilepartnership.domain.counsel.persistence.entity.Counsel;
import com.automobilepartnership.domain.counsel.persistence.entity.CounselType;
import com.automobilepartnership.domain.counsel.persistence.entity.Employee;
import com.automobilepartnership.domain.counsel.persistence.repository.CounselRepository;
import com.automobilepartnership.domain.counsel.persistence.repository.EmployeeRepository;
import com.automobilepartnership.domain.member.persistence.entity.Member;
import com.automobilepartnership.domain.member.persistence.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CounselRepositoryTest {

    @Autowired
    CounselRepository counselRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    Member member;
    Employee employee;

    @BeforeEach
    void beforeEach() {
        member = createMember();
        employee = createEmployee("employee", 30, "서울특별시");
    }

    @Test
    void findByEmployeeId() throws Exception {
        //given
        Counsel counsel1 = createCounsel(member, "test1", "this is test1");
        Counsel counsel2 = createCounsel(member, "test2", "this is test2");
        Counsel counsel3 = createCounsel(member, "test3", "this is test3");

        counselRepository.save(counsel1);
        counselRepository.save(counsel2);
        counselRepository.save(counsel3);

        //when
        counsel1.allocateEmployee(employee);
        counsel2.allocateEmployee(employee);
        counsel3.allocateEmployee(employee);

        List<Counsel> result = counselRepository.findByEmployeeId(String.valueOf(employee.getId()));

        //then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.stream().anyMatch(counsel -> counsel.getEmployeeId().equals(String.valueOf(employee.getId())))).isTrue();
    }

    @Test
    void isEmployeeIdExist() throws Exception {
        //given
        Counsel counsel1 = createCounsel(member, "test1", "this is test1");
        Counsel counsel2 = createCounsel(member, "test2", "this is test2");

        counselRepository.save(counsel1);
        counselRepository.save(counsel2);

        //when
        counsel1.allocateEmployee(employee);

        boolean result1 = counselRepository.isEmployeeIdExist(counsel1.getId()); // true
        boolean result2 = counselRepository.isEmployeeIdExist(counsel2.getId()); // false

        //then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    void sortPage() throws Exception {
        //given
        for (int i = 1; i <= 10; i++) {
            Counsel counsel = createCounsel(member, "test" + i, "this is test" + i);
            counselRepository.save(counsel);

            counsel.allocateEmployee(employee);
            counsel.resolvedByEmployee(employee);
            Thread.sleep(10);
        }
        for (int i = 11; i <= 20; i++) {
            Counsel counsel = createCounsel(member, "test" + i, "this is test" + i);
            counselRepository.save(counsel);

            counsel.allocateEmployee(employee);
            Thread.sleep(10);
        }

        //when
        Page<CounselQueryDto> result1 = counselRepository.sortPage("해결", Pageable.ofSize(10));
        Page<CounselQueryDto> result2 = counselRepository.sortPage("미해결", Pageable.ofSize(10));
        Page<CounselQueryDto> result3 = counselRepository.sortPage("전체", Pageable.ofSize(20));

        //then
        assertThat(result1).extracting("isResolved").containsOnly(true);
        assertThat(result2).extracting("isResolved").containsOnly(false);
        assertThat(result3).extracting("isResolved").contains(true, false);
        assertThat(employee.getCount()).isEqualTo(10);
    }

    @Test
    void searchPageWithKeyword() throws Exception {
        //given
        for (int i = 1; i <= 10; i++) {
            Counsel counsel = createCounsel(member, "a", "this is apple");
            counselRepository.save(counsel);
            Thread.sleep(10);
        }
        for (int i = 11; i <= 20; i++) {
            Counsel counsel = createCounsel(member, "b", "this is banana");
            counselRepository.save(counsel);
            Thread.sleep(10);
        }
        for (int i = 21; i <= 30; i++) {
            Counsel counsel = createCounsel(member, "c", "this is cat");
            counselRepository.save(counsel);
            Thread.sleep(10);
        }

        //when
        Page<CounselQueryDto> result1 = counselRepository.searchPageWithKeyword("apple", Pageable.ofSize(10));
        Page<CounselQueryDto> result2 = counselRepository.searchPageWithKeyword("banana", Pageable.ofSize(10));
        Page<CounselQueryDto> result3 = counselRepository.searchPageWithKeyword(null, Pageable.ofSize(30));

        //then
        assertThat(result1).extracting("title").containsOnly("a");
        assertThat(result2).extracting("title").containsOnly("b");
        assertThat(result3).extracting("title").contains("a", "b", "c");
    }

    Member createMember() {
        Member member = Member.builder()
                .email("test@gmail.com")
                .build();

        memberRepository.save(member);
        return member;
    }

    Employee createEmployee(String name, int age, String residence) {
        Employee employee = new Employee(name, age, residence);
        employeeRepository.save(employee);

        return employee;
    }

    Counsel createCounsel(Member member, String title, String detail) {
        return Counsel.builder()
                .member(member)
                .counselType(CounselType.ETC)
                .title(title)
                .detail(detail)
                .build();
    }
}