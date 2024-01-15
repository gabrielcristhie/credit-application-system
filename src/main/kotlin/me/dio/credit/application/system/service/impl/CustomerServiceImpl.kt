package me.dio.credit.application.system.service.impl

import me.dio.credit.application.system.model.Customer
import me.dio.credit.application.system.repository.CustomerRepository
import me.dio.credit.application.system.service.ICustomerService
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository
): ICustomerService {
    override fun save(customer: Customer): Customer =
       this.customerRepository.save(customer)

    override fun findById(id: Long): Customer =
        this.customerRepository.findById(id).orElseThrow {
            throw BusinessException("Id $id not found")
    }

    override fun delete(id: Long) =
        this.customerRepository.deleteById(id)
}