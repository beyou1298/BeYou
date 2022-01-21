package com.beyou.admin.brand;

import com.beyou.common.entity.Brand;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class BrandServiceTests {

    @MockBean
    private BrandRepository repo;

    @InjectMocks
    private BrandService service;
    
    @Test
    public void testCheckUniqueInNewModeReturnDuplicateName(){
        Integer id = null;
        String name = "Acer";

        Brand brand = new Brand(name);

        Mockito.when(repo.findByName(name)).thenReturn(brand);

        String result = service.checkUnique(id, name);
    }

    @Test
    public void testCheckUniqueInNewModeReturnOK(){
        Integer id = null;
        String name = "AMD";

        Mockito.when(repo.findByName(name)).thenReturn(null);

        String result = service.checkUnique(id, name);
    }

    @Test
    public void testCheckUniqueInEditModeReturnDuplicate(){
        Integer id = 1;
        String name = "Canon";

        Brand brand = new Brand(id,name);

        Mockito.when(repo.findByName(name)).thenReturn(brand);

        String result = service.checkUnique(2,"Canon");
    }

    @Test
    public void testCheckUniqueInEditModeReturnOK(){
        Integer id = 1;
        String name = "Acer";

        Brand brand = new Brand(id,name);

        Mockito.when(repo.findByName(name)).thenReturn(brand);

        String result = service.checkUnique(id, name);
    }
}
