package uo.ri.cws.application.service.mechanic.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.util.assertion.BusinessChecks;
import uo.ri.util.exception.BusinessException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class FindMechanicById {

    private String idMechanic;
    private MechanicGateway mg = Factories.persistence.forMechanic();
    private MechanicDtoAssembler assembler = new MechanicDtoAssembler();

    public FindMechanicById(String idMechanic){
        ArgumentChecks.isNotBlank(idMechanic, "Invalid id");
        this.idMechanic = idMechanic;
    }

    public Optional<MechanicDto> execute(){
        try (Connection c = Jdbc.createThreadConnection()) {
            c.setAutoCommit(false);
            try{
                Optional<MechanicDto> dto = findMechanicById(idMechanic);
                c.commit();
                return dto;
            }
            catch(Exception e){
                c.rollback();
                throw e;
            }
        } catch (SQLException | BusinessException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<MechanicDto> findMechanicById(String id) throws BusinessException {
        Optional<MechanicRecord> record = mg.findById(id);
        BusinessChecks.exists(record, "The mechanic does not exist");
        return assembler.toDto(record);
    }
}
