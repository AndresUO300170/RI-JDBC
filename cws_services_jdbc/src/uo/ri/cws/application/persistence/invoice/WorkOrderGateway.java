package uo.ri.cws.application.persistence.invoice;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.invoice.WorkOrderGateway.WorkOrderRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface WorkOrderGateway extends Gateway<WorkOrderRecord> {

    public boolean checkWorkOrdersExist(List<String> workOrderIDS);
    public boolean checkWorkOrdersApproved(List<String> workOrderIDs);
    public double calculateTotalInvoice(List<String> workOrderIDs);
    public void linkWorkordersToInvoice(String invoiceId, List<String> workOrderIDs);
    public void markWorkOrderAsInvoiced(List<String> ids);
    public void updateVersion(List<String> workOrderIds);
    public void updateTimeVersion(List<String> workOrderIds);

    public class WorkOrderRecord extends BaseRecord{
        public String vehicleId;
        public String description;
        public LocalDateTime date;
        public BigDecimal amount;
        public String state;
    }
}
