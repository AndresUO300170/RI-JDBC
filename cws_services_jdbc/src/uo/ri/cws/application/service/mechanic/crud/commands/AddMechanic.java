package uo.ri.cws.application.service.mechanic.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.command.Command;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.PersistenceException;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;

import java.sql.*;
import java.util.UUID;

public class AddMechanic implements Command<MechanicDto> {

    private MechanicDto dto;
    private MechanicGateway mg = Factories.persistence.forMechanic();
    private MechanicDtoAssembler assembler = new MechanicDtoAssembler();

    public AddMechanic(MechanicDto dto){
        ArgumentChecks.isNotNull(dto, "Invalid mechanic dto");
        ArgumentChecks.isNotBlank(dto.nif, "Invalid nid");
        ArgumentChecks.isNotBlank(dto.name, "Invalid name");
        ArgumentChecks.isNotBlank(dto.surname, "Invalid surname");
        this.dto = dto;
        this.dto.nif = dto.nif;
        this.dto.name = dto.name;
        this.dto.surname = dto.surname;
        this.dto.id = UUID.randomUUID().toString();
        this.dto.version = 1;

        this.dto = dto;
    }

    public MechanicDto execute() throws BusinessException {
        assertMechanicDoesNotExist(dto.nif);
        insertMechanic(dto);
        return dto;
    }

    private void insertMechanic(MechanicDto dto){
        mg.add(assembler.toRecord(dto));
    }

    private void assertMechanicDoesNotExist(String nif) throws BusinessException {
        BusinessChecks.doesNotExist(mg.findByNif(nif), "A mechanic with the same NIF already exists");
    }
}
