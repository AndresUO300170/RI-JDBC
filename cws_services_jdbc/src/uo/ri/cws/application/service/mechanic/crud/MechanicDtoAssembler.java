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
        if(byNif.isEmpty())
            return Optional.empty();
        MechanicRecord r = byNif.get();
        MechanicDto dto = new MechanicDto();
        dto.id = r.id;
        dto.version = r.version;
        dto.nif = r.nif;
        dto.name = r.name;
        dto.surname = r.surname;

        return Optional.of(dto);
    }
}
