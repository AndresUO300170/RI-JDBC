package uo.ri.cws.application.service.invoice.create;

import uo.ri.cws.application.persistence.invoice.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService.WorkOrderDto;

import java.util.Optional;

public class WorkOrderDtoAssembler {

    public WorkOrderRecord toRecord(WorkOrderDto dto){
        WorkOrderRecord r = new WorkOrderRecord();
        r.vehicleId = dto.vehicleId;
        r.description = dto.description;
        r.date = dto.date;
        r.amount = dto.amount;
        r.state = dto.state;
        return r;
    }

    public Optional<WorkOrderDto> toDto(Optional<WorkOrderRecord> record){
        if(record.isEmpty())
            return Optional.empty();
        WorkOrderRecord r = record.get();
        WorkOrderDto dto = new WorkOrderDto();
        dto.vehicleId = r.vehicleId;
        dto.description = r.description;
        dto.date = r.date;
        dto.amount = r.amount;
        dto.state = r.state;
        return Optional.of(dto);
    }
}
