package com.blessing333.restapi.infra.repository;

import com.blessing333.restapi.domain.model.category.Category;
import com.blessing333.restapi.domain.model.category.CategoryNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class JDBCCategoryRepositoryTest {
    @Autowired
    private JDBCCategoryRepository repository;

    @DisplayName("ID로 카테고리 정보를 조회할 수 있어야한다")
    @Test
    void findByTest() {
        Category category = createCategory();

        repository.save(category);

        Category found = repository.findById(category.getId());

        assertNotNull(found);
        assertThat(found.getName()).isEqualTo(category.getName());
    }

    @DisplayName("ID로 카테고리 정보를 조회했을때 카테고리가 존재하지 않는다면 CategoryNotFoundException 발생시킨다")
    @Test
    void findByTestShouldFail() {
        UUID invalidId = UUID.randomUUID();

        assertThrows(CategoryNotFoundException.class, () -> repository.findById(invalidId));
    }

    @DisplayName("새로운 카테고리를 추가할 수 있어야한다")
    @Test
    void saveTest() {
        UUID id = UUID.randomUUID();
        Category category = new Category(id, "test");

        repository.save(category);

        Category found = repository.findById(id);
        assertThat(found.getName()).isEqualTo(category.getName());
    }

    @DisplayName("카테고리 이름을 수정할 수 있어야 한다")
    @Test
    void updateTest() {
        Category category = createCategory();
        String changedName = "changed name";
        repository.save(category);
        category.changeName(changedName);
        repository.update(category);

        Category found = repository.findById(category.getId());
        assertThat(found.getName()).isEqualTo(changedName);
    }

    @DisplayName("카테고리를 삭제할 수 있어야 한다.")
    @Test
    void deleteTest() {
        Category category = createCategory();
        repository.save(category);

        repository.delete(category.getId());

        assertThrows(CategoryNotFoundException.class, () -> repository.findById(category.getId()));
    }


    private Category createCategory() {
        UUID id = UUID.randomUUID();
        return new Category(id, "test");
    }
}