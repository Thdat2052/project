package com.example.common.service.models.responses;

import com.example.common.service.models.ContractModel;
import com.example.common.service.models.ServiceModel;
import com.example.common.service.models.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractResponse extends ContractModel {
    String roomName;
    String roomNumber;
    String fullName;
    List<ServiceModel> services= new ArrayList<>();
    List<UserModel> members= new ArrayList<>();
}
