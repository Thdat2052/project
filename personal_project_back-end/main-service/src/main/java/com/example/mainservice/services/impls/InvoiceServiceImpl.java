package com.example.mainservice.services.impls;

import com.example.common.service.entities.InvoiceEntity;
import com.example.common.service.entities.UtilityIndexEntity;
import com.example.common.service.enums.ContractStatusEnum;
import com.example.common.service.enums.InvoiceStatusEnum;
import com.example.common.service.enums.MaintenanceRequestStatusEnum;
import com.example.common.service.enums.ServiceTypeEnum;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.mappers.InvoiceMapper;
import com.example.common.service.models.responses.ContractResponse;
import com.example.common.service.services.impls.CommonServiceImpl;
import com.example.common.service.utils.AuthUtils;
import com.example.mainservice.constants.ErrorModelConstants;
import com.example.mainservice.models.*;
import com.example.common.service.models.InvoiceModel;
import com.example.mainservice.models.responses.RevenueStatResponse;
import com.example.mainservice.repositories.InvoiceRepository;
import com.example.mainservice.services.ContractService;
import com.example.mainservice.services.InvoiceService;
import com.example.mainservice.services.UserService;
import com.example.mainservice.services.UtilityIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class InvoiceServiceImpl extends CommonServiceImpl<InvoiceEntity, Long, InvoiceRepository>
        implements InvoiceService {
    @Autowired
    private ContractService contractService;
    @Autowired
    private UserService userService;
    @Autowired
    private UtilityIndexService utilityIndexService;

    protected InvoiceServiceImpl(InvoiceRepository repo) {
        super(repo);
    }


    @Override
    public List<RevenueStatResponse> inventory() {
        List<RevenueStatResponse> revenueStatResponses = repo.inventory();
        return fillMissingMonths(revenueStatResponses);
    }

    private List<RevenueStatResponse> fillMissingMonths(List<RevenueStatResponse> originalList) {
        Map<Integer, RevenueStatResponse> monthRevenueMap = originalList.stream()
                .collect(Collectors.toMap(
                        RevenueStatResponse::getMonth,
                        r -> r,
                        (existing, replacement) -> existing
                ));

        List<RevenueStatResponse> result = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            RevenueStatResponse revenue = monthRevenueMap.getOrDefault(month, createEmptyRevenue(month));
            result.add(revenue);
        }

        return result;
    }

    private RevenueStatResponse createEmptyRevenue(int month) {
        RevenueStatResponse empty = new RevenueStatResponse();
        empty.setMonth(month);
        empty.setTotalRevenue(BigDecimal.ZERO);
        return empty;
    }


    @Override
    public RevenueStatResponse inventoryByMonth(int month, int year) {
        return repo.inventoryByMonth(month, year);
    }

    @Override
    public RevenueStatResponse inventoryByQuarter(int quarter, int year) {
        return repo.inventoryByQuarter(quarter, year);
    }

    @Override
    public RevenueStatResponse inventoryByYear(int year) {
        return repo.inventoryByYear(year);
    }

    @Override
    public List<InvoiceInformationModel> getRoomInvoiceServiceData(Long roomId, ContractStatusEnum contractStatus) {
        if (roomId == null) throw new AppException(ErrorModelConstants.ROOM_ID_REQUIRED);
        return repo.getRoomInvoiceServiceData(roomId, contractStatus);
    }

    @Override
    public List<ServiceInRoomModel> getMaintenanceOfRoom(Long contractId, Integer month, Integer year) {
        if (contractId == null) throw new AppException(ErrorModelConstants.ROOM_ID_REQUIRED);
        LocalDate now = LocalDate.now();
        if (month == null) month = now.getMonthValue();
        if (year == null) year = now.getYear();
        return repo.getMaintenanceOfRoom(contractId, month, year, MaintenanceRequestStatusEnum.COMPLETED);
    }

    @Override
    public List<InvoiceInformationDetailModel> calculateInvoice(Long roomId, Integer month, Integer year) {
        // if (roomId == null) throw new AppException(ErrorModelConstants.ROOM_ID_REQUIRED);
        // lấy toàn bộ thông tin phòng, dịch vụ của phòng đang được thuê
        List<InvoiceInformationModel> invoiceInformationModels = getRoomInvoiceServiceData(roomId, ContractStatusEnum.ACTIVE);
        // danh sách hóa đơn của hợp đồng active
        List<InvoiceModel> invoiceModels = getInvoiceByTime(ContractStatusEnum.ACTIVE, month, year);
        Map<Long, List<InvoiceModel>> invoiceMap = invoiceModels.stream()
                .collect(Collectors.groupingBy(InvoiceModel::getContractId));
        Map<Long, List<InvoiceInformationModel>> invoiceInformationModelMap = invoiceInformationModels.stream()
                .collect(Collectors.groupingBy(InvoiceInformationModel::getContractId));
        List<InvoiceInformationDetailModel> invoiceInformationDetailModels = new ArrayList<>();
        for (Map.Entry<Long, List<InvoiceInformationModel>> entry : invoiceInformationModelMap.entrySet()) {
            Long contractId = entry.getKey();
            List<InvoiceInformationModel> list = entry.getValue(); // toàn bộ dịch vụ trong contract
            if (!list.isEmpty()) {
                InvoiceInformationDetailModel invoiceDetail = new InvoiceInformationDetailModel();
                invoiceDetail.setInvoiceInformation(createInvoiceInfoBasic(list.get(0)));
                long totalMoney = list.get(0).getMonthlyRent();
                // Lấy danh sách dịch vụ bảo trì và tính tiền
                List<ServiceInRoomModel> serviceMaintenanceOfRoomModels = getMaintenanceOfRoom(contractId, month, year);
                if(!serviceMaintenanceOfRoomModels.isEmpty()){
                    var serviceMaintenanceOfRoomModel = serviceMaintenanceOfRoomModels.get(0);
                    var totalMoneyForMaintenaceRequest = serviceMaintenanceOfRoomModel.getPrice() !=null?serviceMaintenanceOfRoomModel.getPrice(): 0L;
                    totalMoney +=totalMoneyForMaintenaceRequest;
                }
                invoiceDetail.setServiceMaintenanceOfRooms(serviceMaintenanceOfRoomModels);

                // tính toán số tiền dịch vụ trong tháng
                List<ServiceInRoomModel> serviceOfRooms = new ArrayList<>();
                for (InvoiceInformationModel invoice : list) {
                    ServiceInRoomModel service = new ServiceInRoomModel();
                    service.setServiceName(invoice.getServiceName());
                    UtilityIndexEntity utilityIndexEntity = utilityIndexService.findByRoomIdAndMonthMeasureAndYearMeasure(invoice.getRoomId(), month, year);
                    long totalPrice = calculateServiceTotal(invoice, utilityIndexEntity);
                    service.setTotalMoney(totalPrice);
                    totalMoney += totalPrice;
                    serviceOfRooms.add(service);
                }

                invoiceDetail.setServiceOfRooms(serviceOfRooms);
                invoiceDetail.setTotalMoney(totalMoney);
                invoiceInformationDetailModels.add(invoiceDetail);

                // tạo hoặc cập nhật thông tin hóa đơn
                if(!invoiceMap.isEmpty()){ // trường hợp update
                    var invoiceDatas = invoiceMap.get(contractId);
                    if(invoiceDatas !=null && !invoiceDatas.isEmpty()){
                        // cập nhật thông tin của hóa đơn vào thông tin chi tiết;
                        var invoiceData = invoiceDatas.get(0);
                        invoiceDetail.setDetail(invoiceData);
                        InvoiceEntity entityUpdate = getById(invoiceData.getInvoiceId());
                        entityUpdate.setTotalPrice(totalMoney);
                        repo.save(entityUpdate);
                    }else {
                        invoiceDetail.setDetail(insertEntity(contractId, totalMoney, month, year));
                    }
                }else{
                    invoiceDetail.setDetail(insertEntity(contractId, totalMoney, month, year));
                }
            }
        }
        return invoiceInformationDetailModels;
    }

    @Override
    public List<InvoiceModel> getInvoiceByTime(ContractStatusEnum statusEnum, int month, int year) {
        return repo.getInvoiceByTime(statusEnum, month, year);
    }

    private InvoiceModel insertEntity(Long contractId, Long totalPrice, Integer month, Integer year){
        InvoiceEntity entityInsert = new InvoiceEntity();
        entityInsert.setContractId(contractId);
        entityInsert.setTotalPrice(totalPrice);
        entityInsert.setStatus(InvoiceStatusEnum.UNPAID);
        entityInsert.setYearCalculate(year);
        entityInsert.setMonthCalculate(month);
        var invoice = repo.save(entityInsert);
        InvoiceModel invoiceModel = new InvoiceModel(invoice.getContractId(), invoice.getId(), invoice.getCreatedAt(),
                InvoiceStatusEnum.UNPAID);
        return invoiceModel;
    }
    private InvoiceInformationModel createInvoiceInfoBasic(InvoiceInformationModel informationModel) {
        InvoiceInformationModel information = new InvoiceInformationModel();
        information.setRoomId(informationModel.getRoomId());
        information.setRoomName(informationModel.getRoomName());
        information.setContractId(informationModel.getContractId());
        information.setMonthlyRent(informationModel.getMonthlyRent());
        information.setUserId(informationModel.getUserId());
        information.setUsername(informationModel.getUsername());
        return information;
    }

    private long calculateServiceTotal(InvoiceInformationModel invoice, UtilityIndexEntity utilityIndexEntity) {
        var serviceStatus = invoice.getServiceType() != null ? invoice.getServiceType() : ServiceTypeEnum.OTHER;
        long unitPrice = invoice.getPrice();
        int quantity = invoice.getQuantity();
        return switch (serviceStatus) {
            case ELECTRIC -> utilityIndexEntity != null ? unitPrice * quantity * utilityIndexEntity.getElectricUsage() : 0;
            case WATER -> utilityIndexEntity != null ? unitPrice * quantity * utilityIndexEntity.getWaterUsage() : 0;
            default -> unitPrice * quantity;
        };
    }
    @Override
    public List<InvoiceInformationDetailModel> getMyInvoices() {
        var currentUser= userService.findMe();
        var userId = currentUser.getId();

        List<ContractResponse> contracts = contractService.findMyContract();
        List<InvoiceInformationDetailModel> invoiceInformationDetailModels = new ArrayList<>();

        for (ContractResponse contract : contracts) {
            Long roomId = contract.getRoomId();
            YearMonth start = YearMonth.from(contract.getStartDate());
            YearMonth end   = YearMonth.now();
            long monthsBetween = start.until(end, ChronoUnit.MONTHS);
            Set<YearMonth> validMonths = IntStream
                    .rangeClosed(0, (int) monthsBetween)
                    .mapToObj(start::plusMonths)
                    .collect(Collectors.toSet());
            validMonths.forEach((monthsBetween1) -> {
                int month = monthsBetween1.getMonthValue();
                int year = monthsBetween1.getYear();
                invoiceInformationDetailModels.addAll(calculateInvoice(roomId, month, year));
            });
        }

        return invoiceInformationDetailModels;
    }

    @Override
    public List<InvoiceServiceInfoDetailModel> getInvoiceInfoByTime(int month, int year) {
        List<InvoiceServiceInfoModel> invoiceServiceInfoModels = repo.getInvoiceInfoByTime(month, year);
        return invoiceServiceInfoModels.stream()
                .collect(Collectors.groupingBy(InvoiceServiceInfoModel::getInvoiceId))
                .entrySet()
                .stream()
                .map(entry -> {
                    List<InvoiceServiceInfoModel> group = entry.getValue();
                    InvoiceServiceInfoModel first = group.get(0);
                    UtilityIndexEntity utilityIndexEntity = utilityIndexService.findByRoomIdAndMonthMeasureAndYearMeasure(first.getRoomId(), month, year);
                    var electricUsed = utilityIndexEntity != null ? utilityIndexEntity.getElectricUsage() : 0L;
                    var waterUsed = utilityIndexEntity != null ? utilityIndexEntity.getElectricUsage() :0L;
                    List<ServiceInfoModel> services = group.stream().map(item -> {
                        ServiceInfoModel service = new ServiceInfoModel();
                        service.setServiceId(item.getServiceId());
                        service.setNameService(item.getNameService());
                        if(Objects.equals(item.getServiceType(), ServiceTypeEnum.ELECTRIC.name())){
                            BigDecimal totalPrice = item.getPriceService()
                                    .multiply(BigDecimal.valueOf(electricUsed));
                            service.setPriceService(totalPrice);
                        }else if(Objects.equals(item.getServiceType(), ServiceTypeEnum.WATER.name())){
                            BigDecimal totalPrice = item.getPriceService()
                                    .multiply(BigDecimal.valueOf(waterUsed));
                            service.setPriceService(totalPrice);
                        }else{
                            service.setQuantityService(item.getQuantityService());
                            service.setPriceService(item.getPriceService());
                        }
                        service.setQuantityService(item.getQuantityService());
                        service.setServiceType(item.getServiceType());
                        return service;
                    }).collect(Collectors.toList());

                    InvoiceServiceInfoDetailModel detail = new InvoiceServiceInfoDetailModel();
                    detail.setInvoiceId(first.getInvoiceId());
                    detail.setInvoiceCreated(first.getInvoiceCreated());
                    detail.setMonthCal(first.getMonthCal());
                    detail.setYearCal(first.getYearCal());
                    detail.setTotalInvoice(first.getTotalInvoice());
                    detail.setFullName(first.getFullName());
                    detail.setRoomId(first.getRoomId());
                    detail.setServiceInfos(services);
                    detail.setStatus(first.getStatus());
                    detail.setRoomPrice(first.getRoomPrice() != null ? first.getRoomPrice() : BigDecimal.ZERO);
                    return detail;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @Modifying
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void confirmPayment(Long id) throws AppException {
        var invoice = getById(id);
        if (invoice == null) {
            throw new AppException(ErrorModelConstants.INVOICE_NOT_FOUND);
        }
        if (!Objects.equals(invoice.getStatus(), InvoiceStatusEnum.UNPAID)) {
            throw new AppException(ErrorModelConstants.INVOICE_IS_PAID);
        }
        invoice.setStatus(InvoiceStatusEnum.PAID);
        invoice.setPaymentDate(LocalDateTime.now());
        repo.save(invoice);
    }
}
