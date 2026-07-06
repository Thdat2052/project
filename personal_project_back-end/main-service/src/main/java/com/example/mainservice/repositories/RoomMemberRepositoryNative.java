package com.example.mainservice.repositories;


import com.example.common.service.enums.ContractStatusEnum;
import com.example.common.service.models.UserModel;
import com.example.mainservice.models.InvoiceInformationModel;
import com.example.mainservice.models.ServiceInRoomModel;
import com.example.mainservice.models.responses.RevenueStatResponse;

import java.util.List;


public interface RoomMemberRepositoryNative {
   List<UserModel> findByContractId(Long contractId);
}