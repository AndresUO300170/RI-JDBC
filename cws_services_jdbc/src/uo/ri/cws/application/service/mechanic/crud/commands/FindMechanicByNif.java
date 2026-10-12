package uo.ri.cws.application.service.mechanic.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.PersistenceException;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class FindMechanicByNif {

    private String nif;
    private MechanicGateway mg = Factories.persistence.forMechanic();
    private MechanicDtoAssembler assembler = new MechanicDtoAssembler();

    public FindMechanicByNif(String nif) {
        ArgumentChecks.isNotBlank(nif, "Invalid nif");
        this.nif = nif;
    }

    public Optional<MechanicDto> execute(){
        Optional<MechanicDto> result = Optional.empty();
        try(Connection c = Jdbc.createThreadConnection();){
            c.setAutoCommit(false);
            try{
                result = findMechanicByNif(nif);
                c.commit();
                return result;
            }
            catch(Exception e){
                c.rollback();
                throw e;
            }
        } catch (SQLException | BusinessException e) {
            throw new PersistenceException(e);
        }
    }
    private Optional<MechanicDto> findMechanicByNif(String nif) throws BusinessException {
        Optional<MechanicRecord> record = mg.findByNif(nif);
        BusinessChecks.exists(record, "The mechanic does not exist");
        return assembler.toDto(record);
    }
}
