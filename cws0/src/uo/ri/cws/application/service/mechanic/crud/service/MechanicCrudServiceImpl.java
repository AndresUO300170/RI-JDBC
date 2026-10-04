package uo.ri.cws.application.service.mechanic.crud.service;

import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.commands.*;
import uo.ri.util.exception.BusinessException;

import java.util.List;
import java.util.Optional;

public class MechanicCrudServiceImpl implements MechanicCrudService {


    @Override
    public MechanicDto create(MechanicDto dto) throws BusinessException {
        AddMechanic addMechanic = new AddMechanic(dto);
        return addMechanic.execute();
    }

    @Override
    public void delete(String mechanicId) throws BusinessException {
        DeleteMechanic deleteMechanic = new DeleteMechanic(mechanicId);
        deleteMechanic.execute();
    }

    @Override
    public void update(MechanicDto dto) throws BusinessException {
        UpdateMechanic updateMechanic = new UpdateMechanic(dto);
        updateMechanic.execute();
    }

    @Override
    public Optional<MechanicDto> findById(String id) throws BusinessException {
        return new FindMechanicById(id).execute();
    }

    @Override
    public Optional<MechanicDto> findByNif(String nif) throws BusinessException {
        return new FindMechanicByNif(nif).execute();
    }

    @Override
    public List<MechanicDto> findAll() throws BusinessException {
        return new ListAllMechanics().execute();
    }
}
