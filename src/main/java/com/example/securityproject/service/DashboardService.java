package com.example.securityproject.service;

import com.example.securityproject.components.UserContext;
import com.example.securityproject.dto.PaginatedResponse;
import com.example.securityproject.entities.Client;
import com.example.securityproject.entities.Order;
import com.example.securityproject.exception.SuppressedStackTraceException;
import com.example.securityproject.repository.JpaClientRepo;
import com.example.securityproject.repository.JpaOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DashboardService {
    @Autowired
    UserContext userContext;
    @Autowired
    JpaClientRepo jpaClientRepo;
    @Autowired
    UUIDService uuidService;
    @Autowired
    JpaOrderRepo jpaOrderRepo;

    public PaginatedResponse<Order> getTrackingStatuses(int page, int pageSize) {
        try {
            UUID uuid = userContext.getId();
            Client client = jpaClientRepo.getClientByIdClient(uuidService.uuidToBytes(uuid));
            byte[] clientId = client.getIdClient();
            List<Order> orders = jpaOrderRepo.findAllByClientId(clientId);

            int total = orders.size();

            if (pageSize <= 0) pageSize = 10;
            if (page <= 0) page = 1;

            int totalPages = (int) Math.ceil((double) total / pageSize);
            if (totalPages == 0) totalPages = 1;

            if (page > totalPages) {
                page = totalPages;
            }

            int fromIndex = (page - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, total);

            List<Order> pagedOrders = fromIndex >= total ? List.of() : orders.subList(fromIndex, toIndex);

            PaginatedResponse.Meta meta = new PaginatedResponse.Meta(page, pageSize, total);
            return new PaginatedResponse<>(pagedOrders, meta);

        } catch (Exception e) {
            throw new SuppressedStackTraceException("Error occurred " + e.getMessage());
        }
    }


}
