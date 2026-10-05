package uo.ri.cws.application.service.mechanic.crud;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;

import java.util.Optional;

public class MechanicDtoAssembler {

    public MechanicRecord toRecord(MechanicDto dto){
        MechanicRecord r = new MechanicRecord();
        r.id = dto.id;
        r.nif = dto.nif;
        r.name = dto.name;
        r.surname = dto.surname;
        r.version = dto.version;
        return r;
    }

    public Optional<MechanicDto> toDto(Optional<MechanicRecord> byNif){
        Optional<MechanicDto> result = Optional.empty();
        if(byNif.isPresent())
            result = Optional.of(Optional.of(byNif));
        return result;
    }
}
