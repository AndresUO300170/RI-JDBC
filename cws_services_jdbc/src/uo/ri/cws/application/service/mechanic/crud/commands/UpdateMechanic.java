package uo.ri.cws.application.service.mechanic.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.command.Command;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.util.assertion.BusinessChecks;
import uo.ri.util.exception.BusinessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UpdateMechanic implements Command<Void> {

    private MechanicDto dto;
    private MechanicGateway mg = Factories.persistence.forMechanic();
    private MechanicDtoAssembler assembler = new MechanicDtoAssembler();

    public UpdateMechanic(MechanicDto dto) {
        ArgumentChecks.isNotNull(dto, "Invalid mechanic dto");
        ArgumentChecks.isNotBlank(dto.name, "Invalid name");
        ArgumentChecks.isNotBlank(dto.surname, "Invalid surname");
        ArgumentChecks.isNotBlank(dto.id, "Invalid id");
        this.dto = dto;
    }

    public Void execute() throws BusinessException {
        // Process
        assertMechanicExists(dto.id);
        updateMechanic(dto);
        return null;
    }

    private void updateMechanic(MechanicDto dto){
        mg.update(assembler.toRecord(dto));
    }

    private void assertMechanicExists(String id) throws BusinessException {
        ArgumentChecks.isNotNull(id, "The id can not be null");
        ArgumentChecks.isNotBlank(id, "Invalid id");
        BusinessChecks.exists(mg.findById(id), "The mechanic does not exist");
    }
}
