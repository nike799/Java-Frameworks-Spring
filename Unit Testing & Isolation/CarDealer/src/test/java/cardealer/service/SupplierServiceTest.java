package cardealer.service;

import cardealer.domain.entities.Supplier;
import cardealer.domain.models.service.SupplierServiceModel;
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

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SupplierServiceTest {
    @Autowired
    private SupplierRepository supplierRepository;
    private ModelMapper modelMapper;
    private SupplierServiceImpl supplierService;
    private Supplier supplier;

    @Before
    public void setUp() throws Exception {
        this.modelMapper = new ModelMapper();
        this.supplierService = new SupplierServiceImpl(this.supplierRepository, this.modelMapper);
        this.supplier = new Supplier();
        this.supplier.setName("Pesho");
        this.supplier.setImporter(true);
    }

    @Test
    public void supplierService_saveSupplierWithCorrectValues_returnsCorrect() {
        SupplierServiceModel toBeSaved = new SupplierServiceModel();
        toBeSaved.setName("Nike");
        toBeSaved.setImporter(true);
        SupplierServiceModel actual = this.supplierService.saveSupplier(toBeSaved);
        SupplierServiceModel expected = this.modelMapper.map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);
        Assert.assertEquals(actual.getId(), expected.getId());
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.isImporter(), expected.isImporter());
    }

    @Test(expected = Exception.class)
    public void supplierService_saveSupplierWithNullValues_throwsException() {
        SupplierServiceModel toBeSaved = new SupplierServiceModel();
        toBeSaved.setName(null);
        toBeSaved.setImporter(true);
        SupplierServiceModel actual = this.supplierService.saveSupplier(toBeSaved);
        SupplierServiceModel expected = this.modelMapper.map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);
        Assert.assertEquals(actual.getId(), expected.getId());
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.isImporter(), expected.isImporter());
    }

    @Test
    public void supplierService_editSupplierWithCorrectValues_returnsCorrect() {
        supplier = this.supplierRepository.save(supplier);
        Supplier toBeEdited = new Supplier();
        toBeEdited.setId(supplier.getId());
        toBeEdited.setName("Gosho");
        toBeEdited.setImporter(false);
        SupplierServiceModel actual = this.supplierService.editSupplier(
                this.modelMapper.map(toBeEdited, SupplierServiceModel.class));
        SupplierServiceModel expected = this.modelMapper.map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);
        Assert.assertEquals(actual.getId(), expected.getId());
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.isImporter(), expected.isImporter());
    }

    @Test(expected = Exception.class)
    public void supplierService_editSupplierWithNullValues_throwsException() {

        supplier = this.supplierRepository.save(supplier);

        Supplier toBeEdited = new Supplier();
        toBeEdited.setId(supplier.getId());
        toBeEdited.setName(null);
        toBeEdited.setImporter(false);
        SupplierServiceModel actual = this.supplierService.editSupplier(
                this.modelMapper.map(toBeEdited, SupplierServiceModel.class));
        SupplierServiceModel expected = this.modelMapper.map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);
        Assert.assertEquals(actual.getId(), expected.getId());
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.isImporter(), expected.isImporter());
    }

    @Test
    public void supplierService_deleteSupplier_checkIsDeleted() {
        supplier = this.supplierRepository.save(supplier);
        SupplierServiceModel actual = this.supplierService.deleteSupplier(supplier.getId());
        Supplier expected = this.supplierRepository.findById(supplier.getId()).orElse(null);
        Assert.assertNotNull(actual);
        Assert.assertNull(expected);
    }

    @Test(expected = Exception.class)
    public void supplierService_deleteSupplier_throwsException() {
        supplier = this.supplierRepository.save(supplier);
        SupplierServiceModel actual = this.supplierService.deleteSupplier("invalidID");
    }

    @Test
    public void supplierService_findSupplierWithCorrectId_returnsCorrectSupplier() {
        supplier = this.supplierRepository.save(supplier);
        SupplierServiceModel actual = this.supplierService.findSupplierById(supplier.getId());
        Assert.assertEquals(supplier.getId(), actual.getId());
        Assert.assertEquals(supplier.getName(), actual.getName());
        Assert.assertEquals(supplier.isImporter(), actual.isImporter());
    }

    @Test(expected = Exception.class)
    public void supplierService_findSupplierWithIncorrectId_throwsException() {
        supplier = this.supplierRepository.save(supplier);
        SupplierServiceModel actual = this.supplierService.findSupplierById("invalidID");
    }
}