package com.blessing333.restapi.infra.repository;

import com.blessing333.restapi.domain.model.category.Category;
import com.blessing333.restapi.domain.model.category.CategoryRepository;
import com.blessing333.restapi.domain.model.order.Item;
import com.blessing333.restapi.domain.model.order.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@RequiredArgsConstructor
class JDBCItemRepositoryTest {
    private final UUID categoryId = UUID.randomUUID();
    @Autowired
    private JDBCItemRepository repository;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void addDefaultCategory() {
        Category category = new Category(categoryId, "default-category");
        categoryRepository.save(category);
    }

    @AfterEach
    void deleteAllData(){
        repository.deleteAll();
        categoryRepository.deleteAll();
    }

    @DisplayName("새로운 Item을 저장할 수 있어야한다")
    @Test
    void saveItem() {
        UUID itemId = UUID.randomUUID();
        String name = "testname";
        String desc = "testDes";
        long price = 10000;
        int quantity = 1000;
        Item item = Item.createNewItem(itemId, categoryId, name, desc, price, quantity, LocalDateTime.now());

        repository.save(item);
        Item found = repository.findById(itemId);

        assertNotNull(found);
    }

    @DisplayName("id로 Item을 조회할 수 있어야 한다")
    @Test
    void findItem() {
        UUID itemId = UUID.randomUUID();
        String name = "testname";
        String desc = "testDes";
        long price = 10000;
        int quantity = 1000;
        LocalDateTime createdAt = LocalDateTime.now();
        Item item = Item.createNewItem(itemId, categoryId, name, desc, price, quantity, createdAt);

        repository.save(item);
        Item found = repository.findById(itemId);

        assertThat(found.getId()).isEqualTo(itemId);
        assertThat(found.getName()).isEqualTo(name);
        assertThat(found.getDescription()).isEqualTo(desc);
        assertThat(found.getPrice()).isEqualTo(price);
        assertThat(found.getQuantity()).isEqualTo(quantity);
    }

    @DisplayName("ID로 아이템을 조회할 수 없는 경우, ItemNotFoundException을 발생시킨다.")
    @Test
    void findTestShouldFail(){
        UUID invalidId = UUID.randomUUID();

        assertThrows(ItemNotFoundException.class,()->repository.findById(invalidId));
    }

    @DisplayName("모든 아이템을 조회할 수 있어야한다")
    @Test
    void findAllTest(){
        UUID firstId = UUID.randomUUID();
        String firstName = "first";
        String firstDesc = "first";
        long firstPrice = 10000;
        int firstQuantity = 1000;
        UUID secondId = UUID.randomUUID();
        String secondName = "second";
        String secondDesc = "second";
        long secondPrice = 50000000;
        int secondQuantity = 10;
        Item item = Item.createNewItem(firstId, categoryId, firstName, firstDesc, firstPrice, firstQuantity, LocalDateTime.now());
        Item item2 = Item.createNewItem(secondId, categoryId, secondName, secondDesc, secondPrice, secondQuantity, LocalDateTime.now());
        repository.save(item);
        repository.save(item2);

        List<Item> items = repository.findAll();

        assertThat(items).hasSize(2).contains(item,item2);
    }
}