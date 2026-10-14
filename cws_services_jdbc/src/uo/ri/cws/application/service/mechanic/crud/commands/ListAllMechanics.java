package uo.ri.cws.application.service.mechanic.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.command.Command;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ListAllMechanics implements Command<List<MechanicDto>> {

    private MechanicGateway mg = Factories.persistence.forMechanic();
    private MechanicDtoAssembler assembler = new MechanicDtoAssembler();

    public List<MechanicDto> execute(){
        // Process
        return findAllMechanics();
    }

    public List<MechanicDto> findAllMechanics(){
        List<MechanicRecord> records = mg.findAll();
        List<MechanicDto> mechanics = new LinkedList<>();
        for(MechanicRecord record: records)
            mechanics.add(assembler.toDto(Optional.of(record)).get());
        return mechanics;
    }

}
