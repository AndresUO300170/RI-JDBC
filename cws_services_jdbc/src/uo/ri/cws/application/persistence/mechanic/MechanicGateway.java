package uo.ri.cws.application.persistence.mechanic;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.util.exception.PersistenceException;

import java.util.Optional;

public interface MechanicGateway extends Gateway<MechanicRecord> {

    Optional<MechanicRecord> findByNif(String nif) throws PersistenceException;

    public class MechanicRecord extends BaseRecord{
        public String nif;
        public String name;
        public String surname;
    }
}
