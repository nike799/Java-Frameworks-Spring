package cardealer.service;

import cardealer.domain.models.service.SupplierServiceModel;

public interface SupplierService {

    SupplierServiceModel saveSupplier(SupplierServiceModel supplierServiceModel);

    SupplierServiceModel editSupplier(SupplierServiceModel supplierServiceModel);

    SupplierServiceModel deleteSupplier(String id);

    SupplierServiceModel findSupplierById(String id);
}
