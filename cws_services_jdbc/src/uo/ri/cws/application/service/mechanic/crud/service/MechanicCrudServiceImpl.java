package uo.ri.cws.application.service.mechanic.crud.service;

import uo.ri.cws.application.persistence.command.CommandExecutor;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.commands.*;
import uo.ri.util.exception.BusinessException;

import java.util.List;
import java.util.Optional;

public class MechanicCrudServiceImpl implements MechanicCrudService {

    private static final CommandExecutor executor = new CommandExecutor();

    @Override
    public MechanicDto create(MechanicDto dto) throws BusinessException {
        return executor.execute(new AddMechanic(dto));
    }

    @Override
    public void delete(String mechanicId) throws BusinessException {
        executor.execute(new DeleteMechanic(mechanicId));
    }

    @Override
    public void update(MechanicDto dto) throws BusinessException {
        executor.execute(new UpdateMechanic(dto));
    }

    @Override
    public Optional<MechanicDto> findById(String id) throws BusinessException {
        return executor.execute(new FindMechanicById(id));
    }

    @Override
    public Optional<MechanicDto> findByNif(String nif) throws BusinessException {
        return executor.execute(new FindMechanicByNif(nif));
    }

    @Override
    public List<MechanicDto> findAll() throws BusinessException {
        return executor.execute(new ListAllMechanics());
    }
}
