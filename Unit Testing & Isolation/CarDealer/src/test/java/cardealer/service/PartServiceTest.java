package cardealer.service;

import cardealer.domain.entities.Part;
import cardealer.domain.models.service.PartServiceModel;
import cardealer.domain.models.service.SupplierServiceModel;
import cardealer.repository.PartRepository;
import cardealer.repository.SupplierRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PartServiceTest {
    @Autowired
    private PartRepository partRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    private ModelMapper modelMapper;
    private PartServiceImpl partService;
    private PartServiceModel part;

    @Before
    public void setUp() throws Exception {
        this.modelMapper = new ModelMapper();
        this.partService = new PartServiceImpl(this.partRepository, this.modelMapper);
        this.part = new PartServiceModel();
        part.setName("Engine");
        part.setPrice(BigDecimal.TEN);
    }

    @Test
    public void partService_savePartWithCorrectValues_returnsCorrect() {

        PartServiceModel actual = this.partService.savePart(part);
        PartServiceModel expected = this.modelMapper.map(this.partRepository.findAll().get(0), PartServiceModel.class);
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getPrice(), expected.getPrice());
        Assert.assertEquals(actual.getId(), expected.getId());
        System.out.println();
    }

    @Test(expected = Exception.class)
    public void partService_savePartWithNullValues_throwsException() {

        part.setSupplier(new SupplierServiceModel());
        PartServiceModel actual = this.partService.savePart(part);
        PartServiceModel expected = this.modelMapper.map(this.partRepository.findAll().get(0), PartServiceModel.class);
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getPrice(), expected.getPrice());
        Assert.assertEquals(actual.getSupplier(), expected.getSupplier());
    }


    @Test
    public void partService_editPartWithCorrectValues_returnsCorrect() {
        part = this.partService.savePart(part);

        part.setName("Dinamo");
        part.setPrice(BigDecimal.ONE);
        PartServiceModel actual = this.partService.editPart(
                this.modelMapper.map(part, PartServiceModel.class));
        PartServiceModel expected = this.modelMapper.map(this.partRepository.findAll().get(0), PartServiceModel.class);
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getPrice(), expected.getPrice());

    }

    @Test(expected = Exception.class)
    public void partService_editPartWithNullValues_throwsException() {
        part.setSupplier(new SupplierServiceModel());
        part = this.partService.savePart(part);

        part.setName(null);
        part.setPrice(null);
        part.setSupplier(null);
        PartServiceModel actual = this.partService.editPart(
                this.modelMapper.map(part, PartServiceModel.class));
    }

    @Test
    public void partService_deletePart_checkIsDeleted() {
        part = this.partService.savePart(part);
        PartServiceModel actual = this.partService.deletePart(part.getId());
        Part expected = this.partRepository.findById(part.getId()).orElse(null);
        Assert.assertNotNull(actual);
        Assert.assertNull(expected);
    }

    @Test(expected = Exception.class)
    public void partService_deletePart_throwsException() {
        part.setSupplier(new SupplierServiceModel());
        part = this.partService.savePart(part);
        PartServiceModel actual = this.partService.deletePart("invalidID");
    }

    @Test
    public void partService_findPartWithCorrectId_returnsCorrectCar() {
        part = this.partService.savePart(part);
        PartServiceModel actual = this.partService.findPartById(part.getId());
        Assert.assertEquals(part.getId(), actual.getId());
        Assert.assertEquals(part.getName(), actual.getName());
        Assert.assertEquals(part.getPrice(), actual.getPrice());
    }

    @Test(expected = Exception.class)
    public void partService_findPartWithIncorrectId_throwsException() {
        part.setSupplier(new SupplierServiceModel());
        part = this.partService.savePart(part);
        PartServiceModel actual = this.partService.findPartById("invalidID");
    }
}